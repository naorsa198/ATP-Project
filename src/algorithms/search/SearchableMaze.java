package algorithms.search;
import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    protected Maze maze;
    protected MazeState start;
    protected MazeState goal;
    protected boolean[][] mem; // remember who checked

    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.start = new MazeState(maze.getStartRow(),maze.getStartCol());
        this.goal = new MazeState(maze.getEndRow(), maze.getEndCol());
        mem = new boolean[maze.getMazeRows()][maze.getMazeCols()];
    }


    @Override
    public AState getStartState() {
        return this.start;
    }

    @Override
    public AState getGoalState() {
        return this.goal;
    }

    /**
     * @param x  index of row
     * @param y index of col
     * @return if this position is state that can be moved to here
     */
    private boolean legallCell(int x, int y) {
        if (x >= maze.getMazeRows() || y >= maze.getMazeCols() || x < 0 || y < 0 || maze.getVal(x, y) != 0 )
            return false;
        return true;
    }

    /**
     * @param s - a given state
     * @return all the next possible states
     */
    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        if (s instanceof MazeState) {
            MazeState ms = (MazeState) s;
            ArrayList<AState> possibole = new ArrayList<AState>();
            int row = ms.getRow();
            int col = ms.getCol();
            int up = row - 1;
            int down = row + 1;
            int left = col - 1;
            int right = col + 1;
            if (legallCell(up, col)) {
                MazeState state = new MazeState(up, col);
                state.setCost(10);
                possibole.add(state);
            }
            if (legallCell(down, col)) {
                MazeState state = new MazeState(down, col);
                state.setCost(10);
                possibole.add(state);
            }
            if (legallCell(row, left)){
                MazeState state = new MazeState(row, left);
                state.setCost(10);
                possibole.add(state);
        }

            if (legallCell(row, right)) {
                MazeState state = new MazeState(row, right);
                state.setCost(10);
                possibole.add(state);
            }

            // checking the alchson

            if (legallCell(up, col) && legallCell(up, right) && maze.getVal(up, col) != 'E')  {
                MazeState state = new MazeState(up, right);
                state.setCost(15);
                possibole.add(state);
            }
            if (legallCell(down, col) && legallCell(down, right) && maze.getVal(down, col) != 'E' ) {
                MazeState state = new MazeState(down, right);
                state.setCost(15);
                possibole.add(state);

            }
            if (legallCell(row, left) && legallCell(up, left) && maze.getVal(row, left) != 'E') {
                MazeState state = new MazeState(up, left);
                state.setCost(15);
                possibole.add(state);

            }
            if (legallCell(row, right) && legallCell(down, right)&& maze.getVal(row, right) != 'E') {
                MazeState state = new MazeState(down, right);
                state.setCost(15);
                possibole.add(state);

            }

            return possibole;
        }
        return null;
    }


    /**
     * @param state make the stated has visited
     */
    public void visited(AState state) {
        MazeState s=(MazeState)state;
        this.mem[s.getRow()][s.getCol()] = true;
    }

    /**
     * @param state -check this state
     * @return true if this state has visited by the solver algo
     */
    public boolean checkIfvisited(AState state) {
        MazeState s=(MazeState)state;
        if(  this.mem[s.getRow()][s.getCol()])
            return true;
        return false;
    }

    /**
     * @param s - state in the searchable problem
     * @return     return if this state is state that can be moved to here
     */
    @Override
    public boolean validState(AState s) {
        if (s instanceof MazeState) {
            return legallCell(((MazeState) s).getRow(), ((MazeState) s).getCol());
        }
        return false;
    }

    /**
     * clear the memorize
     */
    public void clear(){
        mem= new boolean[maze.getMazeRows()][maze.getMazeCols()];
    }

    public int cellVal(int row , int col){
        return maze.getVal(row,col);
    }
    public void setllVal(int row , int col){
        maze.setCell(row,col,3);
    }



}