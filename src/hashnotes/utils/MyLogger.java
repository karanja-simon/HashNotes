/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashnotes.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {

    private static final Logger logger = Logger.getLogger("MyLog");
    private static FileHandler fh;

    public static void log(Class myClass, String myMsg) {

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("error_logs/MyLogFile.log", true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages   
            //logger.log(Level.WARNING, "My first log");
            logger.logp(Level.WARNING, myClass.toString(), null, myMsg);

        } catch (SecurityException | IOException e) {
            //e.printStackTrace();
            System.out.println("error: "+e);
        }

    }

}
