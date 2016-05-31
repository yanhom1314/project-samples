package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        //左上角图标
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/icon.jpg")));
        //窗口样式 标题栏 关闭按钮 最大化 最小化
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("css/login.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
