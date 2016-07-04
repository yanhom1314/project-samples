package g2048;

import game2048.GamePane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameController {
    @FXML
    private GamePane root;
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
        root.getGameManager().saveSession();
    }

    @FXML
    public void handleRestoreButtonAction(ActionEvent e) {
        root.getGameManager().restoreSession();
    }

    @FXML
    public void handlePauseButtonAction(ActionEvent e) {
        root.getGameManager().pauseGame();
    }

    @FXML
    public void handleReplayButtonAction(ActionEvent e) {
        root.getGameManager().tryAgain();
    }

    @FXML
    public void handleInfoButtonAction(ActionEvent e) {
        root.getGameManager().aboutGame();
    }

    @FXML
    public void handleQuitButtonAction(ActionEvent e) {
        root.getGameManager().quitGame();
    }
}
