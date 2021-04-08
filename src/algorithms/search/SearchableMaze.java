package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    protected Maze maze;
    protected MazeState start;
    protected MazeState goal;
    protected boolean[][] mem; // remember who checked

    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.start = new MazeState(maze.getStartRow(),maze.getStartCol());
        this.goal = new MazeState(maze.getEndRow(), maze.getMazeCols());
        mem = new boolean[maze.getMazeRows()][maze.getMazeCols()];
    }

    public MazeState getStartRow() {
        return start;
    }

    public MazeState getGoal() {
        return goal;
    }

    @Override
    public AState getStartState() {
        return this.start;
    }

    @Override
    public AState getGoalState() {
        return this.goal;
    }

    private boolean legallCell(int x, int y) {
        if (x >= maze.getMazeRows() || y >= maze.getMazeCols() || x < 0 || y < 0 || maze.getVal(x, y) != 0)
            return false;
        return true;
    }

    /**
     * @param s - a given state
     * @return all the next possible states
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        if (s instanceof MazeState) {
            MazeState ms = (MazeState) s;
            ArrayList<AState> possibole = new ArrayList<AState>();
            int row = ms.getRow();
            int col = ms.getCol();
            int up = row - 1;
            int down = row + 1;
            int left = col - 1;
            int right = col + 1;
            if (legallCell(up, col))
                possibole.add(new MazeState(up,col));
            if (legallCell(down, col))
                possibole.add(new MazeState(down, col));
            if (legallCell(row, left))
                possibole.add(new MazeState(row, left));
            if (legallCell(row, right))
                possibole.add(new MazeState(row, right));


            // checking the alchson

            if (legallCell(up, col) && legallCell(up, right)) {
                MazeState state = new MazeState(up, right);
                state.setCost(15);
            }
            if (legallCell(down, col) && legallCell(down, right)) {
                MazeState state = new MazeState(down, right);
                state.setCost(15);
            }
            if (legallCell(row, left) && legallCell(up, left)) {
                MazeState state = new MazeState(up, left);
                state.setCost(15);
            }
            if (legallCell(row, right) && legallCell(down, right)) {
                MazeState state = new MazeState(down, right);
                state.setCost(15);
            }

            return possibole;
        }
        return null;
    }


    public void visited(AState state) {
        MazeState s=(MazeState)state;
        this.mem[s.getRow()][s.getCol()] = true;
    }

    public boolean checkIfvisited(AState state) {
        MazeState s=(MazeState)state;
        if(  this.mem[s.getRow()][s.getCol()])
            return true;
        return false;
    }

    @Override
    public boolean validState(AState s) {
        if (s instanceof MazeState) {
            MazeState ms = (MazeState) s;
            return legallCell(((MazeState) s).getRow(), ((MazeState) s).getCol());
        }
        return false;
    }

}