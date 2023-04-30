package Engine.Entity;

import Engine.Entity.Items.Ammo;
import Engine.Entity.Items.Heal;
import Engine.Entity.Items.Key;
import Engine.Entity.Tile.Door;
import Engine.Game;
import Engine.Entity.Items.Item;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player
 */
public class Player extends LivingEntity
{
    private final int SPEED_PER_SECOND = 1000;
    private List<Item> inventory = new ArrayList<>();
    private int ammunition = 30;
    private final static Image image = new Image(new File("./src/main/assets/player.png").toURI().toString(), 30, 30, false, false);

    /**
     * Creates a player.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Player(double x, double y)
    {
        super(image, x, y, 100);
        setBoundaries(getX(), getY(), image.getWidth(), image.getHeight());
    }

    /**
     * Creates a player.
     * @param point position {@code Point2D}
     */
    public Player(Point2D point) {
        this(point.getX(), point.getY());
    }

    /**
     * @return the player's speed per second
     */
    public int getSpeedPerSecond()
    {
        return SPEED_PER_SECOND;
    }

    /**
     * @return the player's inventory
     */
    public List<Item> getInventory()
    {
        return inventory;
    }

    public int getAmmo() {
        return ammunition;
    }
    public void decreaseAmmo() {
        ammunition--;
    }
    private void increaseAmmo(int x) {
        ammunition += x;
    }

    public void takeItems(List<Item> items) {
        items.forEach(item -> {
            if (item instanceof Ammo ammo) {
                increaseAmmo(ammo.getAmount());
            }

            if (item instanceof Heal heal) {
                increaseHealth(heal.getAmount());
            }

            if (item instanceof Key key) {
                inventory.add(key);
            }
            //expand
        });
    }

    public void useKey() {
        if (inventory.size() == 0) {
            return;
        }
        System.out.println("key used, but not deleted: " + inventory.size());
        Game.getLevel().getTiles().forEach(tile -> {
            if (tile instanceof Door door) {
                double actionZoneLength = 20;
                Rectangle2D interactionArea = new Rectangle2D(
                        getX() - actionZoneLength,
                        getY() - actionZoneLength,
                        image.getWidth() + 2 * actionZoneLength,
                        image.getHeight() + 2 * actionZoneLength);
                System.out.println("Door detected");
                if (interactionArea.intersects(tile.getBoundaries())) {
                    System.out.println("tile changed");
                    door.open();
                    inventory.remove(inventory.size() - 1);
                }
            }
        });
    }
}
