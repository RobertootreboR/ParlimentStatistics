import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by robert on 15.12.16.
 */
public class DeputyData {
    ConcurrentHashMap<Integer, JSONObject> deputyDataMap = new ConcurrentHashMap<>();

    DeputyData(DeputyPersonalData deputyPersonalData) {
        deputyPersonalData.deputies.parallelStream().forEach(deputy -> deputyDataMap.put(deputy.ID, downloadExpensesData(deputy.ID)));
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

    long getNumberOfJourneys(Integer deputyID) {  //poprawić bo brzydko jest
        if (isWyjazdyEmpty(deputyID))
            return 0;
        else
            return getWyjazdyArray(deputyID)
                    .toList()
                    .stream()
                    .map(HashMap.class::cast)
                    .filter(DeputyData::isForeignJourney)
                    .count();


    }

    private static boolean isForeignJourney(HashMap journey) {
        return !journey.get("kraj").equals("Polska");
    }  //unnecessary?

    boolean isWyjazdyEmpty(Integer deputyID) {
        return deputyDataMap
                .get(deputyID)
                .get("wyjazdy").toString().equals("{}");
    }
    JSONArray getWyjazdyArray(Integer deputyID){
        return deputyDataMap
                .get(deputyID)
                .getJSONArray("wyjazdy");
    }
    JSONArray getPolaArrayFromRocznikiAt(Integer index, Integer deputyID){
        return deputyDataMap
                .get(deputyID)
                .getJSONObject("wydatki")
                .getJSONArray("roczniki")
                .optJSONObject(index)
                .getJSONArray("pola");
    }
    Integer getMinorFixesIndex(Integer deputyID){
        JSONArray punktyArray = getPunktyArray(deputyID);
        for (Object object : punktyArray) {
            if (((JSONObject) object).getString("tytul").equals("Koszty drobnych napraw i remontów lokalu biura poselskiego"))
                return((JSONObject) object).getInt("numer");
        }
        throw new IllegalArgumentException("Something has changed in Sejmowe API. Check if all JSON you use are still there");
    }
    Integer longestJourneyDuration(Integer deputyID) {
        if (isWyjazdyEmpty(deputyID))
            return 0;
        else
            return getWyjazdyArray(deputyID)
                    .toList()
                    .stream()
                    .map(HashMap.class::cast)
                    .map(e -> e.get("liczba_dni"))
                    .map(String.class::cast)
                    .mapToInt(Integer::parseInt)
                    .reduce(0,Integer::max);
    }


    Integer daysAbroad(Integer deputyID) {
        if (isWyjazdyEmpty(deputyID))
            return 0;
        else
            return getWyjazdyArray(deputyID)
                    .toList()
                    .stream()
                    .map(HashMap.class::cast)
                    .map(e -> e.get("liczba_dni"))
                    .map(String.class::cast)            //string czy int?
                    .mapToInt(Integer::parseInt)
                    .sum();
    }
    Double mostExpensiveJourney(Integer deputyID) {
        if (isWyjazdyEmpty(deputyID))
            return 0.0;
        else
            return getWyjazdyArray(deputyID)
                    .toList()
                    .stream()
                    .map(HashMap.class::cast)
                    .map(e -> e.get("koszt_suma"))
                    .map(String.class::cast)
                    .mapToDouble(Double::parseDouble)
                    .reduce(0,Double::max);
    }

    boolean visitedItaly(Integer deputyID) {
        if (isWyjazdyEmpty(deputyID))
            return false;
        else return
                getWyjazdyArray(deputyID)
                .toList()
                .stream()
                .map(HashMap.class::cast)
                .map(e -> e.get("kraj"))
                //.map(String.class::cast)
                .filter(country-> country.equals("Włochy"))
                .count()
                != 0;

    }
}
