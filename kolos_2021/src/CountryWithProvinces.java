import java.util.List;

public class CountryWithProvinces extends Country{
    public List<Country> countryList;
    public CountryWithProvinces(String name,List<Country> countryList) {
        super(name);
        this.countryList = countryList;
    }
}
