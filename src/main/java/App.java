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
            System.out.println("after downloading deputy personal data: " + (midlleTime - startTime));

            System.out.println(deputyPersonalData.getDeputyID(deputyPersonalData.deputies.get(0).name));
            System.out.println(deputyPersonalData.getDeputyID(deputyPersonalData.deputies.get(1).name));
            DeputyData expensesData = new DeputyData(deputyPersonalData);
            System.out.println(expensesData.deputyDataMap.get(1131));
            final long endTime = System.currentTimeMillis();
            System.out.println(" at the end: " + (endTime - midlleTime));

            displayStats(details, expensesData,deputyPersonalData);

            System.out.println(expensesData.deputyDataMap.get(130).getJSONObject("wyjazdy").toString());

            final long endEndTime = System.currentTimeMillis();
            System.out.println(" at the end: " + (endEndTime - endTime));


        } catch (IOException ex) {
            System.out.println(ex);
        } catch (JSONException ex) {
            System.out.println(ex);
        } catch (NumberFormatException ex) {
            System.out.println("first argument has to be a number. 7 or 8." + ex);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }


    }

    private static void displayStats(ParsingDetails details, DeputyData data, DeputyPersonalData deputyPersonalData) {
        ExpensesStats exStats = new ExpensesStats(data);
        System.out.println(details.minorFixesExpenses + "-> spent " + exStats.getDeputyMinorFixesSum(details.minorFixesExpenses.ID) + " PLN on minor fixes");
        System.out.println(details.expenseSum + "-> " + exStats.getDeputyExpenseSum(details.expenseSum.ID) + " PLN is the sum of his expenses");
        System.out.println("Deputies spent on average " + exStats.getAverageExpenseSum() + " PLN");

        TravelsStats trStats = new TravelsStats(data);
        System.out.println(" trips-most foreign journeys. " + deputyPersonalData.getDeputy(trStats.getMostForeignJourneysDeputyID()));
        System.out.println(" days -longest journey. " + deputyPersonalData.getDeputy(trStats.getLongestJourneyDeputyID()));
        System.out.println(" Longest abroad: "+deputyPersonalData.getDeputy(trStats.getLongestAbroadDeputyID()));
        System.out.println(" -most expensive Journey. "+deputyPersonalData.getDeputy(trStats.getMostExpensiveJourneyDeputyID()));
        System.out.println("\nDeputies, who visited Italy:");
        trStats.getWhoVisitedItaly().forEach(e ->System.out.println("\t"+deputyPersonalData.getDeputy(e)));
    }
}



