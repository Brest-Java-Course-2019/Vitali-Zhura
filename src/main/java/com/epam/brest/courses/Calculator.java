package com.epam.brest.courses;

import java.math.BigDecimal;
import org.json.simple.parser.ParseException;
import java.io.*;

public interface Calculator {
    BigDecimal Calculation (BigDecimal weight, BigDecimal distance)throws IOException, ParseException;
}
