package algorithms.search;

import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm{
    Stack<AState>stack;


    public DepthFirstSearch() {
        super();
        this.name = "Depth First Search";
        this.stack=new Stack<AState>();
    }


    @Override
    public Solution solve(ISearchable maze) {
            AState current = maze.getStartState();
            stack.push(root);
            while(! stack.isEmpty()) {
                while(current.left != null) {
                    current = current.left;
                    stack.push(current);
                }
                current = stack.pop();
                visit(current.value);
                if(current.right != null) {
                    current = current.right;
                    stack.push(current);
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
