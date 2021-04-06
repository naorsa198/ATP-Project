package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends  AMazeGenerator{


    /**
     * @param maze
     * @return empty maze - maze without walls
     */
    private Maze zeroMaze(Maze maze) {
        for (int i = 0; i < maze.getMazeRows()-1; i++) {
            for (int j = 0; j < maze.getMazeCols()-1; j++) {
                maze.setCell(i, j, 0);
            }
        }
        return maze;
    }



    @Override
    public Maze generate(int row, int col) {
         Maze maze=new Maze(row,col);
         maze.setStartRow(0);
         maze.setStartCol(0);
         maze.setEndCol(col-1);
         maze.setStartRow(row-1);
         return maze= zeroMaze(maze);
    }
}
