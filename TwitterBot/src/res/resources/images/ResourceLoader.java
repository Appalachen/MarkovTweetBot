package res.resources.images;

import java.awt.Image;
import java.awt.Toolkit;

public class ResourceLoader {

    static ResourceLoader rl = new ResourceLoader();
    static String authKey;

    public static Image loadImage(String imageName) {
        return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource("images/" + imageName));
    }
//    public static String loadAuthkeys(){
//        authKey=
//    }
}