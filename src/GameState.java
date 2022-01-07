import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * This class Write or read the Game from the file
 * and handle networking.
 */
public class GameState {

    public static int locX = 0, locY = 0;
    
    public boolean gameOver;
    public boolean win = false;
    public boolean finish = false;
    public boolean startMenu = true;
    public boolean gameStage = false;
    public boolean askOfOldGame = false;
    public static boolean escape = false;
    public boolean protect = false;
    public boolean PDown = false;
    public boolean RDown = false;
    public boolean ODown = false;
    // game type show the hard easy or normal game
    public Integer gameType=0;
    public Integer kind = 0;
    public Integer oldGame = 0;
    public Integer exit = 0;
    public GameServer server = null;
    public GameClient client = null;
    public String IP;

    public boolean mapEditor = false;
    public char makeMap ;
    public MyTank MYTANK = new MyTank();
    public HardWall HARDWALL = new HardWall(0,100);
	public BigEnemy BIGENEMY = new BigEnemy(0,200);
    public SoftWall SOFTWALL = new SoftWall(0,300);
    public Plant PLANT = new Plant(0,400);
    public ConstantEnemy CONSTANTENEMY = new ConstantEnemy(0,500);
    public CannonFood CANOONFOOD = new CannonFood(0,600);

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mouseDown;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    public static double myTankAngleRad;

   // private Integer changeLocation=0;
    private MyTank myTank;
    public MyTank friend = null;

    private Rectangle tankRectangle;
    public ArrayList<Soil> soils;
    public ArrayList<Teazel> teazels;
    public ArrayList<HardWall> hardWalls;
    public ArrayList<SoftWall> softWalls;
    public ArrayList<Plant> plants;
    public ArrayList<KhengEnemy> khengEnemys;
    public ArrayList<BigEnemy> bigEnemys;
    public ArrayList<ConstantEnemy> constantEnemys;
    public ArrayList<ConstantEnemy1> constantEnemys1;
    public ArrayList<RepairFood> repairFoods;
    public ArrayList<CannonFood> cannonFoods;
    public ArrayList<GunFood> gunFoods;
    public ArrayList<Destroyed> destroyeds;
    public ArrayList<Upgrade> upgrades;
    public ArrayList<Shot> myFires;
    public ArrayList<Life> lifes;
    public static ArrayList<Shot> enemyFires;
    public static ArrayList<SmartShot> smartShots;
    private Integer size = 100;
    public static int XChange;
    public static int YChange;
    private boolean heavyBullet = true;
    
    public boolean connectionLost = false;
    private long mills;
    
    public static int difficulty;//1 for easy 2 for normal 3 for hard
    
    public GameState() {
  
        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mouseDown = false;
        mouseX = 0;
        mouseY = 0;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        
        //read the map
        soils = new ArrayList<Soil>();
        teazels = new ArrayList<Teazel>();
        hardWalls = new ArrayList<HardWall>();
        softWalls = new ArrayList<SoftWall>();
        plants = new ArrayList<Plant>();
        khengEnemys = new ArrayList<KhengEnemy>();
        bigEnemys = new ArrayList<BigEnemy>();
        constantEnemys = new ArrayList<ConstantEnemy>();
        constantEnemys1 = new ArrayList<ConstantEnemy1>();
        repairFoods = new ArrayList<RepairFood>();
        cannonFoods = new ArrayList<CannonFood>();
        gunFoods = new ArrayList<GunFood>();
        myFires = new ArrayList<Shot>();
        enemyFires = new ArrayList<Shot>();
        smartShots = new ArrayList<SmartShot>();
        destroyeds = new ArrayList<Destroyed>();
        upgrades = new ArrayList<Upgrade>();
        lifes = new ArrayList<Life>();
        
    }    

