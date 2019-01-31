package com.epam.brest.courses;


import java.math.BigDecimal;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CalculatorImpl implements Calculator {


    public BigDecimal Calculation(BigDecimal distance, BigDecimal weight) throws IOException, ParseException{


    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader("./src/main/resources/Tarif.json"));
    JSONObject jsonObject = (JSONObject) obj;
    int tarifDistance;
    if (distance.compareTo(BigDecimal.ZERO) > 0 && distance.compareTo(new BigDecimal(5)) <=0)
       tarifDistance = ((Long) jsonObject.get("1-5")).intValue();
    else if (distance.compareTo(new BigDecimal(5)) > 0 && distance.compareTo(new BigDecimal(20)) <=0)
       tarifDistance = ((Long) jsonObject.get("6-20")).intValue();
    else
        tarifDistance = ((Long) jsonObject.get("bolshe 20")).intValue();;

    int tarifWeight;

     if (weight.compareTo(BigDecimal.ZERO) > 0 && weight.compareTo(new BigDecimal(10)) <= 0)
         tarifWeight = ((Long) jsonObject.get("1-10")).intValue();
     else if (weight.compareTo(new BigDecimal(10)) > 0 && weight.compareTo(new BigDecimal(30)) <= 0)
          tarifWeight = ((Long) jsonObject.get("11-30")).intValue();
     else
          tarifWeight = ((Long) jsonObject.get("bolshe 30")).intValue();

            return distance.multiply( BigDecimal.valueOf(tarifDistance+tarifWeight));

    }
    }

