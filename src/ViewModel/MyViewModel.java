package ViewModel;

import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer{

    private static IModel model = new MyModel();
    private static MyViewModel singleton = null;


    private int currentRow;
    private int currentCol;

    public IntegerProperty tokenRow = new SimpleIntegerProperty();
    public IntegerProperty tokenCol = new SimpleIntegerProperty();


    private MyViewModel(){
    }

    public static MyViewModel getViewModelSingleton(){
        if (singleton==null) {
            singleton = new MyViewModel();
        }
        return singleton;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public boolean getIsKnockonWall(){
        return ((MyModel)model).isKnockWall();
    }

    public void setIsKnockonWall(boolean b){
     ((MyModel)model).setKnockWall(b);
    }

    public static IModel getModel() {
        return model;
    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public Solution getSolution(){
        model.solveMaze();
        return model.getSolution();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o==model) {
            currentRow = model.getCurrentRow();
            currentCol = model.getCurrentCol();
            tokenRow.set(currentRow);
            tokenCol.set(currentCol);

            //update all my observers (views)
            setChanged();
            notifyObservers();
        }
    }

    public void setMaze(int rows, int cols) {
        model.generateMaze(rows,cols);
        InitializeProperties();
    }

    private void InitializeProperties() {
        Maze maze = getMaze();

        currentRow = maze.getStartRow();
        currentCol = maze.getStartCol();

        tokenRow.setValue(currentRow);
        tokenCol.setValue(currentCol);

    }

    public boolean isTokenInEnd() {
        return ((MyModel) model).isTokenInEndPoint();
    }

    public void setTokenInEnd(boolean b) {
        ((MyModel) model).setTokenInEnd(b);
    }

    public static void moveToken(KeyCode code) {
        model.moveToken(code);
    }

    public void saveMaze(String name_of_file){

        int index = 1;
        File fileMaze = new File("./resources/Save/"+name_of_file);


        //checks if the file exist- adding indexes
        while(fileMaze.exists()){
            fileMaze = new File("./resources/Save/"+name_of_file +"(" +index+")");
            index++;
        }
        System.out.println(fileMaze.getName());
        model.saveMaze(fileMaze);
    }

    public void loadMaze(File fileMaze) {
        model.loadMaze(fileMaze);

        //update point of start
        currentRow = model.getCurrentRow();
        currentCol= model.getCurrentCol();

    }
}
