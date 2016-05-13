package day6.grid;

/**
 * Created by Rodrigo Nonose on 5/12/16.
 */
public class Grid {

    private int[][] grid;
    private int x;
    private int y;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        grid = new int[x][y];
    }

    public int countLights() {
        int count = 0;
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                count += grid[i][j];
            }
        }
        return count;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}
