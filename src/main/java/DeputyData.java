import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by robert on 16.12.16.
 */
public class DeputyData {
    List<Deputy> deputies = new LinkedList<Deputy>();

    public DeputyData(ParsingDetails details) throws IOException {
        JSONGetter JSONgetter = new JSONGetter();
        String deputiesStr = JSONgetter.getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]="+details.cadence);
        new JSONObject(deputiesStr)                                            // make a JSONObject from the downloaded string
                .getJSONArray("Dataobject")                                    // take an array from the "DataObject" key
                .forEach(e->                                                  // for each object (which is deputy info) in the array
                        deputies.add(                                          // add to the "deputies" list
                                new Deputy(getDeputyName(e),getDeputyID(e)))); // deputy's name and his ID

    }
    private String getDeputyName(Object deputy){
            JSONObject dep = (JSONObject) deputy;
            return dep.getString("slug");
    }
    private Integer getDeputyID(Object deputy){
            JSONObject dep = (JSONObject) deputy;
            return Integer.parseInt(dep.getString("id"));
    }
}
