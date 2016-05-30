package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("icon.jpg")));
        Scene scene = new Scene(root, 280, 192);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("login.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
