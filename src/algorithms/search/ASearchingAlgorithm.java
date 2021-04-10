package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected String name; //name of algo
    protected int visitedNodes; // counter of evaluted nodes
    protected AState start; // start state
    protected AState goal; // goal state
    protected Solution solution; // path of nodes = solution

    public ASearchingAlgorithm() {
        this.name = null;
        this.visitedNodes = 0;
        this.start = null;
        this.goal = null;
        this.solution= new Solution();
    }


    /**
     * @param domain - the problem we need to solve
     * @return solution - path of postions from start to end
     */
    @Override
    public Solution solve(ISearchable domain) {
        return null;
    }


    /**
     * @return the solver algoritem name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return how many nodes evaluated
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }
}
