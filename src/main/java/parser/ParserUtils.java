package parser;

import command.*;
import grid.Coordinate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rodrigo on 12/05/2016.
 */
public class ParserUtils {

    private final static Pattern coordPattern = Pattern.compile("(turn on|turn off|toggle){1}\\s*(\\d+,\\d+){1}\\s*(through){1}\\s*(\\d+,\\d+){1}");

    public static MyCommand parseCommand(String raw) throws InvalidCommandException {
        Matcher matcher = getCoordPattern().matcher(raw);
        if(!matcher.matches()){
            throw new InvalidCommandException("Commoand " + raw + " invalid.");
        }
        String command = matcher.group(1);
        Coordinate c1 = new Coordinate(matcher.group(2));
        Coordinate c2 = new Coordinate(matcher.group(4));
        MyCommand myCommand = getCommand(command, c1, c2);
        myCommand.normalize();
        return myCommand;
    }

    private static MyCommand getCommand(String command, Coordinate c1, Coordinate c2) throws InvalidCommandException {
        switch (command){
            case "turn on":
                return new TurnOn(c1, c2);
            case "turn off":
                return new TurnOff(c1, c2);
            case "toggle":
                return new ToggleLights(c1, c2);
        }
        throw new InvalidCommandException("Command [" + command + "] is invalid.");
    }

    public static Pattern getCoordPattern() {
        return coordPattern;
    }
}
