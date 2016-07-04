package g2048;


import javafx.scene.layout.StackPane;

/**
 * @author bruno.borges@oracle.com
 */
public class GamePaneII extends StackPane {

    public GamePaneII() {
        try {

            this.setOnMouseClicked(e -> requestFocus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
