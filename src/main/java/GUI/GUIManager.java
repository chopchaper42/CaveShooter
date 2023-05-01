package GUI;

import Engine.Game;
import Engine.Level.LevelManager;
import Logs.Logger;
import Utility.Window;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUIManager {
    private Window window;
    private Stage stage;
    private LevelManager levelManager;

    public GUIManager(Window window, Stage stage, LevelManager levelManager) {
        if (stage == null) {
            throw new IllegalArgumentException("Null stage was given.");

        }
        this.window = window;
        this.stage = stage;
        this.levelManager = levelManager;
    }

    public void renderMainWindow() {
        VBox pane = new VBox();

        Button singlePlayerButton = new Button("Single Player");
        Button multiPlayerButton = new Button("Multiplayer");
        singlePlayerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            renderLevels();
        });
        multiPlayerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            //connect to server
        });

        pane.getChildren().addAll(singlePlayerButton, multiPlayerButton);

        Scene mainScene = new Scene(pane, 0, 0);

        stage.setScene(mainScene);
    }

    public void renderLevels() {
        HBox levelsBox = new HBox();
        Scene levelsScene = new Scene(levelsBox);
        List<File> levels = levelManager.getLevels();

        levels.forEach((level) -> {
            Button levelButton = new Button(level.getName());
            levelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                new Game(window, stage, level).run();
            });
            levelsBox.getChildren().add(levelButton);
        });

        stage.setScene(levelsScene);
    }
}
