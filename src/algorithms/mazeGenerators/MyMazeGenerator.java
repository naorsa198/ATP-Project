package algorithms.mazeGenerators;
import java.util.List;
import java.util.Stack;
import java.util.Random;
import java.util.ArrayList;

public class MyMazeGenerator extends  AMazeGenerator{
//cells that have to check them
    Stack<Position>pathStack;
// walls who optionally might be cells path
    List<Position>neigbors;

    public MyMazeGenerator() {
        pathStack = new Stack<Position>();
        neigbors = new ArrayList<Position>();
    }

    /**
     * creates maze with only walls (1)
     * @param row - number of rows in maze
     * @param col - number of columns in maze
     */
    private void fillMaze(int row, int col) {
        maze = new Maze(row, col);
        for (int i = 0; i < maze.getMazeRows(); i++) {
            for (int j = 0; j < maze.getMazeCols(); j++) {
                maze.setCell(i, j, 1);
            }
        }
    }

    private boolean legalPos()

    private Position GetAvailableneighbors(){
        List<Position>neighbor;
        if()
        double random;


    }


    @Override
    public Maze generate(int row, int col) {
        return ;
    }
}

