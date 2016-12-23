import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by robert on 15.12.16.
 */
public class App {
    public static void main(String[] args) {

        try {
            final long startTime = System.currentTimeMillis();
            ParsingDetails details = new ArgumentParser().parseArguments(args);
            DeputyData deputyData = new DeputyData(details);

            deputyData.deputies.forEach(System.out::println);
            details.updateIDs(deputyData);

            final long midlleTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (midlleTime - startTime) );

            System.out.println(deputyData.getDeputyID(deputyData.deputies.get(0).name));
            System.out.println(deputyData.getDeputyID(deputyData.deputies.get(1).name));
            ExpensesData expensesData = new ExpensesData(deputyData);
            System.out.println(expensesData.expensesMap.get(1131));
            final long endTime = System.currentTimeMillis();
            System.out.println(" execution time normal: " + (endTime - midlleTime));



            System.out.println(new ExpensesStats(details.expenseSum.ID,details.minorFixesExpenses.ID,expensesData).deputyMinorFixes);
            System.out.println(new ExpensesStats(details.expenseSum.ID,details.minorFixesExpenses.ID,expensesData).deputyExpenseSum);
            System.out.println(new ExpensesStats(details.expenseSum.ID,details.minorFixesExpenses.ID,expensesData).averageSum);


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


