package Engine.Entity.Items;

import Engine.Entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.File;

public class Heal extends Item {

    public static final int DEFAULT_AMOUNT = 20;
    private static final Image image = new Image(new File("./src/main/assets/heal.png").toURI().toString(),
            ITEM_SIZE, ITEM_SIZE, false, false);

    /**
     * Creates an entity
     *
     * @param x     x coordinate
     * @param y     y coordinate
     */
    public Heal(double x, double y, int amount) {
        super(image, x, y, amount, Type.HEAL);
    }
    public Heal(Point2D position, int amount) {
        this(position.getX(), position.getY(), amount);
    }
    public Heal(int amount) {
        super(Type.HEAL, amount);
    }
}
