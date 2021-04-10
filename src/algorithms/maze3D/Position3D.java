package algorithms.maze3D;

import java.util.Objects;

public class Position3D {
    private int row;
    private int col;
    private int dp;

    public Position3D(int dp,int row, int col) {
        this.row = row;
        this.col = col;
        this.dp=dp;
    }

    public int getDepthIndex(){return dp;}
    public int getRowIndex(){return  row;}
    public int getColumnIndex(){return col;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position3D that = (Position3D) o;
        return row == that.row && col == that.col && dp == that.dp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, dp);
    }
}
