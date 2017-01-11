import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by robert on 18.12.16.
 */
public class DataProcessingTest {
    String JSONtest1 = "{\n" +
            "   \"id\":\"255\",\n" +
            "   \"dataset\":\"poslowie\",\n" +
            "   \"url\":\"https:\\/\\/api-v3.mojepanstwo.pl\\/dane\\/poslowie\\/255\",\n" +
            "   \"mp_url\":\"https:\\/\\/mojepanstwo.pl\\/dane\\/poslowie\\/255\",\n" +
            "   \"schema_url\":\"https:\\/\\/api-v3.mojepanstwo.pl\\/schemas\\/dane\\/poslowie.json\",\n" +
            "   \"global_id\":\"1556756\",\n" +
            "   \"slug\":\"munyama-killion\",\n" +
            "   \"score\":null,\n" +
            "   \"data\":{ },\n" +
            "   \"layers\":{\n" +
            "      \"dataset\":null,\n" +
            "      \"channels\":null,\n" +
            "      \"page\":null,\n" +
            "      \"subscribers\":null,\n" +
            "      \"wyjazdy\":[\n" +
            "         {\n" +
            "            \"kraj\":\"Włochy\",\n" +
            "            \"liczba_dni\":\"5\",\n" +
            "            \"koszt_suma\":\"100.50\"\n" +
            "         },\n" +
            "         {\n" +
              "            \"kraj\":\"Angola\",\n" +
            "            \"liczba_dni\":\"6\",\n" +
            "            \"koszt_suma\":\"4944.50\"\n" +
            "         }" +
            "      ],\n" +
            "      \"wydatki\":{\n" +
            "         \"liczba_pol\":2,\n" +
            "         \"liczba_rocznikow\":2,\n" +
            "         \"punkty\":[\n" +
            "            {\n" +
            "               \"tytul\":\"Koszty konserwacji i naprawy sprz\\u0119tu technicznego biura poselskiego oraz koszty jego eksploatacji\",\n" +
            "               \"numer\":\"1\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"tytul\":\"Koszty drobnych napraw i remont\\u00f3w lokalu biura poselskiego\",\n" +
            "               \"numer\":\"2\"\n" +
            "            }\n" +
            "         ],\n" +
            "         \"roczniki\":[\n" +
            "            {\n" +
            "               \"pola\":[\n" +
            "                  \"50.00\",\n" +
            "                  \"50.00\"\n" +
            "               ],\n" +
            "               \"dokument_id\":\"860728\",\n" +
            "               \"rok\":\"2013\"\n" +
            "            },\n" +
            "            { \"pola\":[\n" +
            "                  \"50.00\",\n" +
            "                  \"0.00\"\n" +
            "               ],\n" +
            "               \"dokument_id\":\"860728\",\n" +
            "               \"rok\":\"2013\"}\n" +
            "         ]\n" +
            "      }\n" +
            "   },\n" +
            "   \"Aggs\":{ }\n" +
            "}";

    String JSONtest2 = "{\n" +
            "   \"id\":\"300\",\n" +
            "   \"dataset\":\"poslowie\",\n" +
            "   \"url\":\"https:\\/\\/api-v3.mojepanstwo.pl\\/dane\\/poslowie\\/255\",\n" +
            "   \"mp_url\":\"https:\\/\\/mojepanstwo.pl\\/dane\\/poslowie\\/255\",\n" +
            "   \"schema_url\":\"https:\\/\\/api-v3.mojepanstwo.pl\\/schemas\\/dane\\/poslowie.json\",\n" +
            "   \"global_id\":\"1556756\",\n" +
            "   \"slug\":\"baba-jaga\",\n" +
            "   \"score\":null,\n" +
            "   \"data\":{ },\n" +
            "   \"layers\":{\n" +
            "      \"dataset\":null,\n" +
            "      \"channels\":null,\n" +
            "      \"page\":null,\n" +
            "      \"subscribers\":null,\n" +
            "      \"wyjazdy\":[\n" +
            "         {\n" +
            "            \"kraj\":\"Niemcy\",\n" +
            "            \"liczba_dni\":\"50\",\n" +
            "            \"koszt_suma\":\"20.30\"\n" +
            "         },\n" +
            "         {\n" +
            "            \"kraj\":\"Angola\",\n" +
            "            \"liczba_dni\":\"6\",\n" +
            "            \"koszt_suma\":\"40.70\"\n" +
            "         }" +
            "      ],\n" +
            "      \"wydatki\":{\n" +
            "         \"liczba_pol\":2,\n" +
            "         \"liczba_rocznikow\":1,\n" +
            "         \"punkty\":[\n" +
            "            {\n" +
            "               \"tytul\":\"Koszty konserwacji i naprawy sprz\\u0119tu technicznego biura poselskiego oraz koszty jego eksploatacji\",\n" +
            "               \"numer\":\"1\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"tytul\":\"Koszty drobnych napraw i remont\\u00f3w lokalu biura poselskiego\",\n" +
            "               \"numer\":\"2\"\n" +
            "            }\n" +
            "         ],\n" +
            "         \"roczniki\":[\n" +
            "            {\n" +
            "               \"pola\":[\n" +
            "                  \"10.00\",\n" +
            "                  \"10.00\"\n" +
            "               ],\n" +
            "               \"dokument_id\":\"860728\",\n" +
            "               \"rok\":\"2013\"\n" +
            "            },\n" +
            "            { }\n" +
            "         ]\n" +
            "      }\n" +
            "   },\n" +
            "   \"Aggs\":{ }\n" +
            "}";


    @Test
    public void ExpensesTest(){
        JSONObject test1 = new JSONObject(JSONtest1).getJSONObject("layers");
        JSONObject test2 = new JSONObject(JSONtest2).getJSONObject("layers");
        HashMap<Integer, JSONObject> deputyDataMap = new HashMap<>();
        deputyDataMap.put(255,test1);
        deputyDataMap.put(300,test2);
        DeputyData deputyData = new DeputyData(deputyDataMap);

        ExpensesStats expensesStats = new ExpensesStats(deputyData);
        Double xr = expensesStats.getDeputyExpenseSum(255);
        assertEquals(new Double(20.0),expensesStats.getDeputyExpenseSum(300));
        assertEquals(new Double(50.0),expensesStats.getDeputyMinorFixesSum(255));
        assertEquals(new Double(85.0),expensesStats.getAverageExpenseSum());


        TravelsStats travelsStats = new TravelsStats(deputyData);

        assertEquals(new Integer(255), travelsStats.getMostForeignJourneysDeputyID());
        assertEquals(new Integer(300), travelsStats.getLongestJourneyDeputyID());
        assertEquals(new Integer(300), travelsStats.getLongestAbroadDeputyID());
        assertEquals(new Integer(255), travelsStats.getMostExpensiveJourneyDeputyID());
        assertEquals(1, travelsStats.getWhoVisitedItaly().size());

    }

}