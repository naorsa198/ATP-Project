package algorithms.search;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    PriorityQueue<AState>nodes;
    Stack<AState> stack;

    public BreadthFirstSearch() {
        super();
        nodes =new PriorityQueue<AState>(1000,new AState.AStateComparator());
        name="BreadthFirstSearch";
        stack = new Stack<AState>();
    }

    /**
     * @param domain - the problem we need to solve
     * @return  solution - path of postions from start to end
     */
    @Override
    public Solution solve(ISearchable domain) {
        domain.clear();
        start = domain.getStartState();
        goal = domain.getGoalState();
        setCostNode(start);
        setCostNode(goal);
        nodes.add(start);
        boolean flag=false;
        ArrayList<AState> possibole;
        AState curr=null;
        while (!nodes.isEmpty()) {
            curr = nodes.remove();
            if (curr.equals(goal))
                break;
            possibole = domain.getAllSuccessors(curr);
            domain.visited(curr);
            visitedNodes++;
            for (int j = 0; j < possibole.size(); j++) {
                if(possibole.get(j).equals(goal)) {
                    possibole.get(j).setStateBefor(curr);
                    curr= possibole.get(j);
                    flag=true;
                    break;
                }
            }
            if(flag==true)
                break;
            for (int i = 0; i < possibole.size(); i++) {
                if (!(domain.checkIfvisited(possibole.get(i)))) {
                    possibole.get(i).setStateBefor(curr);
                    setCostNode(possibole.get(i));
                    nodes.add(possibole.get(i));
                }
            }
        }
        while(curr!=null){
            stack.push(curr);
            curr=curr.stateBefor;
        }
        while(!(stack.isEmpty())) {
            solution.addState(stack.pop());

        }
        return solution;

    }

    /**
     * @return the name of solver algo
     */
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return super.getNumberOfNodesEvaluated();
    }


    /**
     * @param s set the cost of getting to this state
     */
    protected void setCostNode(AState s){
        s.setCost(1);
    }
}
