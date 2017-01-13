import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by robert on 18.12.16.
 */
public class TravelsStats {
    private DeputyData travelData;

    private class IDAndValue {
        Integer ID;
        Number value;

        public IDAndValue(Integer ID, Number value) {
            this.ID = ID;
            this.value = value;
        }
    }

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

    Integer getMostExpensiveJourneyDeputyID() {
        return getDeputyIDWithMost(deputyID -> travelData.mostExpensiveJourney(deputyID));
    }

    private Integer getDeputyIDWithMost(Function<Integer, Number> computation) {
        return travelData.deputyDataMap.keySet()
                .stream()
                .map(deputyID -> new IDAndValue(deputyID, computation.apply(deputyID)))
                .reduce(new IDAndValue(0, 0), TravelsStats::max)
                .ID;
    }

     private static IDAndValue max(IDAndValue idAndValue1, IDAndValue idAndValue2) {
        if (idAndValue1.value.doubleValue() > idAndValue2.value.doubleValue())
            return idAndValue1;
        else return idAndValue2;
    }


    List<Integer> getWhoVisitedItaly() {
        return travelData.deputyDataMap
                .keySet()
                .stream()
                .filter(e -> travelData.visitedItaly(e))
                .collect(Collectors.toList());
    }
}
