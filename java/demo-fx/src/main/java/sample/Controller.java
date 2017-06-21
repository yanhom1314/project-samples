package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@Singleton
public class Controller implements Initializable {
    @FXML
    private GridPane root;
    @FXML
    private Text action_target;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @Inject
    private ResourceBundleHelper resourceBundleHelper;
    @Inject
    private Hello hello;

    @Inject
    private FXMLLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hello:" + hello);
        System.out.println("loader:" + loader);
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        if (username.getText().trim().equalsIgnoreCase("test") && password.getText().trim().equalsIgnoreCase("test")) {
            try {
                ((Stage) root.getScene().getWindow()).close();
                try (InputStream in = ClassLoader.getSystemResourceAsStream("fxml/second.fxml")) {
                    Parent nextRoot = loader.load(in);
                    Stage stage = new Stage();
                    stage.setOnCloseRequest(e -> System.exit(0));
                    stage.initStyle(StageStyle.UNIFIED);
                    stage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/icon.jpg")));
                    stage.setScene(new Scene(nextRoot, 800, 500));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            action_target.setText(MessageFormat.format(resourceBundleHelper.resource().getString("login.err"), username.getText()));
        }
    }


    @FXML
    public void setOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("ENTER TO LOGIN!!!");
            handleSubmitButtonAction(null);
        }
    }

    @FXML
    protected void closeButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
