package network.udp.client;

public class UpdatedState
{
    private String jsonProperty;
    private double[] position;
    private double[] direction;


    public UpdatedState(String jsonProperty, double x, double y)
    {
        this.jsonProperty = jsonProperty;
        this.position = new double[] {x, y};
    }

    public UpdatedState(String jsonProperty, double x, double y, double dx, double dy)
    {
        this.jsonProperty = jsonProperty;
        this.position = new double[] {x, y};
        this.direction = new double[] {dx, dy};
    }

    // Getters and setters
    public String getJsonProperty()
    {
        return jsonProperty;
    }

    public void setJsonProperty(String jsonProperty)
    {
        this.jsonProperty = jsonProperty;
    }

    public double[] getPosition()
    {
        return position;
    }

    public void setPosition(double[] position)
    {
        this.position = position;
    }

    public double[] getDirection()
    {
        return direction;
    }

    public void setDirection(double[] direction)
    {
        this.direction = direction;
    }
}
