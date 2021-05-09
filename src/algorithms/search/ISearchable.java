package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {


    /**
     * @return the start state of the problem
     */
    public AState getStartState();

    /**
     * @return the end state of the problem
     */
    public AState getGoalState();

    /**
     * @param s - a given state
     * @return a list of all the optionally states
     */
    public ArrayList<AState> getAllSuccessors(AState s);

    /**
     * @return true if its valid state
     */
    public boolean validState(AState s);

    /**
     * @param state make the stated has visited
     */
    public void visited(AState state);

    /**
     * @param state -check this state
     * @return return true if visited
     */
    public boolean checkIfvisited(AState state);

    /**
     * clear  the memorize
     */
    public void clear();
}