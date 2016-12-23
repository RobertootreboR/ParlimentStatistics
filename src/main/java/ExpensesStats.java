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
    Double averageSum=0.0;

    ExpensesStats(Integer deputyToCountExpenseID, Integer deputyToCountMinorFixesID, ExpensesData expensesData) {
        this.deputyMinorFixes = deputyMinorFixesCount(deputyToCountMinorFixesID, expensesData);
        this.deputyExpenseSum = deputyExpenseSum(deputyToCountExpenseID.doubleValue(), expensesData);
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

    private double deputyExpenseSum(Double deputyToCountExpenseIDD, ExpensesData expensesData) {
        Integer deputyToCountExpenseID = deputyToCountExpenseIDD.intValue();
        Double result = 0.0;
        int yearCounter = expensesData
                .expensesMap
                .get(deputyToCountExpenseID)
                .getInt("liczba_rocznikow");

        for (int i = 0; i < yearCounter; i++) {
            List<Object> list = expensesData
                    .expensesMap
                    .get(deputyToCountExpenseID)
                    .getJSONArray("roczniki")
                    .optJSONObject(i)
                    .getJSONArray("pola")
                    .toList();                  // sprobować z optional!!!!!!!!!!!!!!!

            for (Object object : list)
                result += Double.parseDouble((String) object);

        }
        return result;
    }


    private Double averageSumCount(ExpensesData expensesData){
        return expensesData.expensesMap
                .keySet()
                .stream()
                .mapToDouble(e -> e)
                .reduce(0.0,(acc,e) -> acc + deputyExpenseSum(e,expensesData))
                /expensesData.expensesMap.size();  //brzydkie rzutowania!! ogarnąć je jakoś albo bez strumienia robić
    }



}



