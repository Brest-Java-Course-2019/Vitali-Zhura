package com.epam.brest.courses;

import java.io.*;
import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.stream.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class CalculatorTarif {


    static public BigDecimal calculateTarif(BigDecimal value, String description) throws ParseException{


        JSONParser parser = new JSONParser();
        InputStreamReader read = null;
        BigDecimal tarif= BigDecimal.valueOf(0.0);

        try {

            read = new InputStreamReader(new FileInputStream("./src/main/resources/TarifDistance.json"), "UTF-8");
            Object obj = parser.parse(read);
            if (obj == null) {
                throw new FileNotFoundException("File with tarif not found.");
            }
            JSONObject object = (JSONObject) obj;
            JSONObject objectValue = (JSONObject) object.get(description);


            Set<String> setKeys = objectValue.keySet();
            SortedSet<Double> sortedKeys = new TreeSet(setKeys.stream().map(s -> Double.parseDouble(s))
                    .collect(Collectors.toSet()));

            double firstValue = sortedKeys.first();
            for (double i : sortedKeys) {
                if (firstValue >= value.doubleValue()) {
                    break;
                }
                firstValue = i;
            }
            tarif = BigDecimal.valueOf((Double) objectValue.get(String.valueOf((int) firstValue)));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
            if( read != null) read.close();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return tarif;
    }

}
