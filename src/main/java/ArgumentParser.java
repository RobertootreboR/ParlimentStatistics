/**
 * Created by robert on 15.12.16.
 */
public class ArgumentParser {

    ParsingDetails parseArguments(String[] args) {
        if (args.length < 5)
            throw new IllegalArgumentException("Arguments shall be like: [7-8] firstname1 lastname1 firstname2 lastname2");

        if(Integer.parseInt(args[0])<7 || Integer.parseInt(args[0])>8)
            throw new IllegalArgumentException("FirstArgument has to be a number: 7 or 8.");
        //ewentualnie dodać ogarnięcie imion - czy są literami (w tym polskimi!!!)
        return new ParsingDetails(
                new Deputy(args[1],args[2]),
                new Deputy(args[3],args[4]),
                Integer.parseInt(args[0]));

    }

}
