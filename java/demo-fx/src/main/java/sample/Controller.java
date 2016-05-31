package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.text.MessageFormat;
import java.util.ResourceBundle;


public class Controller {
    @FXML
    private Text action_target;

    @FXML
    private TextField user_name;


    private ResourceBundle bundle = ResourceBundle.getBundle("messages");

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        if (user_name.getText() != null && user_name.getText().trim().length() > 1) {
            action_target.setText(MessageFormat.format(bundle.getString("login.err"), user_name.getText()));
        } else action_target.setText(bundle.getString("callback.text"));
    }

    @FXML
    protected void closeButtonAction(ActionEvent event) {
        System.exit(0);
    }
}
