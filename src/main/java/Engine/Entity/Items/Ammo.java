package Engine.Entity.Items;

import Engine.Entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.File;

public class Ammo extends Item {

    public static final int DEFAULT_AMOUNT = 30;
    private static final Image image = new Image(new File("./src/main/assets/ammo.png").toURI().toString(),
            ITEM_SIZE, ITEM_SIZE, false, false);

    /**
     * Creates an entity
     *
     * @param x     x coordinate
     * @param y     y coordinate
     */
    public Ammo(double x, double y, int amount) {
        super(image, x, y, amount, Type.AMMO);
    }
    public Ammo(Point2D position, int amount) {
        this(position.getX(), position.getY(), amount);
    }
    public Ammo(int amount) {
        super(Type.AMMO, amount);
    }
}
