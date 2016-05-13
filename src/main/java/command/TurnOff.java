package command;

import grid.Coordinate;
import grid.Grid;

/**
 * Created by rhn on 5/12/16.
 */
public class TurnOff extends MyCommand {

    public static final int TURN_OFF_DECREASE = -1;

    public TurnOff(Coordinate c1, Coordinate c2) {
        super(c1,c2);
    }

    @Override public void executeCommand(Grid grid) {
        int[][] gridInt = grid.getGrid();

        for(int firstX = getFirst().getX(); firstX <= getSecond().getX();firstX++){
            for(int firstY = getFirst().getY(); firstY <= getSecond().getY();firstY++){
                if(gridInt[firstX][firstY] > 0) {
                    gridInt[firstX][firstY] += TURN_OFF_DECREASE;
                }
            }
        }

        grid.setGrid(gridInt);
    }
}
