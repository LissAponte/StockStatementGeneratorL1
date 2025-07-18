package classes;

import org.example.Main;

public class StockObj {



    public int account_number;
    public String ssn;
    public String first_name;
    public String last_name;
    public String email;
    public String phone;
    public double beginning_balance;
    public Trade[] stock_trades;

    public void setBeginning_balance(String obj)
    {
        beginning_balance = Main.strToDouble(obj);
    }
}
