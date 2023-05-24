package network.udp.client;

import java.util.Arrays;
import Logs.*;

public class ClientController
{
    private ClientSocket clientSocket;

    public ClientController(ClientSocket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    /**
     * Runs the client
     */
    public void run()
    {
        while (true)
        {
            byte[] data = clientSocket.listen().getData();
            var clientReceivedState = new ClientReceivedState();
            clientReceivedState.enqueue(data);
//            if (updateFromJson.equals("game over"))
//            {
//                Logger.log("Game over");
//                Logger.log("--------------------\n");
//                Logger.log("Client is closing...");
//                break;
//            }

            // TODO: update the game with the received data

//            System.out.println("Message received: " + message);
//            System.out.println("--------------------\n");
        }
    }
}
