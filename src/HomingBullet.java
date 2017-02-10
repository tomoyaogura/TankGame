import javax.swing.ImageIcon;


public class HomingBullet extends Bullet 
{
	private static final int HOMING_BULLET_WIDTH = 8;
	private static final int HOMING_BULLET_HEIGHT = 8;
	private static final int HOMING_BULLET_VEL = 3;
	private int phaseCount;
	
	public HomingBullet(double positionX, double positionY, Tank shooter) 
	{
		super(positionX, positionY, HOMING_BULLET_WIDTH, HOMING_BULLET_HEIGHT, HOMING_BULLET_VEL, shooter);
		setReflectionCount(1);
		phaseCount = 1;
	}
	
	public void initializeImage()
	{
		if (getPlayerNum() == 1)
			setImage(new ImageIcon("Images/BlueHoming.gif").getImage());
		else if (getPlayerNum() == 2)
			setImage(new ImageIcon("Images/RedHoming.gif").getImage());
	}

	public void movePos()
	{
		if(phaseCount <= 20)
		{
			phaseCount++;
		}
		else if(phaseCount <= 110)
		{
			Tank enemy = null;
		
			enemy = ((MachineInfo)data.get(getPlayerNum())).getOther();
			/*if(playerNum == 1)
				enemy = ();
			if(playerNum == 2)
				enemy = player1Info.getPlayer1();*/
		
			double dx = enemy.getPosX() - getPosX();  
			double dy = enemy.getPosY() - getPosY();	
		
			if (dy == 0) dy++;
		
			double theta= Math.atan(((double)dx)/dy);
			if (dy < 0)
				velX = -(int)(Math.round(HOMING_BULLET_VEL*Math.sin(theta)));
			else	
				velX = (int)(Math.round(HOMING_BULLET_VEL*Math.sin(theta)));
			if (dy < 0 )
				velY = -(int)(Math.round(HOMING_BULLET_VEL*Math.cos(theta)));
			else
				velY = (int)(Math.round(HOMING_BULLET_VEL*Math.cos(theta)));	
			phaseCount++;
		}
		
		super.movePos();
	    
	    if(getPosX() <= 0 || getPosX() >= TankFrame.SCREEN_WIDTH)
	    	removeSelf();
	    else if(getPosY() <= 0 || getPosY() >= TankFrame.SCREEN_HEIGHT)
	    	removeSelf();
	        	
	}
}
