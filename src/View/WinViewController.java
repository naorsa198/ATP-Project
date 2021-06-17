package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static View.Main.SetStageCloseEvent;

public class WinViewController extends MyViewController implements Initializable {

    @FXML
    public ImageView win_image;
    public Button button_main;
    public Label time_label;
    public Button send_result;
    public TextField text_field_name;
    public Label table_label;

    private String time;
    private LinkedList<String> high_scores;

    public WinViewController() {
        high_scores = getHighScoresFile();
    }

    public static LinkedList<String> getHighScoresFile() {

        LinkedList<String> toReturn = null;
        File file = new File("resources/Scores/table");

        //if there is no file (probably first time run) so I will create one
        try {
            if (!file.exists()) {
                FileOutputStream file_output_stream = null;
                file_output_stream = new FileOutputStream(file);


                toReturn = new LinkedList<>();
                file.createNewFile(); //if not exist it will create it
                ObjectOutputStream oos = new ObjectOutputStream(file_output_stream);
                oos.writeObject(toReturn);
                oos.close();
            }
            else {
                //read the file and send it
                FileInputStream file_input_stream = new FileInputStream(file);
                ObjectInputStream oin = new ObjectInputStream(file_input_stream);
                toReturn = (LinkedList<String>) oin.readObject();
                oin.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setNewMusic("win");
        playMusic();
        showTop5OfTable();
//TODO: to change algo to bfs

        try {
            Image x  = new Image(new File("resources/images/win.png").toURI().toURL().toExternalForm());
            win_image.setImage(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ListView<String> list = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList (
                "1 - Single", "55 - Double", "32 - Suite", "5 - Family App");

    }

    public void openMainMenu(ActionEvent actionEvent) {


        Stage s  = (Stage)button_main.getScene().getWindow();
        s.close();
        if (mediaPlayer!=null)
            stopMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
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

    public void setTime(String time) {
        this.time = time;
        time_label.setText(time);
    }

    public void sendAndUpdateTable(ActionEvent actionEvent) {
        send_result.setDisable(true);
        LinkedList<String> newTable = null;
        String name = text_field_name.getText();
        while (name.contains("$")) {
            int index = name.indexOf('$');
            name = name.substring(0,index) + name.substring(index+1);
        }
        File file = new File("resources/Scores/table");
        //read the file and send it
        FileInputStream file_input_stream = null;
        try {
            file_input_stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(file_input_stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
        newTable = (LinkedList<String>) oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            oin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (newTable != null) {
            String row = name + "$" + time;
            newTable.add(row);
        }
        newTable.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int indexOfDollar1 = o1.indexOf('$');
                int indexOfDollar2 = o2.indexOf('$');
                int p1 = Integer.parseInt(o1.substring(indexOfDollar1+1));
                int p2 = Integer.parseInt(o2.substring(indexOfDollar2+1));
                if (p1 < p2 ){
                    return 1;
                }
                else if (p1 > p2){
                    return -1;
                }
                else
                    return 0;
            }

        });

        //write the sorted table to the file
        FileOutputStream file_output_stream = null;
        try {
            file.delete();

            file_output_stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(file_output_stream);

            oos.writeObject(newTable);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        high_scores = getHighScoresFile();
        showTop5OfTable();



    }

    private void showTop5OfTable() {
        String table_view = "";
        for (int i=0; i<5 && i<high_scores.size(); i++) {
            int rank = i+1;
            int indexOfDollar = high_scores.get(i).indexOf('$');
            String name = high_scores.get(i).substring(0,indexOfDollar);
            String name_time = high_scores.get(i).substring(indexOfDollar+1);
            table_view = table_view + rank + ". " + name + " - " + name_time + '\n';
        }
        table_label.setText(table_view);

    }

    @Override
    public void exitCorrectly() {

    }

}
