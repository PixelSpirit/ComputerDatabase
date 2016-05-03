package com.excilys.cli;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.CompanyDAO;
import com.excilys.service.AbstractService;
import com.excilys.service.SimpleServices;

public class ComponentsMenu extends Menu {

    private static final int SIZE = 10;

    private Page<Company> page;

    private AbstractService<Company> service;

    /* Singleton */

    private static ComponentsMenu instance;

    /**
     * Constructs a ComponentsMenu.
     */
    private ComponentsMenu() {
        super(" Components Menu");
        service = new SimpleServices<>(CompanyDAO.getInstance());
        page = service.findPage(new PageRequest(0, SIZE, "", "", null, false));
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
                    if (service.count() >= page.getNumber() * page.getSize()) {
                        page = service.findPage(new PageRequest(page.getNumber() + 1, SIZE, "", "", null, false));
                    } else {
                        System.err.println("No more computers");
                    }
                    isValid = true;
                    break;
                case 1:
                    if (page.getNumber() > 0) {
                        page = service.findPage(new PageRequest(page.getNumber() - 1, SIZE, "", "", null, false));
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
