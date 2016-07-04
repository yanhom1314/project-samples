package g2048;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

import static g2048.GameVals.gameManager;

public class GameController implements Initializable {
    @FXML
    private StackPane gamePane; //private GamePane gamePane;
    @FXML
    private Button mSave;
    @FXML
    private Button mRestore;
    @FXML
    private Button mPause;
    @FXML
    private Button mReplay;
    @FXML
    private Button mInfo;
    @FXML
    private Button mQuit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("gamePane:" + gamePane);
        System.out.println("mSave:" + mSave);
        System.out.println("gameManager:" + gameManager);
    }

    @FXML
    public void handleSaveButtonAction(ActionEvent e) {

        System.out.println("e:" + e.getEventType());
        gameManager.saveSession();
    }

    @FXML
    public void handleRestoreButtonAction(ActionEvent e) {
        gameManager.restoreSession();
    }

    @FXML
    public void handlePauseButtonAction(ActionEvent e) {
        gameManager.pauseGame();
    }

    @FXML
    public void handleReplayButtonAction(ActionEvent e) {
        gameManager.tryAgain();
    }

    @FXML
    public void handleInfoButtonAction(ActionEvent e) {
        gameManager.aboutGame();
    }

    @FXML
    public void handleQuitButtonAction(ActionEvent e) {
        gameManager.quitGame();
    }
}
