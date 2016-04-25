package com.excilys.ui;

public class MainMenu extends Menu {

    /* Singleton */

    private static MainMenu instance;

    /**
     * Constructs a MainMenu.
     */
    private MainMenu() {
        super(" Main Menu");
    }

    /**
     * @return The unique instance of a MainMenu.
     */
    public static MainMenu getInstance() {
        if (instance == null) {
            synchronized (MainMenu.class) {
                if (instance == null) {
                    instance = new MainMenu();
                }
            }
        }
        return instance;
    }

    /* Menu */

    @Override
    protected void printContent() {
    }

    @Override
    protected void printOptions() {
        System.out.println(" 0 - Print computers");
        System.out.println(" 1 - Print companies");
        System.out.println(" 2 - Quit");
    }

    @Override
    protected void selectOption() {

        boolean isValid = false;
        while (!isValid) {
            try {
                switch (Integer.parseInt(scanner.nextLine())) {
                case 0:
                    MenuNavigator.getInstance().setNextMenu(ComputersMenu.getInstance());
                    isValid = true;
                    break;
                case 1:
                    MenuNavigator.getInstance().setNextMenu(ComponentsMenu.getInstance());
                    isValid = true;
                    break;
                case 2:
                    System.exit(0);
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
