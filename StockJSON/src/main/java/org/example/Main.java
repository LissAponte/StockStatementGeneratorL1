package org.example;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import classes.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

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
    public static double strToDouble(String money)
    {
        String cleanedStr = money.substring(1);
        return Double.parseDouble(cleanedStr);
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
                "    <link rel=\"styleSheet\" href=\"style.css\"</link>\n" +
                "    <title>TestData</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + LocalDate.now() + "</h1>" +
                "    <p>" + displayName+"</p>\n" +
                "    <p>" + displaySSN+"</p>\n" +
                "    <p>" + displayEmail+"</p>\n" +
                "    <p>" + displayPhone+"</p>\n" +
                "    <p>" + displayAccountNum+"</p>\n" +
                "    <p>"+displayStart+"</p>\n" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>Type</th>\n" +
                "            <th>Symbol</th>\n" +
                "            <th>Price</th>\n" +
                "            <th>Shares</th>\n" +
                "            <th>Total</th>\n" +
                "        </tr>\n";

        for(Trade transaction: obj.stock_trades)
        {
            double shiftAmount = transaction.count_shares * transaction.price_per_share;
            htmlContent +=
                    "        <tr>\n" +
                            "            <td>"+transaction.type+"</td>\n" +
                            "            <td>"+transaction.stock_symbol+"</td>\n" +
                            "            <td>"+cashFormatter(transaction.price_per_share)+"</td>\n" +
                            "            <td>"+transaction.count_shares+"</td>\n" +
                            "            <td>"+cashFormatter(shiftAmount)+"</td>\n" +
                            "        </tr>\n";
            if(transaction.type.equals("Buy"))
            {
                cashTotal -= shiftAmount;
                stockTotal += shiftAmount;
            }
            else if(transaction.type.equals("Sell")){
                cashTotal += shiftAmount;
                stockTotal -= shiftAmount;
            }
        }
        String displayCashTotal = "Cash Value: "+cashFormatter(cashTotal);
        String displayStockTotal = "Stock Value: "+cashFormatter(stockTotal);

        htmlContent +=
                "</table>\n" +
                "    <p>" + displayCashTotal+"</p>\n" +
                "    <p>" + displayStockTotal+"</p>\n" +
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



