package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new AddModule());
        FXMLLoader fxmlLoader = injector.getInstance(FXMLLoader.class);

        try (InputStream fxmlInputStream = ClassLoader.getSystemResourceAsStream("fxml/main.fxml")) {
            Parent root = fxmlLoader.load(fxmlInputStream);
            primaryStage.setScene(new Scene(root, 400, 320));
            primaryStage.setTitle("JavaFX 8 Dependency injection");
            //logo icon
            primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/icon.jpg")));
            //window title close max min
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
