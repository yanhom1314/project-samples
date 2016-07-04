package g2048;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameController {
    @FXML
    private GamePaneII gamePane;
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
        System.out.println("gamePane:" + gamePane);
        System.out.println("gameManager:" + gamePane.getGameManager());
        System.out.println("e:" + e.getEventType());

        gamePane.getGameManager().saveSession();
    }

    @FXML
    public void handleRestoreButtonAction(ActionEvent e) {
        gamePane.getGameManager().restoreSession();
    }

    @FXML
    public void handlePauseButtonAction(ActionEvent e) {
        gamePane.getGameManager().pauseGame();
    }

    @FXML
    public void handleReplayButtonAction(ActionEvent e) {
        gamePane.getGameManager().tryAgain();
    }

    @FXML
    public void handleInfoButtonAction(ActionEvent e) {
        gamePane.getGameManager().aboutGame();
    }

    @FXML
    public void handleQuitButtonAction(ActionEvent e) {
        gamePane.getGameManager().quitGame();
    }
}
