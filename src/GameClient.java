import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handle the client realtion with the server
 * This class read the data of the  server and save them in its field
 * also write  the exchanges of its Game state to a buffer for its server
 */
public class GameClient extends Thread {
	
	public int locationX;
	public int locationY;
	public static double angleRad;
	public String sendWallNum;
	public HashMap<Integer, Integer> getWallNum = new HashMap<>();
	public int getBigEnemyNum = -1;
	public int getKhengEnemyNum = -1;
	public HashMap<Integer, Integer> getConstantEnemyNum = new HashMap<>();
	public HashMap<Integer, Integer> getConstantEnemy1Num = new HashMap<>();
	public int getRepairFoodNum = -1;
	public int getCannonFoodNum = -1;
	public int getGunFoodNum = -1;
	public int getUpgradeNum = -1;
	public boolean sendBigEnemy = false;
	public String map = "a";
	private String IP;
	public boolean correctIP = false;
	private OutputStream out;
	public boolean changePipe = false;
	public int getLifeNum=-1;

	public GameClient(String IP) {
		this.IP = IP;
	}
	
	@Override
	public void run() {
		try(Socket server = new Socket(IP,6666)){
			correctIP = true;
            out = server.getOutputStream();
            InputStream in = server.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			map = reader.readLine();
            while (true) {
            	//reading the place of server's tank
                if (reader.ready()) {
                	String element = reader.readLine();
                	switch(element) {
                	case "W" :
                		try {
    						int num = Integer.parseInt(reader.readLine());//the number of wall sent from server
    						if (getWallNum.containsKey(num)) {
    							int repeatation = getWallNum.get(num);
								repeatation++;
								getWallNum.put(num, repeatation);
    						}
    						else {
    							getWallNum.put(num, 1);
    						}
    					}
    					catch(Exception IO) {
    					}
                		break;
                	case "C":
                		try {
                			int num = Integer.parseInt(reader.readLine());
    						if (getConstantEnemyNum.containsKey(num)) {
    							int repeatation = getConstantEnemyNum.get(num);
								repeatation++;
								getConstantEnemyNum.put(num, repeatation);
    						}
    						else {
    							getConstantEnemyNum.put(num, 1);
    						}
    					}
    					catch(Exception IO) {
    					}
                		break;
                	case "O":
                		try {
                			int num = Integer.parseInt(reader.readLine());
    						if (getConstantEnemy1Num.containsKey(num)) {
    							int repeatation = getConstantEnemy1Num.get(num);
								repeatation++;
								getConstantEnemy1Num.put(num, repeatation);
    						}
    						else {
    							getConstantEnemy1Num.put(num, 1);
    						}
    					}
    					catch(Exception IO) {
    					}
                		break;
                	case "M" :
                		try {
    						locationX = Integer.parseInt(reader.readLine());
    						locationY = Integer.parseInt(reader.readLine());
    					}
    					catch(Exception IO) {
    					}
    					break;
                	case "P" :
                		try {
                			angleRad = Double.parseDouble(reader.readLine());
                		}
                		catch(Exception IO) {              			
                		}
                		break;
                		
                	case "B" :
                		try {
                			getBigEnemyNum = Integer.parseInt(reader.readLine());
    					}
    					catch(Exception IO) {
    					}
                		break;
                	case "K":
                		try {
                			getKhengEnemyNum = Integer.parseInt(reader.readLine());
                		}
                		catch(Exception IO){
                		}
                		break;
                	case "R":
                		try {
                			getRepairFoodNum = Integer.parseInt(reader.readLine());
                		}
                		catch(Exception IO) {
                		}
                		break;
                	case "A":
                		try {
                			getCannonFoodNum = Integer.parseInt(reader.readLine());
                		}
                		catch(Exception IO) {
                		}
                		break;
                	case "U":
                		try {
                			getUpgradeNum = Integer.parseInt(reader.readLine());
                		}
                		catch(Exception IO) {
                		}
                		break;
                	case "G":
                		try {
                			getGunFoodNum = Integer.parseInt(reader.readLine());
                		}
                		catch(Exception IO) {
                		}
                		break;
                	case "CP":
                		changePipe = true;
                		break;
					case "L":
						getLifeNum = Integer.parseInt(reader.readLine());
						break;
                	}	
				}
            }
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void sendTank(int x, int y) {
		String tank = "M\n";
		try {
			out.write(tank.getBytes());
			String locX = x + "\n";
            out.write(locX.getBytes());
            String locY = y + "\n";
            out.write(locY.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	public boolean sendPipe(double tankAngleRad) {
		String pipe = "P\n";
		try {
			out.write(pipe.getBytes());
			String angleRad = tankAngleRad + "\n";
            out.write(angleRad.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public void sendWall(String sendWallNum) {
		String wall = "W\n";
		try {
			out.write(wall.getBytes());
			out.write(sendWallNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void sendBigEnemy(String sendBigEnemyNum) {
		String enemy = "B\n";
		try {
			out.write(enemy.getBytes());
			out.write(sendBigEnemyNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendKhengEnemy(String sendKhengEnemyNum) {
		String enemy = "K\n";
		try {
			out.write(enemy.getBytes());
			out.write(sendKhengEnemyNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendConstantEnemy(String sendConstantEnemyNum) {
		String enemy = "C\n";
		try {
			out.write(enemy.getBytes());
			out.write(sendConstantEnemyNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendConstantEnemy1(String sendConstantEnemy1Num) {
		String enemy = "O\n";
		System.out.println("client send " + sendConstantEnemy1Num);
		try {
			out.write(enemy.getBytes());
			out.write(sendConstantEnemy1Num.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRepairFood(String sendRepairFoodNum) {
		String food = "R\n";
		try {
			out.write(food.getBytes());
			out.write(sendRepairFoodNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendCannonFood(String sendCannonFoodNum) {
		String food = "A\n";
		try {
			out.write(food.getBytes());
			out.write(sendCannonFoodNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendUpgrade(String sendUpgradeNum) {
		String upgrade = "U\n";
		try {
			out.write(upgrade.getBytes());
			out.write(sendUpgradeNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendGunFood(String sendGunFoodNum) {
		String food = "G\n";
		try {
			out.write(food.getBytes());
			out.write(sendGunFoodNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendChangePipe() {
		String str = "CP\n";//change pipe
		try {
			out.write(str.getBytes());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void sendLife(String sendLifeNum){
		String str = "L\n";
		try {
			out.write(str.getBytes());
			out.write(sendLifeNum.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
