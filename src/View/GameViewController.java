package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static View.Main.SetStageCloseEvent;

public class GameViewController extends MyViewController implements Observer, Initializable {


    public MazeDisplayer mazeDisplayer;
    @FXML
    public Button new_game;
    public Label time_label;
    public Button solve_maze_button;
    public MenuBar menu;
    public Label lbl_after_solve;
    public Button close_button;
    public Button mute_unmute_button;
    private MyViewModel myViewModel;
    private Maze maze;
    private Solution solution;
    private int currentIndexOfCharacter;

    //timer issues:
    private Integer starttime;
    private Integer seconds;
    private boolean DoItOnce;
    private Timeline time;
    private Timeline five_seconds_of_solve;
    private Integer five_sec;
    private MediaPlayer background_player;
    private Media background_sound;
    private boolean alreadyWon;


    public void setAlreadyWon(boolean alreadyWon) {
        this.alreadyWon = alreadyWon;
    }

    protected void setNewMusicWithIndexForBackground(String filename, int index) {

        String location = "resources/music/" +filename + index + ".mp3";
        background_sound = new Media(new File(location).toURI().toString());
        background_player = new MediaPlayer(background_sound);

    }
    private Integer calculateStartTime() {
        int rows = maze.getMazeRows();
        int cols = maze.getMazeCols();

        double res = rows*cols*0.15;
        int result = (int)res;

        if (result==0)
            result=1;
        if (result>100)
            result=99;
        if (result<10)
            result=10;

       // return 50;
        return result;
    }


    public IntegerProperty tokenRow = new SimpleIntegerProperty();
    public IntegerProperty tokenCol = new SimpleIntegerProperty();

    public void setCurrentIndexOfCharacter(int currentIndexOfCharacter) {
        this.currentIndexOfCharacter = currentIndexOfCharacter;
        setNewMusicWithIndex("wall",currentIndexOfCharacter);
    }





    public GameViewController() {
        alreadyWon = false;
        DoItOnce = false;
        mazeDisplayer = new MazeDisplayer();

        //set view model
        myViewModel = MyViewModel.getViewModelSingleton();
        myViewModel.addObserver(this);

        maze = myViewModel.getMaze();
        tokenRow.bindBidirectional(myViewModel.tokenRow);
        tokenCol.bindBidirectional(myViewModel.tokenCol);

        //set timer
    forLoadFromOutside();
    five_sec = 5;

    }

    public void forLoadFromOutside() {
        if (maze!=null) {
            starttime=calculateStartTime();
            seconds=starttime;
        }

    }

    public void setMaze(){



        maze = myViewModel.getMaze();
        mazeDisplayer.setIndexOfCharacter(currentIndexOfCharacter);
        System.out.println(currentIndexOfCharacter);
        mazeDisplayer.setMaze(maze); //also drawing
        if (!DoItOnce) {
            playTheGameMusic();
             forLoadFromOutside();
            time_label.setText(starttime.toString());
            doTime();
            DoItOnce = true;
        }
        //TODO: to fix the servers issue after 10 -20 runs on row

    }


