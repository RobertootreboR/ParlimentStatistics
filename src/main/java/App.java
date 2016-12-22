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

            ParsingDetails details = new ArgumentParser().parseArguments(args);
            DeputyData deputyData = new DeputyData(details);
            deputyData.deputies.forEach(System.out::println);


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


