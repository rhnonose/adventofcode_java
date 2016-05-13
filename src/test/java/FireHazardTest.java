import command.*;
import grid.Coordinate;
import grid.Grid;
import org.junit.Test;
import parser.ParserUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by Rodrigo Nonose on 5/12/16.
 */
public class FireHazardTest {

    public static final int GRID_SIZE = 1000;
    public static final int FINAL_ANSWER = 17836115;

    @Test
    public void testRegex(){
        Pattern coordPattern = ParserUtils.getCoordPattern();
        Matcher turnOnMatcher = coordPattern.matcher("turn on 1,1 through 5,2");
        assertTrue("Pattern should match", turnOnMatcher.matches());
        assertEquals("Should capture the command", "turn on", turnOnMatcher.group(1));
        assertEquals("Should capture the first coordinate", "1,1", turnOnMatcher.group(2));
        assertEquals("Should capture the separator", "through", turnOnMatcher.group(3));
        assertEquals("Should capture the second coordinate", "5,2", turnOnMatcher.group(4));

        Matcher turnOffMatcher = coordPattern.matcher("turn off 2,5 through 10,300");
        assertTrue("Pattern should match", turnOffMatcher.matches());
        assertEquals("Should capture the command", "turn off", turnOffMatcher.group(1));
        assertEquals("Should capture the first coordinate", "2,5", turnOffMatcher.group(2));
        assertEquals("Should capture the separator", "through", turnOffMatcher.group(3));
        assertEquals("Should capture the second coordinate", "10,300", turnOffMatcher.group(4));

        Matcher toggleMatcher = coordPattern.matcher("toggle 222,333 through 1,3");
        assertTrue("Pattern should match", toggleMatcher.matches());
        assertEquals("Should capture the command", "toggle", toggleMatcher.group(1));
        assertEquals("Should capture the first coordinate", "222,333", toggleMatcher.group(2));
        assertEquals("Should capture the separator", "through", toggleMatcher.group(3));
        assertEquals("Should capture the second coordinate", "1,3", toggleMatcher.group(4));

        Matcher invalidCommandMatcher = coordPattern.matcher("destroy 1,1 through 5,2");
        assertFalse("Pattern should not match", invalidCommandMatcher.matches());
    }

    @Test
    public void testCoordinate(){
        Coordinate coordinate = new Coordinate("10,20");
        assertEquals("X should be 10:", 10, coordinate.getX());
        assertEquals("Y should be 20:", 20, coordinate.getY());
    }

    @Test
    public void testParseCommand() throws InvalidCommandException {
        Grid grid = new Grid(10, 10);
        MyCommand turnOnCommand = ParserUtils.parseCommand("turn on 1,1 through 2,2");
        MyCommand turnOffCommand = ParserUtils.parseCommand("turn off 3,3 through 4,4");
        MyCommand toggleCommand = ParserUtils.parseCommand("toggle 5,5 through 6,6");
        assertEquals("Command should be of type command.TurnOn: ", TurnOn.class, turnOnCommand.getClass());
        assertEquals("Command should be of type command.TurnOff: ", TurnOff.class, turnOffCommand.getClass());
        assertEquals("Command should be of type command.ToggleLights: ", ToggleLights.class, toggleCommand.getClass());
        assertEquals("First coordinate should be", new Coordinate("1,1"), turnOnCommand.getFirst());
        assertEquals("Secnod coordinate should be", new Coordinate("2,2"), turnOnCommand.getSecond());
        assertEquals("First coordinate should be", new Coordinate("3,3"), turnOffCommand.getFirst());
        assertEquals("Secnod coordinate should be", new Coordinate("4,4"), turnOffCommand.getSecond());
        assertEquals("First coordinate should be", new Coordinate("5,5"), toggleCommand.getFirst());
        assertEquals("Second coordinate should be", new Coordinate("6,6"), toggleCommand.getSecond());
    }

    @Test(expected = InvalidCommandException.class)
    public void invalidCommandTest() throws InvalidCommandException {
        Grid grid = new Grid(10, 10);
        ParserUtils.parseCommand("break 1,1 through 2,2");
    }

    @Test
    public void simpleTest() throws InvalidCommandException {
        Grid grid = new Grid(3, 3);
        assertEquals("Number of lights on should be 0", 0, grid.countLights());
        MyCommand turnOn = ParserUtils.parseCommand("turn on 0,0 through 2,2");
        turnOn.executeCommand(grid);
        int[][] resultGrid = grid.getGrid();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                assertEquals("All lights should be on", 1, resultGrid[i][j]);
            }
        }
        assertEquals("Number of lights on should be 9", 9, grid.countLights());
    }

    @Test
    public void anotherSimpleTest() throws InvalidCommandException{
        Grid grid = new Grid(5, 5);
        MyCommand turnOn = ParserUtils.parseCommand("turn on 0,0 through 2,2");
        turnOn.executeCommand(grid);
        assertEquals("Number of lights on should be 9", 9, grid.countLights());
        MyCommand turnOn2 = ParserUtils.parseCommand("turn on 3,3 through 4,4");
        turnOn2.executeCommand(grid);
        assertEquals("Number of lights on should be 13", 13, grid.countLights());
    }

    @Test
    public void extractSquareTest(){
        MyCommand command = new TurnOn(new Coordinate("0,2"), new Coordinate("2,0"));
        command.normalize();
        assertEquals("First coordinate should have smaller X and Y", new Coordinate("0,0"), command.getFirst());
        assertEquals("Second coordinate should have bigger X and Y", new Coordinate("2,2"), command.getSecond());

        MyCommand command2 = new TurnOn(new Coordinate("1,4"), new Coordinate("5,1"));
        command2.normalize();
        assertEquals("First coordinate should have smaller X and Y", new Coordinate("1,1"), command2.getFirst());
        assertEquals("Second coordinate should have bigger X and Y", new Coordinate("5,4"), command2.getSecond());

        MyCommand command3 = new TurnOn(new Coordinate("7,3"), new Coordinate("1,5"));
        command3.normalize();
        assertEquals("First coordinate should have smaller X and Y", new Coordinate("1,3"), command3.getFirst());
        assertEquals("Second coordinate should have bigger X and Y", new Coordinate("7,5"), command3.getSecond());
    }

    @Test
    public void thoroughTest() throws InvalidCommandException {
        Grid grid = new Grid(10, 10);
        ParserUtils.parseCommand("turn on 0,0 through 3,3").executeCommand(grid);
        ParserUtils.parseCommand("turn on 7,1 through 6,4").executeCommand(grid);
        ParserUtils.parseCommand("turn off 2,3 through 1,0").executeCommand(grid);
        ParserUtils.parseCommand("toggle 1,8 through 8,7").executeCommand(grid);
        ParserUtils.parseCommand("toggle 7,3 through 5,8").executeCommand(grid);
        assertEquals("Total brightness should be 84", 84, grid.countLights());
    }

    @Test
    public void fullExecutionTest() throws IOException {
        FileInputStream stream = new FileInputStream("src/test/resources/input.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        Grid grid = new Grid(GRID_SIZE,GRID_SIZE);
        while((line = bufferedReader.readLine()) != null){
            try {
                ParserUtils.parseCommand(line).executeCommand(grid);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }
        assertEquals("Total brightness should be " + FINAL_ANSWER, FINAL_ANSWER, grid.countLights());
    }
}
