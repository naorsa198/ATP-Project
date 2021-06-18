package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AboutViewController implements IView, Initializable {

    @FXML
    public Button close_button;
    public Label label_orel;
    public Label label_naor;
    public Button button_send;
    public Label label_application;

    @Override
    public void exitCorrectly() {
        //nothing
    }


    public void closeWindow(ActionEvent actionEvent) {
        Stage s  = (Stage)close_button.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label_orel.setWrapText(true);
        label_naor.setWrapText(true);
        label_application.setWrapText(true);

        //set the text
        label_orel.setText("Orel Revach, 28 years old second year student of Information Systems and Software Engineering in Ben Gurion University, BeerSheva.\n" +
                "\n" +
                "Developed this game by the last year.\n" +
                "for more information, feel free to contact Orel:\n" +
                "Phone number: +972 54-449-8365\n" +
                "Email address: orelre@post.bgu.ac.il");

        label_naor.setText("Naor Saraf, 29 years old second year student of Information Systems and Software Engineering in Ben Gurion University, BeerSheva.\n" +
                "\n" +
                "Developed this game by the last year.\n" +
                "for more information, feel free to contact Naor:\n" +
                "Phone number: +972 52-866-0712\n" +
                "Email address: Naorserf@gmail.com");
        label_application.setText("Version: 1.00\n" +
                "\n" +
                "this game is a just one part of 3 parts who implement a various tools of java developing.\n" +
                "we hope you will enjoy this!\n" +
                "feel free to send us some feedback from \"Help\" section.");

    }

}
