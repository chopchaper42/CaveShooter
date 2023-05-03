package Engine.Level;

import Engine.Entity.Player;
import Engine.Entity.Tile.Tile;
import Logs.Logger;
import Utility.Window;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.util.List;

public final class Level
{
    private LevelInfo level;
    private Point2D initialPosition;
    private Canvas canvas;
    private Window window;


    /**
     * Creates a new level
     * @param file
     */
    public Level(Window window, File file) {
        this.window = window;
        level = LevelReader.readLevel(file);
        initialPosition = getInitialPosition();
        canvas = createCanvas(level.width(), level.height());
    }

    /**
     * Returns the tiles that level contains
     * @return tiles that level contains
     */
    public List<Tile> getTiles()
    {
        return level.tiles();
    }

    /**
     * @return the {@code Point2D} position of the first top-left floor tile, or {@code null} if there is no tiles
     */
    public Point2D getFirstFloorTilePosition() {
        for (Tile tile : level.tiles()) {
            if (!tile.solid())
                return tile.getPosition();
        }
        return getTiles().get(0).getPosition();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void moveCanvasX(double dx) {
        initialPosition = new Point2D(
                initialPosition.getX() - dx,
                initialPosition.getY()
        );
        canvas.setTranslateX(initialPosition.getX());
    }
    public void moveCanvasY(double dy) {
        initialPosition = new Point2D(
                initialPosition.getX(),
                initialPosition.getY() - dy
        );
        canvas.setTranslateY(initialPosition.getY());
    }

    private Canvas createCanvas(double width, double height) {
        Point2D position = getInitialPosition();
        Canvas canvas = new Canvas();
        canvas.setTranslateX(position.getX());
        canvas.setTranslateY(position.getY());
        canvas.setWidth(width);
        canvas.setHeight(height);
        return canvas;
    }

    public Point2D getInitialPosition() {
        Point2D firstTile = getFirstFloorTilePosition();
        Point2D position = new Point2D(
                window.getCenter(Player.getPLAYER_SIZE()).getX() - firstTile.getX() - 17,
                window.getCenter(Player.getPLAYER_SIZE()).getY() - firstTile.getY() - 17
        );
        return position;
    }

    public double getWidth() {
        return level.width();
    }

    public double getHeight() {
        return level.height();
    }

}
