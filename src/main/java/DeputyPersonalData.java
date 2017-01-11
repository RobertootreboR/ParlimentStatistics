import Exceptions.InvalidDataFormatException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by robert on 16.12.16.
 */
public class DeputyPersonalData {
    List<Deputy> deputies = new LinkedList<Deputy>();

    DeputyPersonalData(ParsingDetails details) throws IOException {
        int numberOfSets = DataDownloader.getDatasetsNumber(details);
        for (int i = 1; i <= numberOfSets; i++)
            this.deputies.addAll(fillList(loadDeputyData(details, i)));
    }

    DeputyPersonalData(String deputiesStr) {     // used in save mode
        this.deputies = fillList(deputiesStr);
    }

    private String loadDeputyData(ParsingDetails details, Integer datasetNum) throws IOException {
        return Files.lines(Paths.get(details.path + "/DeputyDataSets/DeputySet" + details.cadence + "_" + datasetNum)).reduce("", String::concat);
    }

    LinkedList<Deputy> fillList(String deputiesStr) {
        LinkedList<Deputy> deputiesTMP = new LinkedList<>();

        for (Object object : new JSONObject(deputiesStr).getJSONArray("Dataobject"))  //make a JSONObject from the downloaded string, take an array from the "DataObject" key
            deputiesTMP.add(new Deputy(getDeputyName(object), getDeputyID(object)));

        return deputiesTMP;
    }

    private String getDeputyName(Object deputy) {
        try {
            JSONObject dep = (JSONObject) deputy;
            return dep.getString("slug");
        } catch (ClassCastException ex) {
            throw new InvalidDataFormatException(deputy, getClass(), "External API (mojepanstwo.pl/api/sejmometr) returned invalid data format");
        }
    }

    Deputy getDeputy(Integer ID) {
        for (Deputy deputy : deputies)
            if (deputy.ID.equals(ID))
                return deputy;
        throw new IllegalArgumentException("Deputy ID " + ID + " is invalid. Try again");
    }

    private Integer getDeputyID(Object deputy) {
        try {
            JSONObject dep = (JSONObject) deputy;
            return Integer.parseInt(dep.getString("id"));
        } catch (ClassCastException ex) {
            throw new InvalidDataFormatException(deputy, getClass(), "External API (mojepanstwo.pl/api/sejmometr) returned invalid data format");
        }
    }

    Integer getDeputyID(String name) {
        for (Deputy deputy : deputies)
            if (deputy.name.equals(name) || deputy.name.startsWith(name + "-"))
                return deputy.ID;
        throw new IllegalArgumentException("Deputy name " + name + " is invalid. Try again");
    }
}
