import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * This class make the main Tank for the Player
 * also intialized to make a nother tank for the Friend
 */

public class MyTank {

	private BufferedImage myTank;
	private BufferedImage tankPipe;
	private boolean heavyBullet = true;
	private Integer life = 3;
	private Integer cannonShell = 100;
	private Integer gunCartridge = 100; 
	public  int locationX;
	public  int locationY;
	public int revive = 3;
	public int cannonUp = 0;
	public int cartridgeUp =0;
	public static double angleRad;

	public MyTank() {
		try {
			myTank = ImageIO.read(new File("tank.png"));
			tankPipe = ImageIO.read(new File("tankGun01.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public BufferedImage getTankImage() {
		return myTank;
	}
	
	public BufferedImage getPipe() {
		return tankPipe;
	}
	
	public void changeGun() {
		if (heavyBullet) {
			try {
				tankPipe = ImageIO.read(new File("tankGun02.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			heavyBullet = false;
		}
		else {
			try {
				tankPipe = ImageIO.read(new File("tankGun01.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			heavyBullet = true;
		}
	}

	public Integer getLife() {
		return life;
	}

	public void decreaseLife() {
		life--;
	}

	public Integer getCannonShell() {
		return cannonShell;
	}

	public void increaseCannonShell() {
		cannonShell += 50;
	}
	
	public void reviveCannonShell() {
		cannonShell = 100;
		cannonUp = 0;
	}
	
	public void decreaseCannonShell() {
		cannonShell -= 1;
	}
	
	public Integer getGunCartridge() {
		return gunCartridge;
	}

	public void increaseGunCartridge() {
		gunCartridge += 50;
	}
	
	public void reviveGunCartridge() {
		gunCartridge = 100;
		cartridgeUp = 0;
	}
	
	public void decreaseGunCartridge() {
		gunCartridge -= 1;
	}
	
	public void increaseLife() {
		life = 3;
	}
	
	public void setLife(Integer life) {
		this.life = life;
	}

	public void setCannonShell(int cannonShell) {
		this.cannonShell = cannonShell;
	}

	public void setGunCartridge(int gunCartridge) {
		this.gunCartridge = gunCartridge;
	}

	public void cannonUp() {
		cannonUp++;
		try {
			tankPipe = ImageIO.read(new File("playerCannonGun2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void revive() {
		revive --;
    	increaseLife();
    	reviveCannonShell();
    	reviveGunCartridge();
    	if (heavyBullet) {
			try {
				tankPipe = ImageIO.read(new File("tankGun01.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				tankPipe = ImageIO.read(new File("tankGun02.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    		
	}
}

