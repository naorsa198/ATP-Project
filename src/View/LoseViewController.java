package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static View.Main.SetStageCloseEvent;

public class LoseViewController extends MyViewController implements Initializable {
    public Label high_scores_table;
    public Label loser_lbl;
    public Button main_menu_button;
    public ImageView loser_cup;
    private LinkedList<String> high_scores;

    public LoseViewController(){
        high_scores = WinViewController.getHighScoresFile();
    }

    @Override
    public void exitCorrectly() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showAllScores();
        try {
            Image x  = new Image(new File("./resources/images/loser.png").toURI().toURL().toExternalForm());
            loser_cup.setImage(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (mediaPlayer!=null)
            stopMusic();
        setNewMusic("lose");
        playMusic();


    }

    private void showAllScores() {
        String table_view = "";
        for (int i=0; i<high_scores.size(); i++) {
            int rank = i+1;
            int indexOfDollar = high_scores.get(i).indexOf('$');
            String name = high_scores.get(i).substring(0,indexOfDollar);
            String name_time = high_scores.get(i).substring(indexOfDollar+1);
            table_view = table_view + rank + ". " + name + " - " + name_time + '\n';
        }
        high_scores_table.setWrapText(true);
        high_scores_table.setText(table_view);
    }

    public void openMainMenu(ActionEvent actionEvent) {

        Stage s  = (Stage)main_menu_button.getScene().getWindow();
        s.close();
        if (mediaPlayer!=null)
            stopMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            int x =9;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        FXMLLoader fxml_loader = new FXMLLoader();
//
//        Parent root = fxml_loader.load(getClass().getResource("MainPage.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root, 400, 700));
        primaryStage.setResizable(false);
        SetStageCloseEvent(primaryStage, loader.getController());
        primaryStage.show();

    }

}
