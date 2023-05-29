package network.udp.client;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import Logs.*;
import network.udp.Socket;
import Utility.ConsoleWriter;

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
        var clientReceivedState = ClientReceivedStateSingleton.getInstance();

        while(true)
        {
            byte[] data = clientSocket.listen().getData();
            clientReceivedState.enqueue(data);
//            System.out.println("Client received: " + new String(data, StandardCharsets.UTF_8));
//            System.out.println("ClientReceivedState size: " + clientReceivedState.size());

//            System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
//            long threadId = Thread.currentThread().getId();
//            System.out.println("Current Thread ID Controller: " + threadId);
//            System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");

//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }


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
        var clientReceivedState = ClientReceivedStateSingleton.getInstance();
        byte[] data = clientSocket.listen().getData();
//        clientReceivedState.enqueue(data);

//        var queue = ClientReceivedStateSingleton.getInstance();
//        if (queue.isEmpty())
//        {
//            return null;
//        }

//        var newStateJson = queue.dequeue();
//        ConsoleWriter.write("queue size: " + queue.size());
//        ConsoleWriter.write("newStateJson:" + newStateJson);
//        System.out.println(newStateJson);

        String jsonString = new String(data).trim();
//        ConsoleWriter.write("------------------------------------------");
//        ConsoleWriter.write("HERE THIS SHIT BREAKING OUR CODE MF" + jsonString);
        // Find the index of the first zero character (null termination)
        int indexOfZero = jsonString.indexOf('\0');
        // Extract the JSON part by substring from the beginning to the first zero
        if (indexOfZero >= 0) {
            jsonString = jsonString.substring(0, indexOfZero);
        }

        return JSONManager.convertJsonToObject(jsonString);
    }
}
