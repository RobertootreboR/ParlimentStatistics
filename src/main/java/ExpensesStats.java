/**
 * Created by robert on 15.12.16.
 */
public class ExpensesStats {
    private DeputyData expensesData;

    ExpensesStats(DeputyData expensesData) {
        this.expensesData = expensesData;
    }

    Double getDeputyMinorFixesSum(Integer deputyID) {
        Double result = 0.0;
        int yearCounter = expensesData.getLiczbaRocznikow(deputyID);
        int minorFixesIndex = expensesData.getMinorFixesIndex(deputyID);

        for (int i = 0; i < yearCounter; i++) {
            result += expensesData
                    .getPolaArrayFromRocznikiAt(i, deputyID)   // roczniki[i] -> pola
                    .optDouble(minorFixesIndex - 1);          // pola[i]
        }
        return result;
    }

    Double getDeputyExpenseSum(Integer deputyID) {
        Double result = 0.0;
        int yearCounter = expensesData.getLiczbaRocznikow(deputyID);

        for (int i = 0; i < yearCounter; i++) {
            result += expensesData
                    .getPolaArrayFromRocznikiAt(i, deputyID)
                    .toList()
                    .stream()
                    .map(String.class::cast)
                    .mapToDouble(Double::parseDouble)
                    .sum();
        }
        return result;
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



