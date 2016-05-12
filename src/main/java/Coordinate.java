/**
 * Created by rhn on 5/12/16.
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(String coord) {
        String[] split = coord.split(",");
        x = Integer.parseInt(split[0]);
        y = Integer.parseInt(split[1]);
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x)
            return false;
        return y == that.y;

    }

    @Override public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
