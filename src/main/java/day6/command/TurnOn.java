package day6.command;

import day6.grid.Coordinate;
import day6.grid.Grid;

/**
 * Created by rhn on 5/12/16.
 */
public class TurnOn extends MyCommand {

    public static final int TURN_ON_INCREASE = 1;

    public TurnOn(Coordinate c1, Coordinate c2) {
        super(c1, c2);
    }

    @Override
    public void executeCommand(Grid grid) {
        int[][] gridInt = grid.getGrid();

        for(int firstX = getFirst().getX(); firstX <= getSecond().getX();firstX++){
            for(int firstY = getFirst().getY(); firstY <= getSecond().getY();firstY++){
                gridInt[firstX][firstY] += TURN_ON_INCREASE;
            }
        }
        grid.setGrid(gridInt);
    }

}
