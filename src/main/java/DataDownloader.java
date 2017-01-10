import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by robert on 09.01.17.
 */
public class DataDownloader {

    void go(ParsingDetails details) throws IOException {
        File dataSet = new File(details.path + "/DeputyDataSets");
        if (!dataSet.exists())
            dataSet.mkdir();

        File deputies = new File(details.path + "/Deputies");
        if (!deputies.exists())
            deputies.mkdir();

        Integer datasets = getDatasetsNumber(details);
        for (int i = 1; i <= datasets; i++) {
            final int y = i;
            new Thread(() -> {
                try {
                    save(details, y);
                } catch (IOException ex) {
                    System.out.println("Couldn't save dataset number" + y + "from cadence" + details.cadence + ". " + ex);
                }
            }).start();
        }
    }

    private void save(ParsingDetails details, Integer datasetNum) throws IOException {
        String deputiesStr = new JSONGetter()
                .getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=" + details.cadence + "&_type=objects&page=" + datasetNum);
        DeputyPersonalData deputyPersonalData = new DeputyPersonalData(deputiesStr);
        saveToFile(deputiesStr, details.path + "/DeputyDataSets/DeputySet" + details.cadence + "_" + datasetNum);

        for (Deputy deputy : deputyPersonalData.deputies) {
            int chances = 5;
            while (chances > 0) {
                try {
                    saveToFile(new JSONGetter().getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie/" + deputy.ID + ".json?layers[]=wydatki&layers[]=wyjazdy")
                            , details.path + "/Deputies/Deputy" + deputy.ID);
                    chances = 0;
                } catch (IOException ex) {
                    if (--chances == 0) throw new IOException("Unable to save file with expenses!");
                }
            }
        }
    }

    private void saveToFile(String text, String path) throws IOException {
        PrintWriter writer = new PrintWriter(path);
        writer.print(text);
        writer.close();
    }

    public static Integer getDatasetsNumber(ParsingDetails details) throws IOException {
        JSONObject object;
        if (details.mode == ParsingDetails.Mode.Diplay) {
            object = new JSONObject(
                    Files.lines(Paths.get(details.path + "/DeputyDataSets/DeputySet" + details.cadence + "_" + 1)).reduce("", String::concat));
        } else {
            object = new JSONObject(new JSONGetter().getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=" + details.cadence));
        }
        String[] tmp = object
                .getJSONObject("Links")
                .getString("last")
                .split("=");
        return Integer.parseInt(tmp[tmp.length - 1]);

    }
}
