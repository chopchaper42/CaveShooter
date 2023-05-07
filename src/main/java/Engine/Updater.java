package Engine;

import Engine.Entity.Bullet;
import Engine.Entity.Enemy;
import Engine.Entity.Entity;
import Engine.Entity.Items.Item;
import Engine.Entity.Player;
import Engine.Level.Level;
import Logs.Logger;
import Utility.Collisions;
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

        //enemies shoot
        level.enemies().forEach(enemy -> enemy.shoot(player, level.bullets(), dt));

        // mark bullets "to remove" if they intersect with a wall
        level.bullets().forEach(bullet -> { // override draw() in bullet to move on each call
            if (Collisions.checkWallCollision(bullet, level.tiles())) {
                toRemove.add(bullet);
            }
            if (bullet.source() != player && player.getBoundaries().intersects(bullet.getBoundaries())) { // may be add source to bullet to except friendly fire
                if (player.getHealth() - bullet.DAMAGE() <= 0) {
                    Logger.log("DEAD");
//                    toRemove.add(player);
                } else {
                    player.decreaseHealth(bullet.DAMAGE());
                    toRemove.add(bullet);
                }
            }
            Enemy intersectedEntity = Collisions.checkBulletCollision(bullet, level.enemies());
            if (intersectedEntity != null) {
                Logger.log(intersectedEntity + "'s been hit. Now he has " + intersectedEntity.getHealth() + " HP.");
                if ((intersectedEntity.getHealth() - bullet.DAMAGE()) <= 0) {
                    Logger.log(intersectedEntity + " added to REMOVE");
                    toRemove.add(intersectedEntity);
                }
                intersectedEntity.decreaseHealth(bullet.DAMAGE());
                toRemove.add(bullet);
            }

            bullet.move(dt);
        });


        // check if player is in enemies vision zone
        Collisions.checkEnemiesVisionZoneIntersection(player, level.enemies());

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
            } else if (entity instanceof Enemy) {
                level.enemies().remove(entity);
            }
        });
    }

    private void redrawEntities(Level level, Canvas canvas) {
        draw(level.tiles(), canvas);
        draw(level.items(), canvas);
        draw(level.bullets(), canvas);
        draw(level.enemies(), canvas);
    }

    private <T extends Entity> void draw(List<T> entities, Canvas canvas) {
        entities.forEach(entity -> {
            entity.draw(canvas);
        });
    }
}
