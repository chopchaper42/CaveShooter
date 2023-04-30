package Engine.Entity.Tile;

import javafx.scene.image.Image;

import java.io.File;

public class Floor extends Tile {
    public static final Image FLOOR_IMG = new Image(new File("./src/main/assets/floor.png").toURI().toString());

    public Floor(double x, double y) {
        super(FLOOR_IMG, x, y, false);
    }
}
