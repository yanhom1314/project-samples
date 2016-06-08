package g2048;

import game2048.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameController {
    @FXML
    private GameManager gm;
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

    @FXML
    public void handleSaveButtonAction(ActionEvent e) {
        gm.saveSession();
    }

    @FXML
    public void handleRestoreButtonAction(ActionEvent e) {
        gm.restoreSession();
    }

    @FXML
    public void handlePauseButtonAction(ActionEvent e) {
        gm.pauseGame();
    }

    @FXML
    public void handleReplayButtonAction(ActionEvent e) {
        gm.tryAgain();
    }

    @FXML
    public void handleInfoButtonAction(ActionEvent e) {
        gm.aboutGame();
    }

    @FXML
    public void handleQuitButtonAction(ActionEvent e) {
        gm.quitGame();
    }
}
