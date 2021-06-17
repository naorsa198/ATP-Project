package View;


import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {

    private Maze maze;
    private int TokenPosRow;
    private int TokenPosCol;
    private int indexOfCharacter;


    private boolean solved;

    public MazeDisplayer() {
        solved = false;
    }

    private Image wall;
    private StringProperty ImageFileNameWall = new SimpleStringProperty();

    private Image path;
    private StringProperty ImageFileNamePath = new SimpleStringProperty();

    private Image token;
    private StringProperty ImageFileNameToken = new SimpleStringProperty();

    private Image endCell;
    private StringProperty ImageFileNameEndCell = new SimpleStringProperty();

    private Image solutionPath;
    private StringProperty ImageFileNameSolutionPath = new SimpleStringProperty();

    private Image startCell;
    private StringProperty ImageFileNameStart = new SimpleStringProperty();

    public void setIndexOfCharacter(int indexOfCharacter) {
        this.indexOfCharacter = indexOfCharacter;
    }

    public void setTokenPosition(int row, int col) {
        TokenPosCol = col;
        TokenPosRow = row;
        redraw();
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    private void redrawToken() {
        if (!solved) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double minSize = Math.max(maze.getMazeRows(), maze.getMazeCols());
            double cellHeight = canvasHeight / minSize;
            double cellWidth = canvasWidth / minSize;
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(cellWidth * maze.getStartCol(), maze.getStartRow() * cellHeight, cellWidth, cellHeight);
            // gc.drawImage(path, maze.getStartCol() * cellWidth, maze.getStartRow() * cellHeight, cellWidth, cellHeight);
            gc.drawImage(startCell, maze.getStartCol() * cellWidth, maze.getStartRow() * cellHeight, cellWidth, cellHeight);
            drawToken(cellHeight, cellWidth);

        }


    }

    public void setMaze(Maze m) {
        this.maze = m;
        TokenPosRow = maze.getStartRow();
        TokenPosCol = maze.getStartCol();
        redraw();
    }

    public void redraw() {

        if (!solved) {
            //TODO: to change 11/15 to the best resolution
            if (maze != null) {
                if (this.getScene() != null) {
                    this.setWidth(this.getScene().getWidth() * 12 / 15);
                    this.setHeight(this.getScene().getHeight() * 12 / 15);
                }
                double canvasHeight = getHeight();
                double canvasWidth = getWidth();
                double minSize = Math.max(maze.getMazeRows(), maze.getMazeCols());
                double cellHeight = canvasHeight / minSize;
                double cellWidth = canvasWidth / minSize;

                try {
                    if (indexOfCharacter == 7)
                        wall = new Image(new FileInputStream("resources/images/wall" + 4 + ".png"));
                    else
                        wall = new Image(new FileInputStream("resources/images/wall" + indexOfCharacter + ".png"));
                    token = new Image(new FileInputStream("resources/images/token" + indexOfCharacter + ".png"));
                    if (indexOfCharacter == 9) {
                        path = new Image(new FileInputStream("resources/images/path" + 2 + ".png"));
                    } else if (indexOfCharacter == 3) {
                        path = new Image(new FileInputStream("resources/images/path" + 7 + ".png"));
                    } else
                        path = new Image(new FileInputStream("resources/images/path" + indexOfCharacter + ".png"));
                    if (indexOfCharacter == 6)
                        endCell = new Image(new FileInputStream("resources/images/end" + 5 + ".png"));
                    else
                        endCell = new Image(new FileInputStream("resources/images/end" + indexOfCharacter + ".png"));
                    if (indexOfCharacter == 6) {
                        startCell = new Image(new FileInputStream("resources/images/start" + 5 + ".png"));
                    } else if (indexOfCharacter == 7 || indexOfCharacter == 8 || indexOfCharacter == 4) {
                        startCell = new Image(new FileInputStream("resources/images/start" + 3 + ".png"));
                    } else if (indexOfCharacter == 1) {
                        startCell = new Image(new FileInputStream("resources/images/start" + 2 + ".png"));
                    } else
                        startCell = new Image(new FileInputStream("resources/images/start" + indexOfCharacter + ".png"));


                    GraphicsContext gc = getGraphicsContext2D();
                    gc.clearRect(0, 0, getWidth(), getHeight());

                    //Draw Maze
                    for (int i = 0; i < maze.getMazeRows(); i++) {
                        for (int j = 0; j < maze.getMazeCols(); j++) {
                            //draw path
                            gc.drawImage(path, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                            if (maze.getVal(i, j) == 1) { //wall
                                //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                                gc.drawImage(wall, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                            }
                        }
                    }
                    //draw end point
                    gc.drawImage(endCell, maze.getEndCol() * cellWidth, maze.getEndRow() * cellHeight, cellWidth, cellHeight);

                    //draw start point
                    gc.drawImage(startCell, maze.getStartCol() * cellWidth, maze.getStartRow() * cellHeight, cellWidth, cellHeight);

                    //Draw Character
                    drawToken(cellHeight, cellWidth);
                    //gc.setFill(Color.RED);
                    //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                    //gc.drawImage(characterImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private void drawToken(double cellHeight, double cellWidth) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.drawImage(token, TokenPosCol * cellWidth, TokenPosRow * cellHeight, cellWidth, cellHeight);
    }

    private Image getWall() {
        return wall;
    }

    private Image getPath() {
        return path;
    }

    private Image getToken() {

        //  Image player =
        return path;
    }

    private Image getEndCell() {
        return endCell;
    }

    private Image getSolutionPath() {
        return solutionPath;
    }


    public void solve(Solution solution) {
        try {
            ArrayList<AState> solution_array = solution.getSolutionPath();
            int size = solution_array.size();
            if (indexOfCharacter == 6)
                solutionPath = new Image(new FileInputStream("resources/images/solutionPath" + 5 + ".png"));
            else
                solutionPath = new Image(new FileInputStream("resources/images/solutionPath" + indexOfCharacter + ".png"));
            GraphicsContext gc = getGraphicsContext2D();

            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double minSize = Math.max(maze.getMazeRows(), maze.getMazeCols());
            double cellHeight = canvasHeight / minSize;
            double cellWidth = canvasWidth / minSize;

            int tmp_size = 1;
            while (tmp_size <= size) {
                for (int i = 0; i < tmp_size; i++) {
                    MazeState tmp = (MazeState) solution_array.get(i);
                    int tmp_row = tmp.getRow();
                    int tmp_col = tmp.getCol();
                    if (tmp_row==maze.getStartRow() && tmp_col==maze.getStartCol()) {//nothing
                    }
                    else if (tmp_row == maze.getEndRow() && tmp_col==maze.getEndCol()) {
                        //nothing
                    }
                    else
                        gc.drawImage(solutionPath, tmp_col * cellWidth, tmp_row * cellHeight, cellWidth, cellHeight);

                }
                tmp_size++;
                //pause();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


        public void clear () {
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());


        }

    }

