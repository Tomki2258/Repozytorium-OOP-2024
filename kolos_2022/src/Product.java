import java.util.ArrayList;
import java.util.List;

public abstract class Product {
    private String name;
    private static List<Product> products = new ArrayList<>(List.of());
    public Product(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public abstract double getPrice(int year, int month);

    public static void clearProducts(){
        products.clear();
    }
    public static void addProducsts(Product product){
        products.add(product);
    }
    public static void getProducts(String prefix) throws AmbigiousProductException{
        List<Product> foundProducts = new ArrayList<>(List.of());

        for (Product product : products){
            if(product.getName().contains(prefix)){
                foundProducts.add(product);
            }
        }

        if(foundProducts.size() > 1){
            throw new AmbigiousProductException(foundProducts);
        }
        System.out.println(foundProducts.getFirst().getName());
    }
}
