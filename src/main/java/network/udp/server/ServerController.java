package network.udp.server;

import Engine.InventoryManager;
import Engine.Level.LevelManager;
import GUI.GUIManager;
import Engine.InventoryManager;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;


public class ServerController
{
    /**
     * Class to verify the game rules
     */
    private GameRulesVerifier gameRulesVerifier;

    /**
     * Manages JSONs
     */
    private JSONManager jsonManager;

    private GameStateSynchronizer gameStateSynchronizer;

    /**
     * Server socket
     */
    private OurServerSocket serverSocket;

    /**
     * Packet to receive messages
     */
    DatagramPacket receivePacket;

    ServerController(OurServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
        gameRulesVerifier = new GameRulesVerifier();
        jsonManager = new JSONManager();
        gameStateSynchronizer = new GameStateSynchronizer(serverSocket);
    }

    public void start()
    {
        while (true)
        {
            receivePacket = serverSocket.listen();
            String jsonStr = jsonManager.parseJSONFromBytes(receivePacket.getData());
            Object jsonObject = jsonManager.createJSONObject(jsonStr);

            if (gameRulesVerifier.verifyGameRules(jsonObject))
            {
                String gameState = jsonManager.createJSON(jsonObject);
                gameStateSynchronizer.synchronizeGameStateAmongAllClients(gameState);
            }
        }
    }
}
