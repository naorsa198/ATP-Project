package algorithms.search;

import algorithms.mazeGenerators.Position;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch extends ASearchingAlgorithm {
    ArrayList<AState> optional;
    ISearchable dm;
    Stack<AState> stack;
    boolean df;


    public DepthFirstSearch() {
        super();
        this.name = "Depth First Search";
        stack = new Stack<AState>();
        df=false;
    }


    @Override
    public Solution solve(ISearchable maze) {
        dm = maze;
        if (dm == null)
            return null;
        goal = dm.getGoalState();
        start = dm.getStartState();
        dm.clear();
        dfs(start);
        return solution;
    }


    private void dfs(AState s) {
        if (s.equals(goal)) {
            goal.setStateBefor(s);
            while (goal != null) {
                goal=goal.stateBefor;
                solution.addState(goal);

            }
            System.out.println("found sol");
            return;
        }
        else{
                dm.visited(s);
                ArrayList<AState> possibole = dm.getAllSuccessors(s);
                for (AState a : possibole) {
                    if (!(dm.checkIfvisited(a))) {
                        visitedNodes++;
                        a.setStateBefor(s);
                        dfs(a);
                    }
                }
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
