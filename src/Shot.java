import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

/**
 *  This class handle all the Shots and bullets both for enemies and for Main tank
 *  This class make both smart shots and normal shots
 *  Smart shots goes to get near to the main tank
 *  Normal Shots  goes a straight line
 */

public class Shot {
    //fields
    private BufferedImage bullet;
    private double bulletX;
    private double bulletY;
    private double xVelocity;
    private double yVelocity;
    public boolean heavyBullet;
    public boolean isAlive = true;

    //constructor
    public Shot(double startX, double startY, double xVelocity, double yVelocity,boolean heavyBullet) {
        try {
        	if (heavyBullet)
        		 bullet = ImageIO.read(new File("HeavyBullet.png"));
        	else
        		 bullet = ImageIO.read(new File("LightBullet.png"));
            bulletX = startX;
            bulletY = startY;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
            this.heavyBullet = heavyBullet;
        }
        catch (IOException e1) {
            e1.printStackTrace();
        } 
    }
    
    public void move() {
    	 bulletX += xVelocity * 10;
         bulletY += yVelocity * 10;
         if (!(0 < bulletX && bulletX < 1280 && 0 < bulletY && bulletY < 720)) {
        	 isAlive = false;
         }
    }
    public Rectangle getRectangle() {
    	Rectangle bulletRectangle = new Rectangle((int)bulletX, (int)bulletY, bullet.getWidth(), bullet.getHeight());
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