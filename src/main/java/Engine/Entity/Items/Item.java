package Engine.Entity.Items;

import Engine.Entity.Entity;
import Engine.Entity.Tile.Tile;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.File;

public class Item extends Entity
{
    protected static final int ITEM_SIZE = 48;
    private int amount;
    private final Type type;

    /**
     * Creates an entity
     *
     * @param image entity's image
     * @param x     x coordinate
     * @param y     y coordinate
     */
    protected Item(Image image, double x, double y, int amount, Type type) {
        super(image, x, y);
        this.amount = amount;
        this.type = type;
    }

    public Item(Type type, int amount) {
        super(null, 0, 0);
        this.type = type;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
    public Type getType() {
        return type;
    }

    public static Point2D getCoordinatesForCenter(double x, double y) {
        return new Point2D(
                x + (Tile.TILE_SIZE - ITEM_SIZE) / 2f,
                y + (Tile.TILE_SIZE - ITEM_SIZE) / 2f
                );
    }
}
