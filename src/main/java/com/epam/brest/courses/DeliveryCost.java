package com.epam.brest.courses;

import java.math.BigDecimal;
import java.io.*;
import org.json.simple.parser.ParseException;


public class DeliveryCost {


    public static void main(String[] args) {

        try{

        ValueItem valueitem = new ValueItem();

        double distance, weight;

        ScannerValue  scannervalue = new  ScannerValue();

        distance = scannervalue.scanValue("distance");
        weight = scannervalue.scanValue("weight");


        CalculatorImpl calculator = new CalculatorImpl();

        valueitem.setDistance(BigDecimal.valueOf(distance));
        valueitem.setWeight(BigDecimal.valueOf(weight));

        System.out.println(calculator.Calculation(valueitem.getDistance(), valueitem.getWeight()));
    }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
            catch (ParseException ex) {
            ex.printStackTrace();
        }
            catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
