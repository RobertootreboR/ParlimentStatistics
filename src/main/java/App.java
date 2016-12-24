import org.json.JSONException;

import java.io.IOException;

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



            System.out.println(new ExpensesStats(details.expenseSum.ID,details.minorFixesExpenses.ID,expensesData).deputyMinorFixes);
            System.out.println(new ExpensesStats(details.expenseSum.ID,details.minorFixesExpenses.ID,expensesData).deputyExpenseSum);
            System.out.println(new ExpensesStats(details.expenseSum.ID,details.minorFixesExpenses.ID,expensesData).averageSum);
            System.out.println(expensesData.deputyDataMap.get(130).getJSONObject("wyjazdy").toString());
            if(expensesData.deputyDataMap.get(130).getJSONObject("wyjazdy").toString().equals("{}")) System.out.println("yaaay");
            Integer podroznik = new TravelsStats(expensesData).mostForeignJourneysID;
            System.out.println(deputyPersonalData.getDeputy(podroznik));



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


