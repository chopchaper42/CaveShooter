package Engine;

import Engine.InventoryManager;
import Engine.Level.LevelManager;
import GUI.GUIManager;
import Utility.Window;
import javafx.application.Application;
import javafx.stage.Stage;
import network.udp.client.ClientConnection;
import network.udp.client.ClientController;

import java.io.File;
import java.io.IOException;


public class CaveShooter extends Application
{
    private ClientController controller;
    public static void main(String[] args)
    {
        launch(args);
    }

    public CaveShooter(ClientController controller) {
        this.controller = controller;
    }
    @Override
    public void start(Stage stage)
    {
        try {
            startGame(stage);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.exit(-1);
        }
    }

    private void startGame(Stage stage) throws IOException {

        // Client requests the server for the necessary parameters to start the game.

        Window window = new Window(
                "Cave Shooter",
                stage,
                1080,
                720,
                false
        );

        LevelManager levelManager = new LevelManager(new File("./src/main/levels"));
        InventoryManager.readInventory("./src/main/inventory/inventory.txt");
        GUIManager guiManager = new GUIManager(window, stage, levelManager);

        guiManager.renderMainWindow();
        stage.show();
    }
}