    /**
     * read the map from the file and save it to the array list of the map.
     */
    public void readMap(String mapName) {
    	if (mapName.equals("0Map.txt"))
    		difficulty = 1;
    	else if (mapName.equals("1Map.txt"))
    		difficulty = 2;
    	else
    		difficulty = 3;
        //read the file
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(mapName));
            int yCounter = 0;
            while (reader.ready()) {    	
                String line = reader.readLine();
                char [] ch = line.toCharArray();
                int xCounter = 0;
                for (char c : ch) {
                	switch(c) {
                	case 'S':
                		soils.add(new Soil(xCounter * size, yCounter * size));
                		break;
                	case 'W':
                		softWalls.add(new SoftWall(xCounter * size, yCounter * size));
                		break;
                	case 'H':
                		hardWalls.add(new HardWall(xCounter * size, yCounter * size));
                		break;
                	case 'T':
                		teazels.add(new Teazel(xCounter * size, yCounter * size));
                		break;
                	case 'P':
                		plants.add(new Plant(xCounter * size, yCounter * size));
                		break;
                	case 'K':
                		khengEnemys.add(new KhengEnemy(xCounter * size, yCounter * size));
                		break;
                	case 'B':
                		bigEnemys.add(new BigEnemy(xCounter * size, yCounter * size));
                		break;
                	case 'a':
                		BigEnemy bigEnemya = new BigEnemy(xCounter * size, yCounter * size);
                		bigEnemya.repairFood = true;
                		bigEnemys.add(bigEnemya);
                		break;
                	case 'b':
                		BigEnemy bigEnemyb = new BigEnemy(xCounter * size, yCounter * size);
                		bigEnemyb.cannonFood = true;
                		bigEnemys.add(bigEnemyb);
                		break;
                	case 'c':
                		BigEnemy bigEnemyc = new BigEnemy(xCounter * size, yCounter * size);
                		bigEnemyc.gunFood = true;
                		bigEnemys.add(bigEnemyc);
                		break;
                	case 'd':
                		BigEnemy bigEnemyd = new BigEnemy(xCounter * size, yCounter * size);
                		bigEnemyd.upgrade = true;
                		bigEnemys.add(bigEnemyd);
                		break;
                	case 'e':
                		BigEnemy bigEnemye = new BigEnemy(xCounter * size, yCounter * size);
                		bigEnemye.extraExistence = true;
                		bigEnemys.add(bigEnemye);
                		break;              		
                	case 'C':
                		constantEnemys.add(new ConstantEnemy(xCounter * size, yCounter * size));
                		break;
                	case 'f':
                		ConstantEnemy constantEnemyf = new ConstantEnemy(xCounter * size, yCounter * size);
                		constantEnemyf.repairFood = true;
                		constantEnemys.add(constantEnemyf);
                		break;
                	case 'g':
                		ConstantEnemy constantEnemyg = new ConstantEnemy(xCounter * size, yCounter * size);
                		constantEnemyg.cannonFood = true;
                		constantEnemys.add(constantEnemyg);
                		break;
                	case 'h':
                		ConstantEnemy constantEnemyh = new ConstantEnemy(xCounter * size, yCounter * size);
                		constantEnemyh.gunFood = true;
                		constantEnemys.add(constantEnemyh);
                		break;
                	case 'i':
                		ConstantEnemy constantEnemyi = new ConstantEnemy(xCounter * size, yCounter * size);
                		constantEnemyi.upgrade = true;
                		constantEnemys.add(constantEnemyi);
                		break;
                	case 'j':
                		ConstantEnemy constantEnemyj = new ConstantEnemy(xCounter * size, yCounter * size);
                		constantEnemyj.extraExistence = true;
                		constantEnemys.add(constantEnemyj);
                		break;
                	case 'O':
                		constantEnemys1.add(new ConstantEnemy1(xCounter * size, yCounter * size));
                		break;
                	case 'k':
                		ConstantEnemy1 constantEnemy1k = new ConstantEnemy1(xCounter * size, yCounter * size);
                		constantEnemy1k.repairFood = true;
                		constantEnemys1.add(constantEnemy1k);
                		break;
                	case 'l':
                		ConstantEnemy1 constantEnemy1l = new ConstantEnemy1(xCounter * size, yCounter * size);
                		constantEnemy1l.cannonFood = true;
                		constantEnemys1.add(constantEnemy1l);
                		break;
                	case 'm':
                		ConstantEnemy1 constantEnemy1m = new ConstantEnemy1(xCounter * size, yCounter * size);
                		constantEnemy1m.gunFood = true;
                		constantEnemys1.add(constantEnemy1m);
                		break;
                	case 'n':
                		ConstantEnemy1 constantEnemy1n = new ConstantEnemy1(xCounter * size, yCounter * size);
                		constantEnemy1n.upgrade = true;
                		constantEnemys1.add(constantEnemy1n);
                		break;
                	case 'o':
                		ConstantEnemy1 constantEnemy1o = new ConstantEnemy1(xCounter * size, yCounter * size);
                		constantEnemy1o.extraExistence = true;
                		constantEnemys1.add(constantEnemy1o);
                		break;
                	case 'R':
                		repairFoods.add(new RepairFood(xCounter * size, yCounter * size));
                		break;
                	case 'A':
                		cannonFoods.add(new CannonFood(xCounter * size, yCounter * size));
                		break;
                	case 'G':
                		gunFoods.add(new GunFood(xCounter * size, yCounter * size));
                		break;
                	case 'M':
                		myTank = new MyTank();
                		tankRectangle = new Rectangle();
                		locX = xCounter * size;
                		locY = yCounter * size;
                		break;
                	case 'D':
                        destroyeds.add(new Destroyed(xCounter *size ,yCounter*size));
                        break;
                	case 'U':
                		upgrades.add(new Upgrade(xCounter *size ,yCounter*size));
                		break;
					case 'L':
						lifes.add(new Life(xCounter *size ,yCounter*size));
						break;
                	}
                	xCounter ++;
                }
                yCounter ++;
            }
        } catch (IOException ex) {
            System.out.println("error in reading th file");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                System.out.println("error in reading the map");
            }
        }

    }
    
    public void writeToSavedFile() {
    	File file = new File("Saved.txt");
    	FileWriter delete;
    	try {
    		delete = new FileWriter(file, false);
    		delete.write("");
    		delete.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	BufferedWriter bufferedWriter = null;
    	try {
			bufferedWriter = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	for (Soil soil : soils) {
    		try {
				bufferedWriter.write("S");
				bufferedWriter.newLine();
				//BUFFERED WRITER DOESN'T WRITE INTEGERS TO FILE
				bufferedWriter.write(soil.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(soil.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (Teazel teazel : teazels) {
    		try {
				bufferedWriter.write("T");
				bufferedWriter.newLine();
				bufferedWriter.write(teazel.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(teazel.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (HardWall hardWall : hardWalls) {
    		try {
				bufferedWriter.write("H");
				bufferedWriter.newLine();
				bufferedWriter.write(hardWall.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(hardWall.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (SoftWall softWall : softWalls) {
    		try {
				bufferedWriter.write("W");
				bufferedWriter.newLine();
				bufferedWriter.write(softWall.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(softWall.getY() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(softWall.getDestruction() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (Plant plant : plants) {
    		try {
				bufferedWriter.write("P");
				bufferedWriter.newLine();
				bufferedWriter.write(plant.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(plant.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (KhengEnemy khengEnemy : khengEnemys) {
    		try {
				bufferedWriter.write("K");
				bufferedWriter.newLine();
				bufferedWriter.write(khengEnemy.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(khengEnemy.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (BigEnemy bigEnemy : bigEnemys) {
    		try {
    			if (bigEnemy.repairFood)
    				bufferedWriter.write("a");
    			else if (bigEnemy.cannonFood)
    				bufferedWriter.write("b");
    			else if (bigEnemy.gunFood)
    				bufferedWriter.write("c");
    			else if (bigEnemy.upgrade)
    				bufferedWriter.write("d");
    			else if (bigEnemy.extraExistence)
    				bufferedWriter.write("e");
    			else
    				bufferedWriter.write("B");
				bufferedWriter.newLine();
				bufferedWriter.write(bigEnemy.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(bigEnemy.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (ConstantEnemy constantEnemy : constantEnemys) {
    		try {
    			if (constantEnemy.repairFood)
    				bufferedWriter.write("f");
    			else if (constantEnemy.cannonFood)
    				bufferedWriter.write("g");
    			else if (constantEnemy.gunFood)
    				bufferedWriter.write("h");
    			else if (constantEnemy.upgrade)
    				bufferedWriter.write("i");
    			else if (constantEnemy.extraExistence)
    				bufferedWriter.write("j");
    			else
    				bufferedWriter.write("C");
				bufferedWriter.newLine();
				bufferedWriter.write(constantEnemy.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(constantEnemy.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (ConstantEnemy1 constantEnemy1 : constantEnemys1) {
    		try {
    			if (constantEnemy1.repairFood)
				bufferedWriter.write("k");
			else if (constantEnemy1.cannonFood)
				bufferedWriter.write("l");
			else if (constantEnemy1.gunFood)
				bufferedWriter.write("m");
			else if (constantEnemy1.upgrade)
				bufferedWriter.write("n");
			else if (constantEnemy1.extraExistence)
				bufferedWriter.write("o");
    			else
    				bufferedWriter.write("O");
				bufferedWriter.newLine();
				bufferedWriter.write(constantEnemy1.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(constantEnemy1.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (RepairFood repairFood : repairFoods) {
    		try {
				bufferedWriter.write("R");
				bufferedWriter.newLine();
				bufferedWriter.write(repairFood.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(repairFood.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (CannonFood cannonFood : cannonFoods) {
    		try {
				bufferedWriter.write("A");
				bufferedWriter.newLine();
				bufferedWriter.write(cannonFood.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(cannonFood.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (Upgrade upgrade :upgrades) {
    		try {
				bufferedWriter.write("U");
				bufferedWriter.newLine();
				bufferedWriter.write(upgrade.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(upgrade.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (GunFood gunFood : gunFoods) {
    		try {
				bufferedWriter.write("G");
				bufferedWriter.newLine();
				bufferedWriter.write(gunFood.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(gunFood.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (Destroyed destroyed : destroyeds) {
    		try {
				bufferedWriter.write("D");
				bufferedWriter.newLine();
				bufferedWriter.write(destroyed.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(destroyed.getY() + "");
				bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	for (Life life :lifes){
    		try {
				bufferedWriter.write("L");
				bufferedWriter.newLine();
				bufferedWriter.write(life.getX() + "");
				bufferedWriter.newLine();
				bufferedWriter.write(life.getY() + "");
				bufferedWriter.newLine();
			}catch (IOException e){
    			e.printStackTrace();
			}
		}
    	try {
			bufferedWriter.write("M");
			bufferedWriter.newLine();
			bufferedWriter.write(myTank.locationX + "");
			bufferedWriter.newLine();
			bufferedWriter.write(myTank.locationY + "");
			bufferedWriter.newLine();
			
			bufferedWriter.write("life");
			bufferedWriter.newLine();
			bufferedWriter.write(myTank.getLife()+"");
			bufferedWriter.newLine();

			bufferedWriter.write("bullet1");
			bufferedWriter.newLine();
			bufferedWriter.write(myTank.getCannonShell()+"");
			bufferedWriter.newLine();

			bufferedWriter.write("bullet2");
			bufferedWriter.newLine();
			bufferedWriter.write(myTank.getGunCartridge()+"");
			bufferedWriter.newLine();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	try {
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    public void readFromSavedFile() {
    	File file = new File("Saved.txt");
    	BufferedReader bufferedReader = null;
    	try {
			bufferedReader = new BufferedReader(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	 try {
			while (bufferedReader.ready()) {    	
			     String line = bufferedReader.readLine();
			     switch(line) {
			  	case "S":
			  		soils.add(new Soil(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "W":
			  		SoftWall softWall = new SoftWall(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		int destruction = Integer.parseInt(bufferedReader.readLine());
			  		for (int i = 0;i < destruction;i++) {
			  			softWall.ruin();
			  		}
			  		softWalls.add(softWall);
			  		break;
			  	case "H":
			  		hardWalls.add(new HardWall(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "T":
			  		teazels.add(new Teazel(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "P":
			  		plants.add(new Plant(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "K":
			  		khengEnemys.add(new KhengEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
				case "b":
			  		BigEnemy bigEnemyb = new BigEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		bigEnemyb.cannonFood = true;
			  		bigEnemys.add(bigEnemyb);
			  		break;
			  	case "c":
			  		BigEnemy bigEnemyc = new BigEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		bigEnemyc.gunFood = true;
			  		bigEnemys.add(bigEnemyc);
			  		break;
			  	case "d":
			  		BigEnemy bigEnemyd = new BigEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		bigEnemyd.upgrade = true;
			  		bigEnemys.add(bigEnemyd);
			  		break;
			  	case "e":
			  		BigEnemy bigEnemye = new BigEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		bigEnemye.extraExistence = true;
			  		bigEnemys.add(bigEnemye);
			  		break;
			  	case "B":
			  		bigEnemys.add(new BigEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "f":
			  		ConstantEnemy constantEnemyf = new ConstantEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemyf.repairFood = true;
			  		constantEnemys.add(constantEnemyf);
			  		break;
			  	case "g":
			  		ConstantEnemy constantEnemyg = new ConstantEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemyg.cannonFood = true;
			  		constantEnemys.add(constantEnemyg);
			  		break;
			  	case "h":
			  		ConstantEnemy constantEnemyh = new ConstantEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemyh.gunFood = true;
			  		constantEnemys.add(constantEnemyh);
			  		break;
			  	case "i":
			  		ConstantEnemy constantEnemyi = new ConstantEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemyi.upgrade = true;
			  		constantEnemys.add(constantEnemyi);
			  		break;
			  	case "j":
			  		ConstantEnemy constantEnemyj = new ConstantEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemyj.extraExistence = true;
			  		constantEnemys.add(constantEnemyj);
			  		break;
			  	case "C":
			  		constantEnemys.add(new ConstantEnemy(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "O":
			  		constantEnemys1.add(new ConstantEnemy1(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "k":
			  		ConstantEnemy1 constantEnemy1k = new ConstantEnemy1(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemy1k.repairFood = true;
			  		constantEnemys1.add(constantEnemy1k);
			  		break;
			  	case "l":
			  		ConstantEnemy1 constantEnemy1l = new ConstantEnemy1(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemy1l.cannonFood = true;
			  		constantEnemys1.add(constantEnemy1l);
			  		break;
			  	case "m":
			  		ConstantEnemy1 constantEnemy1m = new ConstantEnemy1(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemy1m.gunFood = true;
			  		constantEnemys1.add(constantEnemy1m);
			  		break;
			  	case "n":
			  		ConstantEnemy1 constantEnemy1n = new ConstantEnemy1(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemy1n.upgrade = true;
			  		constantEnemys1.add(constantEnemy1n);
			  		break;
			  	case "o":
			  		ConstantEnemy1 constantEnemy1o = new ConstantEnemy1(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine()));
			  		constantEnemy1o.extraExistence = true;
			  		constantEnemys1.add(constantEnemy1o);
			  		break;
			  	case "R":
			  		repairFoods.add(new RepairFood(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "A":
			  		cannonFoods.add(new CannonFood(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "U":
			  		upgrades.add(new Upgrade(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "G":
			  		gunFoods.add(new GunFood(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
			  		break;
			  	case "D":
			          destroyeds.add(new Destroyed(Integer.parseInt(bufferedReader.readLine()) ,Integer.parseInt(bufferedReader.readLine())));
			          break;
			  	case "M":
			  		myTank = new MyTank();
            		tankRectangle = new Rectangle();
            		locX = Integer.parseInt(bufferedReader.readLine());
            		locY = Integer.parseInt(bufferedReader.readLine());
            		break;
			  	case "life":
					myTank.setLife(Integer.parseInt(bufferedReader.readLine()));
					break;
				case "bullet1":
					myTank.setCannonShell(Integer.parseInt(bufferedReader.readLine()));
				 	break;
				case "bullet2":
					 myTank.setGunCartridge(Integer.parseInt(bufferedReader.readLine()));
					 break;
				case "L":
					 lifes.add(new Life(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
					 break;

			  	}
			 }
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

    }
    
    public void clearGame(){
		soils = new ArrayList<Soil>();
		teazels = new ArrayList<Teazel>();
		hardWalls = new ArrayList<HardWall>();
		softWalls = new ArrayList<SoftWall>();
		plants = new ArrayList<Plant>();
		khengEnemys = new ArrayList<KhengEnemy>();
		bigEnemys = new ArrayList<BigEnemy>();
		constantEnemys = new ArrayList<ConstantEnemy>();
		constantEnemys1 = new ArrayList<ConstantEnemy1>();
		repairFoods = new ArrayList<RepairFood>();
		cannonFoods = new ArrayList<CannonFood>();
		gunFoods = new ArrayList<GunFood>();
		myFires = new ArrayList<Shot>();
		enemyFires = new ArrayList<Shot>();
		smartShots = new ArrayList<SmartShot>();
		destroyeds = new ArrayList<Destroyed>();
		upgrades = new ArrayList<Upgrade>();
		lifes = new ArrayList<Life>();
	}

    
    /**
     * The method which updates the game state.
     */
    public void update() {
    	if (!startMenu && constantEnemys1.size() == 0 && constantEnemys.size() == 0 && bigEnemys.size() == 0){
    		win = true;
		}

    	else if (!startMenu && !escape && !connectionLost){
            if (server != null) {
                friend.locationX = server.locationX;
                System.out.println(friend.locationX);
                friend.locationY = server.locationY;
                System.out.println(friend.locationY);
                friend.angleRad = server.angleRad;
                if (server.changePipe) {
                	friend.changeGun();
                	server.changePipe = false;
                }
            }
            
            else if (client!= null) {
            	friend.locationX = client.locationX;
                friend.locationY = client.locationY;
                friend.angleRad = client.angleRad;
                if (client.changePipe) {
                	friend.changeGun();
                	client.changePipe = false;
                }
            }
            if (PDown && RDown && ODown)
            	protect = true;
           // changeLocation =0;

            int dx = 0;
            int dy = 0;

            if (keyUP)
                dy = -8;
            if (keyDOWN)
                dy = 8;
            if (keyLEFT)
                dx = -8;
            if (keyRIGHT)
                dx = 8;

            tankRectangle.setBounds(locX - XChange + dx, locY - YChange + dy, myTank.getTankImage().getWidth(), myTank.getTankImage().getHeight());

            boolean collision = false;
            //the shots should be removed from this array as soon as they intersect with another object don't (don't care what)
            Iterator<Shot> myFiresIt = myFires.iterator();
            while (myFiresIt.hasNext()) {
            	Shot s = myFiresIt.next();
            	s.move();
            	if(!s.isAlive) {
                    myFiresIt.remove();
                }
            }
            Iterator<Shot> enemyFiresIt = enemyFires.iterator();
            while (enemyFiresIt.hasNext()) {
            	Shot s = enemyFiresIt.next();
            	s.move();
            	if(!s.isAlive) {
            		enemyFiresIt.remove();
                }
            }
            Iterator<SmartShot> smartShotIt = smartShots.iterator();
            while (smartShotIt.hasNext()) {
            	SmartShot s = smartShotIt.next();
            	if (friend == null) 
                	s.move(locX, locY);
            	else
            		s.move(locX, locY, friend.locationX, friend.locationY);
            }
            try {//I had an exception on the below line and just when I wanna go to next leve
            	for (HardWall hardWall : hardWalls) {
                    Rectangle hardWallRectangle = new Rectangle(hardWall.getX() - XChange, hardWall.getY() - YChange, hardWall.getImage().getWidth(), hardWall.getImage().getHeight());
                    if (tankRectangle.intersects(hardWallRectangle)) {
                        dx /= 2;
                        dy /= 2;
                        tankRectangle.setBounds(locX - XChange + dx, locY - YChange + dy, myTank.getTankImage().getWidth(), myTank.getTankImage().getHeight());
                        if (tankRectangle.intersects(hardWallRectangle))
                            collision = true;
                    }
                    Iterator<Shot> myFiresIterator = myFires.iterator();
                    while (myFiresIterator.hasNext()) {
                    	try {
                    		 Shot s = myFiresIterator.next();
                             if (s.getRectangle().intersects(hardWallRectangle))
                             	myFiresIterator.remove();
                    	}
                    	catch(Exception e) { 
                    		e.printStackTrace();
                    		//ASK????????????????????????????????
                    		break;//sometimes we have an exception while getting (myFiresIterator.next()) I don't know the reason but we should pass or the screen goes white
                    	}
                    }
                    Iterator<Shot> enemyFiresIterator = enemyFires.iterator();
                    while (enemyFiresIterator.hasNext()) {
                        Shot s = enemyFiresIterator.next();
                        if (s.getRectangle().intersects(hardWallRectangle)) {
                            enemyFiresIterator.remove();
                        }
                        else if (s.getRectangle().intersects(tankRectangle)) {
                            if (!protect)
                            	myTank.decreaseLife();
                            enemyFiresIterator.remove();
                            if (myTank.revive==0 && myTank.getLife() == 0){
                            	gameOver = true;
    						}else if (myTank.revive !=0 && myTank.getLife() == 0){
                            	myTank.revive();
    						}

                        }
                    }
                }
            }
            catch(Exception e) {
            	e.printStackTrace();
            }
            
            Iterator<SoftWall> softWallIterator = softWalls.iterator();
            int wallCounter = 0;
            while (softWallIterator.hasNext()) {
                SoftWall softWall = softWallIterator.next();
                Rectangle softWallRectangle = new Rectangle(softWall.getX() - XChange, softWall.getY() - YChange, softWall.getImage().getWidth(), softWall.getImage().getHeight());
                if (tankRectangle.intersects(softWallRectangle)) {
                    dx /= 2;
                    dy /= 2;
                    tankRectangle.setBounds(locX - XChange + dx, locY - YChange + dy, myTank.getTankImage().getWidth(), myTank.getTankImage().getHeight());
                    if (tankRectangle.intersects(softWallRectangle))
                        collision = true;
                }
                if (server != null) {
                	if (server.getWallNum.containsKey(wallCounter)) {
                		for (int i = 0;i < server.getWallNum.get(wallCounter);i++) {//if there is a number of wall gotten before
							if (softWall.ruin()) {
	                            softWallIterator.remove();
	                            break;
							}
						}
                		server.getWallNum.put(wallCounter, 0);
                	}
                }
                else if (client != null) {
                	if (client.getWallNum.containsKey(wallCounter)) {
                		for (int i = 0;i < client.getWallNum.get(wallCounter);i++) {//if there is a number of wall gotten before
							if (softWall.ruin()) {
	                            softWallIterator.remove();
	                            break;
							}
						}
                		client.getWallNum.put(wallCounter, 0);
                	}
                }
                
                Iterator<Shot> myFiresIterator = myFires.iterator();
                while (myFiresIterator.hasNext()) {
                	try {
                		 Shot s = myFiresIterator.next();
                         if (s.getRectangle().intersects(softWallRectangle)) {
                             myFiresIterator.remove();
                             if (s.heavyBullet) {
                            	 if (server != null) {
                                  	String sendWallNum = wallCounter + "\n";
                                  	server.sendWall(sendWallNum);
                                  }
                                  else if(client != null) {
                                  	String sendWallNum = wallCounter + "\n";
                                  	client.sendWall(sendWallNum);
                                  }
                            	 if (softWall.ruin())
                                     softWallIterator.remove();
                             }
                             else {
                            	 if (softWall.hit(myTank.cartridgeUp)) {
                            		 if (server != null) {
                                       	String sendWallNum = wallCounter + "\n";
                                       	server.sendWall(sendWallNum);
                                       }
                                       else if(client != null) {
                                       	String sendWallNum = wallCounter + "\n";
                                       	client.sendWall(sendWallNum);
                                       }
                                 	 if (softWall.ruin())
                                          softWallIterator.remove();
                            	 }
                             }
                         }
                	}
                	catch(Exception e) {   
                		e.printStackTrace();
                		//ANY GRADE GOOD OR BAD I HAVE TO KNOW THE PROBLEM AND I'M GONNA ASK 
                		break;//sometimes we have an exception while getting (myFiresIterator.next()) I don't know the exception but we should pass or the screen goes white
                	}
                }
                Iterator<Shot> enemyFiresIterator = enemyFires.iterator();
                while (enemyFiresIterator.hasNext()) {
                    Shot s = enemyFiresIterator.next();
                    if (s.getRectangle().intersects(softWallRectangle)) {
                        enemyFiresIterator.remove();
                        GameFrame.playSoundBullet("softwall.wav");
                        if (server != null) {
                        	String sendWallNum = wallCounter + "\n";
                        	server.sendWall(sendWallNum);
                        }
                        else if(client != null) {
                        	String sendWallNum = wallCounter + "\n";
                        	client.sendWall(sendWallNum);
                        }
                        if (softWall.ruin())
                            softWallIterator.remove();
                    }
                }
                wallCounter++;
            }
            Iterator<Teazel> teazelIterator = teazels.iterator();
            while (teazelIterator.hasNext()) {
                Teazel teazel = teazelIterator.next();
                if (tankRectangle.intersects(teazel.getRectangle())) {
                    dx /= 2;
                    dy /= 2;
                    tankRectangle.setBounds(locX - XChange + dx, locY - YChange + dy, myTank.getTankImage().getWidth(), myTank.getTankImage().getHeight());
                    if (tankRectangle.intersects(teazel.getRectangle()))
                        collision = true;
                }
            }
            Iterator <KhengEnemy> khengEnemyIterator = khengEnemys.iterator();
            int khengEnemyCounter = 0;
            while (khengEnemyIterator.hasNext()) {
                KhengEnemy khengEnemy = khengEnemyIterator.next();
                if (server != null) {
                	if (khengEnemyCounter == server.getKhengEnemyNum) {
                		destroyeds.add(new Destroyed(khengEnemy.getX(),khengEnemy.getY()));
                        khengEnemyIterator.remove();
                		server.getKhengEnemyNum = -1;
                	}
                }
                else if (client != null)
                	if (khengEnemyCounter == client.getKhengEnemyNum) {
                		destroyeds.add(new Destroyed(khengEnemy.getX(),khengEnemy.getY()));
                		khengEnemyIterator.remove();
                		client.getKhengEnemyNum = -1;
                	}
                khengEnemy.setCollision(false);
                if (tankRectangle.intersects(khengEnemy.getRectangle())) {
                	destroyeds.add(new Destroyed(khengEnemy.getX(),khengEnemy.getY()));
                    khengEnemyIterator.remove();
                    if (server != null) {
                    	String sendKhengEnemyNum = khengEnemyCounter + "\n";
                    	server.sendKhengEnemy(sendKhengEnemyNum);
                    }
                    else if(client != null) {
                    	String sendKhengEnemyNum = khengEnemyCounter + "\n";
                    	client.sendKhengEnemy(sendKhengEnemyNum);
                    }
                    if (!protect)
                    	myTank.decreaseLife();
                    if (myTank.revive==0 && myTank.getLife() == 0){
                    	gameOver = true;
					}else if (myTank.revive !=0 && myTank.getLife() == 0){
                    	myTank.revive();
					}

                }
                for (HardWall hardWall : hardWalls) {
                    if (khengEnemy.getRectangle().intersects(hardWall.getRectangle())) {
                        khengEnemy.setCollision(true);
                        if (!khengEnemy.moveHorizentalRectangle().intersects(hardWall.getRectangle()))
                        	khengEnemy.moveHorizentally();
                        else if (!khengEnemy.moveVerticalRectangle().intersects(hardWall.getRectangle()))
                        	khengEnemy.moveVertically();
                    }
                }
                for (SoftWall softWall : softWalls) {
                    if (khengEnemy.getRectangle().intersects(softWall.getRectangle())) {
                        khengEnemy.setCollision(true);
                        if (!khengEnemy.moveHorizentalRectangle().intersects(softWall.getRectangle()))
                        	khengEnemy.moveHorizentally();
                        else if (!khengEnemy.moveVerticalRectangle().intersects(softWall.getRectangle()))
                        	khengEnemy.moveVertically();
                    }
                }
                for (Teazel teazel :teazels) {
                    if (khengEnemy.getRectangle().intersects(teazel.getRectangle())) {
                        khengEnemy.setCollision(true);
                        if (!khengEnemy.moveHorizentalRectangle().intersects(teazel.getRectangle()))
                        	khengEnemy.moveHorizentally();
                        else if (!khengEnemy.moveVerticalRectangle().intersects(teazel.getRectangle()))
                        	khengEnemy.moveVertically();
                    }
                }
                Iterator<Shot> myFiresIterator = myFires.iterator();
                while (myFiresIterator.hasNext()) {
                    Shot s = myFiresIterator.next();
                    if (s.getRectangle().intersects(khengEnemy.getRectangle())) {
                    	myFiresIterator.remove();
                    	if (s.heavyBullet) {
                    		 try {                      	
                                 destroyeds.add(new Destroyed(khengEnemy.getX(),khengEnemy.getY()));
                             	khengEnemyIterator.remove();
                             }
                             catch(Exception e) {
                            	 e.printStackTrace();
                             }
                             if (server != null) {
                             	String sendKhengEnemyNum = khengEnemyCounter + "\n";
                             	server.sendKhengEnemy(sendKhengEnemyNum);
                             }
                             else if(client != null) {
                             	String sendKhengEnemyNum = khengEnemyCounter + "\n";
                             	client.sendKhengEnemy(sendKhengEnemyNum);
                             }
                    	}
                    	else {
                    		if (khengEnemy.hit(myTank.cartridgeUp)) {
                    			try {                      	
                                    destroyeds.add(new Destroyed(khengEnemy.getX(),khengEnemy.getY()));
                                	khengEnemyIterator.remove();
                                }
                                catch(Exception e) {
                                	e.printStackTrace();
                                }
                                if (server != null) {
                                	String sendKhengEnemyNum = khengEnemyCounter + "\n";
                                	server.sendKhengEnemy(sendKhengEnemyNum);
                                }
                                else if(client != null) {
                                	String sendKhengEnemyNum = khengEnemyCounter + "\n";
                                	client.sendKhengEnemy(sendKhengEnemyNum);
                                }
                    		}
                    	}
                    }
                }
                khengEnemyCounter++;
            }
            Iterator <BigEnemy> bigEnemyIterator = bigEnemys.iterator();
            int bigEnemyCounter = 0;
            while (bigEnemyIterator.hasNext()) {
                BigEnemy bigEnemy = bigEnemyIterator.next();
                if (server != null) {
                	if (bigEnemyCounter == server.getBigEnemyNum) {
                		if (bigEnemy.repairFood)
                			repairFoods.add(new RepairFood(bigEnemy.getX(),bigEnemy.getY()));
                		else if (bigEnemy.cannonFood)
                			cannonFoods.add(new CannonFood(bigEnemy.getX(),bigEnemy.getY()));
                		else if (bigEnemy.gunFood)
                			gunFoods.add(new GunFood(bigEnemy.getX(),bigEnemy.getY()));
                		else if (bigEnemy.upgrade)
                			upgrades.add(new Upgrade(bigEnemy.getX(),bigEnemy.getY()));
                		else if (bigEnemy.extraExistence)
                			lifes.add(new Life(bigEnemy.getX(),bigEnemy.getY()));
                		else
                			destroyeds.add(new Destroyed(bigEnemy.getX(),bigEnemy.getY()));
                        bigEnemyIterator.remove();
                		server.getBigEnemyNum = -1;
                	}
                }
                else if (client != null)
                	if (bigEnemyCounter == client.getBigEnemyNum) {
                		if (bigEnemy.repairFood)
                			repairFoods.add(new RepairFood(bigEnemy.getX(),bigEnemy.getY()));
                		else if (bigEnemy.cannonFood)
                			cannonFoods.add(new CannonFood(bigEnemy.getX(),bigEnemy.getY()));
                		else if (bigEnemy.gunFood)
                			gunFoods.add(new GunFood(bigEnemy.getX(),bigEnemy.getY()));
                		else if (bigEnemy.upgrade)
                			upgrades.add(new Upgrade(bigEnemy.getX(),bigEnemy.getY()));
                		else if (bigEnemy.extraExistence)
                			lifes.add(new Life(bigEnemy.getX(),bigEnemy.getY()));
                		else
                			destroyeds.add(new Destroyed(bigEnemy.getX(),bigEnemy.getY()));
                        bigEnemyIterator.remove();
                		client.getBigEnemyNum = -1;
                	}

                bigEnemy.setCollision(false);
                if (tankRectangle.intersects(bigEnemy.getRectangle())) {
                    collision = true;
                    bigEnemy.setCollision(true);
                }
                for (HardWall hardWall : hardWalls) {
                    if (bigEnemy.getRectangle().intersects(hardWall.getRectangle()))
                        bigEnemy.setCollision(true);
                }
                for (SoftWall softWall : softWalls) {
                    if (bigEnemy.getRectangle().intersects(softWall.getRectangle()))
                        bigEnemy.setCollision(true);
                }
                for (Teazel teazel :teazels) {
                    if (bigEnemy.getRectangle().intersects(teazel.getRectangle()))
                        bigEnemy.setCollision(true);
                }
                Iterator<Shot> myFiresIterator = myFires.iterator();
                while (myFiresIterator.hasNext()) {
                	try {
                		Shot s = myFiresIterator.next();
                		if (s.getRectangle().intersects(bigEnemy.getRectangle())) {
                            myFiresIterator.remove();
                            if (s.heavyBullet) {
                            	if (bigEnemy.repairFood)
                        			repairFoods.add(new RepairFood(bigEnemy.getX(),bigEnemy.getY()));
                        		else if (bigEnemy.cannonFood)
                        			cannonFoods.add(new CannonFood(bigEnemy.getX(),bigEnemy.getY()));
                        		else if (bigEnemy.gunFood)
                        			gunFoods.add(new GunFood(bigEnemy.getX(),bigEnemy.getY()));
                        		else if (bigEnemy.upgrade)
                        			upgrades.add(new Upgrade(bigEnemy.getX(),bigEnemy.getY()));
                        		else if (bigEnemy.extraExistence)
                        			lifes.add(new Life(bigEnemy.getX(),bigEnemy.getY()));
                        		else
                        			destroyeds.add(new Destroyed(bigEnemy.getX(),bigEnemy.getY()));
                                
                                bigEnemyIterator.remove();
                                
                                if (server != null) {
                                	String sendBigEnemyNum = bigEnemyCounter + "\n";
                                	server.sendBigEnemy(sendBigEnemyNum);
                                }
                                else if(client != null) {
                                	String sendBigEnemyNum = bigEnemyCounter + "\n";
                                	client.sendBigEnemy(sendBigEnemyNum);
                                }
                            }
                            else {
                            	if (bigEnemy.hit(myTank.cartridgeUp)) {
                            		if (bigEnemy.repairFood)
                            			repairFoods.add(new RepairFood(bigEnemy.getX(),bigEnemy.getY()));
                            		else if (bigEnemy.cannonFood)
                            			cannonFoods.add(new CannonFood(bigEnemy.getX(),bigEnemy.getY()));
                            		else if (bigEnemy.gunFood)
                            			gunFoods.add(new GunFood(bigEnemy.getX(),bigEnemy.getY()));
                            		else if (bigEnemy.upgrade)
                            			upgrades.add(new Upgrade(bigEnemy.getX(),bigEnemy.getY()));
                            		else if (bigEnemy.extraExistence)
                            			lifes.add(new Life(bigEnemy.getX(),bigEnemy.getY()));
                            		else
                            			destroyeds.add(new Destroyed(bigEnemy.getX(),bigEnemy.getY()));
                                    
                                    bigEnemyIterator.remove();
                                    
                                    if (server != null) {
                                    	String sendBigEnemyNum = bigEnemyCounter + "\n";
                                    	server.sendBigEnemy(sendBigEnemyNum);
                                    }
                                    else if(client != null) {
                                    	String sendBigEnemyNum = bigEnemyCounter + "\n";
                                    	client.sendBigEnemy(sendBigEnemyNum);
                                    }
                            	}
                            }
                        }
                	}
                	catch(Exception e) {   
                		e.printStackTrace();
                	}
                }
                bigEnemyCounter++;
            }
            Iterator <ConstantEnemy> constantEnemyIterator = constantEnemys.iterator();
            int constantEnemyCounter = 0;
            while (constantEnemyIterator.hasNext()) {
                ConstantEnemy constantEnemy = constantEnemyIterator.next();
                if (server != null) {
                	if (server.getConstantEnemyNum.containsKey(constantEnemyCounter)) {
                		for (int i = 0;i < server.getConstantEnemyNum.get(constantEnemyCounter);i++) {//if there is a number of wall gotten before
                			constantEnemy.kill();
                			if (!constantEnemy.isAlive())
                				break;
						}
                		server.getConstantEnemyNum.put(constantEnemyCounter, 0);
                	}
                }
                else if (client != null)
                	if (client.getConstantEnemyNum.containsKey(constantEnemyCounter)) {
                		for (int i = 0;i < client.getConstantEnemyNum.get(constantEnemyCounter);i++) {//if there is a number of wall gotten before
                			constantEnemy.kill();
                			if (!constantEnemy.isAlive())
                				break;
						}
                		client.getConstantEnemyNum.put(constantEnemyCounter, 0);
                	}
                if (tankRectangle.intersects(constantEnemy.getRectangle()))
                    collision = true;
                Iterator<Shot> myFiresIterator = myFires.iterator();
                while (myFiresIterator.hasNext()) {
                    Shot s = myFiresIterator.next();
                    if (s.getRectangle().intersects(constantEnemy.getRectangle())) {
                        myFiresIterator.remove();
                        if (s.heavyBullet) {
                        	constantEnemy.kill();
                            if (server != null) {
                            	String sendConstantEnemyNum = constantEnemyCounter + "\n";
                            	server.sendConstantEnemy(sendConstantEnemyNum);
                            }
                            else if(client != null) {
                            	String sendConstantEnemyNum = constantEnemyCounter + "\n";
                            	client.sendConstantEnemy(sendConstantEnemyNum);
                            }
                        }
                        else {
                        	if (constantEnemy.hit(myTank.cartridgeUp)) {
                        		constantEnemy.kill();
                                if (server != null) {
                                	String sendConstantEnemyNum = constantEnemyCounter + "\n";
                                	server.sendConstantEnemy(sendConstantEnemyNum);
                                }
                                else if(client != null) {
                                	String sendConstantEnemyNum = constantEnemyCounter + "\n";
                                	client.sendConstantEnemy(sendConstantEnemyNum);
                                }
                        	}
                        }
                    }
                }
                if (!constantEnemy.isAlive()) {
                    constantEnemyIterator.remove();
                    if (constantEnemy.repairFood)
                    	repairFoods.add(new RepairFood(constantEnemy.getX(),constantEnemy.getY()));
                    else if (constantEnemy.cannonFood)
                    	cannonFoods.add(new CannonFood(constantEnemy.getX(),constantEnemy.getY()));
                    else if (constantEnemy.gunFood)
                    	gunFoods.add(new GunFood(constantEnemy.getX(),constantEnemy.getY()));
                    else if (constantEnemy.upgrade)
                    	upgrades.add(new Upgrade(constantEnemy.getX(),constantEnemy.getY()));
                    else if (constantEnemy.extraExistence)
            			lifes.add(new Life(constantEnemy.getX(),constantEnemy.getY()));
                    else
                    	destroyeds.add(new Destroyed(constantEnemy.getX(),constantEnemy.getY()));
                }
                constantEnemyCounter++;
            }
            Iterator <ConstantEnemy1> constantEnemy1Iterator = constantEnemys1.iterator();
            int constantEnemy1Counter = 0; 
            while (constantEnemy1Iterator.hasNext()) {
                ConstantEnemy1 constantEnemy1 = constantEnemy1Iterator.next();
                if (server != null) {
                	if (server.getConstantEnemy1Num.containsKey(constantEnemy1Counter)) {
                		for (int i = 0;i < server.getConstantEnemy1Num.get(constantEnemy1Counter);i++) {//if there is a number of wall gotten before
                			constantEnemy1.kill();
                			if (!constantEnemy1.isAlive())
                				break;
						}
                		server.getConstantEnemy1Num.put(constantEnemy1Counter, 0);
                	}
                }
                else if (client != null)
                	if (client.getConstantEnemy1Num.containsKey(constantEnemy1Counter)) {
                		for (int i = 0;i < client.getConstantEnemy1Num.get(constantEnemy1Counter);i++) {//if there is a number of wall gotten before
                			constantEnemy1.kill();
                			if (!constantEnemy1.isAlive())
                				break;
						}
                		client.getConstantEnemy1Num.put(constantEnemy1Counter, 0);
                	}
                if (tankRectangle.intersects(constantEnemy1.getRectangle()))
                    collision = true;
                Iterator<Shot> myFiresIterator = myFires.iterator();
                while (myFiresIterator.hasNext()) {
                    Shot s = myFiresIterator.next();
                    if (s.getRectangle().intersects(constantEnemy1.getRectangle())) {
                        myFiresIterator.remove();
                        if (s.heavyBullet) {
                        	constantEnemy1.kill();
                            if (server != null) {
                            	String sendConstantEnemy1Num = constantEnemy1Counter + "\n";
                            	server.sendConstantEnemy1(sendConstantEnemy1Num);
                            }
                            else if(client != null) {
                            	String sendConstantEnemy1Num = constantEnemy1Counter + "\n";
                            	client.sendConstantEnemy1(sendConstantEnemy1Num);
                            }
                        }
                        else {
                        	if (constantEnemy1.hit(myTank.cartridgeUp)) {
                        		constantEnemy1.kill();
                                if (server != null) {
                                	String sendConstantEnemy1Num = constantEnemy1Counter + "\n";
                                	server.sendConstantEnemy1(sendConstantEnemy1Num);
                                }
                                else if(client != null) {
                                	String sendConstantEnemy1Num = constantEnemy1Counter + "\n";
                                	client.sendConstantEnemy1(sendConstantEnemy1Num);
                                }
                        	}
                        }
                    }
                }
                if (!constantEnemy1.isAlive()) {
                    constantEnemy1Iterator.remove();
                    if (constantEnemy1.repairFood)
                    	repairFoods.add(new RepairFood(constantEnemy1.getX(),constantEnemy1.getY()));
                    else if (constantEnemy1.cannonFood)
                    	cannonFoods.add(new CannonFood(constantEnemy1.getX(),constantEnemy1.getY()));
                    else if (constantEnemy1.gunFood)
                    	gunFoods.add(new GunFood(constantEnemy1.getX(),constantEnemy1.getY()));
                    else if (constantEnemy1.upgrade)
                    	upgrades.add(new Upgrade(constantEnemy1.getX(),constantEnemy1.getY()));
                    else if (constantEnemy1.extraExistence)
            			lifes.add(new Life(constantEnemy1.getX(),constantEnemy1.getY()));
                    else
                    	destroyeds.add(new Destroyed(constantEnemy1.getX(),constantEnemy1.getY()));
                }
                constantEnemy1Counter++;
            }
            Iterator <SmartShot> smartShotIterator = smartShots.iterator();
            while (smartShotIterator.hasNext()) {
                SmartShot smartShot = smartShotIterator.next();
                if (tankRectangle.intersects(smartShot.getRectangle())) {
                    smartShotIterator.remove();
                    if (!protect)
                    	myTank.decreaseLife();
                    if (myTank.revive==0 && myTank.getLife() == 0){
                    	gameOver = true;
					}else if (myTank.revive !=0 && myTank.getLife() == 0){
                    	myTank.revive();
					}

                }
                
                for (HardWall hardWall : hardWalls) {
                    if (smartShot.getRectangle().intersects(hardWall.getRectangle())) {
                    	try {
                    		smartShotIterator.remove();
                    	}
                    	catch(Exception e) {   
                    		e.printStackTrace();
                    	}
                    }
                }
                Iterator<SoftWall> it = softWalls.iterator();
                while (it.hasNext()) {
                    SoftWall softWall = it.next();
                    if (smartShot.getRectangle().intersects(softWall.getRectangle())) {
                    	try {
                    		smartShotIterator.remove();
                            if (softWall.ruin())
                                it.remove();
                    	}
                        catch(Exception e) {
                        	e.printStackTrace();
                        }
                    }
                }
                Iterator<Shot> myFiresIterator = myFires.iterator();
                while (myFiresIterator.hasNext()) {
                    Shot s = myFiresIterator.next();
                    if (s.getRectangle().intersects(smartShot.getRectangle())) {
                    	try {
                            myFiresIterator.remove();
                            smartShotIterator.remove();
                    	}
                    	catch(Exception e) {
                    		e.printStackTrace();
                    	}
                    }
                }
            }

            Iterator<RepairFood> repairFoodIterator = repairFoods.iterator();
            int repairFoodCounter = 0;
            while (repairFoodIterator.hasNext()) {
                RepairFood repairFood = repairFoodIterator.next();
                if (server != null) {
                	if (repairFoodCounter == server.getRepairFoodNum) {
                		repairFoodIterator.remove();
                		server.getRepairFoodNum = -1;
                	}
                }
                else if (client != null)
                	if (repairFoodCounter == client.getRepairFoodNum) {
                		repairFoodIterator.remove();
                		client.getRepairFoodNum = -1;
                	}
                if (tankRectangle.intersects(repairFood.getRectangle())) {
                    myTank.increaseLife();
                    GameFrame.playSoundBullet("repair.wav");
                    repairFoodIterator.remove();
                    if (server != null) {
                    	String sendRepairFoodNum = repairFoodCounter + "\n";
                    	server.sendRepairFood(sendRepairFoodNum);
                    }
                    else if(client != null) {
                    	String sendRepairFoodNum = repairFoodCounter + "\n";
                    	client.sendRepairFood(sendRepairFoodNum);
                    }
                }
                repairFoodCounter++;
            }

            Iterator<CannonFood> cannonFoodIterator = cannonFoods.iterator();
            int cannonFoodCounter = 0;
            while (cannonFoodIterator.hasNext()) {
                CannonFood cannonFood = cannonFoodIterator.next();
                if (server != null) {
                	if (cannonFoodCounter == server.getCannonFoodNum) {
                		cannonFoodIterator.remove();
                		server.getCannonFoodNum = -1;
                	}
                }
                else if (client != null)
                	if (cannonFoodCounter == client.getCannonFoodNum) {
                		cannonFoodIterator.remove();
                		client.getCannonFoodNum = -1;
                	}
                if (tankRectangle.intersects(cannonFood.getRectangle())) {
                    myTank.increaseCannonShell();
                    cannonFoodIterator.remove();
                    if (server != null) {
                    	String sendCannonFoodNum = cannonFoodCounter + "\n";
                    	server.sendCannonFood(sendCannonFoodNum);
                    }
                    else if(client != null) {
                    	String sendCannonFoodNum = cannonFoodCounter + "\n";
                    	client.sendCannonFood(sendCannonFoodNum);
                    }
                }
                cannonFoodCounter++;
            }
            Iterator<Upgrade> upgradeIterator = upgrades.iterator();
            int upgradeCounter = 0;
            while (upgradeIterator.hasNext()) {
                Upgrade upgrade = upgradeIterator.next();
                if (server != null) {
                	if (upgradeCounter == server.getUpgradeNum) {
                		upgradeIterator.remove();
                		server.getUpgradeNum = -1;
                	}
                }
                else if (client != null)
                	if (upgradeCounter == client.getUpgradeNum) {
                		upgradeIterator.remove();
                		client.getUpgradeNum = -1;
                	}
                if (tankRectangle.intersects(upgrade.getRectangle())) {
                    if (heavyBullet) {
                    	myTank.cannonUp();
                    }
                    else
                    	myTank.cartridgeUp++;
                    upgradeIterator.remove();
                    if (server != null) {
                    	String sendUpgradeNum = upgradeCounter + "\n";
                    	server.sendUpgrade(sendUpgradeNum);
                    }
                    else if(client != null) {
                    	String sendUpgradeNum = upgradeCounter + "\n";
                    	client.sendUpgrade(sendUpgradeNum);
                    }
                }
                upgradeCounter++;
            }
            Iterator<GunFood> gunFoodIterator = gunFoods.iterator();
            int gunFoodCounter = 0;
            while (gunFoodIterator.hasNext()) {
                GunFood gunFood = gunFoodIterator.next();
                if (server != null) {
                	if (gunFoodCounter == server.getGunFoodNum) {
                		gunFoodIterator.remove();
                		server.getGunFoodNum = -1;
                	}
                }
                else if (client != null)
                	if (gunFoodCounter == client.getGunFoodNum) {
                		gunFoodIterator.remove();
                		client.getGunFoodNum = -1;
                	}
                if (tankRectangle.intersects(gunFood.getRectangle())) {
                    myTank.increaseGunCartridge();
                    gunFoodIterator.remove();
                    if (server != null) {
                    	String sendGunFoodNum = gunFoodCounter + "\n";
                    	server.sendGunFood(sendGunFoodNum);
                    }
                    else if(client != null) {
                    	String sendGunFoodNum = gunFoodCounter + "\n";
                    	client.sendGunFood(sendGunFoodNum);
                    }
                }
                gunFoodCounter++;
            }
			Iterator<Life> lifeIterator = lifes.iterator();
			int lifeCounter = 0;
			while (lifeIterator.hasNext()) {
				Life life = lifeIterator.next();
				if (server != null) {
					if (lifeCounter == server.getLifeNum) {
						lifeIterator.remove();
						server.getLifeNum = -1;
					}
				}
				else if (client != null)
					if (lifeCounter == client.getLifeNum) {
						lifeIterator.remove();
						client.getLifeNum = -1;
					}
				if (tankRectangle.intersects(life.getRectangle())) {
					if (myTank.revive!=3){
						myTank.revive++;
					}
//					GameFrame.playSoundBullet("repair.wav");
					lifeIterator.remove();
					if (server != null) {
						String sendLifeNum = lifeCounter + "\n";
						server.sendLife(sendLifeNum);
					}
					else if(client != null) {
						String sendLifeNum = lifeCounter + "\n";
						client.sendLife(sendLifeNum);
					}
				}
				lifeCounter++;
			}

            if (collision) {
                dx = 0;
                dy = 0;
            }
            
            int formerX = locX;
            int formerY = locY;
            
            locX += dx;
            locY += dy;
           

            locX = Math.max(locX, 0);
            //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            locY = Math.max(locY, 0);
            //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            if (locX != formerX || locY != formerY) {
            	if (server != null)
            		server.sendTank(locX, locY);
            	else if (client != null)
            		client.sendTank(locX, locY);
            }
            if (mouseDown && myTank.getGunCartridge() != 0) {
            	if (System.currentTimeMillis() - mills > 100) {
            		Shot shot = new Shot(locX - XChange + 50, locY - YChange + 50, Math.cos(myTankAngleRad), Math.sin(myTankAngleRad),heavyBullet);
            		//GameFrame.playSoundBullet("mashingun.wav");
        			myFires.add(shot);
            		myTank.decreaseGunCartridge();
            		mills = System.currentTimeMillis();
            	}
            }
            myTank.locationX = locX;
            myTank.locationY = locY;            
        }
    }

    public KeyListener getKeyListener() {
        return keyHandler;
    }
    public MouseListener getMouseListener() {
        return mouseHandler;
    }
    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }



    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode())
            {
              case KeyEvent.VK_UP:
              case KeyEvent.VK_W:
            	  if (startMenu && !gameStage && !askOfOldGame)
            		  kind--;
            	  else if (startMenu && !gameStage && askOfOldGame)
            		  oldGame--;

            	  else if(startMenu && gameStage)
                      gameType--;
            	  else if(escape)
            		  exit--;
                  else
                      keyUP = true;
                  break;

              case KeyEvent.VK_S:
              case KeyEvent.VK_DOWN:
            	  if (startMenu && !gameStage && !askOfOldGame)
            		  kind++;
            	  else if (startMenu && !gameStage && askOfOldGame)
            		  oldGame++;
            	  else if(startMenu && gameStage)
                      gameType++;
            	  else if(escape)
            		  exit++;
                  else
                      keyDOWN = true;
                  break;
              case KeyEvent.VK_LEFT:
              case KeyEvent.VK_A:
                  keyLEFT = true;
                  break;
              case KeyEvent.VK_D:
              case KeyEvent.VK_RIGHT:
                  keyRIGHT = true;
                  break;
              case KeyEvent.VK_ENTER:
            	  if (mapEditor){
					  // we should add more
					  softWalls.add(SOFTWALL);
					  hardWalls.add(HARDWALL);
					  escape = false;
					  startMenu = false;
					  mapEditor = false;
				  }

            	  else if (win){
            		  if (!finish) {
            			clearGame();
                  		readMap("level2.txt");
                  		win = false;
                  		finish = true;
            		  }
  				 }
            	  else if (startMenu && !gameStage && !askOfOldGame) {
            		  //playing alone
            		  if (Math.abs(kind % 3) == 0) {
            			  File file = new File("Saved.txt");
            			  if (file.length() == 0)
            				  gameStage = true;
            			  else
            				  askOfOldGame = true;
            		  }
            		  else if (Math.abs(kind % 3) == 1) {
            			  System.out.println("Creating the game as a server");
            			  server = new GameServer();
            			  friend = new MyTank();
            			  gameStage = true;
            		  }
            		  else if (Math.abs(kind % 3) == 2) {
            			  //I SHOULD GET IP IN HERE
            			  //IN THE NAME OF GOD
            			  JFrame IPFrame = new JFrame("Geting IP");
            			  IPFrame.setLayout(new GridLayout(3,1));
            			  IPFrame.setDefaultCloseOperation(IPFrame.DISPOSE_ON_CLOSE);
            			  JLabel IPLabel = new JLabel("Enter IP");
            			  JTextField IPField = new JTextField();
            			  JButton button = new JButton("OK");
            			  button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								IP = IPField.getText();
								System.out.println("joining a game");
								client = new GameClient(IP);
								friend = new MyTank();
								client.start();
								try {
									Thread.sleep(10000);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								if (client.correctIP) {
									while (client.map.equals("a")) {//means client hasn't read the map name
										try {
											//waiting for a second so client can read the map name
											Thread.sleep(1000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									IPFrame.dispose();
									readMap(client.map);
									startMenu = false;
								}
								else
									JOptionPane.showMessageDialog(null, "You have entered wrong IP address");
							}
            				  
            			  });
            			  IPFrame.add(IPLabel);
            			  IPFrame.add(IPField);
            			  IPFrame.add(button);
            			  IPFrame.setLocationRelativeTo(null);
            			  IPFrame.setSize(400, 400);
            			  IPFrame.setVisible(true);
            		  }
            	  }
            	  else if (startMenu && !gameStage && askOfOldGame) {
            		  if (Math.abs(oldGame % 3) == 0) {
            			  readFromSavedFile();
            			  startMenu = false;
            		  }
            		  // new game
            		  else if (Math.abs(oldGame % 3 )==1){
						  System.out.println("new game");
            		  	gameStage = true;
					  }
					  //make a map editor
					  else if (Math.abs(oldGame %3 )== 2 ){
            		  	  mapEditor = true;
            		  	  startMenu = true;
					  }

            	  }

            	  
            	  else if (startMenu && gameStage) {
                      if (Math.abs(gameType % 3 )==0){
                          readMap("0Map.txt");
                          if(server != null) {
                        	  server.map ="0Map.txt\n";
                              server.start();
                          }
                      }else if (Math.abs(gameType %3) ==1){
                          readMap("1Map.txt");
                          if(server != null){
                        	  server.map ="1Map.txt\n";
                              server.start();
                          }
                      }else if (Math.abs(gameType %3) ==2){
                          readMap("Map.txt");
                          if(server != null){
                        	  server.map ="Map.txt\n";
                              server.start();
                          }
                      }
                      if (server != null) {
                    	  while (!server.gotClient) {
            				  try {
    							Thread.sleep(1000);
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
            			  }
                      }
                      
                      startMenu=false;
                  }
            	  else if (escape) {
            		  if (Math.abs(exit % 2) == 0)
            			  escape = false;
            		  else {
            			  writeToSavedFile();
            			  System.exit(0);
            		  }
            	  }
                  break;
              case KeyEvent.VK_ESCAPE:
            	  if (!startMenu) {
            		  escape = true;
            	  }
            	  break;
              case KeyEvent.VK_P:
            	  PDown = true;
            	  break;
              case KeyEvent.VK_R:
            	  RDown = true;
            	  break;
              case KeyEvent.VK_O:
            	  ODown = true;
            	  break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode())
            {
              case KeyEvent.VK_UP:
              case KeyEvent.VK_W:
                  keyUP = false;
                  break;
              case KeyEvent.VK_DOWN:
              case KeyEvent.VK_S:
                  keyDOWN = false;
                  break;
              case KeyEvent.VK_LEFT:
              case KeyEvent.VK_A:
                  keyLEFT = false;
                  break;
              case KeyEvent.VK_RIGHT:
              case KeyEvent.VK_D:
                  keyRIGHT = false;
                  break;
//              case KeyEvent.VK_P:
//            	  PDown = false;
//            	  break;
//              case KeyEvent.VK_R:
//            	  RDown = false;
//            	  break;
//              case KeyEvent.VK_O:
//            	  ODown = false;
//            	  break;
            }
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
        	if (e.getButton()==1 && mapEditor){
        		if (HARDWALL.getRectangle().contains(e.getX(),e.getY()))
        			makeMap = 'H';
				else if (SOFTWALL.getRectangle().contains(e.getX(),e.getY()))
					makeMap = 'S';
				else if (BIGENEMY.getRectangle().contains(e.getX(),e.getY()))
					makeMap = 'B';
				else if (new Rectangle(0,0,100,100).contains(e.getX(),e.getY()))
					makeMap = 'M';
				else if (CANOONFOOD.getRectangle().contains(e.getX(),e.getY()))
					makeMap = 'A';
				else if (CONSTANTENEMY.getRectangle().contains(e.getX(),e.getY()))
					makeMap ='C';
				else if (new Rectangle(0,400,100,100).contains(e.getX(),e.getY()))
					makeMap = 'P';
				else {
					switch (makeMap){
						case 'H':
							hardWalls.add(new HardWall(e.getX()-50,e.getY()-50));
							break;
						case 'S':
							softWalls.add(new SoftWall(e.getX()-50,e.getY()-50));
							break;
						case 'B':
							bigEnemys.add(new BigEnemy(e.getX()-50,e.getY()-50));
							break;
						case 'M':
							myTank = MYTANK;
							tankRectangle = new Rectangle();
							myTank.locationX = e.getX()-50;
							myTank.locationY = e.getY()-50;
							locX = e.getX()-50;
							locY = e.getY()-50;
							break;
						case 'A':
							cannonFoods.add(new CannonFood(e.getX()-50,e.getY()-50));
							break;
						case 'C':
							constantEnemys.add(new ConstantEnemy(e.getX()-50,e.getY()-50));
							break;
						case 'P':
							plants.add(new Plant(e.getX()-50,e.getY()-50));
							break;
					}
				}
			} 

        	else if (e.getButton() == 1 && !escape) {
        		if (heavyBullet && myTank.getCannonShell() != 0) {
        			Shot shot = new Shot(locX - XChange + 50, locY - YChange + 50, Math.cos(myTankAngleRad), Math.sin(myTankAngleRad),heavyBullet);
        			//GameFrame.playSoundBullet("cannon.wav");
        			myFires.add(shot);
        			if (myTank.cannonUp != 0) {
        				Shot shot1 = new Shot(locX - XChange, locY - YChange, Math.cos(myTankAngleRad) - 0.2, Math.sin(myTankAngleRad) - 0.2,heavyBullet);
            			Shot shot2 = new Shot(locX - XChange, locY - YChange, Math.cos(myTankAngleRad) + 0.2, Math.sin(myTankAngleRad) + 0.2,heavyBullet);
            			myFires.add(shot1);
                		myFires.add(shot2);
        			}
        			
//                    GameFrame.playSoundBullet("enemyshot.wav");
            		myTank.decreaseCannonShell();
        		}
        		else if (!heavyBullet) {
        			mouseDown = true;
        			mills = System.currentTimeMillis();
        		}
        	}
        	else if (e.getButton() == 3 && !escape) {
        		if (heavyBullet)
        			heavyBullet = false;
        		else
        			heavyBullet = true;
        		myTank.changeGun();
        		if (server != null) {
        			server.sendChangePipe();
        		}
        		else if (client != null) {
        			client.sendChangePipe();
        		}
        	}
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseDown = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
        
        @Override
      public void mouseMoved(MouseEvent e)
      {
          double dx = e.getX() - locX + XChange - 50;
          double dy = e.getY() - locY + YChange - 50;
          myTankAngleRad = Math.atan2(dy, dx);
          if (server != null) {
        	  if (!server.sendPipe(myTankAngleRad))
        		  connectionLost = true;
          }
          else if (client != null) {
        	  if (!client.sendPipe(myTankAngleRad))
        		  connectionLost = true;
          }
      }
    }

	public MyTank getMyTank() {
		return myTank;
	}

	public void setMyTank(MyTank myTank) {
		this.myTank = myTank;
	}
}
