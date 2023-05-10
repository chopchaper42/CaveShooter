package GUI;

import Engine.Game;
import Engine.GameSettings;
import Engine.Level.LevelManager;
import Utility.Window;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GUIManager {
    private Window window;
    private Stage stage;
    private LevelManager levelManager;
    private GameSettings settings;

    public GUIManager(Window window, Stage stage, LevelManager levelManager) {
        if (stage == null) {
            throw new IllegalArgumentException("Null stage was given.");

        }
        this.window = window;
        this.stage = stage;
        this.levelManager = levelManager;
        this.settings = new GameSettings();
    }

    public void renderMainWindow() {
        VBox pane = new VBox();
        pane.setSpacing(5);
        pane.setAlignment(Pos.CENTER);

        VBox gameTypeBox = new VBox();
        gameTypeBox.setSpacing(10);
        gameTypeBox.setAlignment(Pos.CENTER);
        Button singlePlayerButton = new Button("Single Player");
        Button multiPlayerButton = new Button("Multiplayer");

        gameTypeBox.getChildren().addAll(singlePlayerButton, multiPlayerButton);

        VBox logBox = new VBox();
        logBox.setPrefWidth(120);
        logBox.setSpacing(0);
        logBox.setAlignment(Pos.CENTER);

        CheckBox enableLogging = new CheckBox("Enable logger");
        ToggleGroup logGroup = new ToggleGroup();
        RadioButton logToConsole = new RadioButton("Log to console");
        RadioButton logToFile = new RadioButton("Log to file");
        logToConsole.setToggleGroup(logGroup);
        logToFile.setToggleGroup(logGroup);

        logBox.getChildren().addAll(enableLogging, logToConsole, logToFile);



        singlePlayerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            renderLevels();
        });

        multiPlayerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            //connect to server
        });

        pane.getChildren().addAll(gameTypeBox, logBox);

        Scene mainScene = new Scene(pane, 0, 0);

        stage.setScene(mainScene);
    }

    public void renderLevels() {

        FlowPane levelsPane = new FlowPane(Orientation.HORIZONTAL);
        levelsPane.setPadding(new Insets(5, 5, 5, 5));
        levelsPane.setHgap(5);
        levelsPane.setVgap(3);

        Scene levelsScene = new Scene(levelsPane);
        List<File> levels = levelManager.getLevels();

        levels.forEach((level) -> {
            Button levelButton = new Button(level.getName());
            levelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                new Game(window, this, stage, level).run();
            });
            levelsPane.getChildren().add(levelButton);
        });

        stage.setScene(levelsScene);
    }
}
