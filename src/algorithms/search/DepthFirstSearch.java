package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch extends ASearchingAlgorithm{
    ArrayList<AState>optional;
    ISearchable dm;


    public DepthFirstSearch() {
        super();
        this.name = "Depth First Search";

    }


    @Override
    public Solution solve(ISearchable maze) {
        dm=maze;
        if(dm == null)
            return null;
        goal=dm.getGoalState();
        start=dm.getStartState();
        dfs(start);
        return solution;
    }


    private  void dfs(AState s) {
        if (s.equals(goal))
            return;
        else {
            solution.addState(s);
            dm.visited(s);
            visitedNodes++;
            optional = new ArrayList<AState>();
            optional = dm.getAllPossibleStates(s);
            for (int i = 0; i <= optional.size(); i++) {
                AState curr = optional.get(i);
                if ((dm.checkIfvisited(curr) == false))
                    dfs(curr);
            }
            solution.removeState();
        }
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return super.getNumberOfNodesEvaluated();
    }
}
