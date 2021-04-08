package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected String name;
    protected int visitedNodes;
    protected AState start;
    protected AState goal;
    protected Solution solution;

    public ASearchingAlgorithm() {
        this.name = null;
        this.visitedNodes = 0;
        this.start = null;
        this.goal = null;
        this.solution= new Solution();
    }

    @Override
    public Solution solve(ISearchable domain) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }
}
