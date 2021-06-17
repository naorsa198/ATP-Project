package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //observes funcionallity settings:
        MyViewModel myViewModel = MyViewModel.getViewModelSingleton();
        MyModel model = (MyModel) myViewModel.getModel();
        model.addObserver(myViewModel);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = loader.load();
//        FXMLLoader fxml_loader = new FXMLLoader();
//
//        Parent root = fxml_loader.load(getClass().getResource("MainPage.fxml"));
        primaryStage.setScene(new Scene(root, 400, 700));
        primaryStage.setResizable(false);
        SetStageCloseEvent(primaryStage, loader.getController());
        primaryStage.show();

        //TODO: insert css files and bacground
    }

    public static void SetStageCloseEvent(Stage primaryStage, IView myViewController) {
        primaryStage.setOnCloseRequest(windowEvent -> {
            myViewController.exitCorrectly();
            windowEvent.consume();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
