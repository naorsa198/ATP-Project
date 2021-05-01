package algorithms.maze3D;

import java.util.concurrent.ThreadLocalRandom;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {
    public Maze3D maze;

    @Override
    public abstract Maze3D generate(int depth, int row, int column);

    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long start = System.currentTimeMillis();
        if (depth <2 || row <2 || column <2) {
            return 0;
        }
        maze = generate(depth, row, column);
        if (maze == null)
            return 0;
        long end = System.currentTimeMillis();
        return end - start;
    }

    protected boolean legalSize(int depth, int row, int column) {
        if (depth >= maze.getMazeDepth() || depth < 0)
            return false;
        if (row >= maze.getMazeRow() || row < 0)
            return false;
        if (column >= maze.getMazeCol() || column < 0)
            return false;
        if(maze.getVal(depth,row,column) !=1)
            return false;
        return true;
    }

    protected Position3D createRandomPosition() {
        Position3D pos = new Position3D(0, 0, 0);
        int rowS;
        int colS;
        int depth;
        rowS = maze.getMazeRow();
        colS = maze.getMazeCol();
        depth = maze.getMazeDepth();
        int colPos;
        double zeroOrlast;
        int rowPos = ThreadLocalRandom.current().nextInt(0, rowS);
        if (rowPos != 0 && rowPos != maze.getMazeRow() - 1) {
            zeroOrlast = Math.random();
            if (zeroOrlast < 0.5)
                colPos = 0;
            else
                colPos = maze.getMazeCol() - 1;
            pos.setCol(colPos);
            pos.setRow(rowPos);
            return pos;
        }

        if (rowPos == 0 || rowPos == maze.getMazeRow() - 1) {
            colPos = ThreadLocalRandom.current().nextInt(0, maze.getMazeCol());
            pos.setRow(rowPos);
            pos.setCol(colPos);
            return pos;
        }
        int depthPos = ThreadLocalRandom.current().nextInt(0, depth);
        pos.setDp(depthPos);
        return pos;
    }

}