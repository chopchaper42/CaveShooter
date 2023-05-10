package Engine;

//import Engine.Entity.Items.Ammo;
import Engine.Level.Level;
import Engine.Entity.Player;
import GUI.GUIManager;
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


/**
 * Contains the game logic.
 */
public class Game
{
    private final Window window;
    private final Level level;
    private final Stage stage;
    private final Player player;
    private final InputManager inputManager;
    private final UIManager uiManager;
    private final GUIManager guiManager;
    private final Updater updater;


    /**
     * Constructs a new Game object with a stage and a level
     */
    public Game(Window window, GUIManager guiManager, Stage stage, File level) {
        this.window = window;
        this.stage = stage;
        this.level = new Level(window, level);
        this.player = new Player(
                this.window,
                this.level.initialPlayerPosition().getX(),
                this.level.initialPlayerPosition().getY(),
                new Inventory()
        );
        this.inputManager = new InputManager(this.player, this.level);
        this.uiManager = new UIManager(
                this.window,
                this.player,
                Color.YELLOWGREEN,
                new Font("Verdana", 40)
        );
        this.guiManager = guiManager;
        this.updater = new Updater(this.level, this.player, this.uiManager, this.guiManager);
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
                updater.update(dt);
                inputManager.handleInput(dt);
                lastFrame = now;

                if (!player.alive())
                    this.stop();

                //sendData();
            }
        };
        loop.start();
    }

    private void startGame() {
        Group group = new Group(level.canvas(), player.canvas(), uiManager.canvas());
        Scene scene = new Scene(group);

        addEventListeners(scene);

        player.draw(player.getImage());

        stage.setScene(scene);
        stage.show();
    }

    private void addEventListeners(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, inputManager::press);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, inputManager::release);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            player.shoot(event, level.bullets());
        });
    }


}
