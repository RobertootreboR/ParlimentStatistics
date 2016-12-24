import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by robert on 18.12.16.
 */
public class TravelsStats {
    Integer mostForeignJourneysID;
    Deputy longestJourney;
    Deputy mostExpensiveJourney;
    List<Deputy> visitedItaly = new LinkedList<>();


    public TravelsStats(DeputyData travelData) {
        this.mostForeignJourneysID = getMostForeignJourneysID(travelData);
    }

    Integer getMostForeignJourneysID(DeputyData travelData) {
        long max = 0;
        Integer maxID = 0;
        for (Integer deputyID : travelData.deputyDataMap.keySet()) {
            long tmp = getNumberOfJourneys(deputyID, travelData);
            if (tmp > max) {
                maxID = deputyID;
                max = tmp;
            }

        }
        System.out.print("max: " +max + "maxid" +maxID);
        return maxID;
    }

    private long getNumberOfJourneys(Integer deputyID, DeputyData travelData) {  //poprawić bo brzydko jest
        if (travelData.deputyDataMap
                .get(deputyID)
                .get("wyjazdy").toString().equals("{}"))
            return 0;
        else
            return travelData.deputyDataMap
                    .get(deputyID)
                    .getJSONArray("wyjazdy")
                    .toList()
                    .stream()
                    .map(HashMap.class::cast)
                    .filter(TravelsStats::isForeignJourney)
                    .count();



    }

    private static boolean isForeignJourney(HashMap journey) {
        return !journey.get("kraj").equals("Francja");
    }
}
