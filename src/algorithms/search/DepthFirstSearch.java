package algorithms.search;

import algorithms.mazeGenerators.Position;
import java.util.Stack;
import java.util.ArrayList;

public class DepthFirstSearch extends ASearchingAlgorithm {
    ISearchable dm;
    Stack<AState> stack;
    boolean flag;

    public DepthFirstSearch() {
        super();
        this.name = "Depth First Search";
        stack = new Stack<AState>();
        flag=false;
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
        while(stack.size()>0){
            solution.addState(stack.pop());
        }
        return solution;
    }


    private void dfs(AState s) {
        if (s.equals(goal)) {
            goal.setStateBefor(s);
            while (goal != null) {
                goal=goal.stateBefor;
                if (goal!=null)
                    stack.push(goal);
            }
            flag=true;
            return;
        }
        else {
            dm.visited(s);
            visitedNodes++;
            ArrayList<AState> possibole = dm.getAllSuccessors(s);
            if (flag == false) {
                for (AState a : possibole) {
                    if (!(dm.checkIfvisited(a))) {
                        a.setStateBefor(s);
                        dfs(a);
                    }
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
