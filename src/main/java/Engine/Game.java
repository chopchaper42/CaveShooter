package Engine;

import Engine.Entity.Bullet;
import Engine.Entity.Entity;
//import Engine.Entity.Items.Ammo;
import Engine.Entity.Items.Ammo;
import Engine.Entity.Items.Heal;
import Engine.Entity.Items.Item;
import Engine.Entity.Items.Key;
import Engine.Level.Level;
import Engine.Entity.Player;
import Engine.Entity.Tile.Tile;
import Utility.Collisions;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
    private List<Item> items = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();
    private static Level level;
    private static Player player;
    private final Stage stage;
    private boolean W_pressed = false;
    private boolean A_pressed = false;
    private boolean S_pressed = false;
    private boolean D_pressed = false;


    /**
     * Constructs a new Game object with a stage and a level
     */
    public Game(Stage stage, File level) {
        this.stage = stage;
        this.level = new Level(level);
    }


    /**
     * Runs the game
     */
    public void run() {
        spawnPlayer();
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
                player.handleInput(W_pressed, A_pressed, S_pressed, D_pressed, dt);
                lastFrame = now;
                //sendData();
            }
        };
        loop.start();
    }


    private void update(double dt) {
        List<Entity> toRemove = new ArrayList<>();

        // draw tiles
        level.getTiles().forEach(Tile::draw);

        // draw player
        player.draw();

        //draw bullets
        bullets.forEach(Bullet::draw);

        // draw items
        items.forEach(Entity::draw);

        // mark bullets "to remove" if they intersect with a wall
        bullets.forEach(bullet -> {
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


        Graphics.getGraphics().fillText("HP: " + player.getHealth(), 10, 45);
        Graphics.getGraphics().fillText("Ammo: " + player.getAmmo(), 10, 85);

    }

    private void spawnPlayer()
    {
        Game.player = new Player(level.getFirstFloorTile());
    }

    private void startGame() {
        Group group = new Group(Graphics.getCanvas());
        Scene scene = new Scene(group);

        Graphics.getGraphics().setFill(Color.WHITE);
        Graphics.getGraphics().setFont(new Font("Arial Sans", 50));

        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::press);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::release);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this::shoot);

        Ammo testAmmo = new Ammo(400, 200, 30); // think how to generate it or idk
        items.add(testAmmo);
        Heal heal = new Heal(800, 200, 20);
        items.add(heal);
        Key key = new Key(500, 200, 1);
        items.add(key);

        stage.setScene(scene);
        stage.show();
    }

    private void shoot(MouseEvent event)
    {
        if (player.getAmmo() > 0) {
            Point2D direction = new Point2D(event.getX(), event.getY());
            Bullet bullet = new Bullet(player, direction);
            bullets.add(bullet);
            player.decreaseAmmo();
        }
    }

    private void release(KeyEvent event)
    {
        handle(event.getCode(), false);
    }

    private void press(KeyEvent event) {
        handle(event.getCode(), true);
    }

    private void handle(KeyCode code, boolean pressed) {
        switch (code) {
            case W -> W_pressed = pressed;
            case A -> A_pressed = pressed;
            case S -> S_pressed = pressed;
            case D -> D_pressed = pressed;
            case SPACE -> player.useKey();
        }
    }


    /**
     * Return the currently loaded level
     * @return currently loaded level
     */
    public static Level getLevel()
    {
        return level;
    }
}
