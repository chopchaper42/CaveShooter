package Engine.Entity;

import Logs.Logger;
import Utility.Pythagoras;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.File;

public class Bullet extends MovingEntity
{
    private static Image image = new Image(new File("./src/main/assets/bullet.png").toURI().toString());
    private double speed;
    private Entity source;
    private final int DAMAGE = 25;
    private double deltaX;
    private double deltaY;

    /**
     * Creates a bullet
     * @param source from where the bullet flies
     * @param target to where the bullet flies
     */
    public Bullet(Entity source, Point2D target, double speed)
    {
        super(image, source.getCenter().getX(), source.getCenter().getY());

        this.source = source;

        double dX = source.getCenter().getX() - target.getX();
        double dY = source.getCenter().getY() - target.getY();
        double diagonal = Pythagoras.diagonal(dX, dY);

        double cosA = dX / diagonal;
        double sinA = dY / diagonal;

        deltaX = speed * -cosA;
        deltaY = speed * -sinA;

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

    public int DAMAGE() {
        return DAMAGE;
    }
    public Entity source() {
        return source;
    }
}