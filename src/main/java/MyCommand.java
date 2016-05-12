import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;

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
                int firstX = second.getX();
                int firstY = first.getY();
                Coordinate newFirst = new Coordinate(firstX, firstY);
                int secondX = first.getX();
                int secondY = second.getY();
                Coordinate newSecond = new Coordinate(secondX, secondY);
                first = newFirst;
                second = newSecond;
            }
        } else {
            if(first.getY() > second.getY()){
                int firstX = first.getX();
                int firstY = second.getY();
                Coordinate newFirst = new Coordinate(firstX, firstY);
                int secondX = second.getX();
                int secondY = first.getY();
                Coordinate newSecond = new Coordinate(secondX, secondY);
                first = newFirst;
                second = newSecond;
            }
        }
    }
}
