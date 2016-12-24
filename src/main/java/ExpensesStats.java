import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by robert on 15.12.16.
 */
public class ExpensesStats {
    Double deputyExpenseSum;
    Double deputyMinorFixes;
    Double averageSum;

    ExpensesStats(Integer deputyToCountExpenseID, Integer deputyToCountMinorFixesID, DeputyData expensesData) {
        this.deputyMinorFixes = getDeputyMinorFixesSum(deputyToCountMinorFixesID, expensesData);
        this.deputyExpenseSum = getDeputyExpenseSum(deputyToCountExpenseID, expensesData);
        this.averageSum = getAverageExpenseSum(expensesData);// zmieniać to expenses data???

    }


    private Double getDeputyMinorFixesSum(Integer deputyToCountMinorFixesID, DeputyData expensesData) {
        int minorFixesNumber = -1;
        Double result = 0.0;
        int yearCounter = expensesData.getLiczbaRocznikow(deputyToCountMinorFixesID);
        JSONArray punktyArray = expensesData.getPunktyArray(deputyToCountMinorFixesID);

        for (Object object : punktyArray) {
            if (((JSONObject) object).getString("tytul").equals("Koszty drobnych napraw i remontów lokalu biura poselskiego"))
                minorFixesNumber = ((JSONObject) object).getInt("numer");
        }


        for (int i = 0; i < yearCounter; i++) {
            result += expensesData
                    .deputyDataMap
                    .get(deputyToCountMinorFixesID)
                    .getJSONObject("wydatki")
                    .getJSONArray("roczniki")
                    .optJSONObject(i)
                    .getJSONArray("pola")
                    .optDouble(minorFixesNumber - 1);
        }
        return result;
    }

    private Double getDeputyExpenseSum(Integer deputyToCountExpenseID, DeputyData expensesData) {
        Double result = 0.0;
        int yearCounter = expensesData.getLiczbaRocznikow(deputyToCountExpenseID);

        for (int i = 0; i < yearCounter; i++) {
            result += expensesData
                    .deputyDataMap
                    .get(deputyToCountExpenseID)
                    .getJSONObject("wydatki")
                    .getJSONArray("roczniki")
                    .optJSONObject(i)
                    .getJSONArray("pola")               // da się dać do funkcjii?? uzyte dwukrotnie!!!
                    .toList()
                    .stream()
                    .map(String.class::cast)
                    .mapToDouble(Double::parseDouble)
                    .sum();
        }
        return result;
    }


    private Double getAverageExpenseSum(DeputyData expensesData) {
        return expensesData.deputyDataMap
                .keySet()
                .stream()
                .mapToDouble(e -> getDeputyExpenseSum(e, expensesData))
                .sum()
                / expensesData.deputyDataMap.size();
    }
}



