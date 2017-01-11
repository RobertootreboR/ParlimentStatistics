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
    String path;

    public ParsingDetails(Deputy expenseSum, Deputy minorFixesExpenses, Integer cadence, String path){
        this.expenseSum=expenseSum;
        this.minorFixesExpenses=minorFixesExpenses;
        this.cadence =cadence;
        this.path = path;
    }

    public ParsingDetails(Mode mode, Integer cadence, String path) {
        this.mode = mode;
        this.cadence =cadence;
        this.path = path;
    }

    public void updateIDs(DeputyPersonalData deputyPersonalData) {
        expenseSum.ID= deputyPersonalData.getDeputyID(expenseSum.name);
        minorFixesExpenses.ID = deputyPersonalData.getDeputyID(minorFixesExpenses.name);
    }
}


