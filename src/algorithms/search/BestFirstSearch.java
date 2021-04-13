package algorithms.search;

public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch() {
        super();
        this.name = "BestFirstSearch";
    }
 
    /**
     * @param domain - the problem we need to solve
     * @return  solution - path of postions from start to end
     */
    @Override
    public Solution solve(ISearchable domain) {
        return super.solve(domain);
    }

    /**
     * @return the name of the solver algo
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * @return number of nodes evaluated
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return super.getNumberOfNodesEvaluated();
    }

    /**
     * @param s set the cost of getting to this state
     */
    @Override
    protected void setCostNode(AState s) {
    s.setCost(s.getCost());
    }
}
