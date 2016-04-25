package com.excilys.cli;

import java.util.Scanner;

public abstract class Menu {

    protected String title = null;
    protected static Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a Menu.
     * @param title The title of the menu.
     */
    public Menu(String title) {
        this.title = title;
    }

    /**
     * Runs the Menu.
     */
    public void runMenu() {
        printTitle();
        System.out.println();
        printContent();
        System.out.println();
        printOptions();
        System.out.println();
        System.out.print("% > ");
        selectOption();
    }

    /**
     * Prints the title of the menu.
     */
    private void printTitle() {
        System.out.println("***********************");
        System.out.println(title);
        System.out.println("***********************");
    }

    /**
     * Prints an error message and a new prompt.
     */
    protected void printInvalidCommand() {
        System.err.println("Invalid command !");
        System.out.print("% > ");
    }

    /**
     * Prints the content of the menu.
     */
    protected abstract void printContent();

    /**
     * Prints the options of the menu.
     */
    protected abstract void printOptions();

    /**
     * Requests a line entry to select an option.
     */
    protected abstract void selectOption();

}
