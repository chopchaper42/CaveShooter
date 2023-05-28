package network.udp.client;

import java.util.Arrays;
import Logs.*;
import network.udp.Socket;

public class ClientController/* extends Thread*/
{
    private Socket clientSocket;

    public ClientController(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    /**
     * Runs the client
     */
    public void run()
    {
        var clientReceivedState = new ClientReceivedState();
        while(true) {
            byte[] data = clientSocket.listen().getData();
//            try {
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            long threadId = Thread.currentThread().getId();
            System.out.println("Current Thread ID Controller: " + threadId);
            System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
//                Thread.sleep(100);
//            break;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }
//
    public void send(String jsonProperty, double x, double y)
    {
        var newState = new UpdatedState(jsonProperty, x, y);
        String newStateJSON = JSONManager.convertObjectToJson(newState);
        // send the new state to the server
        clientSocket.send(newStateJSON);
    }

    public void send(String jsonProperty, double x, double y, double dx, double dy)
    {
        var newState = new UpdatedState(jsonProperty, x, y);
        String newStateJSON = JSONManager.convertObjectToJson(newState);
        // send the new state to the server
        clientSocket.send(newStateJSON);
    }

    public UpdatedState checkUpdatesFromAnotherClient()
    {
        var queue = new ClientReceivedState();
        if (queue.isEmpty())
        {
            return null;
        }

        var newStateJson = queue.dequeue();
        return JSONManager.convertJsonToObject(newStateJson);
    }
}
