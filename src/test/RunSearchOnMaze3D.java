package test;

import algorithms.maze3D.*;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;

import java.util.ArrayList;

public class RunSearchOnMaze3D {
        public static void main(String[] args) {
            IMazeGenerator3D mg = new MyMaze3DGenerator();

            Maze3D maze = mg.generate(3,3, 3);
            SearchableMaze3D searchableMaze = new SearchableMaze3D(maze);
            maze.print();
            solveProblem(searchableMaze, new BreadthFirstSearch());
            maze.print();

            solveProblem(searchableMaze, new DepthFirstSearch());

            solveProblem(searchableMaze, new BestFirstSearch());


        }

        private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
            //Solve a searching problem with a searcher
            Solution solution = searcher.solve(domain);
            System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
            //Printing Solution Path
            System.out.println("Solution path:");
            ArrayList<AState> solutionPath = solution.getSolutionPath();
        for(int i=0; i<solutionPath.size(); i++){
            if(((SearchableMaze3D)domain).cellVal(((Maze3DState)solutionPath.get(i)).getDp(),((Maze3DState)solutionPath.get(i)).getRow(),((Maze3DState)solutionPath.get(i)).getCol())==1)
                System.out.println("***********problem**********");
         //   ((SearchableMaze3D)domain).setllVal(((Maze3DState)solutionPath.get(i)).getDp(),((Maze3DState)solutionPath.get(i)).getRow(),((Maze3DState)solutionPath.get(i)).getCol());
        }
            System.out.println(solution.Size());
            ;
       for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }
        }
    }
