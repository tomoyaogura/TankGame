import javax.swing.ImageIcon;


public class PortalBullet extends Bullet 
{
	private static final int PORTAL_BULLET_WIDTH = 10;
	private static final int PORTAL_BULLET_HEIGHT = 10;
	private static final int PORTAL_BULLET_VEL = 10;
	private int type;
	
	public PortalBullet(double positionX, double positionY, Tank shooter, int bulletType) 
	{
		super(positionX, positionY, PORTAL_BULLET_WIDTH, PORTAL_BULLET_HEIGHT, PORTAL_BULLET_VEL, shooter);
		type = bulletType;
		initializeImage();
		MusicPlayer.playPortalGunSE();
	}
	
	public void initializeImage()
	{
		if (type == ActorInfo.ENTRANCE)
			setImage(new ImageIcon("Images/PortalEntrance.gif").getImage());
		if (type == ActorInfo.EXIT)
			setImage(new ImageIcon("Images/PortalExit.gif").getImage());
	}
	
	public void handleXCollision() 
	{
		((ActorInfo)data.get(TankFrame.ACTOR)).removeThisType(type, getPlayerNum());
		
		if(velX > 0)
			((ActorInfo)data.get(TankFrame.ACTOR)).setThisType(new Portal(((MapInfo)data.get(TankFrame.MAP)).getLastHit().getPosX()-PORTAL_BULLET_WIDTH/2, getPosY(), ActorInfo.PORTAL_PLUSX,getPlayerNum(), type));
		else
			((ActorInfo)data.get(TankFrame.ACTOR)).setThisType(new Portal(((MapInfo)data.get(TankFrame.MAP)).getLastHit().getPosX()+((MapInfo)data.get(TankFrame.MAP)).getLastHit().getWidth()-PORTAL_BULLET_WIDTH/2, getPosY(), ActorInfo.PORTAL_NEGX,getPlayerNum(), type));
		
		removeSelf();
	}

	@Override
	public void handleYCollision() 
	{
		((ActorInfo)data.get(TankFrame.ACTOR)).removeThisType(type, getPlayerNum());
		
		if(velY > 0)
			((ActorInfo)data.get(TankFrame.ACTOR)).setThisType(new Portal(getPosX(), ((MapInfo)data.get(TankFrame.MAP)).getLastHit().getPosY()-PORTAL_BULLET_HEIGHT/2, ActorInfo.PORTAL_PLUSY,getPlayerNum(), type));
		else
			((ActorInfo)data.get(TankFrame.ACTOR)).setThisType(new Portal(getPosX(), ((MapInfo)data.get(TankFrame.MAP)).getLastHit().getPosY()+((MapInfo)data.get(TankFrame.MAP)).getLastHit().getHeight()-PORTAL_BULLET_HEIGHT/2, ActorInfo.PORTAL_NEGY,getPlayerNum(), type));
	
		removeSelf();
	}
	
	/*
	 * Overrides the Removeself method to not decrese bullet count
	 * (non-Javadoc)
	 * @see Bullet#removeSelf()
	 */

	public void setPopulation()
	{
		population = 0;
	}
	



}
