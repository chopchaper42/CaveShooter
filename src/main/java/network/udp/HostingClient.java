package network.udp;

import Engine.GameSettings;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HostingClient {

    Socket socket;

    public HostingClient() throws SocketException, UnknownHostException
    {
        socket = new Socket(true);
    }

    public void waitForConnection()
    {
        // wait for connection
        DatagramPacket receivePacket = socket.listen();

        // set target
        socket.setTarget(receivePacket.getAddress());

        // send confirmation
        socket.send(GameSettings.game().levelFile());
    }

    public Socket getSocket() {
    	return socket;
    }
}
