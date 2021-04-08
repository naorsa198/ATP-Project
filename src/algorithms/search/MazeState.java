package algorithms.search;
import java.util.ArrayList;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    private Position pos;

    public MazeState(Position pos) {
        this.cost=10;
        this.pos = pos;


    }


    public Position getPos(){
        return this.pos;
    }

    @Override
    public boolean equals(Object obj) {
        MazeState ms= (MazeState)obj;
        if(ms.pos==this.pos)
            return true;
        else return false;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int getCost() {
        return getCost();
    }

    @Override
    public void setCost(int cost) {
        super.setCost(cost);
    }

    @Override
    public AState getCame() {
        return super.getCame();
    }

    @Override
    public void setCame(AState came) {
        super.setCame(came);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
