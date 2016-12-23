/**
 * Created by robert on 15.12.16.
 */
public class ParsingDetails {
    Deputy expenseSum;
    Deputy minorFixesExpenses;
    Integer cadence;

    public ParsingDetails(Deputy expenseSum, Deputy minorFixesExpenses, Integer cadence){
        this.expenseSum=expenseSum;
        this.minorFixesExpenses=minorFixesExpenses;
        this.cadence =cadence;
    }

    public void updateIDs(DeputyData deputyData) {
        expenseSum.ID=deputyData.getDeputyID(expenseSum.name);
        minorFixesExpenses.ID =deputyData.getDeputyID(minorFixesExpenses.name);
    }
}


