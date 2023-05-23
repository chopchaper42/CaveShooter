package network.udp.client;

import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

import network.udp.IPManager;

public class ClientConnection
{
    /**
     * Runs the client
     */
    public void run() throws SocketException, UnknownHostException
    {
        var ipManager = new IPManager();

        Scanner scr = new Scanner(System.in);
        System.out.println("Type the server's IP address:\n");
        String serverIP = scr.next();

        if (!ipManager.checkIP(serverIP))
        {
            System.out.println("Invalid IP address");
            System.out.println("--------------------\n");
            return;
        }

        System.out.println("--------------------\n");
        System.out.println("Client is running...");
        System.out.println("--------------------\n");

        var clientSocket = new ClientSocket(serverIP, ipManager);

        clientSocket.send("Hello, server!", clientSocket.getTargets()[0]);

        System.out.println("Message sent");
        System.out.println("--------------------\n");


        while (true)
        {
            //
            String levelFromJson = Arrays.toString(clientSocket.listen().getData());

            if (message.equals("exit"))
            {
                System.out.println("Client is closing...");
                break;
            }

            System.out.println("Message received: " + message);
            System.out.println("--------------------\n");
        }
    }
}