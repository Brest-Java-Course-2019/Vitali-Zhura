package com.epam.brest.courses;

import java.math.BigDecimal;
import org.json.simple.parser.ParseException;


public class DeliveryCost {
    private static final String DISTANCE = "distance";
    private static final String WEIGHT = "weight";

    public static void main(String[] args) {

        try{


        BigDecimal distance = ScannerValue.scanValue(DISTANCE);
        BigDecimal weight = ScannerValue.scanValue(WEIGHT);

        ValueItem valueItem = new ValueItem();
        valueItem.setDistance(distance);
        valueItem.setWeight(weight);

        BigDecimal tarifDistance = CalculatorTarif.calculateTarif(valueItem.getDistance(), DISTANCE);
        BigDecimal tarifWeight = CalculatorTarif.calculateTarif(valueItem.getWeight(), WEIGHT);



        CalculatorImpl calculator = new CalculatorImpl();

        System.out.println(calculator.calculate(valueItem.getDistance(),tarifDistance,tarifWeight));

    }

            catch (ParseException ex) {
                System.out.println (ex.getMessage());
        }
            catch (Exception ex){
                System.out.println (ex.getMessage());
        }
    }

}
