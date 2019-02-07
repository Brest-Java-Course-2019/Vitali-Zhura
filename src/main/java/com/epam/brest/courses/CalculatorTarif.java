package com.epam.brest.courses;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.stream.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class CalculatorTarif {

    static public BigDecimal calculateTarif(BigDecimal value, String description) throws IOException, ParseException{


        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("./src/main/resources/TarifDistance.json"));
        if (obj == null) {
            throw new FileNotFoundException("File with prices per km not found.");
        }
        JSONObject object = (JSONObject) obj;
        JSONObject objectValue = (JSONObject) object.get(description);


        Set <String> setKeys= objectValue.keySet();
        SortedSet <Double> sortedKeys = new TreeSet(setKeys.stream().map(s -> Double.parseDouble(s))
                .collect(Collectors.toSet()));

        double firstValue = sortedKeys.first();
        for (double i: sortedKeys) {
            if (firstValue >=value.doubleValue()) {
                break;
            }
            firstValue = i;
        }
        BigDecimal tarif =  BigDecimal.valueOf((Double)objectValue.get(String.valueOf((int) firstValue)));
        return tarif;

    }

}
