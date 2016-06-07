package com.excilys.cli;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

public class ComputerCreationMenu extends Menu {

    /* Singleton */

    private static ComputerCreationMenu instance;

    /**
     * Constructs a ComputerCreationMenu.
     */
    private ComputerCreationMenu() {
        super(" computer Creation");
    }

    /**
     * @return The unique instance of a ComputerCreationMenu.
     */
    public static ComputerCreationMenu getInstance() {
        if (instance == null) {
            synchronized (ComputerCreationMenu.class) {
                if (instance == null) {
                    instance = new ComputerCreationMenu();
                }
            }
        }
        return instance;
    }

    /* Menu */

    /**
     * Ask to the user to insert a name and returns it.
     * @return the name of the computer.
     */
    private static String createName() {
        System.out.print("Enter computer name > ");
        return scanner.nextLine();
    }

    /**
     * Ask to the user to insert a date and returns it.
     * @param title The title of the date
     * @return A date
     */
    private static LocalDate createDate(String title) {
        LocalDate date = null;
        while (date == null) {
            try {
                System.out.print("Enter the " + title + " date > ");
                String format = scanner.nextLine();
                date = LocalDateTime.parse(format, Computer.formatter).toLocalDate();
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format");
            }
        }
        return date;
    }

    /**
     * Ask to the user to insert a company id and returns the company.
     * @return a fresh company
     * @throws ServiceException if an error occurs in services
     */
    private static Company createCompany() {
        Company company = null;
        while (company == null) {
            try {
                System.out.print("Enter Company's id > ");
                Long id = Long.parseLong(scanner.nextLine());
                company = new CompanyService().find(id);
                // TODO : Check if the ID is valid !
            } catch (InputMismatchException e) {
                System.err.println("Invalid date format");
            }
        }
        return company;
    }

    /**
     * Ask to the user to create a computer and returns it.
     * @return The created computer
     * @throws ServiceException if an error occurs in services
     */
    private static Computer createComputer() {
        String name = createName();
        LocalDate introduced;
        LocalDate discontinued;
        boolean firstIteration = true;
        do {
            introduced = createDate("introducing");
            discontinued = createDate("discontinuing");
            if (!firstIteration) {
                System.err.println("Introducing data must be before the discountinuing date !");
            }
            firstIteration = false;
        } while (introduced.isAfter(discontinued));
        Company company = createCompany();
        return new Computer.Builder(name).introduced(introduced).discontinued(discontinued).company(company).build();
    }

    @Override
    protected void printContent() {
        new ComputerService().insert(createComputer());
        System.out.println("computer was succefully added");
    }

    @Override
    protected void printOptions() {
        System.out.println(" 1 - Return to Computers Menu");
        System.out.println(" 2 - Return to Main Menu");
    }

    @Override
    protected void selectOption() {

        boolean isValid = false;
        while (!isValid) {
            try {
                switch (Integer.parseInt(scanner.nextLine())) {
                case 1:
                    MenuNavigator.getInstance().setNextMenu(ComputersMenu.getInstance());
                    isValid = true;
                    break;
                case 2:
                    MenuNavigator.getInstance().setNextMenu(MainMenu.getInstance());
                    isValid = true;
                    break;
                default:
                    printInvalidCommand();
                }
            } catch (NumberFormatException e) {
                printInvalidCommand();
            }
        }
    }

}
