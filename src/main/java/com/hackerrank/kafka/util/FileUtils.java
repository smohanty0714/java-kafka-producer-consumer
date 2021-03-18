package com.hackerrank.kafka.util;

import com.hackerrank.kafka.AppMain;
import com.hackerrank.kafka.model.Card;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class FileUtils {
	
	 public static Set<String> curr = new HashSet<>(Arrays.asList("FJD", "GBP", "USD", "EURO"));

    // Read config
    public static Properties readKafkaConfig() {
        Properties prop = new Properties();

        try (InputStream input = AppMain.class.getClassLoader().getResourceAsStream("kafka.properties")) {
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println("Config: ");
            prop.forEach((key, value) -> System.out.println("Key : " + key + ", Value : " + value));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop;
    }
    
    public static boolean isValidData(Card card) {
    	
    	if(card.getCardNumber().isEmpty() || card.getCardNumber().length() != 8) {
    		return false;
    	}
    	if(card.getCardSecurityCode().isEmpty() || card.getCardSecurityCode().length() != 3) {
    		return false;
    	}
    	
    	if(card.getCardExpirationDate().isEmpty() || !validDate(card.getCardExpirationDate())) {
    		return false;
    	}
    	return true;
    }
   public static boolean validDate(String date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cardDate;
		try {
			cardDate = sdf.parse(date);
			if (cardDate.compareTo(new Date()) > 0) {
	           return true;
	        }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    	return false;
    }
   
   public static boolean validCurrency(String currency) {
	  
	   if(!currency.isEmpty() && curr.contains(currency.toUpperCase())) return true;
	   return false;
   }
}
