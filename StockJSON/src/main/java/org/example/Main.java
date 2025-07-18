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
            createHTMLReport(stockArray[9]);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

    }
    public static double strToDouble(String money)
    {
        String cleanedStr = money.substring(1);
        return 1;
        //return double holdFloat = Double.parseDouble(cleanedStr);
    }
    private static String cashFormatter(double amount)
    {
        return currencyFormatter.format(amount);
    }
    static private void createHTMLReport(StockObj obj)
    {
        double cashTotal = obj.beginning_balance, stockTotal = 0;
        String displayName = "Name: " + obj.first_name+ " " + obj.last_name;
        String displaySSN = "SSN: " + obj.ssn;
        String displayEmail = "Email: " + obj.email;
        String displayPhone = "Phone: " + obj.phone;
        String displayAccountNum = "Account#: " + obj.account_number;
        String displayStart = "Starting Cash: " + cashFormatter(obj.beginning_balance);

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
                "    <p>"+displayStart+"</p>\n" +
                "    <p>"+obj.beginning_balance+"</p>\n" +
                "    <p>"+7680675.39+"</p>\n" +
                //7680675.39
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>Type</th>\n" +
                "            <th>Symbol</th>\n" +
                "            <th>Price</th>\n" +
                "            <th>Shares</th>\n" +
                "            <th>Total</th>\n" +
                "        </tr>\n";
        /*
        for(Trade transaction: obj.stock_trades)
        {
            float shiftAmount = transaction.count_shares * transaction.price_per_share;
            if(transaction.type == "buy")
            {

            }
            else if(transaction.type == "sell"){

            }
        }
         */

        htmlContent +=
                "</table>\n" +
                "</body>\n" +
                "</html>";

        try (PrintWriter writer = new PrintWriter(new FileWriter(obj.account_number + ".html"))) {
            writer.println(htmlContent);
            System.out.println("HTML file created successfully!");
        } catch (IOException e) {
            System.err.println("Error writing HTML file: " + e.getMessage());
        }
    }
}



