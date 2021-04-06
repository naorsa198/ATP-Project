package algorithms.mazeGenerators;

import java.util.concurrent.ThreadLocalRandom;

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
    private Position createRandomPosition() {
        Position pos = new Position(0, 0);
        int rowS;
        int colS;
        rowS = maze.getMazeRows();
        colS = maze.getMazeCols();
        int colPos;
        double zeroOrlast;
        int rowPos = ThreadLocalRandom.current().nextInt(0, rowS);
        if (rowPos != 0 && rowPos != maze.getMazeRows() - 1) {
            zeroOrlast = Math.random();
            if (zeroOrlast < 0.5)
                colPos = 0;
            else
                colPos = maze.getMazeCols() - 1;
            pos.setCol(colPos);
            pos.setRow(rowPos);
            return pos;
        }

        if (rowPos == 0 || rowPos == maze.getMazeRows() - 1) {
            colPos = ThreadLocalRandom.current().nextInt(0, maze.getMazeCols());
            pos.setRow(rowPos);
            pos.setCol(colPos);
            return pos;
        }
        return pos;
    }

    /**
     * @param x Position x -start indexes
     * @param y Position y- end indexes
     * @return false if the postion are completely differents
     */
    private boolean CheckSamePos(Position x, Position y) {
        if (x.getColumnIndex() == y.getColumnIndex() && x.getRowIndex() == y.getRowIndex())
            return true;
        if(x.getColumnIndex()==y.getColumnIndex() || x.getRowIndex()== y.getRowIndex())
            return true;
        return false;
    }


    /**
     * create start and end indexes from the position that made by the func createRandomPosition
     */
    protected void createStartEnd() {
        Position endPos;
        Position startPos;
        endPos = createRandomPosition();
        startPos = createRandomPosition();
        while (CheckSamePos(endPos, startPos)) {
            endPos = createRandomPosition();
            startPos = createRandomPosition();
        }
        maze.setStartRow(startPos.getRowIndex());
        maze.setStartCol(startPos.getColumnIndex());
        maze.setEndRow(endPos.getRowIndex());
        maze.setEndCol(endPos.getColumnIndex());

    }
}
