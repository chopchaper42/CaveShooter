package Engine.Entity;

import Engine.Entity.Tile.Tile;
import Logs.Logger;
import Utility.Collisions;
import Utility.Pythagoras;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Enemy extends LivingEntity {

    private final static int ENEMY_SIZE = 30;
    private final static int SPEED = 900;
    private final double SHOOTING_RATE;
    private final static int VISION_FIELD_RADIUS = 5 * Tile.TILE_SIZE;
    private final static int MOVEMENT_FIELD_RADIUS = 3 * Tile.TILE_SIZE;
    private final static int VISION_FIELD_SIZE = 2 * VISION_FIELD_RADIUS + ENEMY_SIZE;
    private final static int MOVEMENT_FIELD_SIZE = 2 * MOVEMENT_FIELD_RADIUS + ENEMY_SIZE;
    private final double MOVEMENT_RATE;
    private boolean newDestinationCalculated = false;
    private double timeOfMovement = 0;
    private int direction = 1;
    private double xVelocity = 0;
    private double yVelocity = 0;
    private double elapsedTime = 0;
    private boolean seeingPlayer = false;
    private final Rectangle2D visionField;
    private final Rectangle2D movementField;
    private Point2D destination;
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
        this.movementField = new Rectangle2D(
                getCenter().getX() - MOVEMENT_FIELD_RADIUS,
                getCenter().getY() - MOVEMENT_FIELD_RADIUS,
                MOVEMENT_FIELD_SIZE,
                MOVEMENT_FIELD_SIZE
        );
        this.seeingPlayer = false;
        MOVEMENT_RATE = new Random().nextDouble(0.3, 1);
        SHOOTING_RATE = new Random().nextDouble(0.1, 0.6);
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
    public void calculateDestination() {
        destination = pickWhereToGo();
        calculateXYVelocities(destination);
        newDestinationCalculated = true;
    }

    private void calculateXYVelocities(Point2D destination) {
        double dx = destination.getX() - getX(); // the same logic as in player class. Create new?
        double dy = destination.getY() - getY();
        double diagonal = Pythagoras.diagonal(dx, dy);
        double sinX = dy / diagonal;
        double cosX = dx / diagonal;
        xVelocity = SPEED * cosX;
        yVelocity = SPEED * sinX;
    }

    public void move(List<Tile> tiles, double dt) {
        if (elapsedTime > MOVEMENT_RATE) {
            if (!newDestinationCalculated) {
                calculateDestination();
            }

            /*
             *   if (!collides) {
             *       moveX()
             *       moveY()
             *   }
             *
             *
             */

            double newX = getX() + xVelocity * dt; // same logic is in player. Try to unite.
            double newY = getY() + yVelocity * dt;
            Rectangle2D newBounds = new Rectangle2D(
                    newX, newY,
                    ENEMY_SIZE, ENEMY_SIZE
            );

            boolean colliding = Collisions.checkWallCollision(newBounds, tiles);

            if (!colliding) {
                moveX(xVelocity * dt);
                moveY(yVelocity * dt);
//                setBoundaries(newBounds);
            }

            if (colliding || reachedDestination(destination)){
                Logger.log("Collision or reached destination");
                newDestinationCalculated = false;
                elapsedTime = 0;
            }
        } else {
            elapsedTime += dt;
        }
    }

    private boolean reachedDestination(Point2D destination) {
        return (destination.getX() - getX()) * direction < 0;
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

    private Point2D pickWhereToGo() {
        Random rnd = new Random();
        double x = rnd.nextDouble(movementField.getMinX(), movementField.getMaxX());
        double y = rnd.nextDouble(movementField.getMinY(), movementField.getMaxY());
        direction = x - getX() > 0 ? 1 : -1;
        return new Point2D(x, y);
    }
}
