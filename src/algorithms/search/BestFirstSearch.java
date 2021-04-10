package algorithms.search;

public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch() {
        super();
        this.name = "Best First Search";
    }

    @Override
    public Solution solve(ISearchable domain) {
        return super.solve(domain);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return super.getNumberOfNodesEvaluated();
    }

    @Override
    protected void setCostNode(AState s) {
    s.setCost(s.getCost());
    }
}
