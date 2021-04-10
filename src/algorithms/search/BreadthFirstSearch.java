package algorithms.search;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    PriorityQueue<AState>nodes;

    public BreadthFirstSearch() {
        super();
        nodes =new PriorityQueue<AState>(1000,new AState.AStateComparator());
        name="Breadth First Search";
    }

    @Override
    public Solution solve(ISearchable domain) {
        domain.clear();
        start = domain.getStartState();
        goal = domain.getGoalState();
        setCostNode(start);
        setCostNode(goal);
        nodes.add(start);
        ArrayList<AState> possibole;
        AState curr=null;
        while (!nodes.isEmpty()) {
            curr = nodes.remove();
            if (curr.equals(goal))
                break;
            possibole = domain.getAllSuccessors(curr);
            domain.visited(curr);
            visitedNodes++;
            for (int i = 0; i < possibole.size(); i++) {
                if (!(domain.checkIfvisited(possibole.get(i)))) {
                    possibole.get(i).setStateBefor(curr);
                    setCostNode(possibole.get(i));
                    nodes.add(possibole.get(i));
                }
            }
        }
        while(curr!=null){
            solution.addState(curr);
            curr=curr.getStateBefor();
        }
        return solution;
    }
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return super.getNumberOfNodesEvaluated();
    }


    protected void setCostNode(AState s){
        s.setCost(1);
    }
}
