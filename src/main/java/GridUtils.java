import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rhn on 5/12/16.
 */
public class GridUtils {

    private final static Pattern coordPattern = Pattern.compile("(turn on|turn off|toggle){1}\\s*(\\d+,\\d+){1}\\s*(through){1}\\s*(\\d+,\\d+){1}");


    public static MyCommand parseCommand(String raw) throws InvalidCommandException {
        Matcher matcher = getCoordPattern().matcher(raw);
        if(!matcher.matches()){
            throw new InvalidCommandException("Commoand " + raw + " invalid.");
        }
        String command = matcher.group(1);
        Coordinate c1 = new Coordinate(matcher.group(2));
        Coordinate c2 = new Coordinate(matcher.group(4));
        MyCommand myCommand = null;
        switch (command){
        case "turn on":
            myCommand = new TurnOn(c1, c2);
            break;
        case "turn off":
            myCommand = new TurnOff(c1, c2);
            break;
        case "toggle":
            myCommand = new ToggleLights(c1, c2);
            break;
        default:
            throw new InvalidCommandException("Command [" + command + "] is invalid.");
        }
        myCommand.normalize();
        return myCommand;
    }

    public static Pattern getCoordPattern() {
        return coordPattern;
    }

}
