import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by robert on 16.12.16.
 */
public class DeputyPersonalData {
    List<Deputy> deputies = new LinkedList<Deputy>();

    DeputyPersonalData(ParsingDetails details) throws IOException {
        JSONGetter JSONgetter = new JSONGetter();
        String deputiesStr = JSONgetter.getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=" + details.cadence);
        new JSONObject(deputiesStr)                                             // make a JSONObject from the downloaded string
                .getJSONArray("Dataobject")                                     // take an array from the "DataObject" key
                .forEach(e ->                                                   // for each object (which is deputy info) in the array
                        deputies.add(                                           // add to the "deputies" list
                                new Deputy(getDeputyName(e), getDeputyID(e)))); // deputy's name and his ID

    }

    private String getDeputyName(Object deputy) {
        JSONObject dep = (JSONObject) deputy;   // dodaćobsługę błędóW!
        return dep.getString("slug");
    }
    Deputy getDeputy(Integer ID){
        for(Deputy deputy : deputies)
            if(deputy.ID.equals(ID))
                return deputy;
        throw new IllegalArgumentException("Deputy ID "+ ID +" is invalid. Try again");
    }


    private Integer getDeputyID(Object deputy) {
        JSONObject dep = (JSONObject) deputy;
        return Integer.parseInt(dep.getString("id"));  // dodaćobsługę błędóW!
    }

    Integer getDeputyID(String name){
        for(Deputy deputy : deputies)
            if(deputy.name.equals(name) || deputy.name.startsWith(name+"-"))
                return deputy.ID;
        throw new IllegalArgumentException("Deputy name "+ name +" is invalid. Try again");
    }
}
