package Engine;

import Engine.Entity.Bullet;
import Engine.Entity.Player;
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
    boolean W_pressed = false;
    boolean A_pressed = false;
    boolean S_pressed = false;
    boolean D_pressed = false;

    public InputManager(Player player) {
        this.player = player;
    }

    public void handleInput(double dt) {
        double dx = 0;
        double dy = 0;
        double distance = player.getSpeedPerSecond() * dt;

        if (W_pressed && !(D_pressed || A_pressed)) {
            dy = -distance;
            //moveY(-distance);
        }

        if (W_pressed && D_pressed) {
            dx = Pythagoras.leg45deg(distance);
            dy = -1 * Pythagoras.leg45deg(distance);
//                moveDiagonal(Pythagoras.leg(distance), 1, -1);
        }

        if (W_pressed && A_pressed) {
            dx = -1 * Pythagoras.leg45deg(distance);
            dy = -1 * Pythagoras.leg45deg(distance);
//                moveDiagonal(Pythagoras.leg(distance), -1, -1);
        }

        if (A_pressed && !(W_pressed || S_pressed)) {
            dx = -1 * distance;
//                moveX(-distance);
        }

        if (S_pressed && !(D_pressed || A_pressed)) {
            dy = distance;
//                moveY(distance);
        }

        if (S_pressed && A_pressed) {
            dx = -1 * Pythagoras.leg45deg(distance);
            dy = Pythagoras.leg45deg(distance);
//                moveDiagonal(Pythagoras.leg(distance), -1, 1);
        }

        if (S_pressed && D_pressed) {
            dx = Pythagoras.leg45deg(distance);
            dy = Pythagoras.leg45deg(distance);
//                moveDiagonal(Pythagoras.leg(distance), 1, 1);
        }

        if (D_pressed && !(S_pressed || W_pressed)) {
            dx = distance;
//                moveX(distance);
        }


        Rectangle2D newBoundariesX = new Rectangle2D(
                player.getX() + dx,
                player.getY(),
                player.getImage().getWidth(),
                player.getImage().getHeight()
        );
        Rectangle2D newBoundariesY = new Rectangle2D(
                player.getX() + dy,
                player.getY(),
                player.getImage().getWidth(),
                player.getImage().getHeight()
        );

        if (!Collisions.checkWallCollision(Game.getLevel().getTiles(), newBoundariesX)) {
            player.moveX(dx);
        }
        if (!Collisions.checkWallCollision(Game.getLevel().getTiles(), newBoundariesY)) {
            player.moveY(dy);
        }

        player.setBoundaries(
                player.getX(),
                player.getY(),
                player.getImage().getWidth(),
                player.getImage().getHeight()
        );
    }

    public void shoot(MouseEvent event, List<Bullet> bullets) {
        if (player.getAmmo() > 0) {
            Point2D direction = new Point2D(event.getX(), event.getY());
            Bullet bullet = new Bullet(player, direction);
            bullets.add(bullet);
            player.decreaseAmmo();
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
            case W -> W_pressed = pressed;
            case A -> A_pressed = pressed;
            case S -> S_pressed = pressed;
            case D -> D_pressed = pressed;
            case SPACE -> player.useKey();
        }
    }
}
