package org.mql.crowddonating;

import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Test {
    public static void main(String[] args) throws Exception {
    	//File file = new ClassPathResource("paypal.json").getFile();
 //       BufferedReader reader = new BufferedReader(new FileReader(file));
 //       String line;
   //     StringBuilder jsonString = new StringBuilder();
     //   while ((line = reader.readLine()) != null) {
       //     jsonString.append(line);
       // }
       // JSONObject json = new JSONObject(jsonString.toString());

       // String custom = json.getJSONArray("transactions")
        //        .getJSONObject(0)
        //        .getString("custom");
       // json = json.getJSONArray("transactions")
         //       .getJSONObject(0)
           //     .getJSONArray("related_resources")
             //   .getJSONObject(0)
               // .getJSONObject("sale");
     //   String transaction_id = json.getString("id");
     //   double transaction_fee = json.getJSONObject("transaction_fee").getDouble("value");
     //   double amount = json.getJSONObject("amount").getDouble("total");


      // System.out.println("transaction_id  ==> " + transaction_id);
     //  System.out.println("transaction_fee ==> " + transaction_fee);
     //  System.out.println("amount  ==========> " + amount);
   //    System.out.println("custom  ==========> " + custom);
    }

    private String getJsonFromFile() {
        try {
            File file = new ClassPathResource("paypal.json").getFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder jsonString = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            return jsonString.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
