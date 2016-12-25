import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by robert on 18.12.16.
 */
public class TravelsStats {
    DeputyData travelData;


    TravelsStats(DeputyData data) {
        this.travelData = data;
    }

    Integer getMostForeignJourneysDeputyID() {
        long max = 0;
        Integer maxID = 0;
        for (Integer deputyID : travelData.deputyDataMap.keySet()) {
            long tmp = travelData.getNumberOfJourneys(deputyID);
            if (tmp > max) {
                maxID = deputyID;
                max = tmp;
            }

        }
        System.out.print("max: " + max + "maxid" + maxID);
        return maxID;
    }

    Integer getLongestJourneyDeputyID(){
        Integer max =0;
        Integer maxID =0;
        for(Integer deputyID : travelData.deputyDataMap.keySet()){
            Integer tmp = travelData.longestJourneyDuration(deputyID);
            if (tmp > max) {
                maxID = deputyID;
                max = tmp;
            }

        }
        System.out.print("max: " + max + "maxid" + maxID);
        return maxID;
    }

    Integer getLongestAbroadDeputyID(){         //przekazywać funkcje jakoś??? :do innej funkcji?
        Integer max =0;
        Integer maxID =0;
        for(Integer deputyID : travelData.deputyDataMap.keySet()){
            Integer tmp = travelData.daysAbroad(deputyID);
            if (tmp > max) {
                maxID = deputyID;
                max = tmp;
            }

        }
        System.out.print("max: " + max + "maxid" + maxID);
        return maxID;
    }

    Integer getMostExpensiveJourneyDeputyID(){         //przekazywać funkcje jakoś??? :do innej funkcji?
        Double max =0.0;
        Integer maxID =0;
        for(Integer deputyID : travelData.deputyDataMap.keySet()){
            Double tmp = travelData.mostExpensiveJourney(deputyID);
            if (tmp > max) {
                maxID = deputyID;
                max = tmp;
            }

        }
        System.out.print("max: " + max + "maxid" + maxID);
        return maxID;
    }

    List<Integer> getWhoVisitedItaly(){
        return travelData.deputyDataMap.keySet().stream().filter(e ->travelData.visitedItaly(e)).collect(Collectors.toList());
    }




}
