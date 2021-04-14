package algorithms.search;

import java.util.ArrayList;

public class Solution {
    // list of all states from start to goal
    private ArrayList<AState>pathSolution;

    public Solution() {
        this.pathSolution =new ArrayList<AState>();
    }

    /**
     * @return the solution path
     */
    public ArrayList<AState> getSolutionPath(){
        return this.pathSolution;
    }


    /**
     * @param state add new state to the solution path
     */
    public void addState(AState state){
        this.pathSolution.add(state);
    }

    /**
     * @return How many states in the solution
     */
    public int size(){
        return this.pathSolution.size();
    }

}
