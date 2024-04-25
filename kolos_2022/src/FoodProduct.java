import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FoodProduct extends Product{
    List<String> provinces;
    List<Double> prices;

    public FoodProduct(String name, List<Double> prices,List<String> provinces) {
        super(name);
        this.prices = prices;
        this.provinces = provinces;
    }

    @Override
    public double getPrice(int year, int month) {
        return 0;
    }

    public double getPrice(double year, double month){
        double value = 0;
        int firstYear = 2010;
        double targetIndex = (firstYear - year) + month;
        for (int i = 0;i < prices.size();i++){
            if(i == targetIndex){
                value += prices.get((int) targetIndex);
                targetIndex += prices.size() / provinces.size();
            }
        }
        return value / provinces.size();
    }
    public double getPrice(int year, int month, String province){
        int provinceIndex = provinces.indexOf(province);
        if(provinceIndex < 0){
            throw new IndexOutOfBoundsException();
        }
        int firstYear = 2010;

        int targetIndex = provinceIndex* 12 + (firstYear - year) + month;
        return prices.get(targetIndex);
    }
    public static FoodProduct fromCsv(Path path) {
        String name;
        List<Double> prices = new ArrayList<>(List.of());
        FoodProduct foodProduct;
        List<String> provinces = new ArrayList<>(List.of());
        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine(); // odczytuję pierwszą linię i zapisuję ją jako nazwa
            scanner.nextLine();  // pomijam drugą linię z nagłówkiem tabeli
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                List<String> splitted = List.of(line.split(";"));

                provinces.add(splitted.getFirst());
                for (int i = 1 ;i < splitted.size();i++){
                    String currentString = splitted.get(i).replace(",",".");
                    Double prize = Double.valueOf(currentString);
                    prices.add(prize);
                }
            }

            scanner.close();

            return new FoodProduct(name, prices,provinces);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
