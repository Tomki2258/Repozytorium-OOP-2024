import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Country.setFiles(Path.of("src/confirmed_cases.csv"),Path.of("src/deaths.csv"));
        Country country = Country.fromCSV("Australia");
        //LocalDate date = Country.doDate("4/10/20");

        //country.getConfirmedCases(date);
    }
}