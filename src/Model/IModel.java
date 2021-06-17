package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;

public interface IModel {6
    void generateMaze (int row, int col);
    void solveMaze();
    void saveMaze(File Filemaze);
    void moveToken(KeyCode movement);
    void loadMaze(File file); //////???????????????
    int getCurrentRow();
    int getCurrentCol();
    Maze getMaze();
    Solution getSolution();
}
