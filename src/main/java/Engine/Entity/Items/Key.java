package Engine.Entity.Items;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.File;

public class Key extends Item {

    public static final int DEFAULT_AMOUNT = 1;
    private final static Image image = new Image(new File("./src/main/assets/key.png").toURI().toString(),
            ITEM_SIZE, ITEM_SIZE, false, false);

    public Key(double x, double y, int amount) {
        super(image, x, y, amount, Type.KEY);
    }
    public Key(Point2D position, int amount) {
        this(position.getX(), position.getY(), amount);
    }
}
