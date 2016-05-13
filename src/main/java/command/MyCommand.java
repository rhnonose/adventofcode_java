package command;

import grid.Coordinate;
import grid.Grid;

/**
 * Created by Rodrigo Nonose on 5/12/16.
 */
public abstract class MyCommand {

    private Coordinate first;
    private Coordinate second;

    public MyCommand(Coordinate c1, Coordinate c2){
        first = c1;
        second = c2;
    }

    public abstract void executeCommand(Grid grid);

    public Coordinate getFirst() {
        return first;
    }

    public Coordinate getSecond() {
        return second;
    }

    public void normalize(){
        if(first.getX() > second.getX()){
            if(first.getY() > second.getY()){
                Coordinate temp = first;
                first = second;
                second = temp;
            } else {
                Coordinate newFirst = new Coordinate(second.getX(), first.getY());
                Coordinate newSecond = new Coordinate(first.getX(), second.getY());
                first = newFirst;
                second = newSecond;
            }
        } else {
            if(first.getY() > second.getY()){
                Coordinate newFirst = new Coordinate(first.getX(), second.getY());
                Coordinate newSecond = new Coordinate(second.getX(), first.getY());
                first = newFirst;
                second = newSecond;
            }
        }
    }
}
