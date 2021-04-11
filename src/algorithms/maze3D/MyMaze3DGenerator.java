package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class MyMaze3DGenerator extends AMaze3DGenerator{
    public ArrayList<Position3D>neigbors;
    public Stack<Position3D> pathStack;
    private Position3D goalPosition;
    private Position3D startPosition;

    public MyMaze3DGenerator() {
        pathStack = new Stack<Position3D>();
        neigbors = new ArrayList<Position3D>();
    }

    @Override
    public Maze3D generate(int depth, int row, int column)
    {
        if( depth < 2  || row < 2 || column < 2 )
            return null;
        maze = new Maze3D(depth ,row, column);

        fillMaze();
        createStartEnd();

        dfsGenerate();

        createEnd();

        return this.maze;
    }

    private void fillMaze() {

        int rowS = maze.getMazeRow();
        int colS = maze.getMazeCol();
        int depth = maze.getMazeDepth();


        for (int i = 0; i < maze.getMazeRow(); i++) {
            for (int j = 0; j < maze.getMazeCol(); j++) {
                for (int k = 0; k < maze.getMazeDepth(); k++) {
                    maze.setCell(k, i, j, 1);
                }
            }
        }
    }


    private void GetAvailableneighbors(int z, int x,int y) {
        if (legalSize(z, x + 2, y)) {
            Position3D pos = new Position3D(z, x + 2, y);
            neigbors.add(pos);
        }
        if (legalSize(z, x, y + 2)) {
            Position3D pos = new Position3D(z, x, y + 2);
            neigbors.add(pos);
        }
        if (legalSize(z, x - 2, y)) {
            Position3D pos = new Position3D(z, x - 2, y);
            neigbors.add(pos);
        }
        if (legalSize(z, x, y - 2)) {
            Position3D pos = new Position3D(z, x, y - 2);
            neigbors.add(pos);
        }
        if (legalSize(z - 1, x, y)) {
            Position3D pos = new Position3D(z - 1, x, y);
            neigbors.add(pos);
        }
        if (legalSize(z + 1, x, y)) {
            Position3D pos = new Position3D(z + 1, x, y);
            neigbors.add(pos);
        }
    }

    private void breakWalls(Position3D src, Position3D target) {
        // right col
        if (src.getRowIndex() == target.getRowIndex() && src.getDepthIndex()== target.getDepthIndex() )
            if (src.getColumnIndex() > target.getColumnIndex()) {
                maze.map[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex() + 1] = 0;
                maze.map[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        //left col
        if (src.getRowIndex() == target.getRowIndex()  && src.getDepthIndex()== target.getDepthIndex())
            if (src.getColumnIndex() < target.getColumnIndex()) {
                maze.map[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex() - 1] = 0;
                maze.map[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        //up row
        if (src.getColumnIndex() == target.getColumnIndex() && src.getDepthIndex()== target.getDepthIndex())
            if (src.getRowIndex() < target.getRowIndex()) {
                maze.map[target.getDepthIndex()][target.getRowIndex() - 1][target.getColumnIndex()] = 0;
                maze.map[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }
        //down row
        if (src.getColumnIndex() == target.getColumnIndex() && src.getDepthIndex()== target.getDepthIndex())
            if (src.getRowIndex() > target.getRowIndex()) {
                maze.map[target.getDepthIndex()][target.getRowIndex() + 1][target.getColumnIndex()] = 0;
                maze.map[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }

        //depth
        if (src.getColumnIndex() == target.getColumnIndex() && src.getRowIndex()==target.getRowIndex())
                maze.map[target.getDepthIndex()][target.getRowIndex()][target.getColumnIndex()] = 0;
            }




    /**
     * create start and end indexes from the position that made by the func createRandomPosition
     */
    protected void createStartEnd() {
        Position3D endPos;
        Position3D startPos;
        endPos = createRandomPosition();
        startPos = createRandomPosition();
        while ((endPos.equals(startPos))){
            endPos = createRandomPosition();
            startPos = createRandomPosition();
        }
        maze.setStartRow(startPos.getRowIndex());
        maze.setStartCol(startPos.getColumnIndex());
        maze.setEndRow(endPos.getRowIndex());
        maze.setEndCol(endPos.getColumnIndex());
        startPosition=startPos;
        goalPosition=endPos;
    }

    private void createEnd() {
        int strow = maze.getStartRow();
        int stcol= maze.getStartCol();
        int sdepth= maze.getDepthStart();
        boolean flag = true;
        while (flag = true) {
            createStartEnd();
            if(maze.getMazeRow()>2 && maze.getMazeCol()>2 && maze.getMazeDepth()>2){
                if (maze.map[maze.getDepthEnd()][maze.getEndRow()][maze.getEndCol()]==1)
                    continue;}
            if(maze.getEndRow()== strow && maze.getEndCol()==stcol && maze.getDepthEnd()==sdepth)
                continue;
            break;
        }
        maze.setStartRow(strow);
        maze.setStartCol(stcol);
        maze.setDepthStart(sdepth);
        startPosition.setDp(sdepth);
        startPosition.setCol(stcol);
        startPosition.setRow(strow);
    }

    /**
     * @param array give arrandom postion from the array
     * @return
     */
    public static Position3D getRandom(ArrayList<Position3D> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }


    private void dfsGenerate() {
        Position3D current = new Position3D( maze.depthStart,maze.startRow, maze.startCol);
        Position3D choosen;
        maze.setCell(current.getDepthIndex(),current.getRowIndex(), current.getColumnIndex(), 0);
        pathStack.push(current);

        while (pathStack.empty() == false) {
            current = pathStack.pop();
            this.GetAvailableneighbors(current.getDepthIndex(),current.getRowIndex(), current.getColumnIndex());
            if (neigbors.size() > 0) {
                pathStack.push(current);
                choosen = getRandom(neigbors);
                breakWalls(current, choosen);
                current = choosen;
                neigbors.clear();
                pathStack.push(current);
            }

        }
    }

}




