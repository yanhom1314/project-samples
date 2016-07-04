package g2048;

import game2048.Direction;
import game2048.GameManager;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class GameVals {
    public static final GameManager gameManager = new GameManager();
    public static final Bounds gameBounds = gameManager.getLayoutBounds();
    public final static int MARGIN = 36;

    public static void addKeyHandler(Node node) {
        node.setOnKeyPressed(k -> {
            KeyCode kc = k.getCode();
            if (kc.equals(KeyCode.S)) {
                gameManager.saveSession();
                return;
            } else if (kc.equals(KeyCode.R)) {
                gameManager.restoreSession();
                return;
            } else if (kc.equals(KeyCode.P)) {
                gameManager.pauseGame();
                return;
            } else if (kc.equals(KeyCode.Q) || kc.equals(KeyCode.ESCAPE)) {
                gameManager.quitGame();
                return;
            } else if (kc.isArrowKey()) {
                Direction direction = Direction.valueFor(kc);
                move(direction);
            }
        });
    }

    public static void addSwipeHandlers(Node node) {
        node.setOnSwipeUp(e -> move(Direction.UP));
        node.setOnSwipeRight(e -> move(Direction.RIGHT));
        node.setOnSwipeLeft(e -> move(Direction.LEFT));
        node.setOnSwipeDown(e -> move(Direction.DOWN));
    }

    public static void move(Direction direction) {
        gameManager.move(direction);
    }
}
