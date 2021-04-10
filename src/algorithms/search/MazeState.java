package algorithms.search;
import java.util.Objects;

public class MazeState extends AState {

    protected int row;
    protected int col;


    public MazeState(int x,int y) {
        super();
        this.cost=10;
        row=x;
        col=y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * @param o object as MazeState
     * @return return true if the two MazeState is equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeState mazeState = (MazeState) o;
        return row == mazeState.row && col == mazeState.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "{"+ row + "," + col + "}";
    }

    /**
     * @return return the cost of getting to this state
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * @param  cost the value of getting to this state
     */
    @Override
    public void setCost(int cost) {
        super.setCost(cost);
    }

}
