package Engine.Entity;

import javafx.scene.image.Image;

import java.io.File;

public class Enemy extends LivingEntity {

    private final static int ENEMY_SIZE = 30;
    private final static Image image = new Image(
            new File("./src/main/assets/enemy.png").toURI().toString(),
            ENEMY_SIZE, ENEMY_SIZE,
            false,
            false);

    /**
     * Creates an enemy
     *
     * @param x      x coordinate
     * @param y      y coordinate
     */
    public Enemy(double x, double y) {
        super(image, x, y, 50);

    }
}
