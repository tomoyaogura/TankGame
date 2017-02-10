import javax.swing.ImageIcon;


public class Portal extends Actor
{
	private int orientation;
	private int type;
	private int owner;
	
	private static final int PORTAL_WIDTH = 8;
	private static final int PORTAL_HEIGHT = 8;
	private static final int PORTAL_OPEN = 5;
	
	public Portal(double posX, double posY, int direction, int player, int portalType)
	{
		// This sends 0 so that it is recorded in actor info
		super(posX,posY,PORTAL_WIDTH, PORTAL_HEIGHT,0);
		// owner of the portal is stored in different variable
		owner = player;
		orientation = direction;
		type = portalType;
		initializeImage();
		
	}
	
	public void initializeImage()
	{
		if (type == ActorInfo.ENTRANCE)
			setImage(new ImageIcon("Images/PortalEntrance.gif").getImage());
		else
			setImage(new ImageIcon("Images/PortalExit.gif").getImage());
	}
	
	public Portal clone()
	{
		Portal clone = new Portal(getPosX(),getPosY(),orientation, owner,type);
		return clone;
	}
	
	@Override
	public void act() 
	{
	}

	@Override
	public void actCollision(Actor otherActor) 
	{
		if(!(otherActor instanceof Machine))
			return;
		
		Machine other = (Machine)otherActor;
		// If one of the portals do not exist, return
		if(this == null || ((ActorInfo)data.get(TankFrame.ACTOR)).getOther(this) == null)
			return;
		if(other instanceof PortalBullet)
			return;
		
		int position;
		
		position = (this.getDirection() - ((ActorInfo)data.get(TankFrame.ACTOR)).getOther(this).getDirection());

		double temp;
			
		switch(position)
		{
		case 0:
			other.setVelX(-other.getVelX());
			other.setVelY(-other.getVelY());
			break;
		case 1:
			temp = other.getVelX();
			other.setVelX(-other.getVelY());
			other.setVelY(temp);
			break;
		case -1:
			temp = other.getVelX();
			other.setVelX(other.getVelY());
			other.setVelY(-temp);
		case 2:
		case -2:
			break;
		case 3:
		case -3:
			temp = other.getVelX();
			other.setVelX(-other.getVelY());
			other.setVelY(-temp);
			break;
		}	
		other.setPosX(((ActorInfo)data.get(TankFrame.ACTOR)).getOther(this).getOpenX(other));
		other.setPosY(((ActorInfo)data.get(TankFrame.ACTOR)).getOther(this).getOpenY(other));
		MusicPlayer.playTeleportSE();
	}
	
	
	private double getOpenX(Machine other)
	{
		if(orientation == ActorInfo.PORTAL_NEGX)
			return getPosX()+PORTAL_WIDTH+PORTAL_OPEN;
		if(orientation == ActorInfo.PORTAL_PLUSX)
			return getPosX()-other.getWidth()-PORTAL_OPEN;
		if((orientation == ActorInfo.PORTAL_NEGY || orientation == ActorInfo.PORTAL_PLUSY) && getPosX() > TankFrame.SCREEN_WIDTH - Map.DEFAULT_FRAME - other.getWidth())
			return getPosX() - other.getWidth();
		if(orientation == ActorInfo.PORTAL_NEGY)
			return getPosX();
		if(orientation == ActorInfo.PORTAL_PLUSY)
			return getPosX();
		System.out.println("Portal Error: ORIENTATION UNDEFINED");
		return 0.0;
	}
	
	private double getOpenY(Machine other)
	{
		if((orientation == ActorInfo.PORTAL_NEGX || orientation == ActorInfo.PORTAL_PLUSX) && getPosY() > TankFrame.SCREEN_HEIGHT - Map.DEFAULT_FRAME - other.getHeight())
			return getPosY() - other.getWidth();
		if(orientation == ActorInfo.PORTAL_NEGX)
			return getPosY();
		if(orientation == ActorInfo.PORTAL_PLUSX)
			return getPosY();
		if(orientation == ActorInfo.PORTAL_NEGY)
			return getPosY()+PORTAL_HEIGHT+PORTAL_OPEN;
		if(orientation == ActorInfo.PORTAL_PLUSY)
			return getPosY()-other.getHeight()-PORTAL_OPEN;
		System.out.println("Portal Error: ORIENTATION UNDEFINED");
		return 0.0;
	}
	
	public int getDirection()
	{
		return orientation;
	}
	
	public int getType()
	{
		return type;
	}
	
	public int getOwner()
	{
		return owner;
	}

}
