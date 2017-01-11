import Exceptions.InvalidDataFormatException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Created by robert on 15.12.16.
 */
public class DeputyData {
    HashMap<Integer, JSONObject> deputyDataMap = new HashMap<>();

    DeputyData(DeputyPersonalData deputyPersonalData, ParsingDetails details) throws IOException {
        for (Deputy deputy : deputyPersonalData.deputies)
            deputyDataMap.put(deputy.ID, loadExpensesData(deputy.ID, details));
    }

    public DeputyData(HashMap<Integer, JSONObject> deputyDataMap) {
        this.deputyDataMap = deputyDataMap;
    }

    private JSONObject loadExpensesData(Integer ID, ParsingDetails details) throws IOException {
        return new JSONObject(
                Files.lines(Paths.get(details.path + "/Deputies/Deputy" + ID)).reduce("", String::concat))
                .getJSONObject("layers");
    }

    int getLiczbaRocznikow(Integer deputyID) {
        return deputyDataMap
                .get(deputyID)
                .getJSONObject("wydatki")
                .getInt("liczba_rocznikow");
    }

    private JSONArray getPunktyArray(Integer deputyID) {
        return deputyDataMap
                .get(deputyID)
                .getJSONObject("wydatki")
                .getJSONArray("punkty");
    }

    Integer numberOfJourneys(Integer deputyID) {
        if (isWyjazdyArrayEmpty(deputyID))
            return 0;
        else {
            return ((Long) getFromWyjazdyArrayAsStream("kraj", deputyID)
                    .filter(country -> !country.equals("Polska"))           // count returns long. To obtain Integer I have to cast to
                    .count()).intValue();                                   // Long and then take intValue
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
        return Integer.parseInt(
                getPunktyArray(deputyID)
                .toList()
                .stream()
                .map(HashMap.class::cast)
                .filter(e -> e.get("tytul").equals("Koszty drobnych napraw i remontów lokalu biura poselskiego"))
                .map(e -> e.get("numer"))
                .map(String.class::cast)
                .findFirst()
                .orElseThrow(() -> new InvalidDataFormatException("No 'minor fixes' field found")));
    }

    Integer longestJourneyDuration(Integer deputyID) {
        if (isWyjazdyArrayEmpty(deputyID))
            return 0;
        else
            return getFromWyjazdyArrayAsStream("liczba_dni", deputyID)
                    .mapToInt(Integer::parseInt)
                    .reduce(0, Integer::max);
    }

    Integer daysAbroad(Integer deputyID) {
        if (isWyjazdyArrayEmpty(deputyID))
            return 0;
        else
            return getFromWyjazdyArrayAsStream("liczba_dni", deputyID)
                    .mapToInt(Integer::parseInt)
                    .sum();
    }

    Double mostExpensiveJourney(Integer deputyID) {
        if (isWyjazdyArrayEmpty(deputyID))
            return 0.0;
        else
            return getFromWyjazdyArrayAsStream("koszt_suma", deputyID)
                    .mapToDouble(Double::parseDouble)
                    .reduce(0, Double::max);
    }

    boolean visitedItaly(Integer deputyID) {
        return !isWyjazdyArrayEmpty(deputyID)
                &&
                getFromWyjazdyArrayAsStream("kraj", deputyID)
                        .filter(country -> country.equals("Włochy"))
                        .count() != 0;
    }

    private Stream<String> getFromWyjazdyArrayAsStream(String field, Integer deputyID) {
        return getWyjazdyArray(deputyID)
                .toList()
                .stream()
                .map(HashMap.class::cast)
                .map(e -> e.get(field))
                .map(String.class::cast);
    }


}
