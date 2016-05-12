/**
 * Created by Rodrigo Nonose on 5/12/16.
 */
public abstract class MyCommand {

    private final Coordinate first;
    private final Coordinate secnod;

    public MyCommand(Coordinate c1, Coordinate c2){
        first = c1;
        secnod = c2;
    }

    public abstract void executeCommand(Grid grid);

    public Coordinate getFirst() {
        return first;
    }

    public Coordinate getSecond() {
        return secnod;
    }
}
