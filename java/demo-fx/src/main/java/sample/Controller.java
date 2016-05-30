package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.Locale;
import java.util.ResourceBundle;


public class Controller {
    @FXML
    private Text action_target;
    private ResourceBundle bundle  =ResourceBundle.getBundle("messages");
    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        action_target.setText(bundle.getString("callback.text"));
    }
}
