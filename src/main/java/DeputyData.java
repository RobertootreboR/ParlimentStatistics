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
        String deputiesStr = JSONgetter.getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
        JSONObject deputiesJSON = new JSONObject(deputiesStr);
        JSONArray deputiesArray = deputiesJSON.getJSONArray("Dataobject");
        for (int i = 0; i < 49; i++) {
            String deputyName = deputiesArray
                    .optJSONObject(i)               // take object under index i (which is a map)
                    .getString("slug");             // get the value corresponding to the key "slug" - which is a name

            Integer deputyID = Integer.parseInt(deputiesArray
                            .optJSONObject(i)
                            .getString("id"));
            deputies.add(new Deputy(deputyName,deputyID));
        }
    }
}
