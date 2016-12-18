import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by robert on 16.12.16.
 */
public class DeputyData {
    List<Deputy> deputies = new CopyOnWriteArrayList<Deputy>();
    // parses over the JSON https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]= {parameter}
    // using JSONGetter
    // saves deputies from given cadence in a list
}
