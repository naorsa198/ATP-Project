package algorithms.maze3D;

import algorithms.search.AState;



public class Maze3DState extends AState {

    protected int row;
    protected int col;
    protected int dp;



    public Maze3DState(int d,int x, int y) {
        super();
        this.cost = 10;
        row = x;
        col = y;
        dp=d;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getDp() {
        return dp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze3DState that = (Maze3DState) o;
        return row == that.row && col == that.col && dp == that.dp;
    }



    @Override
    public String toString() {
        return "{" +dp+","+ row + "," + col + "}";
    }

    /**
     * @return return the cost of getting to this state
     */
    @Override
    public int getCost() {
        return cost;
    }

    /**
     * @param cost the value of getting to this state
     */
    @Override
    public void setCost(int cost) {
        super.setCost(cost);
    }



}

