package network.udp.client;

public class UpdatedState
{
    private String jsonProperty;
    private int[] position;


    public UpdatedState(String jsonProperty, int x, int y)
    {
        this.jsonProperty = jsonProperty;
        this.position = new int[] {x, y};
    }

    // Getters and setters
    public int[] getJsonProperty()
    {
        return position;
    }

    public void setJsonProperty(String jsonProperty)
    {
        this.jsonProperty = jsonProperty;
    }

    public int[] getPosition()
    {
        return position;
    }

    public void setPosition(int[] position)
    {
        this.position = position;
    }
}
