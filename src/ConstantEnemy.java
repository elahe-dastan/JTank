import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class make a constant enemy and handle its behaviour
 */
public class ConstantEnemy {

	private BufferedImage image;
	private Integer x;
	private Integer y;
	private Integer life;
	private boolean alive = true;
	private int counter = 0;
	public double angleRad;
	public boolean repairFood;//f
	public boolean cannonFood;//g
	public boolean gunFood;//h
	public boolean upgrade;//i
	public boolean extraExistence;//j
	private Integer lightBulletsHit = 0;
	
	public ConstantEnemy(int x, int y) {
		try {
			setImage(ImageIO.read(new File("ConstantEnemy.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setX(x);
		this.setY(y);
		if (GameState.difficulty == 1)
			life = 1;
		else if (GameState.difficulty == 2)
			life = 2;
		else
			life = 3;
	}
	
	public void attack(int myTankLocX,int myTankLocY) {
		//System.out.println("alive");//IF I REMOVE THE SYSOUT IT DOESN'T WORK ANYMORE :||||||||||
		if (!GameState.escape) {
			counter++;
			if (counter == 50) {
				Shot shot = new Shot(x - GameState.XChange, y - GameState.YChange, Math.cos(getConstantEnemyAngleRad(myTankLocX, myTankLocY)), Math.sin(getConstantEnemyAngleRad(myTankLocX, myTankLocY)),true);
				GameState.enemyFires.add(shot);
				counter = 0;
			}	
		}
	}
	public void attack(int myTankLocX,int myTankLocY,int friendLocX, int friendLocY) {
		if (!GameState.escape) {
			counter++;
			if (counter == 50) {
				double distanceMyTank = Math.sqrt(Math.pow(x - myTankLocX, 2) + Math.pow(y - myTankLocY, 2));
				double distanceFriendTank = Math.sqrt(Math.pow(x - friendLocX, 2) + Math.pow(y - friendLocY, 2));
				if (distanceMyTank < distanceFriendTank) {
					Shot shot = new Shot(x - GameState.XChange, y - GameState.YChange, Math.cos(getConstantEnemyAngleRad(myTankLocX, myTankLocY)), Math.sin(getConstantEnemyAngleRad(myTankLocX, myTankLocY)),true);
					GameState.enemyFires.add(shot);
				}
				else if (distanceMyTank > distanceFriendTank) {
					Shot shot = new Shot(x - GameState.XChange, y - GameState.YChange, Math.cos(getConstantEnemyAngleRad(friendLocX, friendLocY)), Math.sin(getConstantEnemyAngleRad(friendLocX, friendLocY)),true);
					GameState.enemyFires.add(shot);
				}
				counter = 0;
			}
		}
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

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	private double getConstantEnemyAngleRad(int locX,int locY) {
		double dx = locX - x;
        double dy = locY - y;
        angleRad = Math.atan2(dy, dx);
        return angleRad;
	}
	
	public Rectangle getRectangle() {
		Rectangle constantEnemyRectangle = new Rectangle(x - GameState.XChange, y - GameState.YChange, image.getWidth(), image.getHeight());
		return constantEnemyRectangle;
	}
	
	public boolean isAlive() {
		return alive;
	}
	public void kill() {
		life--;
		if (life == 0)
			alive = false;
	}
	
	public boolean hit(int cartridgeUp) {
		if (cartridgeUp > 0)
			lightBulletsHit += 2;
		else
			lightBulletsHit++;
		if (lightBulletsHit == 10) {
			lightBulletsHit = 0;
			return true;
		}
		return false;
	}
}
