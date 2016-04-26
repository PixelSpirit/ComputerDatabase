package com.excilys.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

public class ComputersMenu extends Menu {

    private int size;
    private int pageNumber;

    private Logger logger = LoggerFactory.getLogger(ComputersMenu.class);

    /* Singleton */

    private static ComputersMenu instance;

    /**
     * Constructs a ComputersMenu.
     */
    private ComputersMenu() {
        super(" Computers Menu");
        size = 10;
        pageNumber = 0;
    }

    /**
     * @return The unique instance of a ComputersMenu.
     */
    public static ComputersMenu getInstance() {
        if (instance == null) {
            synchronized (ComputersMenu.class) {
                if (instance == null) {
                    instance = new ComputersMenu();
                }
            }
        }
        return instance;
    }

    /* Menu */

    @Override
    protected void printContent() {
        try {
            List<Computer> cpts = new SimpleServices<>(ComputerDAO.getInstance()).findSeveral(size, pageNumber * size);
            for (Computer computer : cpts) {
                System.out.println(computer);
            }
        } catch (ServiceException e) {
            logger.error("[Catch] <ServiceException>");
            System.err.println("Computers can not be printed...");
        }
    }

    @Override
    protected void printOptions() {
        System.out.println("Page " + pageNumber + " :");
        System.out.println(" 0 - Print next computers");
        System.out.println(" 1 - Print previous computers");
        System.out.println(" 2 - Add a computer");
        System.out.println(" 3 - Update a computer");
        System.out.println(" 4 - Return to Main Menu");
    }

    @Override
    protected void selectOption() {

        boolean isValid = false;
        while (!isValid) {
            try {
                switch (Integer.parseInt(scanner.nextLine())) {
                case 0:
                    // TODO check that we are not at the limit !
                    pageNumber++;
                    isValid = true;
                    break;
                case 1:
                    if (pageNumber > 0) {
                        pageNumber--;
                    }
                    isValid = true;
                    break;
                case 2:
                    MenuNavigator.getInstance().setNextMenu(ComputerCreationMenu.getInstance());
                    isValid = true;
                    break;
                case 3:
                    // TODO
                    System.err.println("Not implemented yet !");
                    isValid = true;
                    break;
                case 4:
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
