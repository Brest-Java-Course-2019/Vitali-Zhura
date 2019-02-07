package com.epam.brest.courses;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.stream.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class CalculatorTarif {
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    static public BigDecimal calculateTarif(BigDecimal value, String description) throws IOException, ParseException{


        JSONParser parser = new JSONParser();
        FileReader read = null;
        BigDecimal tarif= BigDecimal.valueOf(0.0);

        try {

            read = new FileReader("./src/main/resources/TarifDistance.json",UTF_8);
            Object obj = parser.parse(read);
            if (obj == null) {
                throw new FileNotFoundException("File with prices per km not found.");
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
