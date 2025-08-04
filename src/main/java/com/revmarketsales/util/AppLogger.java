package com.revmarketsales.util;

import java.io.IOException;
import java.util.logging.*;

public class AppLogger {
    private static final Logger logger = Logger.getLogger("RevMarketSalesLogger");
    
    static  {
        try {
            LogManager.getLogManager().reset();

            //create file handler
            FileHandler fileHandler = new FileHandler("logs/application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);

            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("Failed to initialize logger file handler: "+ e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
