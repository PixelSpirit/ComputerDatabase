package com.excilys.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBInitialiser {

    private static final String COMMAND = "./dbTestInit.sh";

    static final Logger LOGGER = LoggerFactory.getLogger(DBInitialiser.class);

    public static void run() throws IOException, InterruptedException {
        LOGGER.info("[DBInitialisation] run()");

        int res;

        Process p = Runtime.getRuntime().exec(COMMAND);
        res = p.waitFor();
        LOGGER.info("[DBInitialiser] exec " + COMMAND + " : " + res);
    }
}
