import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
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

    private JSONObject loadExpensesData(Integer ID, ParsingDetails details) throws IOException {
        try {
            String file = Files.lines(Paths.get(details.path + "/Deputies/Deputy" + ID)).reduce("", String::concat);
            return new JSONObject(file)
                    .getJSONObject("layers");
        } catch (IOException ex) {
        }
        throw new IOException("couldn't open one of Deputies files");
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
            Long journeys = getFromWyjazdyArrayAsStream("kraj", deputyID)
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
                .filter(e -> e.get("tytul").equals("Koszty drobnych napraw i remontów lokalu biura poselskiego"))
                .map(e -> e.get("numer"))
                .map(String.class::cast)
                .mapToInt(Integer::parseInt)
                .sum();  //there is only one such element, so sum will just return it
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
