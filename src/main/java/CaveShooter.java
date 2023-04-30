import Engine.Game;
import Engine.Level.LevelManager;
import GUI.GUIManager;
import Logs.Logger;
import Utility.Window;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.File;


public class CaveShooter extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage stage)
    {
        try {
            Window window = new Window(
                    "Cave Shooter",
                    1080,
                    720,
                    false
            );
            window.init(stage);
            stage.show();

            LevelManager levelManager = new LevelManager(new File("./src/main/levels"));
            GUIManager guiManager = new GUIManager(stage, levelManager);

            guiManager.renderMainWindow();

        } catch (Exception exception) {
            Logger.log("FATAL: " + exception.getMessage());
            System.exit(-1);
        }
    }
}
