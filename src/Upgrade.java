import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class make the upgrade packages for the tank
 * if the tank intersect with upgrade instances the cannon or cartiger will upgrade
 * If the tank used cannon, it will upgrade else another one will upgrade
 */
public class Upgrade {
    private BufferedImage image;
    private Integer x;
    private Integer y;

    public Upgrade(int x, int y) {
        try {
            setImage(ImageIO.read(new File("upgrade.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setX(x);
        this.setY(y);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Rectangle getRectangle() {
        Rectangle upgradeRectangle =  new Rectangle(x - GameState.XChange, y - GameState.YChange, image.getWidth(), image.getHeight());
        return upgradeRectangle;
    }
}
