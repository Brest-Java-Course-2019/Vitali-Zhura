package com.epam.brest.courses;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class CalculatorImpl implements Calculator {


    public BigDecimal calculate(BigDecimal distance, BigDecimal tarifDistance, BigDecimal tarifWeight) {


           return distance.multiply(tarifDistance.multiply(tarifWeight)).setScale(2, RoundingMode.HALF_UP);
    }
    }

