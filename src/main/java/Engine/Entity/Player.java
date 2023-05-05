package Engine.Entity;

import Engine.Entity.Items.*;
import Engine.Entity.Tile.Door;
import Engine.Entity.Tile.Tile;
import Engine.Inventory;
import Logs.Logger;
import Utility.Window;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;

/**
 * Represents a player
 */
public class Player extends LivingEntity
{
    private final int SPEED_PER_SECOND = 1000;
    private final int ACTION_ZONE_LENGTH = 20;
    private static final int PLAYER_SIZE = 30;
    private final Inventory inventory; // init in constructor, read from file in future
    private final double screenPositionX;
    private final double screenPositionY;
    private final Canvas canvas;
    private Rectangle2D interactionArea;
    private final static Image image = new Image(
            new File("./src/main/assets/player.png").toURI().toString(),
            PLAYER_SIZE, PLAYER_SIZE,
            false,
            false);

    /**
     * Creates a player.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Player(Window window, double x, double y, Inventory inventory) { //
        super(image, x, y, 100);
        canvas = new Canvas(window.getWidth(), window.getHeight());
        setBoundaries(getX(), getY(), image.getWidth(), image.getHeight());
        screenPositionX = (window.getWidth() - PLAYER_SIZE) / 2;
        screenPositionY = (window.getHeight() - PLAYER_SIZE) / 2;
        this.inventory = inventory;
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
    public Inventory getInventory()
    {
        return inventory;
    }

    public int getItemAmount(Type type) {
        return inventory.getAmount(type);
    }
    public void decreaseItem(Type type) {
        inventory.use(type);
    }
    private void increaseItem(Type type, int x) {
        inventory.add(type, x);
    }

    public void takeItems(List<Item> items) {
        items.forEach(item -> {
            if (item.getType() != Type.HEAL) {
                increaseItem(item.getType(), item.getAmount());
                Logger.log("+" + item.getAmount() + " " + item.getType().name() + ". Total: " + inventory.getAmount(item.getType()));
            } else {
                increaseHealth(item.getAmount());
                Logger.log("+" + item.getAmount() + " " + item.getType().name() + ". Health: " + getHealth());
            }
        });
    }

    public void tryOpenDoor(List<Tile> tiles) {
        updateInteractionArea();
        if (inventory.getAmount(Type.KEY) == 0) {
            Logger.log("No keys in inventory");
            return;
        }
        for (Tile tile : tiles) {
            if (tile instanceof Door door) {
                if (interactionArea.intersects(door.getBoundaries())) {
                    inventory.use(Type.KEY);
                    door.open();
                }
            }
        }
    }

    public void draw() {
        canvas.getGraphicsContext2D().drawImage(image, screenPositionX, screenPositionY);
    }

    public static int getPLAYER_SIZE() {
        return PLAYER_SIZE;
    }

    public double positionOnCanvasX() {
        return screenPositionX;
    }

    public double positionOnCanvasY() {
        return screenPositionY;
    }
    public Canvas canvas() {
        return canvas;
    }

    private void updateInteractionArea() {
        interactionArea = new Rectangle2D(
                getX() - ACTION_ZONE_LENGTH,
                getY() - ACTION_ZONE_LENGTH,
                image.getWidth() + 2 * ACTION_ZONE_LENGTH,
                image.getHeight() + 2 * ACTION_ZONE_LENGTH);
    }
}
