package com.epam.brest.courses;

import java.math.BigDecimal;
import java.io.FileNotFoundException;
import org.json.simple.parser.ParseException;


public class DeliveryCost {


    public static void main(String[] args) {

        try{


        BigDecimal distance = ScannerValue.scanValue("distance");
        BigDecimal weight = ScannerValue.scanValue("weight");

        ValueItem valueItem = new ValueItem();
        valueItem.setDistance(distance);
        valueItem.setWeight(weight);

        BigDecimal tarifDistance = CalculatorTarif.calculateTarif(valueItem.getDistance(), "distance");
        BigDecimal tarifWeight = CalculatorTarif.calculateTarif(valueItem.getWeight(), "weight");



        CalculatorImpl calculator = new CalculatorImpl();

        System.out.println(calculator.calculate(valueItem.getDistance(),tarifDistance,tarifWeight));


    }
        catch (FileNotFoundException ex){
            System.out.println (ex.getMessage());
        }
            catch (ParseException ex) {
                System.out.println (ex.getMessage());
        }
            catch (Exception ex){
                System.out.println (ex.getMessage());
        }
    }

}
