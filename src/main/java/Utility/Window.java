package Utility;

import javafx.geometry.Point2D;
import javafx.stage.Stage;

public class Window
{
    private final double width;// = 1080;
    private final double height;// = 720;
    private final boolean resizable;// = false;
    private final String title;// = "Cave Shooter";

    /**
     * Sets up a window
     * @param title the title of the window
     * @param width the width of the window
     * @param height the height of the window
     * @param resizable is resizable
     */
    public Window(String title, double w, double h, boolean resizable) {
        this.title = title;
        width = w;
        height = h;
        this.resizable = resizable;
    }

    /**
     * Initializes the window's stage
     * @param stage stage
     */
    public void init(Stage stage) {
        stage.setTitle(title);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(resizable);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public Point2D getCenter(double d) {
        Point2D position = new Point2D(
                (width - d)/2,
                (height - d)/2
        );
        return position;
    }
}
