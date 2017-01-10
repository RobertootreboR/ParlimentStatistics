import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by robert on 18.12.16.
 */
public class TravelsStats {
    private DeputyData travelData;

    TravelsStats(DeputyData data) {
        this.travelData = data;
    }

    Integer getMostForeignJourneysDeputyID() {
        return getDeputyIDWithMost(deputyID -> travelData.numberOfJourneys(deputyID));
    }

    Integer getLongestJourneyDeputyID() {
        return getDeputyIDWithMost(deputyID -> travelData.longestJourneyDuration(deputyID));
    }

    Integer getLongestAbroadDeputyID() {
        return getDeputyIDWithMost(deputyID -> travelData.daysAbroad(deputyID));
    }

    private Integer getDeputyIDWithMost(Function<Integer, Integer> computation) {
        Integer max = 0;
        Integer maxID = 0;
        for (Integer deputyID : travelData.deputyDataMap.keySet()) {
            Integer tmp = computation.apply(deputyID);
            if (tmp > max) {
                maxID = deputyID;
                max = tmp;
            }
        }
        System.out.print("max: " + max + " ");
        return maxID;
    }

    Integer getMostExpensiveJourneyDeputyID() {
        Double max = 0.0;
        Integer maxID = 0;
        for (Integer deputyID : travelData.deputyDataMap.keySet()) {
            Double tmp = travelData.mostExpensiveJourney(deputyID);
            if (tmp > max) {
                maxID = deputyID;
                max = tmp;
            }
        }
        return maxID;
    }

    List<Integer> getWhoVisitedItaly() {
        return travelData.deputyDataMap
                .keySet()
                .stream()
                .filter(e -> travelData.visitedItaly(e))
                .collect(Collectors.toList());
    }
}
