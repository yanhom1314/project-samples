package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.inject.Inject;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SController implements Initializable {
    @FXML
    private GridPane second;
    @Inject
    private FXMLLoader loader;
    @Inject
    private Controller controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("loader:" + loader);
        System.out.println("controller:" + controller);
    }

    @FXML
    public void thirdPaneSubmitButtonAction(ActionEvent event) {
        try {
            ((Stage) second.getScene().getWindow()).close();
            try (InputStream in = ClassLoader.getSystemResourceAsStream("fxml/third.fxml")) {
                Parent nextRoot = loader.load(in);
                Stage stage = new Stage();
                stage.setOnCloseRequest(e -> System.exit(0));
                stage.initStyle(StageStyle.UNIFIED);
                stage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/icon.jpg")));
                stage.setScene(new Scene(nextRoot, 800, 500));
                ColorfulCircles.second(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
