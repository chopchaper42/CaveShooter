package Engine.Entity.Tile;

import javafx.scene.image.Image;

import java.io.File;

public class Wall extends Tile {
    public static final Image WALL_IMG = new Image(new File("./src/main/assets/wall.png").toURI().toString());

    public Wall(double x, double y) {
        super(WALL_IMG, x, y, true);
    }
}
