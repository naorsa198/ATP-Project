package algorithms.search;

public interface ISearchingAlgorithm {

    /**
     * solve should solve the problem
     * @param domain - the problem we need to solve
     * @return the solution
     */
    public Solution solve(ISearchable domain);

    /**
     *
     * @return the name of the algorithm
     */
    public String getName();

    /**
     *
     * @return the number of nodes the algorithm have checked while the algorithm trying to find the solution
     */
    public int getNumberOfNodesEvaluated();
    }

