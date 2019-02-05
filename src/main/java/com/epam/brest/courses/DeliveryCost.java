package com.epam.brest.courses;

import java.math.BigDecimal;
import java.io.*;
import org.json.simple.parser.ParseException;


public class DeliveryCost {


    public static void main(String[] args) {

        try{


        double distance = ScannerValue.scanValue("distance");
        double weight = ScannerValue.scanValue("weight");


        double tarifdistance = CalculatorTarif.calculateTarif(distance, "distance");
        double tarifweight = CalculatorTarif.calculateTarif(weight, "weight");

        ValueItem valueitem = new ValueItem();
        valueitem.setDistance(BigDecimal.valueOf(distance));


        CalculatorImpl calculator = new CalculatorImpl();

        System.out.println(calculator.Calculate(valueitem.getDistance(),tarifdistance,tarifweight));


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
