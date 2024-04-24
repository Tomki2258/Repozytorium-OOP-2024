import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Country.setFiles(Path.of("src/confirmed_cases.csv"),Path.of("src/deaths.csv"));
        Country country = Country.fromCSV("Afghanistan");
        
    }
}