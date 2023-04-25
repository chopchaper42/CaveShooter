package Utility;

import Engine.Entity.Entity;
import Engine.Entity.Items.Item;
import Engine.Entity.Tiles.Wall;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

public class Collisions
{
    /**
     * Checks if the given object intersects with any object from the given list
     * @param entities list of objects to check the intersection with
     * @param object an object
     * @param <T> a type which extends the Entity class
     * @return {@code true} if object intersects, otherwise {@code false}
     */
    public static <T extends Entity> boolean checkWallCollision(List<T> entities, Rectangle2D object) {
        boolean intersects = false;
        boolean isWall = false;
        boolean collides = false;

        for (T entity : entities) {
            if (!collides) {
                intersects = object.intersects(((Entity) entity).getBoundaries());//.intersects(object);
                isWall = entity instanceof Wall;
                collides = intersects && isWall;

            }

        }
        return collides;
    }

    public static <T extends Item> List<Item> checkItemCollision(List<T> items, Rectangle2D object) {
        List<Item> intersected = new ArrayList<>();
        for (T item : items) {
            if (object.intersects(((Item) item).getBoundaries())) {
                intersected.add(item);
                System.out.println("intersection");
            }

        }
        return intersected;
    }
}
