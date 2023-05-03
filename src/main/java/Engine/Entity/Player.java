package Engine.Entity;

import Engine.Entity.Items.*;
import Engine.Entity.Tile.Door;
import Engine.Inventory;
import Engine.Level.Level;
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
    public Player(Window window, Level level, double x, double y, Inventory inventory) {
        super(image, x, y, 100);
        canvas = new Canvas(window.getWidth(), window.getHeight());
        setBoundaries(getX(), getY(), image.getWidth(), image.getHeight());
        onCanvasX = (canvas.getWidth() - PLAYER_SIZE) / 2;
        onCanvasY = (canvas.getHeight() - PLAYER_SIZE) / 2;
        this.level = level;
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
            increaseItem(item.getType(), item.getAmount());
        });
    }

    public void useKey() {
        updateInteractionArea();
        if (inventory.getAmount(Type.KEY) == 0) {
            return;
        }
        level.getTiles().forEach(tile -> { // move this somewhere. Don't like level in player.
            if (tile instanceof Door door) {
                if (interactionArea.intersects(tile.getBoundaries())) {
                    inventory.use(Type.KEY);
                    door.open();
                }
            }
        });
    }

    public void draw(Canvas canvas) {
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
