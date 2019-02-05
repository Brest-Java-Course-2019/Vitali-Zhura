package com.epam.brest.courses;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class CalculatorImpl implements Calculator {


    public BigDecimal Calculate(BigDecimal distance, double tarifdistance, double tarifweight) {


           return distance.multiply( BigDecimal.valueOf(tarifdistance*tarifweight)).setScale(2, RoundingMode.HALF_UP);
    }
    }

