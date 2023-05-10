package Engine;

import Utility.Pythagoras;
import javafx.geometry.Point2D;

public class Speed {
    private double xComponent;
    private double yComponent;
    private double speed;

    public Speed(Point2D source, Point2D target, double speed) {
        double dx = target.getX() - source.getX();
        double dy = target.getY() - source.getY();
        double diagonal = Pythagoras.diagonal(dx, dy);
        double sinX = dy / diagonal;
        double cosX = dx / diagonal;
        this.xComponent = speed * cosX;
        this.yComponent = speed * sinX;
        this.speed = speed;
    }

    public double xComponent() {
        return xComponent;
    }

    public double yComponent() {
        return yComponent;
    }
}
