package Exceptions;


/**
 * Created by robert on 10.01.17.
 */
public class InvalidDataFormatException extends RuntimeException {
    public InvalidDataFormatException( Object object,String message) {
        System.out.print(message+"\nExpected: org.json.JSONObject. Received: "+object.getClass() +'\n');
    }
    public InvalidDataFormatException(String message) {
       super(message);
    }
}
