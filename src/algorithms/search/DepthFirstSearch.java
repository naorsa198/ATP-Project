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
        dfsIt(start);
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
        else{
            if(flag==true) {
                return;}
                dm.visited(s);
                ArrayList<AState> possibole;
                possibole = dm.getAllSuccessors(s);
                for (int i = 0; i < possibole.size(); i++) {
                    if (!(dm.checkIfvisited(possibole.get(i)))) {
                        possibole.get(i).setStateBefor(s);
                        visitedNodes++;
                        dfs(possibole.get(i));
                    }
                }
        }
    }

    private void dfsIt(AState curr){

        while(!(flag)){
            if(curr.equals(goal)) {
                goal.setStateBefor(curr);
                while (goal != null) {
                    goal=goal.stateBefor;
                    if (goal!=null)
                        stack.push(goal);
                }
                return;
            }
            dm.visited(curr);
            int counter=0;
            ArrayList<AState> possibole;
            possibole = dm.getAllSuccessors(curr);
            for (int i = 0; i < possibole.size(); i++) {
                if (!(dm.checkIfvisited(possibole.get(i)))) {
                    possibole.get(i).setStateBefor(curr);
                    curr=possibole.get(i);
                    visitedNodes++;
                    break;
                }
                else counter++;
                if(counter==possibole.size())
                    curr=curr.getStateBefor();
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
