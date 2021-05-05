package algorithms.mazeGenerators;

public interface IMazeGenerator {

    /**
     * @param row - amount of rows in the maze
     * @param col - amount of colums in the maze
     * @return an initialized maze
     */
    public abstract Maze generate(int row, int col);

    /**
     * @param row - amount of rows in the maze
     * @param col - amount of colums in the maze
     * @return the time in ms
     */
    public long measureAlgorithmTimeMillis(int row, int col);

}

