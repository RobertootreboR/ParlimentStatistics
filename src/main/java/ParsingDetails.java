/**
 * Created by robert on 15.12.16.
 */
public class ParsingDetails {
    public enum Mode {
        Diplay,UpdateData
    }

    Deputy expenseSum;
    Deputy minorFixesExpenses;
    Integer cadence;
    Mode mode =Mode.Diplay;

    public ParsingDetails(Deputy expenseSum, Deputy minorFixesExpenses, Integer cadence){
        this.expenseSum=expenseSum;
        this.minorFixesExpenses=minorFixesExpenses;
        this.cadence =cadence;
    }

    public ParsingDetails(Mode mode, Integer cadence) {
        this.mode = mode;
        this.cadence =cadence;
    }

    public void updateIDs(DeputyPersonalData deputyPersonalData) {
        expenseSum.ID= deputyPersonalData.getDeputyID(expenseSum.name);
        minorFixesExpenses.ID = deputyPersonalData.getDeputyID(minorFixesExpenses.name);
    }
}


