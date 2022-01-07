import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Destroyed {
    private BufferedImage image;
    private Integer x;
    private Integer y;

    public Destroyed(int x, int y) {
        try {
            setImage(ImageIO.read(new File("destroyed.png")));
            this.setX(x);
            this.setY(y);
            System.out.println("enter");
        } catch (IOException e) {
           e.printStackTrace();
        }
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

}