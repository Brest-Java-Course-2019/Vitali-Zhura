package com.epam.brest.courses;

import java.io.*;
import java.util.*;
import java.util.Set;
import java.util.stream.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CalculatorTarif {

    static public double calculateTarif(double value, String description) throws IOException, ParseException{

        double tarif=0.0;
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("./src/main/resources/TarifDistance.json"));
        JSONObject object = (JSONObject) obj;
        JSONObject object1 = (JSONObject) object.get(description);


        Set <String> a = object1.keySet();
        SortedSet <Double> t = new TreeSet(a.stream().map(s -> Double.parseDouble(s))
                .collect(Collectors.toSet()));

        Double min = 0.0;
        for (double i: t) {
            if (value >=min && value < i) {
                tarif =  (Double)object1.get(String.valueOf((int)i));
                break;
            }
            min = i;
        }
        return tarif;

    }

}
