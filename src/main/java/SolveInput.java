import java.io.*;

/**
 * Created by rhn on 5/12/16.
 */
public class SolveInput {

    public static void main(String[] args) throws IOException {
        FileInputStream stream = new FileInputStream("src/main/resources/input.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        Grid grid = new Grid(1000,1000);
        while((line = bufferedReader.readLine()) != null){
            try {
                GridUtils.parseCommand(line).executeCommand(grid);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }
        int count = grid.countLights();
    }
}
