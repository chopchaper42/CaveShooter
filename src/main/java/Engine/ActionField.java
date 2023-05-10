package Engine;

import Engine.Entity.Entity;
import Engine.Entity.Tile.Tile;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class ActionField {
    private Entity entity;
    private double radiusInPixels;
    private Rectangle2D bounds;

    public ActionField(double radiusInTiles, Entity entity) {
        this.entity = entity;
        this.radiusInPixels = radiusInTiles * Tile.TILE_SIZE;
        updateToMatchCoordinates();
    }

    public Rectangle2D bounds() {
        return bounds;
    }

    public void updateToMatchCoordinates() {
        Point2D entityCenter = entity.center();
        this.bounds = new Rectangle2D(
                entityCenter.getX() - radiusInPixels,
                entityCenter.getY() - radiusInPixels,
                2 * radiusInPixels + entity.width(),
                2 * radiusInPixels + entity.height()
        );
    }

    public void draw(Canvas canvas) {
        canvas.getGraphicsContext2D().setStroke(Color.YELLOWGREEN);
        canvas.getGraphicsContext2D().strokeRect(
                bounds.getMinX(),
                bounds.getMinY(),
                bounds.getWidth(),
                bounds.getHeight()
        );
    }

}
