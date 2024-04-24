import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws AmbigiousProductException {
        Product nonFoodProduct = NonFoodProduct.fromCsv(Path.of("src/nonfood/benzyna.csv"));
        Product foodProduct = FoodProduct.fromCsv(Path.of("src/food/buraki.csv"));
        Product.addProducsts(nonFoodProduct);
        Product.addProducsts(foodProduct);

        Cart cart = new Cart();
        cart.addProduct(nonFoodProduct,5);
        cart.addProduct(foodProduct,1);

        System.out.println(cart.getPrice(2010,3));
    }
}