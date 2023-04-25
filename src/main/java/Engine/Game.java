package Engine;

import Engine.Entity.Bullet;
import Engine.Entity.Entity;
import Engine.Entity.Items.Ammo;
import Engine.Entity.Items.Item;
import Engine.Level.Level;
import Engine.Entity.Player;
import Engine.Entity.Tiles.Tile;
import Utility.Collisions;
import com.sun.javafx.scene.control.LabeledText;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains the game logic.
 */
public class Game
{
    private List<Entity> entities = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
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
    public Game(Stage stage) {
        this.stage = stage;
        level = new Level(new File("./src/main/levels/level1.txt"));
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

        // draw entities
        entities.forEach(Entity::draw);

        // draw items
        items.forEach(Entity::draw);

        // mark bullets "to remove" if they intersect with a wall
        entities.forEach(entity -> {
            if (entity instanceof Bullet) {
                if (Collisions.checkWallCollision(level.getTiles(), entity.getBoundaries())) {
                    toRemove.add(entity);
                }
                ((Bullet) entity).move(dt);
            }
        });

        // check for items in player's range
        List<Item> itemsInRange = Collisions.checkItemCollision(items, player.getBoundaries());
        player.takeItems(itemsInRange);
        toRemove.addAll(itemsInRange);

        // remove entities to remove
        toRemove.forEach(entity -> {
            if (entity instanceof Item) {
                items.remove(entity);
            }
            else {
                entities.remove(entity);
            }
        });


        Graphics.getGraphics().fillText(String.valueOf(player.getAmmo()), 10, 45);
    }

    private void spawnPlayer()
    {
        Player player = new Player(level.getFirstFloorTile());
        entities.add(player);
        Game.player = player;
    }

    private void startGame() {
        Group group = new Group(Graphics.getCanvas());
        Scene scene = new Scene(group);

//        Text ammoCount = new Text();
//        ammoCount.setX(0);
//        ammoCount.setY(0);
//        ammoCount.setText(String.valueOf(player.getAmmo()));
//        ammoCount.setFill(Color.WHITE);
//        ammoCount.setFont(new Font(48));
//        group.getChildren().add(ammoCount);

        Graphics.getGraphics().setFill(Color.WHITE);
        Graphics.getGraphics().setFont(new Font("Arial Sans", 50));
//        Graphics.getGraphics().fillText(String.valueOf(player.getAmmo()), 0, 0);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::press);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::release);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this::shoot);

        Ammo testAmmo = new Ammo(400, 200); // think how to generate it or idk
        items.add(testAmmo);

        stage.setScene(scene);
        stage.show();
    }

    private void shoot(MouseEvent event)
    {
        if (player.getAmmo() > 0) {
            Point2D direction = new Point2D(event.getX(), event.getY());
            Bullet bullet = new Bullet(player, direction);
            entities.add(bullet);
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
