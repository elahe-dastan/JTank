import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class make the smart shots
 *
 */
public class SmartShot {

	 private BufferedImage bullet;
	    private double bulletX;
	    private double bulletY;
	    private double XMove = 0;
	    private double YMove = 0;
	    
	    //constructor
	    public SmartShot(int startX, int startY) {
	        try {
	        	//IT SHOULD BE CHANGED OFCOURSE
	        	bullet = ImageIO.read(new File("smartShot.png"));
	            bulletX = startX;
	            bulletY = startY;

	        }
	        catch (IOException e1) {
	            e1.printStackTrace();
	        }
	    }
		
	    public void move(int myTankLocX,int myTankLocY) {
	    	if (!GameState.escape) {
				double dx = myTankLocX - bulletX;
				double dy = myTankLocY - bulletY; 
	            double angleRad = Math.atan2(dy, dx);
	            double XMove = Math.cos(angleRad);
	            double YMove = Math.sin(angleRad);
				bulletX += XMove * 5;
				bulletY += YMove * 5;
			}
	    }
	    
	    public void move(int myTankLocX,int myTankLocY, int friendLocX, int friendLocY) {
	    	if (!GameState.escape) {
	    		double distanceMyTank = Math.sqrt(Math.pow(bulletX - myTankLocX, 2) + Math.pow(bulletY - myTankLocY, 2));
				double distanceFriendTank = Math.sqrt(Math.pow(bulletX - friendLocX, 2) + Math.pow(bulletY - friendLocY, 2));
				double dx = 0;
				double dy = 0;
				if (distanceMyTank < distanceFriendTank) {
					dx = myTankLocX - bulletX;
					dy = myTankLocY - bulletY;
				}
				else if (distanceMyTank > distanceFriendTank) {
					dx = friendLocX - bulletX;
					dy = friendLocY - bulletY; 
				}
				double angleRad = Math.atan2(dy, dx);
	            double XMove = Math.cos(angleRad);
	            double YMove = Math.sin(angleRad);
				bulletX += XMove * 10;
				bulletY += YMove * 10;
			}
	    }
	    
	    public Rectangle getRectangle() {
	    	//THE BUFFERED IMAGE OF THIS CLASS IS GONNA CHANGE AND A BIGGER IMAGE IS GONNA BE INSTEAD SOOOOO
	    	//THE NUMBER 9 IN THE NEXT LINE WILL CHANGE FOR SURE
	    	Rectangle bulletRectangle = new Rectangle((int)(bulletX - GameState.XChange), (int)(bulletY - GameState.YChange), bullet.getWidth(), bullet.getHeight());
	    	return bulletRectangle;
	    }

	    public BufferedImage getBullet() {
	    	return bullet;
	    }

	    public int getBulletX() {
	    	return (int)bulletX;
	    }

	    public int getBulletY() {
	    	return (int)bulletY;
	    }

}
