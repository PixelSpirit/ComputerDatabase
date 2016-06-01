package com.excilys.utils;

import java.io.IOException;

public class DBInitialiser {

    public static String COMMAND = "mysql -uroot < ";
    public static String SCHEMA = "spec-cdb/config/db/1-SCHEMA.sql";
    public static String PRIVILEGES = "spec-cdb/config/db/2-PRIVILEGES.sql";
    public static String ENTRIES = "spec-cdb/config/db/3-ENTRIES.sql";

    public static void run() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(COMMAND + SCHEMA);
        p.waitFor();

        p = Runtime.getRuntime().exec(COMMAND + PRIVILEGES);
        p.waitFor();

        p = Runtime.getRuntime().exec(COMMAND + ENTRIES);
        p.waitFor();
    }
}
