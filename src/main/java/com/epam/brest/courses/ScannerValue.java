package com.epam.brest.courses;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScannerValue {

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Logger LOGGER = LogManager.getLogger();

    public static BigDecimal scanValue (String valueDescription) {

            Scanner scan = new Scanner(System.in, UTF_8);
            BigDecimal scannerValue;

            do {
                System.out.println("Enter " + valueDescription + ":");
                while (!scan.hasNextBigDecimal()) {
                    LOGGER.info("Incorrect value:");
                    scan.next();
                }
                scannerValue = scan.nextBigDecimal();
                if (scannerValue.compareTo(BigDecimal.ZERO) <= 0) {
                    LOGGER.info("Incorrect value: {}", scannerValue);
                }

            } while (scannerValue.compareTo(BigDecimal.ZERO) <= 0);
            return scannerValue;

    }


}
