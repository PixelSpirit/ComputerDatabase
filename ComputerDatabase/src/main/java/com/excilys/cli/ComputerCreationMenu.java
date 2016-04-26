package com.excilys.cli;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

public class ComputerCreationMenu extends Menu {

    private Logger logger = LoggerFactory.getLogger(ComputerCreationMenu.class);

    /* Singleton */

    private static ComputerCreationMenu instance;

    /**
     * Constructs a ComputerCreationMenu.
     */
    private ComputerCreationMenu() {
        super(" Computer Creation");
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
        System.out.print("Enter Computer name > ");
        return scanner.nextLine();
    }

    /**
     * Ask to the user to insert a date and returns it.
     * @param title The title of the date
     * @return A date
     */
    private static LocalDateTime createDate(String title) {
        LocalDateTime date = null;
        while (date == null) {
            try {
                System.out.print("Enter the " + title + " date > ");
                String format = scanner.nextLine();
                date = LocalDateTime.parse(format, Computer.formatter);
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
    private static Company createCompany() throws ServiceException {
        Company company = null;
        while (company == null) {
            try {
                System.out.print("Enter Company's id > ");
                Long id = Long.parseLong(scanner.nextLine());
                company = new SimpleServices<>(CompanyDAO.getInstance()).find(id);
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
    private static Computer createComputer() throws ServiceException {
        String name = createName();
        LocalDateTime introduced;
        LocalDateTime discontinued;
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
        return new Computer.Builder().name(name).introduced(introduced).discontinued(discontinued).company(company)
                .build();
    }

    @Override
    protected void printContent() {
        try {
            new SimpleServices<>(ComputerDAO.getInstance()).insert(createComputer());
        } catch (ServiceException e) {
            logger.error("[Catch] <ServiceException>");
            System.err.println("The computer was not added...");
        }
        System.out.println("Computer was succefully added");
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
