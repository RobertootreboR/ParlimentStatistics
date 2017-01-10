package Exceptions;

import java.util.function.Function;

/**
 * Created by robert on 10.01.17.
 */
public class InvalidDataFormatException extends RuntimeException {
    public InvalidDataFormatException( Object object, Class<?> clas,String message) {
        System.out.print(message+"\nExpected: org.json.JSONObject. Received: "+object.getClass() +'\n' +"thrown in " + clas +  '\n');
    }
}
