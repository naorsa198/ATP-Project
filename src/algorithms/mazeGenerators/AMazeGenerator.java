package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{
    public Maze maze;

    @Override
    public Maze generate(int row, int col) {
        return null;
    }

    @Override
    public long measureAlgorithmTimeMillis(int row, int col) {
        long start = System.currentTimeMillis();
        if (!legalSize(row, col)) {
            //on unlegal cases we will create 10x10 maze as default
            row = 10;
            col = 10;
        }
        maze = generate(row,col);
        long end = System.currentTimeMillis();
        return end-start;
    }

    /**
     * @param row
     * @param col
     * @return true if legal
     */
    protected boolean legalSize(int row, int col) {
        if (row == 1 && col == 1) {
            return false;
        }
        if (row == 0 || col == 0) {
            return false;
        }
        if (row < 0 || col <0) {
            return false;
        }
        return true;
    }

}
