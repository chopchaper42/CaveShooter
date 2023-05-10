import Engine.Level.LevelManager;
import GUI.GUIManager;
import Logs.Logger;
import Utility.Window;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


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
            startGame(stage);

        } catch (Exception exception) {
            Logger.log("FATAL: " + exception.getMessage());
            System.exit(-1);
        }
    }

    private void startGame(Stage stage) {
        Window window = new Window(
                "Cave Shooter",
                stage,
                1080,
                720,
                false
        );

        LevelManager levelManager = new LevelManager(new File("./src/main/levels"));
        GUIManager guiManager = new GUIManager(window, stage, levelManager);

        guiManager.renderMainWindow();
        stage.show();
    }
}
