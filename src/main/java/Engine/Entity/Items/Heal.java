package Engine.Entity.Items;

import javafx.scene.image.Image;

import java.io.File;

public class Heal extends Item{

    private static final Image image = new Image(new File("./src/main/assets/heal.png").toURI().toString(), 30, 30, false, false);

    /**
     * Creates an entity
     *
     * @param x     x coordinate
     * @param y     y coordinate
     */
    public Heal(double x, double y) {
        super(image, x, y);
    }
}
