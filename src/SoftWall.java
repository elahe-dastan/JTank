import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SoftWall {

	private BufferedImage image;
	private Integer x;
	private Integer y;
	private Integer destruction = 0;
	private Integer lightBulletsHit = 0;
	
	public SoftWall(int x, int y) {
		try {
			setImage(ImageIO.read(new File("softWall.png")));
			this.setX(x);
			this.setY(y);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public boolean ruin() {
		destruction++;
		GameFrame.playSoundBullet("softwall.wav");
		try {
			if (destruction == 1)
				setImage(ImageIO.read(new File("softWall1.png")));
			else if (destruction == 2)
				setImage(ImageIO.read(new File("softWall2.png")));
			else if (destruction == 3)
				setImage(ImageIO.read(new File("softWall3.png")));
			else if (destruction == 4)
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		return false;
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
		Rectangle softWallRectangle =  new Rectangle(x - GameState.XChange, y - GameState.YChange, image.getWidth(), image.getHeight());
		return softWallRectangle;
	}
	
	public Integer getDestruction() {
		return destruction;
	}
	
	public boolean hit(int cartridgeUp) {
		if (cartridgeUp > 0) {
			lightBulletsHit += 2;
		}
		else
			lightBulletsHit++;
		if (lightBulletsHit == 10) {
			lightBulletsHit = 0;
			return true;
		}
		return false;
	}
}
