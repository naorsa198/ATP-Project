package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

public class Maze3D {
    protected int[][][] map;
    protected int startRow;
    protected int endRow;
    protected int startCol;
    protected int endCol;
    protected int depthStart;
    protected int depthEnd;

    public Maze3D( int depth, int row , int col ) {
        map = new int[depth][row][col];
    }

        public int[][][] getMap(){
        return map;
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

    public int getMazeDepth(){
        return map.length;
    }

    public int getMazeRow(){
        return map[0].length;
    }

    public int getMazeCol(){
        return map[0][0].length;
    }

    public int[][][] getMaze() {
        return map;
    }

    public void setMaze(int[][][] maze) {
        this.map = maze;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }

    public int getDepthStart() {
        return depthStart;
    }

    public void setDepthStart(int depthStart) {
        this.depthStart = depthStart;
    }

    public int getDepthEnd() {
        return depthEnd;
    }
    public int getVal(int dp,int row,int col){
        return map[dp][row][col];
    }

    public void setDepthEnd(int depthEnd) {
        this.depthEnd = depthEnd;
    }
    public void setCell(int depth,int row, int col, int val) {
        map[depth][row][col] = val;
    }


    public void print(){
        System.out.println("{");
        for(int depth = 0; depth < map.length; depth++){
            for(int row = 0; row < map[0].length; row++) {
                System.out.print("{ ");
                for (int col = 0; col < map[0][0].length; col++) {
                    if (depth == getStartPosition().getDepthIndex() && row == getStartRow() && col == getStartPosition().getColumnIndex()) // if the position is the start - mark with S
                        System.out.print("S ");
                    else {
                        if (depth == depthEnd && row ==endRow && col == endCol) // if the position is the goal - mark with E
                            System.out.print("E ");
                        else
                            System.out.print(map[depth][row][col] + " ");
                    }
                }
                System.out.println("}");
            }
            if(depth < map.length - 1) {
                System.out.print("---");
                for (int i = 0; i < map[0][0].length; i++)
                    System.out.print("--");
                System.out.println();
            }
        }
        System.out.println("}");
    }
}

