import java.util.stream.IntStream;

/**
 * Created by robert on 15.12.16.
 */
public class ExpensesStats {
    private DeputyData expensesData;

    ExpensesStats(DeputyData expensesData) {
        this.expensesData = expensesData;
    }

    Double getDeputyMinorFixesSum(Integer deputyID) {
        return IntStream.range(0, expensesData.getLiczbaRocznikow(deputyID))
                .mapToDouble(i -> expensesData.getPolaArrayFromRocznikiAt(i, deputyID)          // roczniki[i] -> pola
                        .optDouble(expensesData.getMinorFixesIndex(deputyID) - 1))              // pola[i])
                .sum();
    }

    Double getDeputyExpenseSum(Integer deputyID) {
        return IntStream.range(0, expensesData.getLiczbaRocznikow(deputyID))
                .mapToDouble(i -> expensesData
                        .getPolaArrayFromRocznikiAt(i, deputyID)
                        .toList()
                        .stream()
                        .map(String.class::cast)
                        .mapToDouble(Double::parseDouble)
                        .sum())
                .sum();
    }

    Double getAverageExpenseSum() {
        return expensesData.deputyDataMap
                .keySet()
                .stream()
                .mapToDouble(this::getDeputyExpenseSum)
                .sum()
                / expensesData.deputyDataMap.size();
    }

}



