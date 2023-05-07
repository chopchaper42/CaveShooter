package Engine.Entity;

import Logs.Logger;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;

public class Enemy extends LivingEntity {

    private final static int ENEMY_SIZE = 30;
    private final static double SHOOTING_RATE = 0.6;
    private final static int VISION_FIELD_RADIUS = 320;
    private final static int VISION_FIELD_SIZE = 2 * VISION_FIELD_RADIUS + ENEMY_SIZE;
    private boolean seeingPlayer;
    private final Rectangle2D visionField;
    private double timeSinceLastShot = 0;
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
        this.visionField = new Rectangle2D(
                getCenter().getX() - VISION_FIELD_RADIUS,
                getCenter().getY() - VISION_FIELD_RADIUS,
                VISION_FIELD_SIZE,
                VISION_FIELD_SIZE
        );
        this.seeingPlayer = false;
    }

    public void shoot(Player player, List<Bullet> bullets, double dt) {
        if (seeingPlayer) {
            addTimeToLastShot(dt);
            if (timeSinceLastShot > SHOOTING_RATE) {
                Point2D direction = new Point2D(
                        player.getCenter().getX(),
                        player.getCenter().getY()
                );
                Bullet bullet = new Bullet(this, direction, 2500);
                bullets.add(bullet);
                Logger.log(this + "'s shot");
                timeSinceLastShot = 0;
            }
        }
    }

    public Rectangle2D visionField() {
        return visionField;
    }

    public boolean seeingPlayer() {
        return seeingPlayer;
    }

    public void setSeeingPlayer(boolean seeing) {
        seeingPlayer = seeing;
    }

    private void addTimeToLastShot(double dt) {
        timeSinceLastShot += dt;
    }
}
