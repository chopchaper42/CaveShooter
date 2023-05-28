package network.udp;

import Engine.GameSettings;
import GUI.GUIManager;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class HostingClient {

    GUIManager guiManager;
    Socket socket;

    public HostingClient(GUIManager guiManager) throws SocketException, UnknownHostException
    {
        this.guiManager = guiManager;
        socket = new Socket(true);
    }

    public void waitForConnection()
    {
        // wait for connection
        DatagramPacket receivePacket = socket.listen();

        // set target
        socket.setTarget(receivePacket.getAddress());

        // send confirmation
        socket.send(GameSettings.game().levelFile(), receivePacket.getAddress());

        // run the game
        GameSettings.game().run();
    }

    public void send(String message)
    {
        socket.send(message, socket.socketTarget);
    }
}
