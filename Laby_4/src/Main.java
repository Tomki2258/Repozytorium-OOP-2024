import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Person> personList = new ArrayList<>();
    public static void main(String[] args) {
        //Person person = Person.fromCSVLine("Marek Kowalski,15.05.1899,25.06.1957,,\n");
        personList = Person.fromCsv(Path.of("src/family.csv"));
    }
}