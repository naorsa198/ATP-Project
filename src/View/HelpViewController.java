package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class HelpViewController implements IView, Initializable {

    @FXML
    public Button close_button;
    public Label label_bugs;
    public Label label_instructions;
    public TextField text_field_name;
    public TextArea text_field_bug;
    public TextField text_field_email;
    public Button button_send;

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
        label_bugs.setWrapText(true);
        label_instructions.setWrapText(true);

        //set the text
        label_instructions.setText("your main goal is to go to the end point of the maze.\n" +
                "you can use your keyboard in order to to that.\n" +
                "Keys: \n" +
                "8 for UP\n" +
                "2 for Down\n" +
                "6 for Right\n" +
                "4 for Left\n" +
                "9 for Diagonal to the Right-Up\n" +
                "7 for Diagonal to the Left-Up\n" +
                "3 for Diagonal to the Right-Down\n" +
                "5 for Diagonal to the Left-Down\n" +
                "\n" +
                "if you are not having NUMPAD numbers on your laptop, you can always use the regular number keys.\n" +
                "\n");

        label_bugs.setText("Please report the bug and we will take care of it as soon as possible! thanks");

    }

    public void SendEmail(ActionEvent actionEvent) {

        String name = text_field_name.getText();
        String email = text_field_email.getText();
        String message = text_field_bug.getText();
        String our_mail = "naorsaraf@gmail.com";

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Sent your bug");
        alert.setContentText("Thanks for your support");
        ButtonType close = new ButtonType("close");
        alert.getButtonTypes().setAll(close);
        alert.showAndWait();
        text_field_bug.clear();
        text_field_email.clear();
        text_field_name.clear();


    }
}
