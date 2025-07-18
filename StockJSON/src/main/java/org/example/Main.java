package org.example;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import classes.*;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {
    
    private static File jsonFile;
    static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    public static void main(String[] args) {
        jsonFile = new File("src/main/stock_transations.by.account.holder.json");
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            StockObj[] stockArray = mapper.readValue(jsonFile, StockObj[].class);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

    }
    public static float strToFloat(String money)
    {
        String cleanedStr = money.substring(1);
        return Float.parseFloat(cleanedStr);
    }
    private static String cashFormatter(float amount)
    {
        return currencyFormatter.format(amount);
    }
    private void createHTMLReport(StockObj obj)
    {
        String displayName = "Name: " + obj.first_name+ " " + obj.last_name;
        String displaySSN = "SSN: " + obj.ssn;
        String displayEmail = "Email: " + obj.email;
        String displayPhone = "Phone: " + obj.phone;
        String displayAccountNum = "Account#: " + obj.account_number;
    }
}



