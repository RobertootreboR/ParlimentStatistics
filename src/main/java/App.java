import Exceptions.InvalidDataFormatException;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by robert on 15.12.16.
 */
public class App {
    public static void main(String[] args) {

        try {

            final long startTime = System.currentTimeMillis();
            ParsingDetails details = new ArgumentParser().parseArguments(args);

            if (details.mode == ParsingDetails.Mode.UpdateData)
                new DataDownloader().go(details);
            else {
                DeputyPersonalData deputyPersonalData = new DeputyPersonalData(details);
                details.updateIDs(deputyPersonalData);
                DeputyData deputyData = new DeputyData(deputyPersonalData, details);
                displayStats(details, deputyData, deputyPersonalData);
                final long endTime = System.currentTimeMillis();
                System.out.println(" at the end: " + (endTime - startTime));

            }
        } catch (IOException | JSONException ex) {
            System.out.println(ex + ex.getMessage());
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            System.out.println("first argument has to be a number. 7 or 8." + ex);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            ex.getCause();
        } catch (InvalidDataFormatException ex){
            System.out.print(ex);
        }

    }

    private static void displayStats(ParsingDetails details, DeputyData data, DeputyPersonalData deputyPersonalData) {
        ExpensesStats exStats = new ExpensesStats(data);
        System.out.println(details.minorFixesExpenses + "-> spent " + exStats.getDeputyMinorFixesSum(details.minorFixesExpenses.ID) + " PLN on minor fixes");
        System.out.println(details.expenseSum + "-> " + exStats.getDeputyExpenseSum(details.expenseSum.ID) + " PLN is the sum of his expenses");
        System.out.println("Deputies spent on average " + new BigDecimal(exStats.getAverageExpenseSum()).setScale(2, BigDecimal.ROUND_HALF_UP) + " PLN");

        TravelsStats trStats = new TravelsStats(data);
        System.out.println("Most foreign journeys. " + deputyPersonalData.getDeputy(trStats.getMostForeignJourneysDeputyID()));
        System.out.println("longest journey. " + deputyPersonalData.getDeputy(trStats.getLongestJourneyDeputyID()));
        System.out.println("Longest abroad: " + deputyPersonalData.getDeputy(trStats.getLongestAbroadDeputyID()));
        System.out.println("Most expensive Journey. " + deputyPersonalData.getDeputy(trStats.getMostExpensiveJourneyDeputyID()));
        System.out.println("\nDeputies, who visited Italy:");
        trStats.getWhoVisitedItaly().forEach(e -> System.out.println("\t" + deputyPersonalData.getDeputy(e)));
        System.out.print(trStats.getWhoVisitedItaly().size());

    }


}



