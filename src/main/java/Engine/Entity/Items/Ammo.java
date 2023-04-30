package Engine.Entity.Items;

import Engine.Entity.Entity;
import javafx.scene.image.Image;

import java.io.File;

public class Ammo extends Item {

    private static final Image image = new Image(new File("./src/main/assets/ammo.png").toURI().toString(), 30, 30, false, false);

    /**
     * Creates an entity
     *
     * @param x     x coordinate
     * @param y     y coordinate
     */
    public Ammo(double x, double y, int amount) {
        super(image, x, y, amount);
    }
}
