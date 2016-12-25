import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert on 15.12.16.
 */
public class App {
    public static void main(String[] args) {

        try {
            final long startTime = System.currentTimeMillis();
            ParsingDetails details = new ArgumentParser().parseArguments(args);
            DeputyPersonalData deputyPersonalData = new DeputyPersonalData(details);

            deputyPersonalData.deputies.forEach(System.out::println);
            details.updateIDs(deputyPersonalData);

            final long midlleTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (midlleTime - startTime) );

            System.out.println(deputyPersonalData.getDeputyID(deputyPersonalData.deputies.get(0).name));
            System.out.println(deputyPersonalData.getDeputyID(deputyPersonalData.deputies.get(1).name));
            DeputyData expensesData = new DeputyData(deputyPersonalData);
            System.out.println(expensesData.deputyDataMap.get(1131));
            final long endTime = System.currentTimeMillis();
            System.out.println(" execution time normal: " + (endTime - midlleTime));



            ExpensesStats exStats = new ExpensesStats(expensesData);
            Double xx = 0.0;
            System.out.println(exStats.getDeputyMinorFixesSum(details.minorFixesExpenses.ID));
            System.out.println(exStats.getDeputyExpenseSum(details.expenseSum.ID));
            System.out.println(exStats.getAverageExpenseSum());


            System.out.println(expensesData.deputyDataMap.get(130).getJSONObject("wyjazdy").toString());
            TravelsStats deputy =new TravelsStats(expensesData);
            Integer podroznik = deputy.getMostForeignJourneysDeputyID();
            System.out.println(deputyPersonalData.getDeputy(podroznik));
            podroznik = deputy.getLongestJourneyDeputyID();
            System.out.println(deputyPersonalData.getDeputy(podroznik));
            podroznik = deputy.getMostExpensiveJourneyDeputyID();
            System.out.println(deputyPersonalData.getDeputy(podroznik));
            podroznik = deputy.getLongestAbroadDeputyID();
            System.out.println(deputyPersonalData.getDeputy(podroznik));
            List<Integer> italy = deputy.getWhoVisitedItaly();
            italy.forEach(System.out::println);



        } catch (IOException ex) {
            System.out.println(ex);
        } catch (JSONException ex) {
            System.out.println(ex);
        } catch (NumberFormatException ex) {
            System.out.println("first argument has to be a number. 7 or 8." +ex);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }


    }
}


