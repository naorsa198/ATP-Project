package algorithms.search;
import java.util.Comparator;
import java.util.Objects;

public abstract class AState {
    protected int cost=0;
    protected boolean visited=false;
    protected AState stateBefor;


    public AState() {
        this.cost = 0;
        stateBefor=null;
    }


    /**
     * @param obj -another state
     * @return true if the strings that describes the states is equal
     */
    public abstract boolean equals(Object obj);
    public abstract String toString();

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public AState getStateBefor() {
        return stateBefor;
    }

    public void setStateBefor(AState stateBefor) {
        this.stateBefor = stateBefor;
    }

    /**
     * an comperator class to compare between two states by their costs
     */
    static class AStateComparator implements Comparator<AState> {
        public int compare(AState s1, AState s2) {
            if (s1.cost < s2.cost)
                return 1;
            else if (s1.cost > s2.cost)
                return -1;
            return 0; //equal
        }
    }

}