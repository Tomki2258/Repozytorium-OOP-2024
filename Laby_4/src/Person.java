import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private List<Person> parents = new ArrayList<>();
    private Person father;
    private Person mather;

    public void setFather(Person father) {
        this.father = father;
    }

    public void setMother(Person mather) {
        this.mather = mather;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public static List<String> splitNames(String line) {
        List<String> splitted = List.of(line.split(" "));
        return splitted;
    }

    private static LocalDate doDate(String dateString) {
        if (dateString.isEmpty()) return null;
        List<String> stringList = List.of(dateString.split("\\."));

        int day = Integer.parseInt(stringList.getFirst());
        int mounth = Integer.parseInt(stringList.get(1));
        int year = Integer.parseInt(stringList.getLast());
        return LocalDate.of(
                year, mounth, day
        );
    }

    public Person(String firstName, String lastName, LocalDate birthDate, LocalDate deathDate) throws NegativeLifespanException {
        this.firstName = firstName;
        this.lastName = lastName;
        if (deathDate != null) {
            if (birthDate.isAfter(deathDate)) {
                throw new NegativeLifespanException(this.firstName);
            }
        }
        this.birthDate = birthDate;
        this.deathDate = deathDate;

    }

    public static Person fromCSVLine(String line) throws NegativeLifespanException {
        List<String> splittedLine = List.of(line.split(","));
        List<String> splittedNames = splitNames(splittedLine.getFirst());
        LocalDate birthDate = doDate(splittedLine.get(1));
        LocalDate deathDate = doDate(splittedLine.get(2));

        return new Person(splittedNames.getFirst(), splittedNames.getLast(), birthDate, deathDate);
    }

    public static List<Person> fromCsv(Path path) {
        File file = new File(String.valueOf(path));
        List<Person> personList = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                Person person = Person.fromCSVLine(line);
                List<String> splittedList = List.of(line.split(","));
                List<String> splittedParentOne = List.of(splittedList.get(splittedList.size() - 2).split(" "));
                List<String> splittedParentTwo = List.of(splittedList.getLast().split(" "));

                for (Person p : personList) {
                    if (splittedParentOne.getFirst().equals(p.firstName) && splittedParentOne.getLast().equals(p.lastName)) {
                        person.setFather(p);
                    }
                    if (splittedParentTwo.getFirst().equals(p.firstName) && splittedParentTwo.getLast().equals(p.lastName)) {
                        person.setMother(p);
                    }
                }
                personList.add(person);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NegativeLifespanException e) {
            throw new RuntimeException(e);
        }

        return personList;
    }

    public void printData() {
        System.out.println(
                "Siema jestem " + this.firstName + " - " + this.lastName + " - " +
                        this.birthDate + " - " + this.deathDate
        );
    }

    public void describePerson() {

        if (this.father != null) this.father.printData();
        if (this.mather != null) this.mather.printData();
    }
}
