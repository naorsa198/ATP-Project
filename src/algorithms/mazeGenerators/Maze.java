package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Maze implements Serializable {
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
        maze[row][col] =val;
    }

    public int getVal(int row,int col){
        return maze[row][col];
    }

// in the test its print and in the pdf its Print;
    public void print(){
        Print();
    }


    @Override
    public String toString() {
        return "Maze{" +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", startCol=" + startCol +
                ", endCol=" + endCol +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze1 = (Maze) o;
        return startRow == maze1.startRow && endRow == maze1.endRow && startCol == maze1.startCol && endCol == maze1.endCol && Arrays.equals(maze, maze1.maze);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.toByteArray());
    }


    /**
     * print the maze
     */
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



    private int extractVariables(byte[] arr, int from, int end) {
        int value;
        for(value = 0; from != end; ++from) {
            value += arr[from] & 255;
        }

        return value;
    }

    /**
     * function to convert the maze to byte array
     * 0 - 0
     * 1 - 1
     * 2 - start
     * 3 - end
     * 4 - new line zero
     * 5 - new line one
     * 6 - new line start
     * 7 - new line end
     * @return a byte array according to those settings ^
     */

    public byte[] toByteArray() {
        int rows = getMazeRows();
        int cols = getMazeCols();
        byte[] array= new byte[rows*cols];
        int k=0;
        //insert start and end point
        this.maze[getStartRow()][getStartCol()]=2;
        this.maze[getEndRow()][getEndCol()]=3;

        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++, k++) {
                if (j==cols-1){
                    if (this.maze[i][j]==1)
                        array[k]=5;
                    else if(this.maze[i][j]==0)
                        array[k]=4;
                    else if (this.maze[i][j]==2)
                        array[k]=6;
                    else
                        array[k]=7;

                }
                else{ //regular case
                    array[k]= (byte) this.maze[i][j];

                }
            }
        }
        //return the maze to original
        this.maze[getStartRow()][getStartCol()]=0;
        this.maze[getEndRow()][getEndCol()]=0;

        return array;

    }




    /**
     * function to convert the byte array to Maze
     * 0 - 0
     * 1 - 1
     * 2 - start
     * 3 - end
     * 4 - new line zero
     * 5 - new line one
     * 6 - new line start
     * 7 - new line end
     * @return a byte array according to those settings ^
     */
    public Maze(byte[] array) {
        int cols = 0;
        int rows = 0;
        int size = array.length;
        // find number of rows
        for (int i = 0; i < size; i++) {
            if (array[i] == 4 || array[i] == 5 || array[i] == 6 || array[i] == 7) {
                rows = rows + 1;
            }
        }
        //find number of cols;
        for (int i = 0; i < size; i++) {
            if (array[i] == 4 || array[i] == 5 || array[i] == 6 || array[i] == 7) {
                cols = i + 1;
                break;
            }
        }

        this.maze = new int[rows][cols];

        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++, k++) {
                if (array[k] == 4)
                    maze[i][j] = 0;

                else if (array[k] == 5)
                    maze[i][j] = 1;

                else if (array[k] == 6 || array[k] == 2) {
                    maze[i][j] = 0;
                    this.startRow = i;
                    this.startCol = j;
                } else if (array[k] == 7 || array[k] == 3) {
                    maze[i][j] = 0;
                    this.endCol = j;
                    this.endRow = i;
                }
                else maze[i][j]=array[k];
            }
        }
    }



}



