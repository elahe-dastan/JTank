import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

/**
 * this class draw the frame by using buffer strategy
 * iterate to the all arraylists to draw the elements are in the screen size ...
 * include the a method to play the sounds and the musics
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio
    private long lastRender;
    private ArrayList<Float> fpsHistory;
    private BufferStrategy bufferStrategy;

    private Integer picSizeX = 85;
    private Integer picSizeY = 91;
    private BufferedImage backGround;
    private int screenSize =800 ;
    
    
    private BufferedImage cannonShell;
    private BufferedImage gunCartridge;
    private BufferedImage health;
    private BufferedImage medal;
    
    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);
        try {
			backGround = ImageIO.read(new File("background.png"));
			cannonShell = ImageIO.read(new File("NumberOfHeavyBullet2.png"));
			gunCartridge = ImageIO.read(new File("NumberOfMachinGun2.png"));
			health = ImageIO.read(new File("health.png"));
			medal = ImageIO.read(new File("medal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }
    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
    	BufferedImage star =  null;
    	if (state.mapEditor){
			try {
				g2d.drawImage(ImageIO.read(new File("background.png")),0,0,null);
				g2d.drawImage(state.HARDWALL.getImage(),state.HARDWALL.getX(),state.HARDWALL.getY(),null);
				g2d.drawImage(state.SOFTWALL.getImage(),state.SOFTWALL.getX(),state.SOFTWALL.getY(),null);
				g2d.drawImage(state.BIGENEMY.getImage(),state.BIGENEMY.getX(),state.BIGENEMY.getY(),null);
				g2d.drawImage(state.MYTANK.getTankImage(),state.MYTANK.locationX,state.MYTANK.locationY,null);
				g2d.drawImage(state.CANOONFOOD.getImage(),state.CANOONFOOD.getX(),state.CANOONFOOD.getY(),null);
				g2d.drawImage(state.CONSTANTENEMY.getImage(),state.CONSTANTENEMY.getX(),state.CONSTANTENEMY.getY(),null);
				g2d.drawImage(state.PLANT.getImage(),state.PLANT.getX(),state.PLANT.getY(),null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (state.hardWalls.size()!=0){
				for (HardWall hardWall : state.hardWalls){
					g2d.drawImage(hardWall.getImage(),hardWall.getX(),hardWall.getY(),null);
				}
			}
			if (state.softWalls.size()!=0){
				for (SoftWall softWall : state.softWalls){
					g2d.drawImage(softWall.getImage(),softWall.getX(),softWall.getY(),null);
				}
			}
			if (state.bigEnemys.size()!=0){
				for (BigEnemy bigEnemy : state.bigEnemys){
					g2d.drawImage(bigEnemy.getImage(),bigEnemy.getX(),bigEnemy.getY(),null);
				}
			}
			if (state.getMyTank() != null){
				g2d.drawImage(state.MYTANK.getTankImage(),state.MYTANK.locationX,state.MYTANK.locationY,null);
			}
			if (state.cannonFoods.size()!= 0){
				for (CannonFood cannonFood : state.cannonFoods){
					g2d.drawImage(cannonFood.getImage(),cannonFood.getX(),cannonFood.getY(),null);
				}
			}
			if (state.constantEnemys.size()!=0){
				for (ConstantEnemy constantEnemy :state.constantEnemys){
					g2d.drawImage(constantEnemy.getImage(),constantEnemy.getX(),constantEnemy.getY(),null);
				}
			}
			if (state.plants.size() != 0){
				for (Plant plant : state.plants){
					g2d.drawImage(plant.getImage(),plant.getX(),plant.getY(),null);
				}
			}

		}

    	else if (state.win && !state.finish){
			String str = "YOU WIN\nENTER TO GO TO NEXT LEVEL";
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
    		// go to the next stage
		}
    	else if (state.win && state.finish) {
    		String str = "YOU WON\n";
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
    	}

    	else if (state.startMenu && !state.gameStage && !state.askOfOldGame) {
    		try {
    			BufferedImage networking = ImageIO.read(new File("menu2.jpg"));
    			star = ImageIO.read(new File("StartupStar.png"));
    			g2d.drawImage(networking, 0, 0, null);
    			if (Math.abs(state.kind % 3 )==0){
					g2d.drawImage(star,170,100,null);
				}else if (Math.abs(state.kind %3) ==1){
					g2d.drawImage(star,170,370,null);
				}else if (Math.abs(state.kind %3) ==2){
					g2d.drawImage(star,170,590,null);
				}		
    		}
    		catch(Exception e) {
    			System.out.println("couldn't read image");
    		}
    	}
    	
    	else if (state.startMenu && !state.gameStage && state.askOfOldGame) {
			try {
				BufferedImage image = ImageIO.read(new File("oldGame.jpg"));
				star = ImageIO.read(new File("StartupStar.png"));
				g2d.drawImage(image,0,0,null);
				if (Math.abs(state.oldGame % 3 )==0){
					g2d.drawImage(star,170,120,null);
				}else if (Math.abs(state.oldGame % 3) ==1){
					g2d.drawImage(star,170,400,null);
				}else if (Math.abs(state.oldGame%3)==2){
					g2d.drawImage(star,170,600,null);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

    	}


    	else if (state.startMenu && state.gameStage) {
			try {
				BufferedImage image = ImageIO.read(new File("startupnew.png"));
				star = ImageIO.read(new File("StartupStar.png"));
				g2d.drawImage(image,0,0,null);
				if (Math.abs(state.gameType % 3)== 0){
					g2d.drawImage(star,170,320,null);
				}else if (Math.abs(state.gameType % 3) == 1){
					g2d.drawImage(star,170,360,null);
				}else if (Math.abs(state.gameType % 3) == 2){
					g2d.drawImage(star,170,400,null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	else if (state.connectionLost) {
    		String str = "YOUR PARTNER EXITED THE GAME\n";
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
    	}
    	else if (state.escape) {
    		try {
    			
				BufferedImage image = ImageIO.read(new File("escape.jpg"));
				star = ImageIO.read(new File("StartupStar.png"));
				g2d.drawImage(image,0,0,null);
				if (Math.abs(state.exit % 2 )==0){
					g2d.drawImage(star,350,350,null);
				}else{
					g2d.drawImage(star,350,450,null);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		else {
			int tankX = state.locX;
			int tankY = state.locY;
			int XChange = 0;
			int YChange = 0;

			if (tankX > GAME_WIDTH / 2)
				XChange = tankX - GAME_WIDTH / 2;
			if (tankY > GAME_HEIGHT / 2)
				YChange = tankY - GAME_HEIGHT / 2;
			state.XChange = XChange;
			state.YChange = YChange;
			g2d.drawImage(backGround, 0, 0, null);
			for (Soil soil : state.soils) {
				if (soil.getX() < tankX + 1200 && soil.getX() > tankX - 800 && soil.getY() < tankY + 620 && soil.getY() > tankY - 620)
					g2d.drawImage(soil.getImage(), soil.getX() - XChange, soil.getY() - YChange, null);
			}
			for (Plant plant : state.plants) {
				if (plant.getX() < tankX + 1200 && plant.getX() > tankX - 800 && plant.getY() < tankY + 620 && plant.getY() > tankY - 620) {
					g2d.drawImage(plant.getImage(), plant.getX() - XChange, plant.getY() - YChange, null);
				}
			}
			for (SoftWall softWall : state.softWalls) {
				if (softWall.getX() < tankX + 1200 && softWall.getX() > tankX - 800 && softWall.getY() < tankY + 620 && softWall.getY() > tankY - 620)
					g2d.drawImage(softWall.getImage(), softWall.getX() - XChange, softWall.getY() - YChange, null);
			}
			for(HardWall hardWall : state.hardWalls) {
				if(hardWall.getX() < tankX + 1200 && hardWall.getX() > tankX - 800 && hardWall.getY() < tankY + 620 && hardWall.getY() > tankY - 620)
					g2d.drawImage(hardWall.getImage(), hardWall.getX() - XChange, hardWall.getY() - YChange, null);
			}
			for (Teazel teazel : state.teazels) {
				if (teazel.getX() < tankX + 800 && teazel.getX() > tankX - 800 && teazel.getY() < tankY + 620 && teazel.getY() > tankY - 620)
					g2d.drawImage(teazel.getImage(), teazel.getX() - XChange, teazel.getY() - YChange, null);
			}
			for (KhengEnemy khengEnemy : state.khengEnemys) {
				if ((khengEnemy.getX() < tankX + 400 && khengEnemy.getX() > tankX - 400 && khengEnemy.getY() < tankY + 310 && khengEnemy.getY() > tankY - 310) || (state.friend != null && khengEnemy.getX() < state.friend.locationX + 400 && khengEnemy.getX() > state.friend.locationX - 400 && khengEnemy.getY() < state.friend.locationY + 310 && khengEnemy.getY() > state.friend.locationY - 310)){
					if (state.friend != null) {
						khengEnemy.attack(state.locX, state.locY, state.friend.locationX, state.friend.locationY);
					}
					else
						khengEnemy.attack(state.locX,state.locY);
					g2d.drawImage(khengEnemy.getImage(), khengEnemy.getX() - XChange, khengEnemy.getY() - YChange, null);
				}
			}
			for (RepairFood repairFood : state.repairFoods) {
				if (repairFood.getX() < tankX + 1200 && repairFood.getX() > tankX - 800 && repairFood.getY() < tankY + 620 && repairFood.getY() > tankY - 620)
					g2d.drawImage(repairFood.getImage(), repairFood.getX() - XChange, repairFood.getY() - YChange, null);
			}
			for (CannonFood cannonFood : state.cannonFoods) {
				if (cannonFood.getX() < tankX + 1200 && cannonFood.getX() > tankX - 800 && cannonFood.getY() < tankY + 620 && cannonFood.getY() > tankY - 620)
					g2d.drawImage(cannonFood.getImage(), cannonFood.getX() - XChange, cannonFood.getY() - YChange, null);
			}
			for (Upgrade upgrade : state.upgrades) {
				if (upgrade.getX() < tankX + 1200 && upgrade.getX() > tankX - 800 && upgrade.getY() < tankY + 620 && upgrade.getY() > tankY - 620)
					g2d.drawImage(upgrade.getImage(), upgrade.getX() - XChange, upgrade.getY() - YChange, null);
			}
			for (GunFood gunFood : state.gunFoods) {
				if (gunFood.getX() < tankX + 1200 && gunFood.getX() > tankX - 800 && gunFood.getY() < tankY + 620 && gunFood.getY() > tankY - 620)
					g2d.drawImage(gunFood.getImage(), gunFood.getX() - XChange, gunFood.getY() - YChange, null);
			}
			for (BigEnemy bigEnemy : state.bigEnemys) {
				if ((bigEnemy.getX() < tankX + 800 && bigEnemy.getX() > tankX - 800 && bigEnemy.getY() < tankY + 620 && bigEnemy.getY() > tankY - 620) || (state.friend != null && bigEnemy.getX() < state.friend.locationX + 800 && bigEnemy.getX() > state.friend.locationX - 800 && bigEnemy.getY() < state.friend.locationY + 620 && bigEnemy.getY() > state.friend.locationY - 620)) {
					if (state.friend != null) {
						bigEnemy.attack(state.locX, state.locY, state.friend.locationX, state.friend.locationY);
					}
					else
						bigEnemy.attack(state.locX,state.locY);
					g2d.drawImage(bigEnemy.getImage(), bigEnemy.getX() - XChange, bigEnemy.getY() - YChange, null);
					int cx = bigEnemy.getPipe().getWidth() / 4;
					int cy = bigEnemy.getPipe().getHeight() / 4;
					AffineTransform oldAT = g2d.getTransform();
					g2d.translate(bigEnemy.getX() - XChange + 50, bigEnemy.getY() - YChange + 45);
					g2d.rotate(bigEnemy.angleRad);//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					g2d.translate(-cx, -cy);
					g2d.drawImage(bigEnemy.getPipe(), 0, 0, null);
					g2d.setTransform(oldAT);				
				}
			}
			for (ConstantEnemy constantEnemy : state.constantEnemys) {
				if ((constantEnemy.getX() < tankX + 800 && constantEnemy.getX() > tankX - 800 && constantEnemy.getY() < tankY + 620 && constantEnemy.getY() > tankY - 620) || (state.friend != null && constantEnemy.getX() < state.friend.locationX + 800 && constantEnemy.getX() > state.friend.locationX - 800 && constantEnemy.getY() < state.friend.locationY + 620 && constantEnemy.getY() > state.friend.locationY - 620)) {
					if (state.friend != null)
						constantEnemy.attack(GameState.locX, GameState.locY,  state.friend.locationX, state.friend.locationY);
					else
						constantEnemy.attack(GameState.locX, GameState.locY);
					//g2d.drawImage(constantEnemy.getImage(), constantEnemy.getX() - XChange, constantEnemy.getY() - YChange, null);
					int cx = constantEnemy.getImage().getWidth() / 4;
					int cy = constantEnemy.getImage().getHeight() / 4;
					AffineTransform oldAT = g2d.getTransform();
					g2d.translate(constantEnemy.getX() - XChange, constantEnemy.getY() - YChange);
					g2d.rotate(constantEnemy.angleRad,constantEnemy.getRectangle().width/2,constantEnemy.getRectangle().height/2);//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					g2d.translate(-cx, -cy);
					g2d.drawImage(constantEnemy.getImage(), 0, 0, null);
					g2d.setTransform(oldAT);
				}
			}
			for (ConstantEnemy1 constantEnemy1 : state.constantEnemys1) {
				if ((constantEnemy1.getX() < tankX + 800 && constantEnemy1.getX() > tankX - 800 && constantEnemy1.getY() < tankY + 620 && constantEnemy1.getY() > tankY - 620) || (state.friend != null && constantEnemy1.getX() < state.friend.locationX + 800 && constantEnemy1.getX() > state.friend.locationX - 800 && constantEnemy1.getY() < state.friend.locationY + 620 && constantEnemy1.getY() > state.friend.locationY - 620)) {
					if (state.friend != null)
						constantEnemy1.attack(GameState.locX, GameState.locY,  state.friend.locationX, state.friend.locationY);
					else
						constantEnemy1.attack(GameState.locX, GameState.locY);
					//g2d.drawImage(constantEnemy.getImage(), constantEnemy.getX() - XChange, constantEnemy.getY() - YChange, null);
					int cx = constantEnemy1.getImage().getWidth() / 4;
					int cy = constantEnemy1.getImage().getHeight() / 4;
					AffineTransform oldAT = g2d.getTransform();
					g2d.translate(constantEnemy1.getX() - XChange, constantEnemy1.getY() - YChange);
					g2d.rotate(constantEnemy1.angleRad,constantEnemy1.getRectangle().width/2,constantEnemy1.getRectangle().height/2d);//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					g2d.translate(-cx, -cy);
					g2d.drawImage(constantEnemy1.getImage(), 0, 0, null);
					g2d.setTransform(oldAT);
				}
			}
			for (Destroyed destroyed :state.destroyeds){
				if (destroyed.getX() < tankX + 800 && destroyed.getX() > tankX - 800 && destroyed.getY() < tankY + 620 && destroyed.getY() > tankY - 620)
					g2d.drawImage(destroyed.getImage(), destroyed.getX() - XChange, destroyed.getY() - YChange, null);
			}
			for (SmartShot smartShot : state.smartShots) {
				if (smartShot.getBulletX() < tankX + 800 && smartShot.getBulletX() > tankX - 800 && smartShot.getBulletY() < tankY + 620 && smartShot.getBulletY() > tankY - 620)
					g2d.drawImage(smartShot.getBullet(), smartShot.getBulletX() - XChange, smartShot.getBulletY() - YChange, null);
			}
			for (Life life : state.lifes) {
				if (life.getX() < tankX + 1200 && life.getX() > tankX - 800 && life.getY() < tankY + 620 && life.getY() > tankY - 620)
					g2d.drawImage(life.getImage(), life.getX() - XChange, life.getY() - YChange, null);
			}

        //drawing tank
			g2d.drawImage(state.getMyTank().getTankImage(), state.locX - XChange, state.locY - YChange, null);
        //drawing the tank pipe
			int cx = state.getMyTank().getPipe().getWidth() / 4;
			int cy = state.getMyTank().getPipe().getHeight() / 4;
			AffineTransform oldAT = g2d.getTransform();
			g2d.translate(state.locX - XChange + 50, state.locY - YChange + 45);
			g2d.rotate(state.myTankAngleRad);
			g2d.translate(-cx, -cy);
			if (state.getMyTank().cannonUp == 0)
				g2d.drawImage(state.getMyTank().getPipe(), 0, 0, null);
			else
				g2d.drawImage(state.getMyTank().getPipe(), -15, -15, null);
			g2d.setTransform(oldAT);

			//draw friend
			if (state.friend != null) {
				g2d.drawImage(state.friend.getTankImage(),state.friend.locationX - XChange,state.friend.locationY - YChange,null);
				g2d.translate(state.friend.locationX - XChange + 50, state.friend.locationY - YChange + 45);
				g2d.rotate(state.friend.angleRad);
				g2d.translate(-cx, -cy);
				g2d.drawImage(state.friend.getPipe(), 0, 0, null);
				g2d.setTransform(oldAT);
			}

			try {
				for (Shot s : state.myFires) {
					g2d.drawImage(s.getBullet(), s.getBulletX(), s.getBulletY(), null);	
				}
				for (Shot s : state.enemyFires) {
					g2d.drawImage(s.getBullet(), s.getBulletX()+50, s.getBulletY()+50, null);
				}			
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			int healthCounter = 1;
			for (int i = 0;i < state.getMyTank().getLife();i++){
				g2d.drawImage(health,healthCounter*28+500,50,null);
				healthCounter++;
			}
			int reviveCounter = 1;
			for (int i = 0;i < state.getMyTank().revive;i++){
				g2d.drawImage(medal,reviveCounter*35+1000,50,null);
				reviveCounter++;
			}
			g2d.drawImage(cannonShell, 10, 30, null);
			g2d.drawImage(gunCartridge, 10, 70, null);
			g2d.setColor(Color.WHITE);
			g2d.drawString(state.getMyTank().getCannonShell() + "", 40, 80);
			g2d.drawString(state.getMyTank().getGunCartridge() + "", 40, 130);
			// Draw GAME OVER
			if (state.gameOver) {
				String str = "GAME OVER";
				g2d.setColor(Color.WHITE);
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
				int strWidth = g2d.getFontMetrics().stringWidth(str);
				g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
			}
		}

    }
    public static void playSoundBullet(String soundName) {
    	try {
//			InputStream inputStream = getClass().getResourceAsStream("enemyshot.wav");
//			AudioStream audioStream = new AudioStream(inputStream);
//			AudioPlayer.player.start(audioStream);
			// Open an audio input stream.
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(soundName));
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();

//			URL url = GameFrame.class.getResource("enemyshot.wav");
//			AudioClip clip = Applet.newAudioClip(url);
//			clip.play();
		} catch (Exception e){
			System.out.println(e);
		}

	}
}
