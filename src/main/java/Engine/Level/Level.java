package Engine.Level;

import Engine.Graphics;
import Engine.Entity.Tile.Tile;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.util.List;

public final class Level
{
    private LevelInfo level = null;
    private Point2D defaultPosition;

    /**
     * Creates a new level
     * @param file
     */
    public Level(File file) {
        level = LevelReader.readLevel(file);
        setCanvasWidthAndHeight(level.width(), level.height());
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
    public Point2D getFirstFloorTile() {
        for (Tile tile : level.tiles()) {
            if (!tile.solid())
                return tile.getPosition();
        }
        return getTiles().get(0).getPosition();
    }

    private void setCanvasWidthAndHeight(double width, double height) {
        Canvas canvas = Graphics.getCanvas();
        canvas.setWidth(width);
        canvas.setHeight(height);
    }
}
