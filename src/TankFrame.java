import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import java.awt.Toolkit;



@SuppressWarnings("serial")
public class TankFrame extends JFrame implements KeyListener, Runnable
{
	/*----------------------------------------------------------*
	 *		Adding Item Step 1:									*
	 * 		Add new items in the UpgradeFile file.				*
	 * 		Step 2: Go to Status class and write another case 	*
	 * 															*
	 *----------------------------------------------------------*/
	/*----------------------------------------------------------*
	 *		Adding Map Step 1:									*
	 * 		Add title to MapFile file.							*
	 * 		Step 2: Create new txt file with title of the map	*
	 * 		same as the name in MapFile except without space.	*
	 * 		Use the codes for lines, box, etc.				 	*
	 * 															*
	 *----------------------------------------------------------*/
	
	/*
	 * 	JFrame Settings
	 */
	
	public static final int FRAME_WIDTH = 450;
	public static final int FRAME_HEIGHT = 500;
	public static final int SCREEN_WIDTH = 450;
	public static final int SCREEN_HEIGHT = 450;

	/*
	 * 	JFrame Items
	 */
	private static JMenuBar mainMenuBar;
	
	private static JMenu fileTab;
	private static JMenu optionsTab;
	private static JMenu helpTab;
	
	private static JMenuItem restartItem;
	private static JMenuItem exitItem;
	
	private static JMenu items;
	private static JMenuItem allOff;
	private static JMenuItem allOn;

	private static ArrayList<JCheckBoxMenuItem> upgrades;
	public static int ITEM_TYPES;
	public static boolean itemsOn;
	
	private static JMenu healthFrequency;
	private static JRadioButtonMenuItem h_off;
	private static JRadioButtonMenuItem h_low;
	private static JRadioButtonMenuItem h_normal;
	private static JRadioButtonMenuItem h_high;
	
	private static JMenu itemsFrequency;
	private static JRadioButtonMenuItem low;
	private static JRadioButtonMenuItem normal;
	private static JRadioButtonMenuItem high;
	
	/*
	 * Adding maps 1: add new radio Button
	 * go to constructor
	 */
	private static JMenu maps;
	private static ArrayList<JRadioButtonMenuItem> mapSelections;
	private static final int DEFAULT_MAP = 1;
	
	private static JMenuItem controls;
	
	private static JMenuItem itemsDetails;
	private static JMenuItem controlsDetails;
	
	private static ButtonGroup dropRateButtons;
	private static ButtonGroup mapButtons;
	private static ButtonGroup healthButtons;
	
	/*
	 *  More GUI Instance Fields
	 */
	
	private static GamePanel playingField;
	private static Label message;
	private static HealthPanel player1Health;
	private static HealthPanel player2Health;
	
	/*
	 *		Info Fields and Tanks
	 */

	
	private static ArrayList<Info> data;
	public static final int ACTOR= 0;
	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2= 2;
	public static final int MAP = 3;
	
	private static Map mapCreator;

	/*
	 * 	Game Status
	 */
	
	private static int status;
	private static final int PLAYING = 1;
	private static final int RESULT = 2;
	
	/*
	 * 	Thread Settings
	 */
	
	private static int SPEED = 50;
	private int sleepCount;
	private boolean running;
	private Thread gameThread;
	
	/*
	 *  Key Constants
	 */
	private static final int ROTATE_ANGLE = 6;
	private static final int VEL = 2;
	
	/*
	 * 	Images
	 */
	
	//private Image offImage;
	private Image background;
	
	/*
	 * 	KEY PRESSED CONSTANTS
	 */
	
	private boolean keyPressedDown_1;
	private boolean keyPressedUp_1;
	private boolean keyPressedRight_1;
	private boolean keyPressedLeft_1;
	private boolean shotBullet_1;
	private boolean rotateLeft_1;
	private boolean rotateRight_1;
	private boolean shotExit_1;
	private boolean shotEntrance_1;
	
	private boolean keyPressedDown_2;
	private boolean keyPressedUp_2;
	private boolean keyPressedRight_2;
	private boolean keyPressedLeft_2;
	private boolean shotBullet_2;
	private boolean rotateLeft_2;
	private boolean rotateRight_2;
	private boolean shotExit_2;
	private boolean shotEntrance_2;
	
