package classes;

import org.example.Main;

public class Trade {
        public String type;
        public String stock_symbol;
        public int count_shares;
        public double price_per_share;

        public void setPrice_per_share(String money)
        {
            price_per_share = Main.strToDouble(money);
        }

}
