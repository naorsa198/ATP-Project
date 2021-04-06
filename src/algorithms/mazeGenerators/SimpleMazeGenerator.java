package algorithms.mazeGenerators;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * create random cells with one and zero
     */
    private void fillMaze() {
        int rowS = maze.getMazeRows();
        int colS = maze.getMazeCols();

        double random = Math.random();
        for (int i = 0; i < rowS; i++) {
            for (int j = 0; j < colS; j++) {
                if (random > 0.5) {
                    maze.setCell(i, j, 1);
                }
                random = Math.random();
            }

        }
    }

    private Position createRandomPosition() {
        Position pos = new Position(0, 0);
        int rowS;
        int colS;
        rowS = maze.getMazeRows();
        colS = maze.getMazeCols();
        int colPos;
        double zeroOrlast;
        int rowPos = ThreadLocalRandom.current().nextInt(0, rowS + 1);
        if (rowPos != 0 && rowPos != maze.getMazeRows() - 1) {
            zeroOrlast = Math.random();
            if (zeroOrlast < 0.5)
                colPos = 0;
            else
                colPos = maze.getMazeCols()-1;
            pos.setCol(colPos);
            pos.setRow(rowPos);
            return pos;
        }

        if (rowPos == 0 || rowPos == maze.getMazeRows() - 1) {
            colPos = ThreadLocalRandom.current().nextInt(0, maze.getMazeCols() - 1 + 1);
            pos.setRow(rowPos);
            pos.setCol(colPos);
            return pos;
        }
        return pos;
    }

    private boolean CheckSamePos(Position x,Position y){
        if(x.getColumnIndex()==y.getColumnIndex() && x.getRowIndex()==y.getRowIndex())
            return true;
        return false;
    }


    private void createStartAandEnd(){
        Position endPos;
        Position startPos;
        endPos=createRandomPosition();
        startPos=createRandomPosition();
        while(CheckSamePos(endPos,startPos))
        {
            endPos=createRandomPosition();
            startPos=createRandomPosition();
        }
        maze.setStartRow(startPos.getRowIndex());
        maze.setStartCol(startPos.getColumnIndex());
        maze.setEndRow(endPos.getRowIndex());
        maze.setEndCol(endPos.getColumnIndex());

    }


private void generateWay(){

}

}