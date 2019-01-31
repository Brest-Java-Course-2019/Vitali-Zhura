package com.epam.brest.courses;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScannerValue {

    private static final Logger LOGGER = LogManager.getLogger();

    public double scanValue (String valuedescription){
        Scanner scan = new Scanner(System.in);
        double scannervalue;

        do {
            System.out.println("Enter "+valuedescription);
            while (!scan.hasNextDouble()) { LOGGER.info("Incorrect value:");
                scan.next();
            }
            scannervalue = scan.nextDouble();
            if (scannervalue <=0){
                LOGGER.info("Incorrect value: {}", scannervalue);
            }

        } while (scannervalue<= 0);
        return scannervalue;
    }

}
