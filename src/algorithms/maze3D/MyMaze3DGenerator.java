package algorithms.maze3D;

public class MyMaze3DGenerator implements IMazeGenerator3D{
    public  Maze3D maze;




    @Override
    public Maze3D generate(int depth, int row, int column) {
        return null;
    }

    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long start = System.currentTimeMillis();
        if (!legalSize(depth,row, column)) {
            //on unlegal cases we will create 10x10 maze as default
            return 0;
        }
        maze = generate(depth,row, column);
        if (maze == null)
            return 0;
        long end = System.currentTimeMillis();
        return end - start; 
    }

    private boolean legalSize(int depth, int row, int column) {
    }
}
