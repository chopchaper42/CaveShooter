package network.udp.server;

public class ServerMain
{
    /**
     * The main method of the server.
     */
    public static void main(String[] args)
    {
        ServerPlayersConnections server = null;
        try
        {
            server = new ServerPlayersConnections();
            server.start();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}