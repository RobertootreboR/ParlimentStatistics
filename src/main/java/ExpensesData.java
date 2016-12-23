import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by robert on 15.12.16.
 */
public class ExpensesData {
    ConcurrentHashMap<Integer, JSONObject> expensesMap = new ConcurrentHashMap<>();

    ExpensesData(DeputyData deputyData) {
        deputyData.deputies.parallelStream().forEach(deputy ->expensesMap.put(deputy.ID, downloadExpensesData(deputy.ID) )) ;
    }

    private JSONObject downloadExpensesData(Integer ID) {
        try {
        return new JSONObject(new JSONGetter().getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie/"+ID+".json?layers[]=wydatki"))
                .getJSONObject("layers")
                .getJSONObject("wydatki");
        }catch(IOException ex){
            System.out.print(ex +"cos nie tak z pobieraniem expensow :( ");
            return new JSONObject("");   //brzyyyyydkoooooo! coś z tym zrobić :(
    }
    }



}
