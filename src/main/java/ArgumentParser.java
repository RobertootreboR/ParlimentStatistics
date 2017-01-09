/**
 * Created by robert on 15.12.16.
 */
public class ArgumentParser {

    ParsingDetails parseArguments(String[] args) {
        if(args.length ==2
                && args[0].equals("-u")
                &&(Integer.parseInt(args[1]) == 7 || Integer.parseInt(args[1]) ==8))
                    return new ParsingDetails(ParsingDetails.Mode.UpdateData, Integer.parseInt(args[1]));
        if (args.length < 5)
            throw new IllegalArgumentException("Arguments shall be like: [7-8] firstname1 lastname1 firstname2 lastname2");

        if (Integer.parseInt(args[0]) < 7 || Integer.parseInt(args[0]) > 8)
            throw new IllegalArgumentException("FirstArgument has to be a number: 7 or 8.");
        return new ParsingDetails(
                new Deputy(args[1], args[2]),
                new Deputy(args[3], args[4]),
                Integer.parseInt(args[0])
                );
    }
}
