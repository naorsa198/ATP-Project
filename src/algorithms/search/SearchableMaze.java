package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable{

    private Maze maze;
    private MazeState start;
    private MazeState goal;


    public SearchableMaze(Maze maze) {
        this.maze = maze;
        this.start = new MazeState(maze.getStartPosition());
        this.goal = new MazeState(maze.getGoalPosition());

    }

    @Override
    public AState getStartState() {
        return this.start;
    }

    @Override
    public AState getGoalState() {
        return  this.goal;
    }

    private boolean legallCell(int x,int y){
        if(x>=maze.getMazeRows() || y>=maze.getMazeCols() || x<0 || y<0 || maze.getVal(x,y)!=0)
            return false;
        return true;
    }

    /**
     * @param s - a given state
     * @return all the next possible states
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        if (s instanceof  MazeState) {
            MazeState ms = (MazeState) s;
            Position pos = ((MazeState) s).getPos();
            ArrayList<AState> possibole = new ArrayList<AState>();
            int row= pos.getRowIndex();
            int col= pos.getColumnIndex();
            int up = row-1;
            int down= row +1;
            int left = col -1;
            int right = col+1;

            if(legallCell(up,col))
                possibole.add(new MazeState(new Position(up,col)));
            if(legallCell(down,col))
                possibole.add(new MazeState(new Position(down,col)));
            if(legallCell(row,left))
                possibole.add(new MazeState(new Position(row,left)));
            if(legallCell(row,right))
                possibole.add(new MazeState(new Position(row,right)));


            // checking the alchson

            if(legallCell(up,col) && legallCell(up,right)){
                MazeState state= new MazeState(new Position(up,right)) ;
                state.setCost(15);
            }
            if(legallCell(down,col) && legallCell(down,right)){
                MazeState state= new MazeState(new Position(down,right)) ;
                state.setCost(15);
            }
            if(legallCell(row,left) && legallCell(up,left)){
                MazeState state= new MazeState(new Position(up,left)) ;
                state.setCost(15);
            }
            if(legallCell(row,right) && legallCell(down,right)){
                MazeState state= new MazeState(new Position(down,right)) ;
                state.setCost(15);
            }

        return possibole;
        }
        return null;
    }




}
