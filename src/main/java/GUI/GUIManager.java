package GUI;

import Engine.Game;
import Engine.GameSettings;
import Engine.Level.LevelManager;
import Logs.LogTo;
import Logs.Logger;
import Utility.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
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

    public void renderMainWindow() throws FileNotFoundException {
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
        logToConsole.setId(LogTo.CONSOLE.name());
        RadioButton logToFile = new RadioButton("Log to file");
        logToFile.setId(LogTo.FILE.name());
        logToConsole.setToggleGroup(logGroup);
        logToFile.setToggleGroup(logGroup);

        logBox.getChildren().addAll(enableLogging, logToConsole, logToFile);

        /*if (enableLogging.isSelected()) {
            System.out.println("Logger selected");
            Logger.setEnabled(true);

            if (logGroup.getSelectedToggle().getProperties().get("id") == LogTo.FILE.name()) {
                System.out.println("Log to file selected");
                Logger.setOutput();
            }
        }*/

        enableLogging.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> {
            Logger.setEnabled(enableLogging.isSelected());
        });

        logToFile.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> {
            try {
                Logger.setOutput(new PrintStream("./src/main/logs/log.txt"));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

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

        Button backToMenu = new Button("<- Back");
        backToMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            try {
                renderMainWindow();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        levelsPane.getChildren().add(
                backToMenu
        );

        levels.forEach((level) -> {
            Button levelButton = new Button(level.getName());
            levelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                new Game(window, this, stage, level).run();
            });
            levelsPane.getChildren().add(levelButton);
        });

        stage.setScene(levelsScene);
    }

    public void renderWin() {
        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setAlignment(Pos.CENTER);

        Label text = new Label("You won! All enemies are dead.");
        Button goToLevels = new Button("Go to Levels");

        goToLevels.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            renderLevels();
        });

        pane.getChildren().addAll(text, goToLevels);
        stage.setScene(new Scene(pane));
    }

    public void renderLose() {
        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setAlignment(Pos.CENTER);

        Label text = new Label("You lose! Try again next time.");
        Button goToLevels = new Button("Go to Levels");

        goToLevels.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            renderLevels();
        });

        pane.getChildren().addAll(text, goToLevels);
        stage.setScene(new Scene(pane));
    }
}
