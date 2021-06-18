package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import static View.Main.SetStageCloseEvent;

public class NewGameController implements IView, Observer, Initializable {

    @FXML
    public javafx.scene.control.TextField rows;
    public javafx.scene.control.TextField cols;
    public ArrayList<String> characters;
    @FXML
    public Label char_name_label;
    public ImageView showed_char;
    public ImageView stam_tmuna;
    private int currentIndexCharacter;

    public MyViewModel myViewModel;
    @FXML
    public Button start_game;

    public NewGameController(){
         myViewModel = MyViewModel.getViewModelSingleton();
         myViewModel.addObserver(this);
         characters = new ArrayList<>();
         addAllCharacters();
         currentIndexCharacter=0;
    }

    public int getCurrentIndexCharacter() {
        return currentIndexCharacter;
    }

    private void addAllCharacters() {
        characters.add("Kobe Braynt");
        characters.add("Sholi");
        characters.add("Simba");
        characters.add("France");
        characters.add("Super Mario");
        characters.add("Morty");
        characters.add("Rick");
        characters.add("Linoy Ashram");
        characters.add("Avigdor Liberman");
        characters.add("Naftali Bennett");
    }

    @Override
    public void update(Observable o, Object arg) {
        //nothing to update here
    }

    public void generateMazeOnView(ActionEvent actionEvent) {

        start_game.getScene().getWindow().hide();

        boolean problemInInput = false;
        String string_rows = rows.getText();
        String string_cols = cols.getText();

        for (int i=0; i<string_rows.length(); i++) {
            if (string_rows.charAt(i) < '0' || string_rows.charAt(i) > '9') {
                problemInInput = true;
                break;
            }
        }
            for (int i=0; i<string_cols.length() && problemInInput==false; i++) {
                if (string_cols.charAt(i) < '0' || string_cols.charAt(i) > '9') {
                    problemInInput = true;
                    break;
                }
            }

        int rows_int;
        int cols_int;

        if (problemInInput==true) {
            rows_int = 10;
            cols_int = 10;
        }

        else {
            rows_int = Integer.valueOf(rows.getText());
            cols_int = Integer.valueOf(cols.getText());
        }

        if (rows_int > 100)
            rows_int=100;
        if (cols_int >100)
            cols_int=100;

        myViewModel.setMaze(rows_int,cols_int);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();
            GameViewController game_view_controller = fxmlLoader.getController();
            game_view_controller.setCurrentIndexOfCharacter(currentIndexCharacter);
            game_view_controller.setMaze();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
       // stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(true);
        stage.setTitle("Game");
        Scene scene = new Scene(root2);
        stage.setScene(scene);
       // stage.resizableProperty();
        game_view_controller.setResizeEvent(scene);
            SetStageCloseEvent(stage, fxmlLoader.getController());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    @Override
    public void exitCorrectly() {
//nothing
    }

    public void getPrevChar(ActionEvent actionEvent) {
        if (currentIndexCharacter==0)
            currentIndexCharacter=characters.size()-1;
        else {
            currentIndexCharacter= (currentIndexCharacter-1)%characters.size();
        }
        String name = characters.get(currentIndexCharacter);
        char_name_label.setText(name);
        Image x = null;
        try {
            x = new Image(new File("./resources/images/token" + currentIndexCharacter + ".png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        showed_char.setImage(x);

    }


    public void getNextChar(ActionEvent actionEvent) {
        if (currentIndexCharacter==characters.size()-1)
            currentIndexCharacter=0;
        else {
            currentIndexCharacter= (currentIndexCharacter+1)%characters.size();
        }
        String name = characters.get(currentIndexCharacter);
        char_name_label.setText(name);
        Image x = null;
        try {
            x = new Image(new File("./resources/images/token" + currentIndexCharacter + ".png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        showed_char.setImage(x);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image x = null;
        try {
            x = new Image(new File("./resources/images/token" + currentIndexCharacter + ".png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        showed_char.setImage(x);
        char_name_label.setText(characters.get(currentIndexCharacter));

    }
}
