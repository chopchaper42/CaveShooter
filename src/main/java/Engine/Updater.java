package Engine;

import Engine.Entity.Bullet;
import Engine.Entity.Entity;
import Engine.Entity.Items.Item;
import Engine.Entity.Player;
import Engine.Level.Level;
import Utility.Collisions;
import com.sun.source.doctree.UnknownInlineTagTree;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Updater {
    private final List<Entity> toRemove = new ArrayList<>();
    private Level level;
    private Player player;
    private UIManager uiManager;

    public Updater(Level level, Player player, UIManager uiManager) {
        this.level = level;
        this.player = player;
        this.uiManager = uiManager;
    }

    public void update(double dt) {
        redrawEntities(level, level.canvas());

        //redraw UI
        uiManager.update();

        // mark bullets "to remove" if they intersect with a wall
        level.bullets().forEach(bullet -> { // override draw() in bullet to move on each call
            if (Collisions.checkWallCollision(bullet, level.tiles())) {
                toRemove.add(bullet);
            }
            bullet.move(dt);
        });

        // check for items in player's range
        List<Item> itemsInRange = Collisions.checkItemCollision(player, level.items());
        player.takeItems(itemsInRange);
        toRemove.addAll(itemsInRange);

        // remove entities to remove
        toRemove.forEach(entity -> {
            if (entity instanceof Item) {
                level.items().remove(entity);
            } else if (entity instanceof Bullet) {
                level.bullets().remove(entity);
            }
        });
    }

    private void redrawEntities(Level level, Canvas canvas) {
        draw(level.tiles(), canvas);
        draw(level.items(), canvas);
        draw(level.bullets(), canvas);
    }

    private <T extends Entity> void draw(List<T> entities, Canvas canvas) {
        entities.forEach(entity -> {
            entity.draw(canvas);
        });
    }
}
