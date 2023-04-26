package Engine.Entity.Tiles;

import Engine.Entity.Entity;
import Engine.Entity.Items.Item;
import javafx.scene.image.Image;

public abstract class Tile extends Entity
{
    /**
     * The size of a tile
     */
    public static final int TILE_SIZE = 64;

    /**
     * Creates a tile
     * @param image tile's image
     * @param x x coordinate
     * @param y y coordinate
     */
    public Tile(Image image, double x, double y)
    {
        super(image, x, y);
    }
}
