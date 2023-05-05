package Engine;

import Engine.Entity.Bullet;
import Engine.Entity.Items.Type;
import Engine.Entity.Player;
import Engine.Level.Level;
import Utility.Collisions;
import Utility.Pythagoras;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class InputManager {
    Player player;
    Level level;
    boolean UP = false;
    boolean LEFT = false;
    boolean DOWN = false;
    boolean RIGHT = false;

    public InputManager(Player player, Level level) {
        this.player = player;
        this.level = level;
    }

    public void handleInput(double dt) {
        double dx = 0;
        double dy = 0;
        double distance = player.getSpeedPerSecond() * dt;

        if (UP && !(RIGHT || LEFT)) {
            dy = -distance;
        }

        if (UP && RIGHT) {
            dx = Pythagoras.leg45deg(distance);
            dy = -1 * Pythagoras.leg45deg(distance);
        }

        if (UP && LEFT) {
            dx = -1 * Pythagoras.leg45deg(distance);
            dy = -1 * Pythagoras.leg45deg(distance);
        }

        if (LEFT && !(UP || DOWN)) {
            dx = -1 * distance;
        }

        if (DOWN && !(RIGHT || LEFT)) {
            dy = distance;
        }

        if (DOWN && LEFT) {
            dx = -1 * Pythagoras.leg45deg(distance);
            dy = Pythagoras.leg45deg(distance);
        }

        if (DOWN && RIGHT) {
            dx = Pythagoras.leg45deg(distance);
            dy = Pythagoras.leg45deg(distance);
        }

        if (RIGHT && !(DOWN || UP)) {
            dx = distance;
        }


        Rectangle2D newBoundariesX = new Rectangle2D(
                player.getX() + dx,
                player.getY(),
                player.getImage().getWidth(),
                player.getImage().getHeight()
        );
        Rectangle2D newBoundariesY = new Rectangle2D(
                player.getX(),
                player.getY() + dy,
                player.getImage().getWidth(),
                player.getImage().getHeight()
        );

        if (dx != 0 && !Collisions.checkWallCollision(newBoundariesX, level.tiles())) {
            player.moveX(dx);
            level.moveCanvas(dx, 0);
        }
        if (dy != 0 && !Collisions.checkWallCollision(newBoundariesY, level.tiles())) {
            player.moveY(dy);
            level.moveCanvas(0, dy);
        }

        player.setBoundaries(
                player.getX(),
                player.getY(),
                player.getImage().getWidth(),
                player.getImage().getHeight()
        );
    }

    public void shoot(MouseEvent event, List<Bullet> bullets) {
        if (player.getItemAmount(Type.AMMO) > 0) {
            Point2D direction = new Point2D(
                    player.getX() - (player.positionOnCanvasX() - event.getX()),
                    player.getY() - (player.positionOnCanvasY() - event.getY())
            );
            Bullet bullet = new Bullet(player, direction);
            bullets.add(bullet);
            player.decreaseItem(Type.AMMO);
        }
    }

    public void release(KeyEvent event)
    {
        handle(event.getCode(), false);
    }

    public void press(KeyEvent event) {
        handle(event.getCode(), true);
    }

    private void handle(KeyCode code, boolean pressed) {
        switch (code) {
            case W -> UP = pressed;
            case A -> LEFT = pressed;
            case S -> DOWN = pressed;
            case D -> RIGHT = pressed;
            case SPACE -> {
                if (pressed)
                    player.tryOpenDoor(level.tiles());
            }
        }
    }
}
