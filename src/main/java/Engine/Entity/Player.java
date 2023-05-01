package Engine.Entity;

import Engine.Entity.Items.Ammo;
import Engine.Entity.Items.Heal;
import Engine.Entity.Items.Key;
import Engine.Entity.Tile.Door;
import Engine.Game;
import Engine.Level.Level;
import Logs.Logger;
import Utility.Window;
import Engine.Entity.Items.Item;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    private final int ACTION_ZONE_LENGTH = 20;
    private static final int PLAYER_SIZE = 30;
    private final List<Item> inventory = new ArrayList<>(); // init in constructor, read from file in future
    private int ammunition = 30; // move to inventory
    private final double onCanvasX;
    private final double onCanvasY;
    private final Canvas canvas;
    private Level level;
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
    public Player(Window window, Level level, double x, double y) {
        super(image, x, y, 100);
        canvas = new Canvas(window.getWidth(), window.getHeight());
        setBoundaries(getX(), getY(), image.getWidth(), image.getHeight());
        onCanvasX = (canvas.getWidth() - PLAYER_SIZE) / 2;
        onCanvasY = (canvas.getHeight() - PLAYER_SIZE) / 2;
        this.level = level;
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
        updateInteractionArea();
        if (inventory.size() == 0) {
            return;
        }
        level.getTiles().forEach(tile -> { // move this somewhere. Don't like level in player.
            if (tile instanceof Door door) {
                if (interactionArea.intersects(tile.getBoundaries())) {
                    door.open();
                    inventory.remove(inventory.size() - 1);
                }
            }
        });
    }

    public void draw(Canvas canvas) {
//        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().drawImage(image, onCanvasX, onCanvasY);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public static int getPLAYER_SIZE() {
        return PLAYER_SIZE;
    }

    public double getOnCanvasX() {
        return onCanvasX;
    }

    public double getOnCanvasY() {
        return onCanvasY;
    }

    private void updateInteractionArea() {
        interactionArea = new Rectangle2D(
                getX() - ACTION_ZONE_LENGTH,
                getY() - ACTION_ZONE_LENGTH,
                image.getWidth() + 2 * ACTION_ZONE_LENGTH,
                image.getHeight() + 2 * ACTION_ZONE_LENGTH);
    }
}
