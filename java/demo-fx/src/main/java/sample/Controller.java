package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private GridPane root;
    @FXML
    private Text action_target;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    private ResourceBundle bundle = ResourceBundle.getBundle("messages");

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        if (username.getText().trim().equalsIgnoreCase("test") && password.getText().trim().equalsIgnoreCase("test")) {
            try {
                ((Stage) root.getScene().getWindow()).close();
                Parent nextRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/second.fxml"));
                //Stage stage = (Stage) root.getScene().getWindow();
                Stage stage = new Stage();
                stage.setOnCloseRequest(e -> System.exit(0));
                stage.initStyle(StageStyle.UNIFIED);
                stage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/icon.jpg")));
                stage.setScene(new Scene(nextRoot, 800, 500));
                ColorfulCircles.second(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            action_target.setText(MessageFormat.format(bundle.getString("login.err"), username.getText()));
        }
    }

    @FXML
    protected void closeButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
