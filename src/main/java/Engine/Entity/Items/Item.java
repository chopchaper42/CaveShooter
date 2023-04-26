package Engine.Entity.Items;

import Engine.Entity.Entity;
import javafx.scene.image.Image;

public abstract class Item extends Entity
{
    /**
     * Creates an entity
     *
     * @param image entity's image
     * @param x     x coordinate
     * @param y     y coordinate
     */
    public Item(Image image, double x, double y) {
        super(image, x, y);
    }
}
