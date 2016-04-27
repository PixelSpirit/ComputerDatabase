package com.excilys.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.service.ServiceException;
import com.excilys.service.SimpleServices;

public class ComponentsMenu extends Menu {

    private int size;
    private int pageNumber;

    private Logger logger = LoggerFactory.getLogger(ComponentsMenu.class);

    /* Singleton */

    private static ComponentsMenu instance;

    /**
     * Constructs a ComponentsMenu.
     */
    private ComponentsMenu() {
        super(" Components Menu");
        size = 10;
        pageNumber = 0;
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
        try {
            List<Company> cpns = new SimpleServices<>(CompanyDAO.getInstance()).findSeveral(size, pageNumber * size);
            for (Company company : cpns) {
                System.out.println(company);
            }
        } catch (ServiceException e) {
            logger.error("[Catch] <ServiceException>");
            System.err.println("Companies can not be printed...");
        }
    }

    @Override
    protected void printOptions() {
        System.out.println("Page " + pageNumber + " :");
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
                        if (new SimpleServices<>(CompanyDAO.getInstance()).count() >= pageNumber * size) {
                            pageNumber++;
                        } else {
                            System.err.println("No more companies");
                        }
                    } catch (ServiceException e) {
                        logger.error("[Catch] <ServiceException>");
                        System.err.println("service unavailable");
                    }
                case 1:
                    if (pageNumber > 0) {
                        pageNumber--;
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
