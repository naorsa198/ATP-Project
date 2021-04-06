package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int col;
    boolean val;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
        val=false;
    }

    public int getRowIndex() {
        return row;
    }


    public int getColumnIndex() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isVal() {
        return val;
    }

    public void setVal(boolean val) {
        this.val = val;
    }

    public String toString() {
        return "{" + row + ',' + col + '}';
    }
    public boolean equals(Position obj) {
        if(row==obj.row && col==obj.col)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return (row+col)%13;
    }


}