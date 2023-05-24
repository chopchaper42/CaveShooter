package network.udp.client;

import java.net.*;
import java.util.Scanner;

import Logs.*;

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
        Logger.log("Type the server's IP address:\n");
        String serverIP = scr.next();

        if (!ipManager.checkIP(serverIP))
        {
            Logger.log("Invalid IP address");
            Logger.log("--------------------\n");
            return;
        }

        Logger.log("--------------------\n");
        Logger.log("Client is running...");
        Logger.log("--------------------\n");

        var clientSocket = new ClientSocket(serverIP, ipManager);

        clientSocket.send("Hello, server!", clientSocket.getTargets()[0]);

        Logger.log("Message sent");
        Logger.log("--------------------\n");

        var clientController = new ClientController(clientSocket);
        clientController.run();
        // clientController --> CaveShooter --> GUI --> Game -->  Updater --> Updater.update.. <

    }
}