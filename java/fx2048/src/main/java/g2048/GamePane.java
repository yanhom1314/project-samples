package g2048;

import game2048.Direction;
import game2048.GameManager;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author bruno.borges@oracle.com
 */
public class GamePane extends StackPane {

    private GameManager gameManager;
    private Bounds gameBounds;
    private final static int MARGIN = 36;

    public GamePane() {
        gameManager = new GameManager();
        gameBounds = gameManager.getLayoutBounds();

        getChildren().add(gameManager);

        getStyleClass().addAll("game-root");
        ChangeListener<Number> resize = (ov, v, v1) -> {
            double scale = Math.min((getWidth() - MARGIN) / gameBounds.getWidth(), (getHeight() - MARGIN) / gameBounds.getHeight());
            gameManager.setScale(scale);
            gameManager.setLayoutX((getWidth() - gameBounds.getWidth()) / 2d);
            gameManager.setLayoutY((getHeight() - gameBounds.getHeight()) / 2d);
        };
        widthProperty().addListener(resize);
        heightProperty().addListener(resize);

        addSwipeHandlers(this);
        setFocusTraversable(true);
        this.setOnMouseClicked(e -> requestFocus());
    }


    private void addSwipeHandlers(Node node) {
        node.setOnSwipeUp(e -> move(Direction.UP));
        node.setOnSwipeRight(e -> move(Direction.RIGHT));
        node.setOnSwipeLeft(e -> move(Direction.LEFT));
        node.setOnSwipeDown(e -> move(Direction.DOWN));
    }

    private void move(Direction direction) {
        gameManager.move(direction);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public static int getMargin() {
        return MARGIN;
    }
}
