package network.udp.server;

import network.udp.JSONManager;

import java.net.DatagramPacket;


public class ServerController
{
    /**
     * Manages JSONs
     */
    private JSONManager jsonManager;

    /**
     * Synchronizes the game state among all clients
     */
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
        jsonManager = new JSONManager();
        gameStateSynchronizer = new GameStateSynchronizer(serverSocket);
    }

    public void start()
    {
        while (true)
        {
            receivePacket = serverSocket.listen();
            int clientMessageFromID = serverSocket.getID(receivePacket.getAddress());

            if (clientMessageFromID == -1)
            {

                ServerLogger.log("Error: client not found");
                continue;
            }
            ServerLogger.log("Player " + clientMessageFromID + " has sent a message");

            ServerLogger.log("--------------------\n");

            // If the message is "game over", then the other client is sent the same message and the server shuts down
            if (receivePacket.getData().toString().equals("game over"))
            {
                gameStateSynchronizer.synchronizeGameBetweenClients("game over", clientMessageFromID);
                ServerLogger.log("Player " + clientMessageFromID + " has lost the game");
                ServerLogger.log("Game is over...");
                ServerLogger.log("--------------------\n");                           // Logger
                ServerLogger.log("Server is shutting down...");                         // Logger
                ServerLogger.log("--------------------\n");                           // Logger
                break;
            }

            // If the message is not "game over", then the game state is synchronized among all clients
            gameStateSynchronizer
                    .synchronizeGameBetweenClients(
                            jsonManager.parseJSONFromBytes(receivePacket.getData()), clientMessageFromID);
            ServerLogger.log("Server sent the game state to another client");
        }
    }
//            String jsonStr = jsonManager.parseJSONFromBytes(receivePacket.getData());
//            Object jsonObject = jsonManager.createJSONObject(jsonStr);

//            String gameState = jsonManager.createJSON(jsonObject);
//            gameStateSynchronizer.synchronizeGameStateAmongAllClients(gameState, );
}
