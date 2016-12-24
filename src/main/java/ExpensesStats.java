import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by robert on 15.12.16.
 */
public class ExpensesStats {
    Double deputyExpenseSum;
    Double deputyMinorFixes;
    Double averageSum;

    ExpensesStats(Integer deputyToCountExpenseID, Integer deputyToCountMinorFixesID, ExpensesData expensesData) {
        this.deputyMinorFixes = deputyMinorFixesCount(deputyToCountMinorFixesID, expensesData);
        this.deputyExpenseSum = deputyExpenseSum(deputyToCountExpenseID, expensesData);
        this.averageSum = averageSumCount(expensesData);

    }


    private Double deputyMinorFixesCount(Integer deputyToCountMinorFixesID, ExpensesData expensesData) {
        int minorfixesNumber = -1;
        Double result = 0.0;
        int yearCounter = expensesData
                .expensesMap
                .get(deputyToCountMinorFixesID)
                .getInt("liczba_rocznikow");
        JSONArray jsonArray = expensesData
                .expensesMap
                .get(deputyToCountMinorFixesID)
                .getJSONArray("punkty");

        for (Object object : jsonArray) {
            if (((JSONObject) object).getString("tytul").equals("Koszty drobnych napraw i remontów lokalu biura poselskiego"))
                minorfixesNumber = ((JSONObject) object).getInt("numer");
        }
        //if(minorfixesNumber == -1) throw new Exception("\"Koszty drobnych napraw i remontów lokalu biura poselskiego\" are no more supplied by sejmowe API");


        for (int i = 0; i < yearCounter; i++) {
            result += expensesData
                    .expensesMap
                    .get(deputyToCountMinorFixesID)
                    .getJSONArray("roczniki")
                    .optJSONObject(i)
                    .getJSONArray("pola")
                    .optDouble(minorfixesNumber - 1);
        }
        return result;
    }

    private Double deputyExpenseSum(Integer deputyToCountExpenseID, ExpensesData expensesData) {
        Double result = 0.0;
        int yearCounter = expensesData
                .expensesMap
                .get(deputyToCountExpenseID)
                .getInt("liczba_rocznikow");

        for (int i = 0; i < yearCounter; i++) {
            result += expensesData
                    .expensesMap
                    .get(deputyToCountExpenseID)
                    .getJSONArray("roczniki")
                    .optJSONObject(i)
                    .getJSONArray("pola")
                    .toList()
                    .stream()
                    .map(String.class::cast)
                    .mapToDouble(Double::parseDouble)
                    .sum();
        }
        return result;
    }


    private Double averageSumCount(ExpensesData expensesData) {
        return expensesData.expensesMap
                .keySet()
                .stream()
                .map(Integer.class::cast)
                .mapToDouble(e -> deputyExpenseSum(e, expensesData))
                .sum()
                / expensesData.expensesMap.size();
    }


}



