import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Soil {

	private BufferedImage image;
	private Integer x;
	private Integer y;
	
	public Soil(int x, int y) {
		try {
			setImage(ImageIO.read(new File("soil.png")));
			this.setX(x);
			this.setY(y);
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
