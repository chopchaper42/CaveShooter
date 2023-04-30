package Engine.Entity;

import Utility.Collisions;
import Utility.Pythagoras;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;

public class Bullet extends MovingEntity
{
    private static Image image = new Image(new File("./src/main/assets/bullet.png").toURI().toString());
    private final double SPEED = 8000;
    private double deltaX;
    private double deltaY;

    /**
     * Creates a bullet
     * @param source from where the bullet flies
     * @param target to where the bullet flies
     */
    public Bullet(Entity source, Point2D target)
    {
        super(image, source.getCenter().getX(), source.getCenter().getY());

        double dX = source.getCenter().getX() - target.getX();
        double dY = source.getCenter().getY() - target.getY();
        double diagonal = Pythagoras.diagonal(dX, dY);

        double cosA = dX / diagonal;
        double sinA = dY / diagonal;

        deltaX = SPEED * -cosA;
        deltaY = SPEED * -sinA;
    }

    /**
     * Moves a bullet
     * @param dt time elapsed since the last frame
     */
    public void move(double dt) {
        moveX(deltaX * dt);
        moveY(deltaY * dt);
        setBoundaries(getX(), getY(), image.getWidth(), image.getHeight());
    }
}