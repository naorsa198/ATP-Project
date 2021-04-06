package algorithms.mazeGenerators;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * create random cells with one and zero
     */
    private void fillMaze() {
        int rowS = maze.getMazeRows();
        int colS = maze.getMazeCols();

        double random = Math.random();
        for (int i = 0; i < rowS; i++) {
            for (int j = 0; j < colS; j++) {
                if (random > 0.5) {
                    maze.setCell(i, j, 1);
                }
                random = Math.random();
            }

        }
    }

    /**
     * @return return Position that represent Index of row and col
     */



    /**
     * create a way from the start to the end and fill it by 0-zero
     */
    private void generateWay() {

        int row = maze.getStartRow();
        int col = maze.getStartCol();
        maze.setCell(row, col, 0);

        while (maze.maze[maze.getEndRow()][maze.getEndCol()] != 0) {


            double randomRowOrCol = Math.random();
            if (randomRowOrCol > 0.5 && row != maze.getEndRow()) { //row
                if (row < maze.getEndRow())
                    row++;
                else
                    row--;
                maze.setCell(row, col, 0);
                continue;
            }
            if (col != maze.getEndCol()) { //col
                if (col < maze.getEndCol())
                    col++;
                else
                    col--;
                maze.setCell(row, col, 0);
                continue;
            }

        }
    }

    /**
     * @param row -number of rows
     * @param col-number of columns
     * @return return SimpleMaze
     */
    @Override
    public Maze generate(int row, int col) {

        maze = new Maze(row, col);
        // create maze full of 0 or 1
        fillMaze();
        //set start and end position of maze
        createStartEnd();
        //create a way from start to end
        generateWay();

        return maze;
    }
}