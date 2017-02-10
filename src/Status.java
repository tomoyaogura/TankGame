import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Status 
{
    // Timer values
    private long lastUpgrade;
    private long upgradeDuration;
	
	private Tank owner;
	
    // Adjustable stats for powerUps
    private int bulletMax;
    private long shootingInterval;
	private double speedUpFactor;
	private int shootingType;

    
    // Image boolean
    private boolean imageChanged;
    private static final int STRONG = 1;
    private static final int WEAK = 2;
    
    // Time Constants
    private static final long shortTime= 5000;
    private static final long mediumTime= 10000;
    private static final long longTime= 15000;
    
    // Status Constants
    private static final int DEFAULT_BULLETMAX = 5;
    private static final int DEFAULT_SPEEDUPFACTOR = 1;
    private static final long DEFAULT_SHOOTINGINTERVAL = 500;
    private static final int DEFAULT_SHOOTINGTYPE = Tank.NORMAL_SHOT;
	
	
	public Status(Tank myTank)
	{
		owner = myTank;
		imageChanged = true;
		restoreDefault();
		lastUpgrade = 0;
		upgradeDuration = 0;
	}
	
	// Restores all powerUp data
	public void restoreDefault()
	{
		bulletMax = DEFAULT_BULLETMAX;
		shootingInterval = DEFAULT_SHOOTINGINTERVAL;
		speedUpFactor = DEFAULT_SPEEDUPFACTOR;
		shootingType = DEFAULT_SHOOTINGTYPE;
		if(imageChanged)
		{
			if(owner.getPlayerNum() == 1)
				owner.setImage(new ImageIcon("Images/BlueTank.gif").getImage());
			if(owner.getPlayerNum() == 2)
				owner.setImage(new ImageIcon("Images/RedTank.gif").getImage());
		}
	}
	
	private void changeShootingInterval(long interval) 
	{
		shootingInterval = interval;
		if(interval < DEFAULT_SHOOTINGINTERVAL)
			changeImage(STRONG);
		else if(interval > DEFAULT_SHOOTINGINTERVAL)
			changeImage(WEAK);
		upgradeDuration = mediumTime;
	}
	
	private void changeBulletMax(int num)
	{
		bulletMax = num;
		if(num > DEFAULT_BULLETMAX)
			changeImage(STRONG);
		else if(num < DEFAULT_BULLETMAX)
			changeImage(WEAK);
		upgradeDuration = longTime;
	}
	
	private void changeShootingType(int type) 
	{
		shootingType = type;
		changeImage(STRONG);
		upgradeDuration = mediumTime;
	}
	
	private void changeSpeed(double factor) 
	{
		speedUpFactor = factor;
		if(factor > DEFAULT_SPEEDUPFACTOR)
			changeImage(STRONG);
		else if(factor < DEFAULT_SPEEDUPFACTOR)
			changeImage(WEAK);
		upgradeDuration = shortTime;
	}
	
	private void changeVisibility()
	{
		owner.setImage(new ImageIcon("Images/Invisible.jpg").getImage());
		upgradeDuration = mediumTime;
	}
	
	private void changeImage(int type)
	{
		imageChanged = true;
		if(type == STRONG)
		{
			if(owner.getPlayerNum() == 1)
				owner.setImage(new ImageIcon("Images/BlueTankStrong.gif").getImage());
			else if(owner.getPlayerNum() == 2)
				owner.setImage(new ImageIcon("Images/RedTankStrong.gif").getImage());
		}
		if(type == WEAK)
		{
			if(owner.getPlayerNum() == 1)
				owner.setImage(new ImageIcon("Images/BlueTankWeak.gif").getImage());
			else if(owner.getPlayerNum() == 2)
				owner.setImage(new ImageIcon("Images/RedTankWeak.gif").getImage());
		}
		upgradeDuration = longTime;
	}
	
	public void upgradeStatus()
	{
		/*
		*		Adding Item Step 2: Add item to list below and 
		*		add the index to one of the cases 
		* 		Go to Tank shot type and change if necessary.
		* 		Make sure these are fixed when restoreDefault()
		*/
		
		/* 	speedUp 		[0]
		 * 	speedDown 		[1]
		 * 	rapidFire		[2]
		 * 	slowFire		[3]
		 * 	invisible		[4]
		 * 	increaseMaxFire	[5]
		 * 	decreaseMaxFire	[6]
		 * 	homingRockets	[7]
		 * 	plasmaShot		[8]
		 *  shotGunShot		[9]
		 */

		restoreDefault();
		
		ArrayList<Boolean> selection = TankFrame.getItemBoolean();
		ArrayList<Integer> validItems = new ArrayList<Integer>();
		ArrayList<Double> percentages = new ArrayList<Double>();
		
		int optionCount = 0;
		for(int i = 0; i < selection.size(); i ++)
			if(selection.get(i).booleanValue())
			{
				optionCount++;
				validItems.add(i);
			}
		
		for(int i = 0 ; i < selection.size(); i ++)
			if(!selection.get(i).booleanValue())
			{
				selection.remove(i);
				i--;
			}
				
		double percentDivision = 1.0/optionCount;
		
		int percentAssignment = 1;
				
		for(int i = 0 ; i < selection.size(); i ++)
		{
			percentages.add(percentDivision*percentAssignment);
			percentAssignment++;
		}
					
		double random = Math.random();
		System.out.println(random);
		int id = -1; 
		
		for(int i = 0 ; i < selection.size(); i ++)
		{
			if(random < percentages.get(i))
			{
				id = validItems.get(i);
				break;
			}
		}
		
		switch(id)
		{
		case 0:
			changeSpeed(2);
			break;
		case 1:
			changeSpeed(.5);
			break;
		case 2:
			changeShootingInterval(250);
			break;
		case 3:
			changeShootingInterval(1000);
			break;
		case 4:
			changeVisibility();
			break;
		case 5:
			changeBulletMax(7);
			break;
		case 6:
			changeBulletMax(3);
			break;
		case 7:
			changeShootingType(Tank.HOMING_SHOT);
			break;
		case 8:
			changeShootingType(Tank.PLASMA_SHOT);
			break;
		case 9:
			changeShootingType(Tank.SHOTGUN_SHOT);
			break;
		}	
		lastUpgrade = System.currentTimeMillis();
	}
	
	
	
	/*
	 * Accessor Methods
	 */
	
	public int getBulletMax()
	{
		return bulletMax;
	}
	
	public double getSpeedUpFactor() 
	{
		return speedUpFactor;
	}

	public long getShootingInterval() 
	{
		return shootingInterval;
	}

	public int getShootingType() 
	{
		return shootingType;
	}
	
	public long getLastUpgrade()
	{
		return lastUpgrade;
	}
	
	public long getUpgradeDuration()
	{
		return upgradeDuration;
	}
}
