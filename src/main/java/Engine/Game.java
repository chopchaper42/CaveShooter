package Engine;

import Engine.Entity.Bullet;
import Engine.Entity.Entity;
//import Engine.Entity.Items.Ammo;
import Engine.Entity.Items.*;
import Engine.Level.Level;
import Engine.Entity.Player;
import Engine.Entity.Tile.Tile;
import Utility.Collisions;
import Utility.Window;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains the game logic.
 */
public class Game
{
    private Window window;
    private final List<Item> items = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Tile> tiles = new ArrayList<>(); // ?
    private final Level level;
    private final Player player;
    private final Stage stage;
    private final InputManager inputManager;


    /**
     * Constructs a new Game object with a stage and a level
     */
    public Game(Window window, Stage stage, File level) {
        this.window = window;
        this.stage = stage;
        this.level = new Level(window, level);
        this.player = new Player(window, this.level,
                this.level.getFirstFloorTilePosition().getX() + Player.getPLAYER_SIZE() / 2f,
                this.level.getFirstFloorTilePosition().getY() + Player.getPLAYER_SIZE() / 2f,
                new Inventory());

        this.inputManager = new InputManager(player, this.level);
    }


    /**
     * Runs the game
     */
    public void run() {
        startGame();
        AnimationTimer loop = new AnimationTimer()
        {
            long lastFrame;
            @Override
            public void handle(long now)
            {
                double dt = (now - lastFrame) / 10e9;
                //receiveData();
                update(dt);

                inputManager.handleInput(dt);
                lastFrame = now;
                //sendData();
            }
        };
        loop.start();
    }


    private void update(double dt) {
        List<Entity> toRemove = new ArrayList<>();

        redraw();

        // mark bullets "to remove" if they intersect with a wall
        bullets.forEach(bullet -> { // override draw() in bullet to move on each call
            if (Collisions.checkWallCollision(level.getTiles(), bullet.getBoundaries())) {
                toRemove.add(bullet);
            }
            bullet.move(dt);
        });

        // check for items in player's range
        List<Item> itemsInRange = Collisions.checkItemCollision(items, player.getBoundaries());
        player.takeItems(itemsInRange);
        toRemove.addAll(itemsInRange);

        // remove entities to remove
        toRemove.forEach(entity -> {
            if (entity instanceof Item) {
                items.remove(entity);
            } else if (entity instanceof Bullet) {
              bullets.remove(entity);
            }
        });

        player.getCanvas().getGraphicsContext2D().fillText("HP: " + player.getHealth(), 10, 45);
        player.getCanvas().getGraphicsContext2D().fillText("Ammo: " + player.getItemAmount(Type.AMMO), 10, 85);

    }

    private void startGame() {
        Group group = new Group(
                level.getCanvas(),
                player.getCanvas()
        );
        Scene scene = new Scene(group);

        configureUIFont();
        addEventListeners(scene);

        player.draw(player.getCanvas());

        // spawn items
        Ammo testAmmo = new Ammo(400, 200, 30); // think how to generate it or idk
        items.add(testAmmo);
        Heal heal = new Heal(800, 200, 20);
        items.add(heal);
        Key key = new Key(500, 200, 1);
        items.add(key);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Return the currently loaded level
     * @return currently loaded level
     */
    public Level getLevel()
    {
        return level;
    }

    private void addEventListeners(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, inputManager::press);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, inputManager::release);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            inputManager.shoot(event, bullets);
        });
    }

    private void configureUIFont() {
        Graphics.getGraphics().setFill(Color.WHITE);
        Graphics.getGraphics().setFont(new Font("Arial Sans", 50));
    }

    private void redraw() {
        // draw tiles
        level.getTiles().forEach(tile -> tile.draw(level.getCanvas()));

        // draw player
//        Logger.log("X: " + player.getX() + "\nY: ");

        //draw bullets
        bullets.forEach(bullet -> bullet.draw(level.getCanvas()));

        // draw items
        items.forEach(item -> item.draw(level.getCanvas()));
    }
}
