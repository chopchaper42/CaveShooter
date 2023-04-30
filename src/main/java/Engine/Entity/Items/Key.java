package Engine.Entity.Items;

import javafx.scene.image.Image;

import java.io.File;

public class Key extends Item {

    private final static Image image = new Image(new File("./src/main/assets/key.png").toURI().toString(), 48, 48, false, false);

    public Key(double x, double y, int amount) {
        super(image, x, y, amount);
    }
}
