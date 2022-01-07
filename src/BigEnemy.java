import java.awt.Rectangle; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class make the Enemies with feature of moving
 * Its movement is Handled smart and it goes get near to MyTank
 * and also Shot some bullet
 */
public class BigEnemy {

	//I WILL PUT THREE OTHER CHARS FOR ENEMYS WITH GIFTS
	private BufferedImage image;
	private BufferedImage pipe;
	private double x;
	private double y;
	public double angleRad;
	private double XMove = 0;
	private double YMove = 0;
	private boolean collision = false;
	private int counter = 0;
	public boolean repairFood;//a
	public boolean cannonFood;//b
	public boolean gunFood;//c
	public boolean upgrade;//d
	public boolean extraExistence;//e
	private Integer lightBulletsHit = 0;

	
	public BigEnemy(int x, int y) {
		try {
			setImage(ImageIO.read(new File("BigEnemy.png")));
			setPipe(ImageIO.read(new File("BigEnemyGun.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setX(x);
		this.setY(y);
	}
	public void attack(int myTankLocX,int myTankLocY) {
		//System.out.println("alive");//IF I REMOVE THE SYSOUT IT DOESN'T WORK ANYMORE :||||||||||
		if (!GameState.escape) {
			XMove = Math.cos(Math.atan2(myTankLocY - y, myTankLocX - x)) * 3;
			YMove = Math.sin(Math.atan2(myTankLocY - y, myTankLocX - x)) * 3;
			if (!collision) {
				x += XMove;
				y += YMove;
			}
			counter++;
			if (counter == 30) {
				Shot shot = new Shot(x - GameState.XChange, y - GameState.YChange, Math.cos(getBigEnemyAngleRad(myTankLocX, myTankLocY)), Math.sin(getBigEnemyAngleRad(myTankLocX, myTankLocY)),true);
        		GameState.enemyFires.add(shot);
        		counter = 0;
			}
		}
	}
	public void attack(int myTankLocX,int myTankLocY,int friendLocX, int friendLocY) {
		if (!GameState.escape) {
			double distanceMyTank = Math.sqrt(Math.pow(x - myTankLocX, 2) + Math.pow(y - myTankLocY, 2));
			double distanceFriendTank = Math.sqrt(Math.pow(x - friendLocX, 2) + Math.pow(y - friendLocY, 2));
			if (distanceMyTank < distanceFriendTank) {
				XMove = Math.cos(Math.atan2(myTankLocY - y, myTankLocX - x)) * 3;
				YMove = Math.sin(Math.atan2(myTankLocY - y, myTankLocX - x)) * 3;
			}
			else if (distanceMyTank > distanceFriendTank){
				XMove = Math.cos(Math.atan2(friendLocY - y, friendLocX - x)) * 3;
				YMove = Math.sin(Math.atan2(friendLocY - y, friendLocX - x)) * 3;
			}
			if (!collision) {
				x += XMove;
				y += YMove;
			}
			counter++;
			if (counter == 30) {
				if (distanceMyTank < distanceFriendTank) {
					Shot shot = new Shot(x - GameState.XChange, y - GameState.YChange, Math.cos(getBigEnemyAngleRad(myTankLocX, myTankLocY)), Math.sin(getBigEnemyAngleRad(myTankLocX, myTankLocY)),true);
					GameState.enemyFires.add(shot);
				}
				else {
					Shot shot = new Shot(x - GameState.XChange, y - GameState.YChange, Math.cos(getBigEnemyAngleRad(friendLocX, friendLocY)), Math.sin(getBigEnemyAngleRad(friendLocX, friendLocY)),true);
					GameState.enemyFires.add(shot);
				}
        		counter = 0;
			}
		}
	}

	public Integer getX() {
		return (int)x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return (int)y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getPipe() {
		return pipe;
	}

	public void setPipe(BufferedImage pipe) {
		this.pipe = pipe;
	}
	public double getBigEnemyAngleRad(int locX,int locY) {
		double dx = locX - x;
        double dy = locY - y;
        angleRad = Math.atan2(dy, dx);
        return angleRad;
	}
	
	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	
	
	public Rectangle getRectangle() {
		Rectangle bigEnemyRectangle =  new Rectangle((int)(x - GameState.XChange + XMove), (int)(y - GameState.YChange + YMove), image.getWidth(), image.getHeight());
		return bigEnemyRectangle;
	}
	
	public boolean hit(int cartridgeUp) {
		if (cartridgeUp > 0) {
			lightBulletsHit += 2;
		}
		else
			lightBulletsHit++;
		if (lightBulletsHit == 8) {
			lightBulletsHit = 0;
			return true;
		}
		return false;
	}
}
