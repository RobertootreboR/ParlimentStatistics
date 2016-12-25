import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by robert on 15.12.16.
 */
public class DeputyData {
    ConcurrentHashMap<Integer, JSONObject> deputyDataMap = new ConcurrentHashMap<>();

    DeputyData(DeputyPersonalData deputyPersonalData) {
        deputyPersonalData.deputies.parallelStream()
                .forEach(deputy -> deputyDataMap.put(deputy.ID, downloadExpensesData(deputy.ID)));
    }

    private JSONObject downloadExpensesData(Integer ID) {
        try {
            return new JSONObject(new JSONGetter().getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie/" + ID + ".json?layers[]=wydatki&layers[]=wyjazdy"))
                    .getJSONObject("layers");
        } catch (IOException ex) {
            System.out.print(ex + "cos nie tak z pobieraniem expensow :( ");
            return new JSONObject("");   //brzyyyyydkoooooo! coś z tym zrobić :(
        }
    }

    int getLiczbaRocznikow(Integer deputyID) {
        return deputyDataMap
                .get(deputyID)
                .getJSONObject("wydatki")
                .getInt("liczba_rocznikow");
    }

    JSONArray getPunktyArray(Integer deputyID) {
        return deputyDataMap
                .get(deputyID)
                .getJSONObject("wydatki")
                .getJSONArray("punkty");
    }

    Integer numberOfJourneys(Integer deputyID) {
        if (isWyjazdyArrayEmpty(deputyID))
            return 0;
        else {
            Long journeys = getFromWyjazdyArrayAsStream("kraj",deputyID)
                    .filter(country -> !country.equals("Polska"))
                    .count();
            return journeys.intValue();
        }
    }

    private boolean isWyjazdyArrayEmpty(Integer deputyID) {
        return deputyDataMap
                .get(deputyID)
                .get("wyjazdy").toString().equals("{}");
    }

    private JSONArray getWyjazdyArray(Integer deputyID) {
        return deputyDataMap
                .get(deputyID)
                .getJSONArray("wyjazdy");
    }

    JSONArray getPolaArrayFromRocznikiAt(Integer index, Integer deputyID) {
        return deputyDataMap
                .get(deputyID)
                .getJSONObject("wydatki")
                .getJSONArray("roczniki")
                .optJSONObject(index)
                .getJSONArray("pola");
    }

    Integer getMinorFixesIndex(Integer deputyID) {
        return getPunktyArray(deputyID)
                .toList()
                .stream()
                .map(HashMap.class::cast)
                .filter(e-> e.get("tytul").equals("Koszty drobnych napraw i remontów lokalu biura poselskiego"))
                .map(e-> e.get("numer"))
                .map(String.class::cast)
                .mapToInt(Integer::parseInt)
                .sum();  //there is only one such element, so sum will just return it
    }

    Integer longestJourneyDuration(Integer deputyID) {
        if (isWyjazdyArrayEmpty(deputyID))
            return 0;
        else
            return getFromWyjazdyArrayAsStream("liczba_dni",deputyID)
                    .mapToInt(Integer::parseInt)
                    .reduce(0, Integer::max);
    }

    Integer daysAbroad(Integer deputyID) {
        if (isWyjazdyArrayEmpty(deputyID))
            return 0;
        else
            return getFromWyjazdyArrayAsStream("liczba_dni",deputyID)
                    .mapToInt(Integer::parseInt)
                    .sum();
    }

    Double mostExpensiveJourney(Integer deputyID) {
        if (isWyjazdyArrayEmpty(deputyID))
            return 0.0;
        else
            return getFromWyjazdyArrayAsStream("koszt_suma",deputyID)
                    .mapToDouble(Double::parseDouble)
                    .reduce(0, Double::max);
    }

    boolean visitedItaly(Integer deputyID) {
        return !isWyjazdyArrayEmpty(deputyID)
                &&
                getFromWyjazdyArrayAsStream("kraj",deputyID)
                        .filter(country -> country.equals("Włochy"))
                        .count() != 0;
    }
    private Stream<String> getFromWyjazdyArrayAsStream(String field, Integer deputyID){
        return getWyjazdyArray(deputyID)
                .toList()
                .stream()
                .map(HashMap.class::cast)
                .map(e -> e.get(field))
                .map(String.class::cast);
    }
}