	private final int	KEY_UP_1 = KeyEvent.VK_W;
	private final int	KEY_LEFT_1  = KeyEvent.VK_A;
	private final int	KEY_DOWN_1 = KeyEvent.VK_S;
	private final int	KEY_RIGHT_1 = KeyEvent.VK_D;
	private final int	KEY_SHOOT_1 = KeyEvent.VK_SPACE;
	private final int	KEY_ROTATE_L_1 = KeyEvent.VK_Q;
	private final int	KEY_ROTATE_R_1 = KeyEvent.VK_E;
	private final int 	KEY_EXIT_1 = KeyEvent.VK_3;
	private final int 	KEY_ENTRANCE_1 = KeyEvent.VK_1;
	
	private final int	KEY_UP_2 = KeyEvent.VK_NUMPAD5;
	private final int	KEY_LEFT_2  = KeyEvent.VK_NUMPAD1;
	private final int	KEY_DOWN_2 = KeyEvent.VK_NUMPAD2;
	private final int	KEY_RIGHT_2 = KeyEvent.VK_NUMPAD3;
	private final int	KEY_SHOOT_2 = KeyEvent.VK_NUMPAD0;
	private final int	KEY_ROTATE_L_2 = KeyEvent.VK_NUMPAD4;
	private final int	KEY_ROTATE_R_2 = KeyEvent.VK_NUMPAD6;
	private final int 	KEY_EXIT_2 = KeyEvent.VK_NUMPAD9;
	private final int 	KEY_ENTRANCE_2 = KeyEvent.VK_NUMPAD7;
	
	public TankFrame()
	{
		setTitle("TankGame");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//  GUI Frame Setting Start
		mainMenuBar = new JMenuBar();
		
			fileTab = new JMenu("File");
			
				restartItem = new JMenuItem("Restart");
				exitItem = new JMenuItem("Exit");
				exitItem.addActionListener(new CloseGame());
			
			optionsTab = new JMenu("Option");
			
		// Creates new upgrade reader but locally so it is discarded
		UpgradeReader menuItemReader = new UpgradeReader();
		MapReader radioButtonReader = new MapReader();
			
				items= new JMenu("Items Setting");
					allOff = new JMenuItem("All Items Off");
					allOff.addActionListener(new TurnAllButtonsOff());
					allOn = new JMenuItem("All Items On");
					allOn.addActionListener(new TurnAllButtonsOn());
					// uses upgrade reader to read an arrayList of upgrade checkboxes
					upgrades = menuItemReader.getJCheckBoxUpgrades();
					ITEM_TYPES = upgrades.size();
									
				dropRateButtons = new ButtonGroup();
				itemsFrequency = new JMenu("Drop Rate");
					low = new JRadioButtonMenuItem("Low");
					normal = new JRadioButtonMenuItem("Normal");
					high = new JRadioButtonMenuItem("High");
					
				healthButtons = new ButtonGroup();
				healthFrequency = new JMenu("Health Pack");
					h_off = new JRadioButtonMenuItem("Off");
					h_low = new JRadioButtonMenuItem("Low");
					h_normal = new JRadioButtonMenuItem("Normal");
					h_high = new JRadioButtonMenuItem("High");
		
					/*
					 * Adding maps 2: create new map
					 * now go add to group and maps
					 */
				mapButtons = new ButtonGroup();
				maps = new JMenu("Maps");
					mapSelections = radioButtonReader.getJRadioButtonMaps();
		
				controls = new JMenuItem("Set Controls");
				
			helpTab = new JMenu("Help");
				
				itemsDetails = new JMenuItem("Item");
				controlsDetails = new JMenuItem("Control");
				
			fileTab.add(restartItem);
			fileTab.addSeparator();
			fileTab.add(exitItem);
			restartItem.addActionListener(new Restart());

				items.add(allOff);
				items.add(allOn);
				items.addSeparator();
				
				items.add(healthFrequency);
					healthFrequency.add(h_off);
					healthFrequency.addSeparator();
					healthFrequency.add(h_low);
					healthFrequency.add(h_normal);
					healthFrequency.add(h_high);
					healthButtons.add(h_off);
					healthButtons.add(h_low);
					healthButtons.add(h_normal);
					healthButtons.add(h_high);
					h_off.setSelected(true);
					
				items.addSeparator();
				for(JCheckBoxMenuItem option : upgrades)
					items.add(option);
					
				itemsFrequency.add(low);
				itemsFrequency.add(normal);
				itemsFrequency.add(high);
				dropRateButtons.add(low);
				dropRateButtons.add(normal);
				dropRateButtons.add(high);
				normal.setSelected(true);
		

				/*
				 * Adding maps 3: add new radio Button to group and options
				 * go to get map selection
				 */
				for(JRadioButtonMenuItem button : mapSelections)
				{
					maps.add(button);
					mapButtons.add(button);
				}
				mapSelections.get(DEFAULT_MAP).setSelected(true);
				
			optionsTab.add(items);
			optionsTab.add(itemsFrequency);
			optionsTab.add(maps);
			optionsTab.add(controls);
			
			helpTab.add(itemsDetails);
			helpTab.add(controlsDetails);
		
		mainMenuBar.add(fileTab);
		mainMenuBar.add(optionsTab);
		mainMenuBar.add(helpTab);	
		
		playingField = new GamePanel();
		playingField.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		message = new Label("Game!");

		player1Health = new HealthPanel(PLAYER_1);
		player2Health = new HealthPanel(PLAYER_2);
		getContentPane().add(BorderLayout.WEST, player1Health);
		getContentPane().add(BorderLayout.EAST, player2Health);
		
		//offImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
		background = new ImageIcon("Images/Field.jpg").getImage();
		
		getContentPane().add(BorderLayout.NORTH, mainMenuBar);
		getContentPane().add(BorderLayout.SOUTH, message);
		getContentPane().add(BorderLayout.CENTER, playingField);

		pack();
		// GUI Frame Setting End
		gameThread = new Thread(this);	
		
		addKeyListener(this);
		setVisible(true);
		
		startGame();
		gameThread.start();
	}
	
