package g2048;

import game2048.GameManager;
import javafx.geometry.Bounds;

public class GameVals {
    public static final GameManager gameManager = new GameManager();
    public static final Bounds gameBounds = gameManager.getLayoutBounds();
    public final static int MARGIN = 36;
}
