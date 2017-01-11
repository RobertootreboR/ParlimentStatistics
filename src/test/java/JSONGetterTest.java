import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

/**
 * Created by robert on 18.12.16.
 */
public class JSONGetterTest {

    @Test
    public void getJsonTest() throws IOException{

        String test =JSONGetter.getJSON("https://api-v3.mojepanstwo.pl/dane/poslowie/174.json?layers=dataset");
        assertTrue(test.startsWith("{\"id\":\"174\",\"dataset\":\"poslowie\",\"url\":\"https:\\/\\/api-v3.mojepanstwo.pl\\/dane"));
        assertTrue(test.endsWith("rtosc_biuro_srodki_trwale\":399},\"layers\":{\"dataset\":null,\"channels\":null,\"page\":null,\"subscribers\":null},\"Aggs\":{\"_page\":{\"doc_count\":0,\"page\":{\"hits\":{\"total\":0,\"max_score\":null,\"hits\":[]}}}}}"));

    }

}