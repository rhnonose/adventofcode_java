/**
 * Created by rhn on 5/12/16.
 */
public class ToggleLights extends MyCommand {
    public ToggleLights(Coordinate c1, Coordinate c2) {
        super(c1,c2);
    }

    @Override public void executeCommand(Grid grid) {
        int[][] gridBefore = grid.getGrid();
        int[][] gridAfter = gridBefore.clone();

        for(int firstX = getFirst().getX(); firstX <= getSecond().getX();firstX++){
            for(int firstY = getFirst().getY(); firstY <= getSecond().getY();firstY++){
                gridAfter[firstX][firstY] += 2;
            }
        }

        grid.setGrid(gridAfter);
    }

}
