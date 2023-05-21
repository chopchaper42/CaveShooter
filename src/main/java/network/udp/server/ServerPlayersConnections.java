package network.udp.server;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import network.udp.IPManager;

public class ServerPlayersConnections
{
    /**
     * The number of players in the game.
     */
    private int numOfConnectingPlayers;

    /**
     * The packet that is received from the client.
     */
    DatagramPacket receivePacket;
//    DatagramPacket sendingPacket;

    public ServerPlayersConnections() throws UnknownHostException
    {
    }

    /**
     * start() method is the main method of the server.
     */
    public void start() throws IOException
    {
        selectThePlayersNumber();

        var ipManager = new IPManager();

        var serverSocket = new OurServerSocket(ipManager);

        while (numOfConnectingPlayers > 0) {

            receivePacket = serverSocket.listen();
            var ipOfNewPlayer = receivePacket.getAddress();

            if (ipManager.checkIP(ipOfNewPlayer.toString()))
            {
                System.out.println("Another dead fellow has been found.. HA-HA-HA!");
                System.out.println("--------------------\n");
                numOfConnectingPlayers--;
                serverSocket.addIPAddress(ipOfNewPlayer);
            }
        }
        serverSocket.close();

        System.out.println("Server is running...");
        System.out.println("--------------------\n");

        System.out.println("The game is starting...");

        var serverController =  new ServerController(serverSocket);
        serverController.start();
    }

    public void selectThePlayersNumber()
    {
        Scanner in = new Scanner(System.in);

        int players = 0;

        while (players < 1 || players > 2)
        {
            System.out.println("How many dead fellows today?");
            players = Integer.parseInt(in.nextLine());;
            System.out.println("--------------------\n");

            if (players < 1 || players > 2)
            {
                System.out.println("Either one dead fellow or two dead fellows is enough for today...");
                System.out.println("--------------------\n");
            }
        }
        this.numOfConnectingPlayers = players;
    }
}