import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Teazel {

	private BufferedImage image;
	private Integer x;
	private Integer y;
	
	public Teazel(int x, int y) {
		try {
			setImage(ImageIO.read(new File("teazel2.png")));
			this.setX(x);
			this.setY(y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	public Rectangle getRectangle() {
		Rectangle teazelRectangle =  new Rectangle(x - GameState.XChange, y - GameState.YChange, image.getWidth(), image.getHeight());
		return teazelRectangle;
	}
}
