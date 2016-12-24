import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by robert on 15.12.16.
 */
public class DeputyData {
    ConcurrentHashMap<Integer, JSONObject> deputyDataMap = new ConcurrentHashMap<>();

    DeputyData(DeputyPersonalData deputyPersonalData){
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
        JSONArray getPunktyArray(Integer deputyID){
            return deputyDataMap
                    .get(deputyID)
                    .getJSONObject("wydatki")
                    .getJSONArray("punkty");
        }







}
