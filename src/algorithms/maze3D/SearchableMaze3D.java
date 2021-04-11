package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {

    protected Maze3D maze;
    protected Maze3DState start;
    protected Maze3DState goal;
    protected boolean[][][] mem; // remember who checked

    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
        this.start = new Maze3DState(maze.getDepthStart(), maze.getStartRow(),maze.getStartCol());
        this.goal = new Maze3DState(maze.getDepthEnd(), maze.getEndRow(), maze.getEndCol());
        mem = new boolean[maze.getMazeDepth()][maze.getMazeRow()][maze.getMazeCol()];
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
    private boolean legallCell(int z,int x, int y) {
        if (z>=maze.getMazeDepth() || x >= maze.getMazeRow() || y >= maze.getMazeCol() || x < 0 || y < 0 || z<0|| maze.getVal(z,x, y) != 0 )
            return false;
        return true;
    }

    /**
     * @param s - a given state
     * @return all the next possible states
     */
    @Override
    public ArrayList<AState> getAllSuccessors(AState s) {
        if (s instanceof Maze3DState) {
            Maze3DState ms = (Maze3DState) s;
            ArrayList<AState> possibole = new ArrayList<AState>();
            int row = ms.getRow();
            int col = ms.getCol();
            int dp = ms.getDp();
            int up = row - 1;
            int down = row + 1;
            int left = col - 1;
            int right = col + 1;
            int inside= dp-1;
            int outsider = dp+1;
            if (legallCell(dp,up, col)) {
                Maze3DState state = new Maze3DState(dp,up, col);
                state.setCost(10);
                possibole.add(state);
            }
            if (legallCell(dp,down, col)) {
                Maze3DState state = new Maze3DState(dp,down, col);
                state.setCost(10);
                possibole.add(state);
            }
            if (legallCell(dp,row, left)){
                Maze3DState state = new Maze3DState(dp,row, left);
                state.setCost(10);
                possibole.add(state);
            }

            if (legallCell(dp,row, right)) {
                Maze3DState state = new Maze3DState(dp,row, right);
                state.setCost(10);
                possibole.add(state);
            }
            if(legallCell(dp-1,row,col)) {
                Maze3DState state = new Maze3DState(dp - 1, row, col);
                state.setCost(10);
                possibole.add(state);
            }
            if(legallCell(dp+1,row,col)){
                Maze3DState state = new Maze3DState(dp+1,row, col);
                state.setCost(10);
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
        Maze3DState s=(Maze3DState)state;
        this.mem[s.getDp()][s.getRow()][s.getCol()] = true;
    }

    /**
     * @param state -check this state
     * @return true if this state has visited by the solver algo
     */
    public boolean checkIfvisited(AState state) {
        Maze3DState s=(Maze3DState)state;
        if(  this.mem[s.getDp()][s.getRow()][s.getCol()])
            return true;
        return false;
    }

    /**
     * @param s - state in the searchable problem
     * @return     return if this state is state that can be moved to here
     */
    @Override
    public boolean validState(AState s) {
        if (s instanceof Maze3DState) {
            return legallCell(((Maze3DState) s).getDp(),((Maze3DState) s).getRow(), ((Maze3DState) s).getCol());
        }
        return false;
    }

    /**
     * clear the memorize
     */
    public void clear(){
        mem= new boolean[maze.getMazeDepth()][maze.getMazeRow()][maze.getMazeCol()];
    }

/*//functions for my testing
    private int cellVal(int dp,int row , int col){
        return maze.getVal(dp,row,col);
    }
    private void setllVal(int dp,int row , int col){
        maze.setCell(dp,row,col,3);
    }

*/


}

