package Utility;

import Engine.Entity.Items.Item;
import Engine.Entity.Tile.Tile;
import Logs.Logger;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

public class Collisions
{
    /**
     * Checks if the given object intersects with any object from the given list
     * @param tiles list of objects to check the intersection with
     * @param object an object
     * @param <T> a type which extends the Entity class
     * @return {@code true} if object intersects, otherwise {@code false}
     */
    public static boolean checkWallCollision(List<Tile> tiles, Rectangle2D object) {
        boolean collides = false;

        for (Tile tile : tiles) {
            if (!collides) {
                boolean intersects = object.intersects(tile.getBoundaries());
                collides = intersects && tile.solid();

            }

        }
        return collides;
    }

    public static <T extends Item> List<Item> checkItemCollision(List<T> items, Rectangle2D object) {
        List<Item> intersected = new ArrayList<>();
        for (T item : items) {
            if (object.intersects(((Item) item).getBoundaries())) {
                intersected.add(item);
                Logger.log("intersection with " + item);
            }

        }
        return intersected;
    }
}
