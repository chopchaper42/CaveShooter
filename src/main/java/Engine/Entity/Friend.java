package Engine.Entity;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.io.File;

public class Friend extends LivingEntity{
    private static Image image = new Image(
            new File("./src/main/assets/player2.png").toURI().toString(),
            Player.SIZE, Player.SIZE,
            false,
            false);

    /**
     * Creates a living entity
     *
     * @param x      x coordinate
     * @param y      y coordinate
     * @param health health
     */
    public Friend(double x, double y, int health) {
        super(image, x, y, health);
    }

    public void draw(Canvas canvas) {
        canvas.getGraphicsContext2D().drawImage(image, getX(), getY());
    }
}
