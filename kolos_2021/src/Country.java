import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public abstract class Country {
    public final String name;
    private static Path pathOne;
    private static Path pathTwo;
    private static CountryColumns cColumns;
    public CountryColumns readCountryCollumns(){
        return cColumns;
    }
    public String getName(){
        return name;
    }
    public Country(String name){
        this.name = name;
    }
    public static void setFiles(Path one,Path two) throws FileNotFoundException {
        File fileOne = new File(String.valueOf(one));
        File fileTwo = new File(String.valueOf(two));

        boolean resultOne =  fileOne.exists() && fileOne.canRead();
        boolean resultTwo =  fileTwo.exists() && fileTwo.canRead();

        if(!resultOne) throw new FileNotFoundException(fileOne.getPath());
        if(!resultTwo) throw new FileNotFoundException(fileTwo.getPath());

        pathOne = one;
        pathTwo = two;
    }
    public static List<String> getSplittedLine(String line,String delimeter){
        return List.of(line.split(delimeter));
    }
    public static LocalDate doDate(String dateString) throws ParseException {
        List<String> splitted = getSplittedLine(dateString,"/");
        LocalDate localDate = LocalDate.of(
                Integer.parseInt(splitted.get(2)) + 2000,
                Integer.parseInt(splitted.get(0)),
                Integer.parseInt(splitted.get(1)));
        return localDate;
    }
    public static CountryWithoutProvinces getCountry(Scanner scannerCases, Scanner deathsCases, String name, CountryColumns countryColumns, int foundIndex) throws ParseException {
        CountryWithoutProvinces countryWithoutProvinces = new CountryWithoutProvinces(name);
        while(scannerCases.hasNext()){
            String line = scannerCases.nextLine();
            String lineCases = deathsCases.nextLine();
            List<String> splittedDeaths = List.of(line.split(";"));
            List<String> splittedCases = List.of(lineCases.split(";"));
            System.out.println(splittedDeaths.get(foundIndex));
            int deaths = Integer.parseInt(splittedDeaths.get(foundIndex));
            int confirmedCases = Integer.parseInt(splittedCases.get(foundIndex));
            countryWithoutProvinces.addDailyStatistic(doDate(splittedDeaths.getFirst()),deaths,confirmedCases);
        }
        return countryWithoutProvinces;
    }
    public static Country fromCSV(String name) throws FileNotFoundException, ParseException {
        Country country = null;
        File fileOne = new File(String.valueOf(pathOne));
        File fileTwo = new File(String.valueOf(pathTwo));

        CountryColumns countryColumns = null;
        List<String> splittedTop = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(fileOne);
            splittedTop = Collections.singletonList(scanner.nextLine());
            countryColumns = getCountryColumns(String.valueOf(splittedTop), name);
            cColumns = countryColumns;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CountryNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner scannerCases = new Scanner(fileOne);
        Scanner scannerDeaths = new Scanner(fileTwo);

        scannerCases.nextLine();
        scannerCases.nextLine();
        scannerDeaths.nextLine();
        scannerDeaths.nextLine();

        if(countryColumns.columnCount == 1){
             country = getCountry(scannerCases,scannerDeaths,name,countryColumns, countryColumns.firstColumnIndex);
        }
        else if(countryColumns.columnCount > 1){
            List<Country> countryWithoutProvincesList = new ArrayList<>();
            List<Integer> foundIndexes = new ArrayList<>();

            for(int i = 0 ;i < splittedTop.size();i++){
                if(splittedTop.get(i).contains(name)){
                    foundIndexes.add(i);
                }
            }
            for (int i = 0;i < foundIndexes.size();i++){
                Country countryTemp = getCountry(scannerCases,scannerDeaths,name,countryColumns, foundIndexes.get(i));
                countryWithoutProvincesList.add(countryTemp);
            }
            CountryWithProvinces countryWithProvinces = new CountryWithProvinces(name,countryWithoutProvincesList);
            country = countryWithProvinces;
        }
        return country;
    }
    public static CountryColumns getCountryColumns(String line, String target) throws CountryNotFoundException {
        List<String> splittedLine = List.of(line.split(";"));
        List<Country> countryList = new ArrayList<>();
        int foundIndex = 0;
        int columnCount = 0;
        for (String str : splittedLine){
            if(str.contains(target)){
                if(foundIndex == 0){
                    foundIndex = splittedLine.indexOf(target);
                }
                columnCount++;
            }
        }
        if(foundIndex == 0 || columnCount == 0) throw new CountryNotFoundException();

        return new CountryColumns(foundIndex,columnCount);
    }

    private static class CountryColumns{
        public final int firstColumnIndex;
        public final int columnCount;

        private CountryColumns(int firstColumnIndex, int columnCount) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnCount;
        }
    }
    public void getConfirmedCases(LocalDate date){
        if(cColumns.columnCount == 1){
            System.out.println("nie ma ma prowincji");
        }
        else{
            System.out.println("ma prowincje");
        }
    }
}