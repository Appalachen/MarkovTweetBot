package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Markov Twitter Bot");
        primaryStage.setScene(new Scene(root, 900, 800));
        primaryStage.show();
        primaryStage.getIcons().add(new Image("/res/resources/images/icon.png"));
    }


    public static void main(String[] args) {
        launch(args);

    }
}
