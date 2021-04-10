package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

public class Maze3D {
    protected int[][][] maze;
    protected int startRow;
    protected int endRow;
    protected int startCol;
    protected int endCol;
    protected int depthStart;
    protected int depthEnd;

    public Maze3D(int[][][] maze, int depth, int row , int col ) {
        maze = new int[depth][row][col];}

    public int[][][] getMap(){
        return maze;
        }

    public Position3D getStartPosition(){
        Position3D startPosition = new Position3D(depthStart,startRow, startCol);
        return startPosition;
    }

    /**
     * @return -the goal position
     */
    public Position3D getGoalPosition() {
        Position3D goalPositon = new Position3D(depthEnd,endRow, endCol);
        return goalPositon;
    }

}

