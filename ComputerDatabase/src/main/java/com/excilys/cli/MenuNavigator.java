package com.excilys.cli;

public class MenuNavigator {

    private Menu nextMenu = null;

    /* Singleton */

    private static MenuNavigator instance;

    /**
     * Constructs a MenuNavigator.
     */
    private MenuNavigator() {
        nextMenu = MainMenu.getInstance();
    }

    /**
     * @return The unique instance of a MenuNavigator.
     */
    public static MenuNavigator getInstance() {
        if (instance == null) {
            synchronized (MenuNavigator.class) {
                if (instance == null) {
                    instance = new MenuNavigator();
                }
            }
        }
        return instance;
    }

    /* MenuNavigator */

    public void setNextMenu(Menu menu) {
        this.nextMenu = menu;
    }

    /**
     * Runs the menuNavigation.
     */
    public void run() {
        while (true) {
            nextMenu.runMenu();
        }
    }

}
