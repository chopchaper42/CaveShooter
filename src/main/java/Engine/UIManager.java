package Engine;

import Engine.Entity.Items.Type;
import Engine.Entity.Player;
import Utility.Window;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIManager {
    private Canvas canvas;

    private Player player;

    public UIManager(Window window, Player player, Color color, Font font) {
        this.player = player;
        this.canvas = new Canvas(window.getWidth(), window.getHeight());
        this.canvas.getGraphicsContext2D().setFill(color);
        this.canvas.getGraphicsContext2D().setFont(font);
    }

    public void update() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().fillText("Ammo: " + player.getItemAmount(Type.AMMO), 0, 35);
        canvas.getGraphicsContext2D().fillText("HP: " + player.getHealth(), 0, 75);

    }

    public Canvas canvas() {
        return canvas;
    }
}
