package com.excilys.cli;

import com.excilys.model.Computer;
import com.excilys.model.OrderBy;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.service.ComputerService;

public class ComputersMenu extends Menu {

    private static final int SIZE = 10;

    private Page<Computer> page;

    private ComputerService service;

    /* Singleton */

    private static ComputersMenu instance;

    /**
     * Constructs a ComputersMenu.
     */
    private ComputersMenu() {
        super(" Computers Menu");
        service = new ComputerService();
        page = service.findPage(new PageRequest(0, SIZE, "", OrderBy.DEFAULT, false));
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
                    if (service.count() >= page.getNumber() * page.getSize()) {
                        page = service
                                .findPage(new PageRequest(page.getNumber() + 1, SIZE, "", OrderBy.DEFAULT, false));
                    } else {
                        System.err.println("No more computers");
                    }
                    isValid = true;
                    break;
                case 1:
                    if (page.getNumber() > 0) {
                        page = service
                                .findPage(new PageRequest(page.getNumber() - 1, SIZE, "", OrderBy.DEFAULT, false));
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
