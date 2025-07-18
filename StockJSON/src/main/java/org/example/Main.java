package org.example;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import classes.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
            createHTMLReport(stockArray[0]);
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
    static private void createHTMLReport(StockObj obj)
    {
        String displayName = "Name: " + obj.first_name+ " " + obj.last_name;
        String displaySSN = "SSN: " + obj.ssn;
        String displayEmail = "Email: " + obj.email;
        String displayPhone = "Phone: " + obj.phone;
        String displayAccountNum = "Account#: " + obj.account_number;

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>TestData</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <p>" + displayName+"</p>\n" +
                "    <p>" + displaySSN+"</p>\n" +
                "    <p>" + displayEmail+"</p>\n" +
                "    <p>" + displayPhone+"</p>\n" +
                "    <p>" + displayAccountNum+"</p>\n" +
                "    <p>This HTML was generated using java.io.</p>\n" +
                "</body>\n" +
                "</html>";

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.html"))) {
            writer.println(htmlContent);
            System.out.println("HTML file created successfully!");
        } catch (IOException e) {
            System.err.println("Error writing HTML file: " + e.getMessage());
        }
    }
}



