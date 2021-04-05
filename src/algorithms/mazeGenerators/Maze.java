package algorithms.mazeGenerators;

public class Maze {
    protected int[][] maze;
    protected int startRow;
    protected int endRow;
    protected int startCol;
    protected int endCol;

    /**
     * @param row - number of rows in the maze
     * @param col - number of column in the maze
     */
    public Maze(int row, int col) {
        maze = new int[row][col];
    }

    /**
     * @return -the start position
     */
    public Position getStartPosition() {
        Position startPosition = new Position(startRow, startCol);
        return startPosition;
    }

    /**
     * @return -the goal position
     */
    public Position getGoalPosition() {
        Position goalPositon = new Position(endRow, endCol);
        return goalPositon;
    }

    /**
     * @return start row
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * @param startRow- set the start row
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * @return get the end row
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * @param endRow set the end row
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    /**
     * @return get the start col
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * @param startCol set the start col
     */
    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    /**
     * @return get the end col
     */
    public int getEndCol() {
        return endCol;
    }

    /**
     * @param endCol set the end col
     */
    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }

    /**
     * @return maze rows amount
     */
    public int getMazeRows() {
        return maze.length;
    }

    /**
     * @return maze columns amount
     */
    public int getMazeCols() {
        return maze[0].length;


    }

    public void setCell(int row, int col, int val) {
        if (row < 2 || col < 2 || row > getMazeRows() || col > getMazeCols())
            return;
        maze[row][col] = val;
    }

    public void Print() {
        if (maze == null || getMazeCols() == 0 || getMazeRows() == 0 || (getMazeRows() == 1 && getMazeCols() == 1)) {
            return;
        }

        char[][] print_maze = new char[getMazeRows()][getMazeCols()];

        int rowSize = getMazeRows();
        int colSize = getMazeCols();

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                print_maze[i][j] = Character.forDigit(maze[i][j], 10);
            }
        }
        print_maze[getStartRow()][getStartCol()] = 'S';
        print_maze[getEndRow()][getEndCol()] = 'E';


        for (int i = 0; i < rowSize; i++) {
            System.out.print("{");
            for (int j = 0; j < colSize; j++) {
                System.out.print(" "+print_maze[i][j]);
            }
            System.out.print(" }");
            System.out.println();
        }

    }
}



