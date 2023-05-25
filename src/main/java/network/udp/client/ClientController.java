package network.udp.client;

import java.util.Arrays;
import Logs.*;

public class ClientController/* extends Thread*/
{
    private ClientSocket clientSocket;

    ClientController(ClientSocket clientSocket)
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            } catch (Exception e) {
//                System.out.println("Sleep is interrupted");
//            }
        }
//        clientReceivedState.enqueue(data);
//
//        if (Arrays.toString(data).equals("game over"))
//        {
//            Logger.log("Game over");
//            Logger.log("--------------------\n");
//            Logger.log("Client is closing...");
//            send("game over", 0, 0);
//        }
    }
//
    public void send(String jsonProperty, double x, double y)
    {
        var newState = new UpdatedState(jsonProperty, x, y);
        String newStateJSON = JSONManager.convertObjectToJson(newState);
        // send the new state to the server
        clientSocket.send(newStateJSON, clientSocket.getTargets()[0]);
    }

    public void send(String jsonProperty, double x, double y, double dx, double dy)
    {
        var newState = new UpdatedState(jsonProperty, x, y);
        String newStateJSON = JSONManager.convertObjectToJson(newState);
        // send the new state to the server
        clientSocket.send(newStateJSON, clientSocket.getTargets()[0]);
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
