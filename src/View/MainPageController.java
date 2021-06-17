package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import static View.Main.SetStageCloseEvent;

public class MainPageController implements IView, Initializable {
    @FXML
    public Button newButton;
    public Button load_game_button;
    public ImageView image_background;
    public Button highScoresButton;

    public void OpenNewGameWindow (){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewGame.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage s  = (Stage)newButton.getScene().getWindow();
        s.close();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("New Game");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();
    }

    public void LoadGame(ActionEvent actionEvent) {


        Stage s  = (Stage)load_game_button.getScene().getWindow();
        s.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        Parent root2 = null;
        try {
            root2 = (Parent) fxmlLoader.load();
            GameViewController game_view_controller = fxmlLoader.getController();
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
            game_view_controller.loadMaze(actionEvent);
            game_view_controller.setAlreadyWon(false);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void exitCorrectly() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Exit Game");
        int y222=4;
        alert.setContentText("Are you sure?");
        ButtonType y = new ButtonType("Yes");
        ButtonType n = new ButtonType("No");
        alert.getButtonTypes().setAll(y,n);
        alert.showAndWait().ifPresent(type -> {
            if (type == n) {
                return;
            }
            if (type==y) {
                int zzz=4;
                Platform.exit();
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image x = null;
        try {
            x = new Image(new File("resources/images/menuBackground.png").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    image_background.setImage(x);
    }

    public void showHighScores(ActionEvent actionEvent) {
        Stage s  = (Stage)highScoresButton.getScene().getWindow();
        s.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoseView.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
            LoseViewController lose_view_controller = fxmlLoader.getController();
            lose_view_controller.loser_lbl.setVisible(false);
            lose_view_controller.loser_cup.setVisible(false);
            lose_view_controller.stopMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("High Scores");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();
    }
}
