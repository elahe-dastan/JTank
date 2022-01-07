import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class make suicide Enemy
 * This enemy goes to hit the tank and then its die.
 */
public class KhengEnemy {

	private BufferedImage image;
	private double x;
	private double y;
	private boolean collision = false;
	private double XMove;
	private double YMove;
	private Integer lightBulletsHit = 0;
	private int speed;

	public KhengEnemy(int x, int y) {
		try {
			setImage(ImageIO.read(new File("KhengEnemy.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setX(x);
		this.setY(y);
		if (GameState.difficulty == 1)
			speed = 3;
		else if (GameState.difficulty == 2)
			speed = 4;
		else
			speed = 5;
	}

	public void attack(int myTankLocX,int myTankLocY) {	
		XMove = Math.cos(Math.atan2(myTankLocY - y, myTankLocX - x)) * speed;
		YMove = Math.sin(Math.atan2(myTankLocY - y, myTankLocX - x)) * speed;
		if (!collision) {
			x += XMove;
			y += YMove;
		}
	}

	public void attack(int myTankLocX,int myTankLocY,int friendLocX, int friendLocY) {
		if (!GameState.escape) {
			double distanceMyTank = Math.sqrt(Math.pow(x - myTankLocX, 2) + Math.pow(y - myTankLocY, 2));
			double distanceFriendTank = Math.sqrt(Math.pow(x - friendLocX, 2) + Math.pow(y - friendLocY, 2));
			if (distanceMyTank < distanceFriendTank) {
				XMove = Math.cos(Math.atan2(myTankLocY - y, myTankLocX - x)) * speed;
				YMove = Math.sin(Math.atan2(myTankLocY - y, myTankLocX - x)) * speed;
			}
			else if (distanceMyTank > distanceFriendTank){
				XMove = Math.cos(Math.atan2(friendLocY - y, friendLocX - x)) * speed;
				YMove = Math.sin(Math.atan2(friendLocY - y, friendLocX - x)) * speed;
			}
			if (!collision) {
				x += XMove;
				y += YMove;
			}
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
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
	
	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public void moveHorizentally() {
		x += XMove;
	}
	
	public void moveVertically() {
		y += YMove;
	}
	public Rectangle getRectangle() {
		Rectangle khengEnemyRectangle = new Rectangle((int)(x - GameState.XChange+XMove) , (int)(y - GameState.YChange+YMove), image.getWidth(), image.getHeight());
		return khengEnemyRectangle;
	}

	public Rectangle moveVerticalRectangle() {
		Rectangle khengEnemyRectangle = new Rectangle((int)(x - GameState.XChange) , (int)(y - GameState.YChange+YMove), image.getWidth(), image.getHeight());
		return khengEnemyRectangle;
	}
	
	public Rectangle moveHorizentalRectangle() {
		Rectangle khengEnemyRectangle = new Rectangle((int)(x - GameState.XChange+XMove) , (int)(y - GameState.YChange), image.getWidth(), image.getHeight());
		return khengEnemyRectangle;
	}
	
	public boolean hit(int cartridgeUp) {
		if (cartridgeUp > 0)
			lightBulletsHit += 2;
		else
			lightBulletsHit++;
		if (lightBulletsHit == 5) {
			lightBulletsHit = 0;
			return true;
		}
		return false;
	}
}
