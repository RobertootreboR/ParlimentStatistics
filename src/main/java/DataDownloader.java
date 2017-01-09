import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by robert on 09.01.17.
 */
public class DataDownloader {

    public void go(Integer cadence) throws IOException{
        Integer datasets = getDatasetsNumber(cadence);

        for(int i=1; i<=datasets; i++){
            final int y =i;
            new Thread(() -> {
                                try {
                                    save(cadence,y);
                                    }catch(IOException ex){ System.out.println("Couldn't save dataset number" + y + "from cadence" + cadence +". " + ex);}}).start();
        }
    }


    public void save(Integer cadence,Integer datasetNum)throws IOException{
        String deputiesStr = new JSONGetter()
               .getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]="+cadence +"&_type=objects&page="+datasetNum);
        DeputyPersonalData deputyPersonalData = new DeputyPersonalData(deputiesStr);
        saveToFile(deputiesStr,"/home/robert/Sejm/DeputyDataSets/DeputySet"+cadence+"_"+datasetNum);

        for(Deputy deputy: deputyPersonalData.deputies) {
            int chances = 5;
            while (chances > 0) {
                try {
                    saveToFile(new JSONGetter().getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie/" + deputy.ID + ".json?layers[]=wydatki&layers[]=wyjazdy"), "/home/robert/Sejm/Deputies/Deputy" + deputy.ID);
                    chances = 0;
                }catch (IOException ex) {
                    if(--chances == 0) throw new IOException("Unable to save file with expenses!");
                }
            }
        }
    }

    private void saveToFile(String text, String path) throws IOException{
        PrintWriter writer = new PrintWriter(path);
        writer.print(text);
        writer.close();
    }

    public static Integer getDatasetsNumber(Integer cadence) throws IOException{
        JSONObject object = new JSONObject(
                                    new JSONGetter()
                                    .getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]="+cadence)
                                    );
        String[] tmp =object
                        .getJSONObject("Links")
                        .getString("last")
                        .split("=");
        return Integer.parseInt(tmp[tmp.length-1]);

    }
}
