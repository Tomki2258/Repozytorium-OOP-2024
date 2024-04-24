import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CountryWithoutProvinces extends Country{
    List<LocalDate> dateList = new ArrayList<>();
    List<Integer> deathsList = new ArrayList<>();;
    List<Integer> confirmedCasesList = new ArrayList<>();;
    public CountryWithoutProvinces(String name){
        super(name);
    }
    public void addDailyStatistic(LocalDate date,int deaths,int confirmedCases){
        dateList.add(date);
        deathsList.add(deaths);
        confirmedCasesList.add(confirmedCases);
    }
}
