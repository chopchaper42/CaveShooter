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

            if (Arrays.toString(data).equals("game over"))
            {
                Logger.log("Game over");
                Logger.log("--------------------\n");
                Logger.log("Client is closing...");
                break;
            }
        }
    }

    public void send(String newState) {
        // send the new state to the server
        clientSocket.send(newState, clientSocket.getTargets()[0]);
    }

//    public void checkQueue()
}
