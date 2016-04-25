package com.excilys.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * The main functions.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        logger.info(" --- Starting new Client --- ");
        MenuNavigator.getInstance().run();
    }

}
