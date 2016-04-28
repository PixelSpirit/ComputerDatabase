package com.excilys.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;
import com.excilys.service.AbstractService;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

public class ComponentsMenu extends Menu {

    private static final int SIZE = 10;

    private Page<Company> page;

    private AbstractService<Company> service;

    private Logger logger = LoggerFactory.getLogger(ComponentsMenu.class);

    /* Singleton */

    private static ComponentsMenu instance;

    /**
     * Constructs a ComponentsMenu.
     */
    private ComponentsMenu() {
        super(" Components Menu");
        service = new SimpleServices<>(CompanyDAO.getInstance());
        try {
            page = service.findPage(0, SIZE);
        } catch (ServiceException e) {
            logger.error("[Catch] <ServiceException>");
            page = new Page<Company>(0, SIZE, 0, null);
        }
    }

    /**
     * @return The unique instance of a ComponentsMenu.
     */
    public static ComponentsMenu getInstance() {
        if (instance == null) {
            synchronized (ComponentsMenu.class) {
                if (instance == null) {
                    instance = new ComponentsMenu();
                }
            }
        }
        return instance;
    }

    /* Menu */

    @Override
    protected void printContent() {
        if (page.getContent() != null) {
            for (Company company : page.getContent()) {
                System.out.println(company);
            }
        } else {
            System.err.println("Computers can not be printed...");
        }
    }

    @Override
    protected void printOptions() {
        System.out.println("Page " + page.getNumber() + " :");
        System.out.println(" 0 - Print next companies");
        System.out.println(" 1 - Print previous companies");
        System.out.println(" 2 - Return to Main Menu");
    }

    @Override
    protected void selectOption() {

        boolean isValid = false;
        while (!isValid) {
            try {
                switch (scanner.nextInt()) {
                case 0:
                    try {
                        if (service.count() >= page.getNumber() * page.getSize()) {
                            page = service.findPage(page.getNumber() + 1, SIZE);
                        } else {
                            System.err.println("No more computers");
                        }
                    } catch (ServiceException e) {
                        logger.error("[Catch] <ServiceException>");
                        page = new Page<Company>(page.getNumber() + 1, page.getMaxNumber(), SIZE, null);
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
                            page = new Page<Company>(page.getNumber() - 1, page.getMaxNumber(), SIZE, null);
                            System.err.println("service unavailable");
                        }
                    }
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
