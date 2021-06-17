package View;
import Server.Configurations;
import Server.Server;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesViewController extends Dialog implements Initializable {

    private String algorithmString;
    private String generatorString;
    private int threadNum;
    public ChoiceBox algorithmChoiceBox;
    public ChoiceBox mazeGeneratorChoiceBox;
    public Spinner spinner;
    public SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
    private Stage stage;
    Properties properties = Configurations.getInstance().getProperties();


    public void saveChanges(){
        algorithmString = (String)algorithmChoiceBox.getValue();
        generatorString = (String)mazeGeneratorChoiceBox.getValue();
        threadNum = (Integer)spinner.getValue();

        setProperties(threadNum,algorithmString, generatorString);

        stage.close();
    }

    private void setProperties(int threadNum, String algorithmString, String generatorString) {

        if (generatorString=="SimpleMazeGenerator")
            generatorString="MyMazeGenerator";

        properties.setProperty("solve_maze_algorithm",algorithmString);
        properties.setProperty("generate_maze_algorithm",generatorString);
        properties.setProperty("num_of_threads_in_pool",Integer.toString(threadNum));

        OutputStream output;
        String filename = "config.properties";
        try {
            output = new FileOutputStream(filename);
            properties.store(output, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }



    public void closeButton(){
        stage.close();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        spinner.setValueFactory(valueFactory);

        algorithmChoiceBox.getItems().addAll("BestFirstSearch","DepthFirstSearch","BreadthFirstSearch","Random");
        String searchValue = properties.getProperty("solve_maze_algorithm");
        algorithmChoiceBox.setValue(searchValue);
        mazeGeneratorChoiceBox.getItems().addAll("MyMazeGenerator","SimpleMazeGenerator");
        String generateValue = properties.getProperty("generate_maze_algorithm");
        mazeGeneratorChoiceBox.setValue(generateValue);

    }
}