package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class MyMaze3DGenerator implements IMazeGenerator3D{
    public  Maze3D maze;
    public ArrayList<Position3D>neighbor;
    public Stack<Position3D> stack;

    @Override
    public Maze3D generate(int depth, int row, int column) {
        return null;
    }

    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long start = System.currentTimeMillis();
        //TODO CHANGE HEERE THE LEGEASIZE
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
        if(depth > maze.getMazeDepth() || depth<0)
            return false;
        if(row>maze.getMazeRow() || row<0)
            return false;
        if(column > maze.getMazeCol() || column<0)
            return false;
        if(maze.maze[depth][row][column] != 1)
            return  false;
        return true;
    }

    private void GetAvailableneighbors(int z, int x,int y) {
        if (legalSize(z, x + 2, y)) {
            Position3D pos = new Position3D(z, x + 2, y);
            neighbor.add(pos);
        }
        if (legalSize(z, x, y + 2)) {
            Position3D pos = new Position3D(z, x, y + 2);
            neighbor.add(pos);
        }
        if (legalSize(z, x - 2, y)) {
            Position3D pos = new Position3D(z, x - 2, y);
            neighbor.add(pos);
        }
        if (legalSize(z, x, y - 2)) {
            Position3D pos = new Position3D(z, x, y - 2);
            neighbor.add(pos);
        }
        if (legalSize(z - 1, x, y)) {
            Position3D pos = new Position3D(z - 1, x, y);
            neighbor.add(pos);
        }
        if (legalSize(z + 1, x, y)) {
            Position3D pos = new Position3D(z + 1, x, y);
            neighbor.add(pos);
        }
    }

    private void breakWalls(Position3D src, Position3D target) {
        // right col
        if (src.getRowIndex() == target.getRowIndex() && src.getDepthIndex()== target.getDepthIndex() )
            if (src.getColumnIndex() > target.getColumnIndex()) {
                maze.maze[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex() + 1] = 0;
                maze.maze[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        //left col
        if (src.getRowIndex() == target.getRowIndex()  && src.getDepthIndex()== target.getDepthIndex())
            if (src.getColumnIndex() < target.getColumnIndex()) {
                maze.maze[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex() - 1] = 0;
                maze.maze[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        //up row
        if (src.getColumnIndex() == target.getColumnIndex() && src.getDepthIndex()== target.getDepthIndex())
            if (src.getRowIndex() < target.getRowIndex()) {
                maze.maze[target.getDepthIndex()][target.getRowIndex() - 1][target.getColumnIndex()] = 0;
                maze.maze[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        //down row
        if (src.getColumnIndex() == target.getColumnIndex() && src.getDepthIndex()== target.getDepthIndex())
            if (src.getRowIndex() > target.getRowIndex()) {
                maze.maze[target.getDepthIndex()][target.getRowIndex() + 1][target.getColumnIndex()] = 0;
                maze.maze[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }

        //depth
        if (src.getColumnIndex() == target.getColumnIndex() && src.getRowIndex()> target.getRowIndex())
                maze.maze[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }



    private Position3D createRandomPosition() {
        Position3D pos = new Position3D(0, 0,0);
        int rowS;
        int colS;
        int depth;
        rowS = maze.getMazeRow();
        colS = maze.getMazeCol();
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
        return pos;
    }
    }





