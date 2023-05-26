package network.udp;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class Socket
{
    /**
     * The socket that is used to send and receive data.
     */
    private DatagramSocket socket;

    /**
     * The default port that is used to send and receive data.
     */
    private final int DEFAULT_PORT = 10421;

    /**
     * The targets that the socket will send data to.
     * The two targets are used to send data from the server to the clients
     * In the case of the client, only the first target is used.
     */
    protected InetAddress[] socketTargets = new InetAddress[2];

    /**
     * The buffer that is used to receive data.
     */
    private byte[] receiveBuffer = new byte[256];

    /**
     * The buffer that is used to send data.
     */
    private byte[] sendBuffer = new byte[256];

    /**
     * Creates a new socket.
     * @param ipManager The IPManager that is used to get the IP address of the local machine.
     */
    public Socket(IPManager ipManager) throws UnknownHostException, SocketException
    {
        socket = new DatagramSocket(DEFAULT_PORT, ipManager.getMyIP());
    }

    /**
     * Sends a message to the targets.
     * @param message The message that is sent.
     */
    public void send(String message, InetAddress target)
    {
        sendBuffer = message.getBytes(StandardCharsets.UTF_8);

            if (target != null)
            {
                try
                {
                    DatagramPacket sendingPacket = new DatagramPacket(
                            sendBuffer,
                            sendBuffer.length,
                            target,
                            DEFAULT_PORT
                    );

                    socket.send(sendingPacket);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
    }

    /**
     * Returns the targets that the socket will send data to.
     */
    public InetAddress[] getTargets()
    {
        return socketTargets;
    }

    public int getID(InetAddress target)
    {
        for (int i = 0; i < socketTargets.length; i++)
        {
            if (socketTargets[i].equals(target))
                return i;
        }
        return -1;
    }

    /**
     * Listens for incoming messages.
     * @return The message that was received.
     */
    public DatagramPacket listen()
    {
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer,
                receiveBuffer.length);

        try
        {
            socket.receive(receivePacket);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return receivePacket;
    }

    public void close()
    {
        socket.close();
    }
}
