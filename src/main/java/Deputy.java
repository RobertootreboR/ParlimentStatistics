/**
 * Created by robert on 15.12.16.
 */
public class Deputy {
    String name;
    Integer ID;

    public Deputy(String firstName, String lastName, Integer ID){
        this.name=(lastName + "-" + firstName).toLowerCase();
        this.ID = ID;
    }
    public Deputy(String firstName, String lastName){
        this.name=(lastName + "-" + firstName).toLowerCase();
    }

    public Deputy(String name, Integer ID){
        this.name=name;
        this.ID = ID;
    }
    // Enable third name in constructor (Middle name)!!!!!!!!!!



}
