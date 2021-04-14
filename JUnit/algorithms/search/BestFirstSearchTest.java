package algorithms.search;

import org.junit.jupiter.api.Test;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BestFirstSearchTest {

    MyMazeGenerator test = new MyMazeGenerator();
    Maze testMyMaze= test.generate(100,100);
    BestFirstSearch bfs = new BestFirstSearch();

    @Test
    void solve() {
        Solution s = bfs.solve(new SearchableMaze(testMyMaze));
        assertNotEquals(0,s.size());
        assertNotEquals( null,s);
        Maze testMyMaze2= test.generate(0,0);
        assertEquals( null,testMyMaze2);
        Maze testMyMaze3= test.generate(1,1);
        assertEquals( null,testMyMaze3);
    }



    @Test
    void getName() {
        assertEquals("BestFirstSearch", bfs.getName());
    }

    @Test
    void getNumberOfNodesEvaluated() {
        assertEquals(0, bfs.getNumberOfNodesEvaluated());
    }

    @Test
    void setCostNode() {
        SearchableMaze mz = new SearchableMaze(testMyMaze);
        int x;
        x= mz.getGoalState().getCost();
        bfs.setCostNode(mz.getGoalState());
        assertEquals(mz.getGoalState().getCost(),x);
    }
}



