import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Cart {
    List<Product> cartItems = new ArrayList<>();
    public void addProduct(Product product,int amount){
        for (int i = 0 ;i < amount;i++){
            cartItems.add(product);
        }
    }

    public double getPrice(int year,int mounth){
        double value = 0;
        for (Product p : cartItems){
            value += p.getPrice(year,mounth);
        }
        return value;
    }

    public double getInflation(int year1, int month1, int year2, int month2){
        Date daneOne = new Date(year1,month1,0);
        Date daneTwo = new Date(year2,month2,0);
        if(daneTwo.after(daneOne)){
            double valueOne = getPrice(year1,month1);
            double valueTwo = getPrice(year2,month2);

            double result = (valueTwo - valueOne) / valueOne * 100 / (month2 - month1) * 12;
            return result;
        }
        return -1;
    }
}