    public void playTheGameMusic() {
        if (currentIndexOfCharacter==7)
            setNewMusicWithIndexForBackground("game",3);
        else if (currentIndexOfCharacter==6)
            setNewMusicWithIndexForBackground("game",5);
        else
            setNewMusicWithIndexForBackground("game",currentIndexOfCharacter);
        background_player.setOnEndOfMedia(new Runnable() {
            public void run() {
                background_player.seek(Duration.ZERO);
            }
        });
        background_player.play();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    public void solve_maze(ActionEvent actionEvent) {
        if (time!=null)
            time.stop();
        solve_maze_button.setDisable(true);
        solution = myViewModel.getSolution();
        if (solution !=null) {
            mazeDisplayer.solve(solution);
            mazeDisplayer.setSolved(true);
        }
        wait5secondsAndClear();
    }

    private void wait5secondsAndClear() {
        lbl_after_solve.setText("Game Over! to close the window - click the button");
        menu.setDisable(true);
        new_game.setDisable(true);
        close_button.setVisible(true);
    }



    @Override
    public void update(Observable o, Object arg) {
        if (o == myViewModel) {
            if (!mazeDisplayer.isSolved()) {
                setMaze();
                int row = (myViewModel.getCurrentRow());
                int col = myViewModel.getCurrentCol();
                mazeDisplayer.setTokenPosition(row, col);

                //for sound of token on wall

                if (myViewModel.getIsKnockonWall()) {
                    if (!isPlayingMusic())
                        playMusic();
                    else {
                        stopMusic();
                        playMusic();
                    }
                    myViewModel.setIsKnockonWall(false);
                    }
                }


                if (myViewModel.isTokenInEnd()) {
                    if (time_label.getText() != "0" && !alreadyWon) {
                        alreadyWon = true;
                        Win();
                        time.stop();
                        myViewModel.setTokenInEnd(false); //to avoid still show the win message for new game
                    }

                }
            }
        }


    private void LoseGame() {
        Stage s  = (Stage)new_game.getScene().getWindow();
        s.close();
        if (background_player!=null)
            background_player.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoseView.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Lose game");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();


    }


    private void Win() {
        Stage s  = (Stage)new_game.getScene().getWindow();
        s.close();
        if (background_player!=null)
            background_player.stop();
        if (mediaPlayer!=null)
            mediaPlayer.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WinView.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WinViewController win_controller = fxmlLoader.getController();
        win_controller.setTime(time_label.getText());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Wow!!! You won");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();


    }

    public void KeyPressed(KeyEvent keyEvent) {

            MyViewModel.moveToken(keyEvent.getCode());
            keyEvent.consume();


    }

    public void setResizeEvent(Scene scene){
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mazeDisplayer.redraw();
        }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mazeDisplayer.redraw();
            }
        });
    }

    public void saveMaze(ActionEvent actionEvent) {

        Date today = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("dd-MM-yy");
        String defualt_file_name = format.format(today);

        TextInputDialog dialog = new TextInputDialog("Maze "+ defualt_file_name);
        dialog.setTitle("Save Maze");
        dialog.setHeaderText("Please enter a name to your game:");

        Optional<String> result = dialog.showAndWait();
        String filename = "";
        if (!result.isPresent()) {
            filename = "Maze "+ defualt_file_name;
        } else
            filename = result.get();

        myViewModel.saveMaze(filename);

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Save Maze");
        alert.setContentText("Saved Maze! :)");
        ButtonType ok = new ButtonType("OK");
        alert.getButtonTypes().setAll(ok);
        alert.showAndWait();

    }

    public void loadMaze(ActionEvent actionEvent) {
        if (time!=null)
            time.stop();
        alreadyWon=false;


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please choose a maze to load");
        fileChooser.setInitialDirectory(new File("./resources/Save"));
        File file = fileChooser.showOpenDialog(new PopupWindow() {
        });
        if (file ==null) {
            Stage s = (Stage) solve_maze_button.getScene().getWindow();
            s.close();
            return;
        }
        //check if the file is a maze:

        if (!fileIsMaze(file) || !file.exists() || file.isDirectory()) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Unsupported File");
            alert.setContentText("Please choose supported maze file");
            ButtonType ok = new ButtonType("OK");
            ButtonType cancel = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(ok,cancel);
            alert.showAndWait().ifPresent(type -> {
                if (type == cancel) {//Current
                    return;
                }
                if (type==ok) {
                    loadMaze(actionEvent);
                }
            });

        } else {
            myViewModel.loadMaze(file);
        }
        starttime=calculateStartTime();
        seconds=starttime;
        time.play();
        if (mediaPlayer==null)
        setCurrentIndexOfCharacter(currentIndexOfCharacter);
    }

    private boolean fileIsMaze(File file) {


        if (file.getAbsolutePath().contains("Save"))
            return true;
        return false;
        }

    public void newGame(ActionEvent actionEvent) {
        if (myViewModel.getMaze()==null)
            solve_maze_button.setDisable(true);
        if (time!=null)
            time.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewGame.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int ssss;

        Stage s  = (Stage)new_game.getScene().getWindow();
        s.close();
        if (background_player!=null)
            background_player.stop();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("New Game");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);

        stage.show();
    }

    public void Exit(ActionEvent actionEvent) {
        exitCorrectly();
    }

    @Override
    public void exitCorrectly() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Exit Game");
        int x = 0;
        alert.setContentText("Are you sure?");
        ButtonType y = new ButtonType("Yes");
        ButtonType n = new ButtonType("No");
        alert.getButtonTypes().setAll(y,n);
        alert.showAndWait().ifPresent(type -> {
            if (type == n) {
                return;
            }
            if (type==y) {
                Platform.exit();
            }
        });

    }

    public void help_function(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpView.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int s;

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Help");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);

        stage.show();

    }

    public void openPropView(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Properties");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("PropertiesView.fxml").openStream());
            Scene scene = new Scene(root,400,350);
            stage.setScene(scene);
            PropertiesViewController propertiesViewController = fxmlLoader.getController();
            propertiesViewController.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch (Exception e){

        }

    }

    public void openAboutView(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutView.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int s;

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("About");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);

        stage.show();
    }

    private void doTime() {
        time= new Timeline();


        KeyFrame frame= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {


                seconds--;
                time_label.setText(seconds.toString());
                if(seconds<=0){
                    time.stop();
                    time_label.setTextFill(Color.RED);
                    if (!alreadyWon)
                        LoseGame();
                }

                if (myViewModel.isTokenInEnd())
                    time.stop();
                //to end the thread and make the win page controoler got it good


            }


        });

        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if(time!=null){
            time.stop();
        }
        time.play();


    }

    public void openMainMenu(ActionEvent actionEvent) {


        Stage s  = (Stage)solve_maze_button.getScene().getWindow();
        s.close();
        if (background_player!=null)
            background_player.stop();
        if (mediaPlayer!=null)
            mediaPlayer.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        FXMLLoader fxml_loader = new FXMLLoader();
//
        int y=9;
//        Parent root = fxml_loader.load(getClass().getResource("MainPage.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root, 400, 700));
        primaryStage.setResizable(false);
        SetStageCloseEvent(primaryStage, loader.getController());
        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void closeButton(ActionEvent actionEvent) {
        openMainMenu(actionEvent);
    }


    public void muteOrUnmute(ActionEvent actionEvent) {
        if (!background_player.isMute()) {
        mute_unmute_button.setText("Unmute");
            if (background_player!=null)
                background_player.setMute(true);
            if (mediaPlayer!=null)
                mediaPlayer.setMute(true);
        }
        else {
            mute_unmute_button.setText("Mute");
            if (background_player!=null)
                background_player.setMute(false);
            if (mediaPlayer!=null)
                mediaPlayer.setMute(false);

        }
    }
}
