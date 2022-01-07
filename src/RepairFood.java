import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class makes the packages for the main food that
 *  if main tank intersect with , it heals.
 */
public class RepairFood {

	private BufferedImage image;
	private Integer x;
	private Integer y;
	
	public RepairFood(int x, int y) {
		try {
			setImage(ImageIO.read(new File("RepairFood.png")));
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
		Rectangle repairFoodRectangle =  new Rectangle(x - GameState.XChange, y - GameState.YChange, image.getWidth(), image.getHeight());
		return repairFoodRectangle;
	}
}
