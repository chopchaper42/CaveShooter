package network.udp.server;

public class GameStateSynchronizer
{
    private final OurServerSocket ourServerSocket;

    public GameStateSynchronizer(OurServerSocket ourServerSocket)
    {
        this.ourServerSocket = ourServerSocket;
    }

    public void synchronizeGameStateAmongAllClients(String gameState)
    {
        ourServerSocket.send(gameState);
    }
}
