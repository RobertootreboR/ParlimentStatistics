import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by robert on 18.12.16.
 */
public class AppTest {
    @Test
    public void main() throws Exception {
        ParsingDetails details7 = new ParsingDetails(new Deputy("krystyna", "pawlowicz"), new Deputy("krystyna", "pawlowicz"), 7, "/home/robert/Sejm");

        DeputyPersonalData deputyPersonalData = new DeputyPersonalData(details7);
        details7.updateIDs(deputyPersonalData);
        DeputyData deputyData = new DeputyData(deputyPersonalData, details7);
        ExpensesStats expensesStats = new ExpensesStats(deputyData);
        TravelsStats travelsStats = new TravelsStats(deputyData);

        Double minorFixes = expensesStats.getDeputyMinorFixesSum(details7.minorFixesExpenses.ID);
        assertTrue(4266.48 < minorFixes && 4266.50 > minorFixes);

        Double expenseSum = expensesStats.getDeputyExpenseSum(details7.expenseSum.ID);
        assertTrue(430531.19 < expenseSum && 430531.21 > expenseSum);

        Double average = new BigDecimal(expensesStats.getAverageExpenseSum()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertTrue(272247.60 < average && 272247.62 > average);

        assertEquals(new Integer(134), travelsStats.getMostForeignJourneysDeputyID());
        assertEquals(new Integer(255), travelsStats.getLongestJourneyDeputyID());
        assertEquals(new Integer(134), travelsStats.getLongestAbroadDeputyID());
        assertEquals(new Integer(376), travelsStats.getMostExpensiveJourneyDeputyID());
        assertEquals(50, travelsStats.getWhoVisitedItaly().size());

        //430531.2
        // average 272247.61
        // Most foreign journeys. name: iwinski-tadeusz id: 134
        //longest journey. name: munyama-killion id: 255
        //Longest abroad: name: iwinski-tadeusz id: 134
        //Most expensive Journey. name: szejnfeld-adam id: 376


        ParsingDetails details8 = new ParsingDetails(new Deputy("krystyna", "pawlowicz"), new Deputy("krystyna", "pawlowicz"), 8, "/home/robert/Sejm");

        DeputyPersonalData deputyPersonalData8 = new DeputyPersonalData(details8);
        details8.updateIDs(deputyPersonalData8);
        DeputyData deputyData8 = new DeputyData(deputyPersonalData8, details8);
        ExpensesStats expensesStats8 = new ExpensesStats(deputyData8);
        TravelsStats travelsStats8 = new TravelsStats(deputyData8);


        Double average8 = new BigDecimal(expensesStats8.getAverageExpenseSum()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertTrue(144882.95 < average8 && 144882.97 > average8);

        assertEquals(new Integer(81), travelsStats8.getMostForeignJourneysDeputyID());
        assertEquals(new Integer(255), travelsStats8.getLongestJourneyDeputyID());
        assertEquals(new Integer(81), travelsStats8.getLongestAbroadDeputyID());
        assertEquals(new Integer(414), travelsStats8.getMostExpensiveJourneyDeputyID());
        assertEquals(25, travelsStats8.getWhoVisitedItaly().size());
    }

}