	/*
	 *  INNER CLASSES
	 */
	
	class TurnAllButtonsOn implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			for(JCheckBoxMenuItem item: upgrades)
				item.setSelected(true);
				
			itemsOn = true;
			resetKey();
		}	
	}
	
	class TurnAllButtonsOff implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			for(JCheckBoxMenuItem item: upgrades)
				item.setSelected(false);
			
			itemsOn = false;
			resetKey();
		}	
	}
	
	class Restart implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			MusicPlayer.stopAllMusic();
			status = PLAYING;
			message.setText("GameStart!");
			deleteAll();
			startGame();
		}	
	}
	
	class CloseGame implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
	            endGame();
	    }
		
	}
	
	private void endGame()
	{
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	
	public void deleteAll()
	{
		for(Info dataTable : data)
			dataTable.destroy();
	}
	
	/*
	 * 	Initializes Key flags
	 */
	public void resetKey()
	{
		keyPressedDown_1 = false;
		keyPressedRight_1 = false;
		keyPressedLeft_1 = false;
		keyPressedUp_1 = false;
		rotateLeft_1 = false;
		rotateRight_1 = false;
		shotBullet_1 = false;
		shotExit_1 = false;
		shotEntrance_1 = false;
		
		keyPressedDown_2 = false;
		keyPressedRight_2 = false;
		keyPressedLeft_2 = false;
		keyPressedUp_2 = false;
		rotateLeft_2 = false;
		rotateRight_2 = false;
		shotBullet_2 = false;
		shotExit_2 = false;
		shotEntrance_2 = false;
	}
	/*
	 * 	Initializes game settings
	 */
	public void initialize()
	{	
		resetKey();			
		running = true;
		sleepCount = 0;
		status = PLAYING;
		
		for(JCheckBoxMenuItem item: upgrades)
			item.setSelected(true);
		
		itemsOn = true;
		
		// CHANGE TO OTHER PLACE
			
	}
	
	/*
	 * 	Starts Game by creating Tanks and InfoFields and calls initialize method
	 */
	public void startGame()
	{
		initialize();
		sleepCount = 1;
		 /* Index
	     *  Player 0: Actors
	     *  Player 1: Player 1
	     *  Player 2: Player 2
	     *  Player 3: Obstacles
	     */
		
		data = new ArrayList<Info>();
		data.add(new ActorInfo());
		data.add(new MachineInfo(PLAYER_1));
		data.add(new MachineInfo(PLAYER_2));
		data.add(new MapInfo());
		
		Actor.setData(data);
		
		
		
		((MachineInfo)data.get(PLAYER_1)).addTank(new Tank(50,50,1));
		((MachineInfo)data.get(PLAYER_2)).addTank(new Tank(400,400,2));
		//((MachineInfo)data.get(PLAYER_2)).addTank(new AITank(400,400,2));
		mapCreator = new Map();
		mapCreator.setMap();
		MusicPlayer.playMainMusic();
	
		
		repaint();
		
	}

	public void gameClear(int i)
	{
		message.setText("GameOver!");
		repaint();
		MusicPlayer.stopMainMusic();
		MusicPlayer.playVictorySE();
		status = RESULT;
	}


	public void run() 
	{		
		while(running)
		{
			try
			{
				if (status == PLAYING){
				
				Thread.sleep(SPEED);
				((MachineInfo)data.get(PLAYER_1)).getPlayer().setVelX(0);
				((MachineInfo)data.get(PLAYER_1)).getPlayer().setVelY(0);
				((MachineInfo)data.get(PLAYER_2)).getPlayer().setVelX(0);
				((MachineInfo)data.get(PLAYER_2)).getPlayer().setVelY(0);
				
				if(keyPressedDown_1)
					((MachineInfo)data.get(PLAYER_1)).getPlayer().setVelY(VEL);       
				if(keyPressedUp_1)
                    ((MachineInfo)data.get(PLAYER_1)).getPlayer().setVelY(-VEL);
                if(keyPressedLeft_1)
                    ((MachineInfo)data.get(PLAYER_1)).getPlayer().setVelX(-VEL);
                if(keyPressedRight_1)
                    ((MachineInfo)data.get(PLAYER_1)).getPlayer().setVelX(VEL);
                if(rotateLeft_1)
                	((MachineInfo)data.get(PLAYER_1)).getPlayer().rotate(ROTATE_ANGLE);
                if(rotateRight_1) 
                	((MachineInfo)data.get(PLAYER_1)).getPlayer().rotate(-ROTATE_ANGLE);
                if(shotBullet_1)
            		((MachineInfo)data.get(PLAYER_1)).getPlayer().shoot();
                if(shotExit_1)
                	((MachineInfo)data.get(PLAYER_1)).getPlayer().shootExit();
                if(shotEntrance_1)
                	((MachineInfo)data.get(PLAYER_1)).getPlayer().shootEntrance();
				if(keyPressedDown_2)
                    ((MachineInfo)data.get(PLAYER_2)).getPlayer().setVelY(VEL);
                if(keyPressedUp_2)
                    ((MachineInfo)data.get(PLAYER_2)).getPlayer().setVelY(-VEL);
                if(keyPressedLeft_2)
                    ((MachineInfo)data.get(PLAYER_2)).getPlayer().setVelX(-VEL);
                if(keyPressedRight_2)
                    ((MachineInfo)data.get(PLAYER_2)).getPlayer().setVelX(VEL);
                if(rotateLeft_2)
                	((MachineInfo)data.get(PLAYER_2)).getPlayer().rotate(ROTATE_ANGLE);
                if(rotateRight_2) 
                	((MachineInfo)data.get(PLAYER_2)).getPlayer().rotate(-ROTATE_ANGLE);
                if(shotBullet_2)         
                	((MachineInfo)data.get(PLAYER_2)).getPlayer().shoot();
                if(shotExit_2)
                	((MachineInfo)data.get(PLAYER_2)).getPlayer().shootExit();
                if(shotEntrance_2)
                	((MachineInfo)data.get(PLAYER_2)).getPlayer().shootEntrance();
                if(sleepCount > 0)
                	sleepCount--;
                

                for(Info infoTable : data)
                	infoTable.act();
             
                
                Actor p1Temp, p2Temp, aTemp;
                Hashtable<Integer,Actor> p1Hash, p2Hash, aHash;
                Enumeration<Actor> p1, p2, a;
             
                p1Hash = data.get(PLAYER_1).getHashtable();
                p2Hash = data.get(PLAYER_2).getHashtable();
                aHash = data.get(ACTOR).getHashtable();
                
                p1 = p1Hash.elements();
                while(p1.hasMoreElements())
                {
                	p1Temp = (Actor)p1.nextElement();
                	
                	p2 = p2Hash.elements();
                	while(p2.hasMoreElements())
                	{
                		p2Temp = (Actor)p2.nextElement();
                		p1Temp.getCollision(p2Temp);
                		if(!((p1Temp instanceof Bullet) && (p2Temp instanceof Bullet)))
                			p2Temp.getCollision(p1Temp);
                		a = aHash.elements();
                			while(a.hasMoreElements())
                			{
                				aTemp = (Actor) a.nextElement();
                				aTemp.getCollision(p1Temp);
                				aTemp.getCollision(p2Temp);
                			}
                	}
                }
                
                repaint();
                
                if(((MachineInfo)data.get(PLAYER_1)).getPlayer().isDead())
                	gameClear(2);
                if(((MachineInfo)data.get(PLAYER_2)).getPlayer().isDead())
                	gameClear(1);    
				}
				if(status == RESULT)
				{
					Thread.sleep(SPEED);
					repaint();
				}
			}
			catch(InterruptedException e)
			{
				System.out.println(e.toString());
			}
					
		}
		
	}
	
	public void keyPressed(KeyEvent e)
	{
			switch(e.getKeyCode())
			{
                    case KEY_DOWN_1:
                        if(!keyPressedDown_1)
                            keyPressedDown_1 = true;
                        break;
                    case KEY_UP_1:
                        if(!keyPressedUp_1)
                            keyPressedUp_1 = true;
                        break;
                    case KEY_LEFT_1:
                        if(!keyPressedLeft_1)
                            keyPressedLeft_1 = true;
                        break;
                    case KEY_RIGHT_1:
                        if(!keyPressedRight_1)
                            keyPressedRight_1 = true;
                        break;
                    case KEY_SHOOT_1:
                    	if(!shotBullet_1)
                    		shotBullet_1 = true;	
                    	break;     
					case KEY_ROTATE_L_1:
						if(! rotateLeft_1) 
							rotateLeft_1 = true;
						break;
					case KEY_ROTATE_R_1:
						if(! rotateRight_1) 
							rotateRight_1 = true;
						break;
					case KEY_EXIT_1:
						if(! shotExit_1) 
							shotExit_1= true;
						break;
					case KEY_ENTRANCE_1:
						if(! shotEntrance_1) 
							shotEntrance_1= true;
						break;	
                    case KEY_DOWN_2:
                        if(!keyPressedDown_2)
                            keyPressedDown_2 = true;
                        break;
                    case KEY_UP_2:
                        if(!keyPressedUp_2)
                            keyPressedUp_2 = true;
                        break;
                    case KEY_LEFT_2:
                        if(!keyPressedLeft_2)
                            keyPressedLeft_2 = true;
                        break;
                    case KEY_RIGHT_2:
                        if(!keyPressedRight_2)
                            keyPressedRight_2 = true;
                        break;
                    case KEY_SHOOT_2:
                      	if(!shotBullet_2)
                   		shotBullet_2 = true;	
                      	break;
					case KEY_ROTATE_L_2:
						if(! rotateLeft_2) 
							rotateLeft_2 = true;
						break;
					case KEY_ROTATE_R_2:
						if(! rotateRight_2) 
							rotateRight_2 = true;
						break;
					case KEY_EXIT_2:
						if(! shotExit_2) 
							shotExit_2= true;
						break;
					case KEY_ENTRANCE_2:
						if(! shotEntrance_2) 
							shotEntrance_2= true;
						break;
			}
	}

	public void keyReleased(KeyEvent e)
	{
                switch(e.getKeyCode())
                {
                    case KEY_DOWN_1:
                        keyPressedDown_1 = false;
                        break;
                    case KEY_UP_1:
                        keyPressedUp_1 = false;
                        break;
                    case KEY_LEFT_1:
                        keyPressedLeft_1 = false;
                        break;
                    case KEY_RIGHT_1:
                        keyPressedRight_1 = false;
                        break;
                    case KEY_ROTATE_L_1:
                    	rotateLeft_1 = false;
                    	break;
                    case KEY_ROTATE_R_1:
                    	rotateRight_1 = false;
                    	break;
                    case KEY_SHOOT_1:
                    	shotBullet_1 = false;
                    	break;
					case KEY_EXIT_1:
							shotExit_1= false;
						break;
					case KEY_ENTRANCE_1:
							shotEntrance_1= false;
						break;
                    case KEY_DOWN_2:
                        keyPressedDown_2 = false;
                        break;
                    case KEY_UP_2:
                        keyPressedUp_2 = false;
                        break;
                    case KEY_LEFT_2:
                        keyPressedLeft_2 = false;
                        break;
                    case KEY_RIGHT_2:
                        keyPressedRight_2 = false;
                        break;
                    case KEY_ROTATE_L_2:
                    	rotateLeft_2 = false;
                    	break;
                    case KEY_ROTATE_R_2:
                    	rotateRight_2 = false;
                    	break;
                    case KEY_SHOOT_2:
                    	shotBullet_2 = false;
                    	break;
					case KEY_EXIT_2:
						shotExit_2= false;
					break;
					case KEY_ENTRANCE_2:
						shotEntrance_2= false;
					break;
                }   
    }

	public void keyTyped(KeyEvent e) 
	{
	}
	
	public static double getDropRate()
	{
		if(low.isSelected())
			return .005;
		if(normal.isSelected())
			return .01;
		if(high.isSelected())
			return .05;
		return 0;
	}
	
	public static double getHealthRate()
	{
		if(h_low.isSelected())
			return .005;
		if(h_normal.isSelected())
			return .010;
		if(h_high.isSelected())
			return .025;
		return 0;
	}
	
	public static String getMapSelection()
	{
		for(JRadioButtonMenuItem temp : mapSelections)
			if(temp.isSelected())
				return temp.getActionCommand().replaceAll(" ", "") + ".txt";

		System.out.println("GetMapSelection Error");
		return "Blank";
	}
	
	public static ArrayList<Boolean> getItemBoolean()
	{
		ArrayList<Boolean> selection = new ArrayList<Boolean>();
		
		for(JCheckBoxMenuItem option : upgrades)
			selection.add(option.isSelected());
		
		for(Boolean option : selection)
			if(option.booleanValue())
			{
				itemsOn = true;
				break;
			}
		
		return selection;
	}
	
	public static boolean itemOn()
	{
		itemsOn = false;

		for(JCheckBoxMenuItem option : upgrades)
			if(option.isSelected())
				itemsOn = true;
		
		return itemsOn;
	}
	
	class GamePanel extends JPanel 

	{
		
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			
	    	g.drawImage(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
	    	   	
	    	Actor tempActor;
	    	Hashtable<Integer, Actor> tempHash;
	    	Enumeration<Actor> tempEnum;
	    	
	    	for(Info infoTable: data)
	    	{
	    		tempHash = infoTable.getHashtable();
	    		tempEnum = tempHash.elements();
	    		while(tempEnum.hasMoreElements())
	    		{
	    			tempActor = (Actor) tempEnum.nextElement();
	    			tempActor.paintScreen(g);
	    		}
	    	}
	    	
		}

		
	}
	
	class HealthPanel extends JPanel 
	{
		private int playerNum;
		private Label name;
		private static final int BOX_WIDTH = 20;
		private static final int BAR_HEIGHT = 240;
		private static final int BOX_DEFAULT_Y = 20;
		private static final int BOX_GAP_X = 10;
		private double height;
		
		public HealthPanel(int num)
		{
			setPreferredSize(new Dimension(BOX_WIDTH+BOX_GAP_X,SCREEN_HEIGHT));
			playerNum = num;
			name = new Label ("P" +playerNum);
			name.setAlignment(Label.CENTER);
			add(name);
			height = BAR_HEIGHT / (double) Machine.DEFAULT_TANK_LIFE;
		}

		protected void paintComponent(Graphics g) 
		{
			if(playerNum == 1)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.RED);
			if ( ((MachineInfo)data.get(playerNum)).getPlayer() != null)
				for(int i = 1; i <= ((MachineInfo)data.get(playerNum)).getPlayer().getLifePoint() ; i++)
					g.fillRect((getWidth()-BOX_WIDTH)/2,(int)Math.round(Machine.DEFAULT_TANK_LIFE*height-i*height + BOX_DEFAULT_Y), BOX_WIDTH, (int)Math.round(height));
		}

		
	}

}
