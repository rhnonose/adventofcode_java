import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by Rodrigo Nonose on 5/12/16.
 */
public class FireHazardTest {

    @Test
    public void testRegex(){
        Pattern coordPattern = GridUtils.getCoordPattern();
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
        MyCommand turnOnCommand = GridUtils.parseCommand("turn on 1,1 through 2,2");
        MyCommand turnOffCommand = GridUtils.parseCommand("turn off 3,3 through 4,4");
        MyCommand toggleCommand = GridUtils.parseCommand("toggle 5,5 through 6,6");
        assertEquals("Command should be of type TurnOn: ", TurnOn.class, turnOnCommand.getClass());
        assertEquals("Command should be of type TurnOff: ", TurnOff.class, turnOffCommand.getClass());
        assertEquals("Command should be of type ToggleLights: ", ToggleLights.class, toggleCommand.getClass());
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
        GridUtils.parseCommand("break 1,1 through 2,2");
    }

    @Test
    public void simpleTest() throws InvalidCommandException {
        Grid grid = new Grid(3, 3);
        assertEquals("Number of lights on should be 0", 0, grid.countLights());
        MyCommand turnOn = GridUtils.parseCommand("turn on 0,0 through 2,2");
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
        MyCommand turnOn = GridUtils.parseCommand("turn on 0,0 through 2,2");
        turnOn.executeCommand(grid);
        assertEquals("Number of lights on should be 9", 9, grid.countLights());
        MyCommand turnOn2 = GridUtils.parseCommand("turn on 3,3 through 4,4");
        turnOn2.executeCommand(grid);
        assertEquals("Number of lights on should be 13", 13, grid.countLights());
    }

}
