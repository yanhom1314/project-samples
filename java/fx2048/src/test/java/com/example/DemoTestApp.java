package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DemoTestApp extends Application {
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("test.fxml"));

        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/icon.jpg")));

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
