package com.excilys.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.AbstractService;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

public class ComputersMenu extends Menu {

    private static final int SIZE = 10;

    private Page<Computer> page;

    private AbstractService<Computer> service;

    private Logger logger = LoggerFactory.getLogger(ComputersMenu.class);

    /* Singleton */

    private static ComputersMenu instance;

    /**
     * Constructs a ComputersMenu.
     */
    private ComputersMenu() {
        super(" Computers Menu");
        service = new SimpleServices<>(ComputerDAO.getInstance());
        try {
            page = service.findPage(0, SIZE);
        } catch (ServiceException e) {
            logger.error("[Catch] <ServiceException>");
            page = new Page<Computer>(0, SIZE, 0, null);
        }
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
        if (page.getContent() != null) {
            for (Computer computer : page.getContent()) {
                System.out.println(computer);
            }
        } else {
            System.err.println("Computers can not be printed...");
        }
    }

    @Override
    protected void printOptions() {
        System.out.println("Page " + page.getNumber() + " :");
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
                    try {
                        if (service.count() >= page.getNumber() * page.getSize()) {
                            page = service.findPage(page.getNumber() + 1, SIZE);
                        } else {
                            System.err.println("No more computers");
                        }
                    } catch (ServiceException e) {
                        logger.error("[Catch] <ServiceException>");
                        page = new Page<Computer>(page.getNumber() + 1, page.getMaxNumber(), SIZE, null);
                        System.err.println("service unavailable");
                    }
                    isValid = true;
                    break;
                case 1:
                    if (page.getNumber() > 0) {
                        try {
                            page = service.findPage(page.getNumber() - 1, SIZE);
                        } catch (ServiceException e) {
                            logger.error("[Catch] <ServiceException>");
                            page = new Page<Computer>(page.getNumber() - 1, page.getMaxNumber(), SIZE, null);
                            System.err.println("service unavailable");
                        }
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
