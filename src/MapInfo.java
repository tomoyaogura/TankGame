import java.util.Enumeration;


public class MapInfo extends Info
{
	// TAKE STRINGTOKENIZER AND READ MAPS FROM TXT
	// MapInfo holds only Obstacles
	

	// Last Hit
	private static Obstacle lastHit;
	
	public MapInfo()
	{
		lastHit = null;
	}

	public boolean isOpen(double locX, double locY, int width, int height)
	{    	
		Obstacle temp;
		Enumeration<Actor>  o = getHashtable().elements();
		while(o.hasMoreElements())
		{
			temp = (Obstacle) o.nextElement();
			if(temp.getBounds().intersects(locX, locY, (double) width, (double)height))
	    	{
	    		lastHit = (Obstacle) temp;
	    		return false;
	    	}	
		}
		return true;
	}
		
	/*
	 * Overrides Info: Actors in hash do not act
	 * 		Not OO: Must have isOpen method accessible
	 * 				so must be in MapInfo, even though
	 * 				it's really related with ActorInfo.
	 * @see Info#act()
	 */
	public void act()
	{
		if (TankFrame.itemOn())
		{
			double randomPosX = Math.random()*TankFrame.SCREEN_WIDTH;
			double randomPosY = Math.random()*TankFrame.SCREEN_HEIGHT;
		
			if(Math.random() < TankFrame.getDropRate())
				if(isOpen(randomPosX,randomPosY,ActorInfo.ITEM_WIDTH,ActorInfo.ITEM_HEIGHT))
					new PowerUps(randomPosX,randomPosY,ActorInfo.ITEM_WIDTH,ActorInfo.ITEM_HEIGHT);	
			
		}
		if(Math.random() < TankFrame.getHealthRate())
		{
			double randomPosX = Math.random()*TankFrame.SCREEN_WIDTH;
			double randomPosY = Math.random()*TankFrame.SCREEN_HEIGHT;
			if(isOpen(randomPosX,randomPosY,ActorInfo.ITEM_WIDTH,ActorInfo.ITEM_HEIGHT))
				new HealthPack(randomPosX,randomPosY,ActorInfo.ITEM_WIDTH,ActorInfo.ITEM_HEIGHT);	
		}
	}
	
	public Obstacle getLastHit()
	{
		return lastHit;
	}
}
