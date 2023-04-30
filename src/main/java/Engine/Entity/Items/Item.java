package Engine.Entity.Items;

import Engine.Entity.Entity;
import javafx.scene.image.Image;

import java.io.File;

public class Item extends Entity
{
    protected int amount;
    /**
     * Creates an entity
     *
     * @param image entity's image
     * @param x     x coordinate
     * @param y     y coordinate
     */
    protected Item(Image image, double x, double y, int amount) {
        super(image, x, y);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
