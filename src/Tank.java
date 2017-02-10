
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Tank extends Machine {

	// Angle
    protected int mcnAngle;
    
    // Last time bullet shot
    private long lastTime;

    // Number of bullets that it shot
	public int bulletNum;
	
	//Bullet Type
	public static final int NORMAL_SHOT = 0;
	public static final int PLASMA_SHOT = 1;
	public static final int HOMING_SHOT = 2;
	public static final int SHOTGUN_SHOT = 3;
	
    //Tank Creation
    private static final int DEFAULT_HEIGHT = 20;
    private static final int DEFAULT_WIDTH = 20;
    
    // Portals
    private long lastPortalTime;
    private static final long PORTAL_INTERVAL = 1000;
    
    private Status mcnStatus;
    
	public Tank(double positionX, double positionY, int playerID) 
	{
		super(positionX,positionY,DEFAULT_WIDTH, DEFAULT_HEIGHT, playerID);
		mcnStatus = new Status(this);
		mcnAngle = 90;
		lastTime = 0;
		bulletNum = 0;
		setLifePoint(DEFAULT_TANK_LIFE);
		lastPortalTime = 0;
	}
	
	public boolean canShoot()
	{
		if(System.currentTimeMillis() - lastTime < mcnStatus.getShootingInterval())
			return false;
		else
			lastTime = System.currentTimeMillis();
		if(bulletNum >= mcnStatus.getBulletMax())
			return false;
		return true;	
	}
	
	public void shoot()
	{
		if(canShoot())
		{
			switch(mcnStatus.getShootingType())
			{
			case PLASMA_SHOT:
				new PlasmaBullet(getPosX(), getPosY(), this);
				break;
			case HOMING_SHOT:
				new HomingBullet(getPosX(), getPosY(), this);
				break;
			case SHOTGUN_SHOT:
				new ShotGunBullet(getPosX(), getPosY(), this);
				break;
			case NORMAL_SHOT:
				new NormalBullet(getPosX(), getPosY(), this);
				break;
			}
		}
	}
	
	/*
	 *  BULLET MANAGEMENT METHODS
	 */
	
	public void increaseBulletNum()
	{
		bulletNum++;
	}
	public void increaseBulletNum(int i)
	{
		bulletNum += i;
	}
	
	public void decreaseBulletNum()
	{
		bulletNum--;
	}
	
	public void decreaseBulletNum(int i)
	{
		bulletNum -= i;
	}
	
	/*
	 *  PORTAL MANAGEMENT METHODS
	 */
	public boolean canShootPortal()
	{
		if(System.currentTimeMillis() - lastPortalTime < PORTAL_INTERVAL)
			return false;
		else
			lastPortalTime = System.currentTimeMillis();
		return true;	
	}
	
	public void shootEntrance()
	{
		if(canShootPortal())
			new PortalBullet(getPosX(), getPosY(), this, ActorInfo.ENTRANCE);
	}
	
	public void shootExit()
	{
		if(canShootPortal())
			new PortalBullet(getPosX(), getPosY(), this, ActorInfo.EXIT);
	}

	public int getMcnAngle()
	{
		return mcnAngle;
	}
	
	/*
	 * OVERRIDES PAINT METHODS WITH ROTATIONS
	 */
	
    public void paintScreen(Graphics g)
    {
    	double scaleX, scaleY;
    		
    	scaleX = (double)getWidth()/getImage().getWidth(null);
        scaleY = (double)getHeight()/getImage().getHeight(null);
    	AffineTransform at = new AffineTransform();
    	Graphics2D g2 = (Graphics2D) g;
    	at.translate(getPosX(), getPosY());
    	at.rotate(-Math.toRadians((double)mcnAngle), getWidth()/2, getHeight()/2);
    	at.scale(scaleX, scaleY);
    	g2.drawImage(getImage(), at, null);
    }

	public void rotate(int angle)
	{
		if (angle != 0)
    		movePosSound(); // CHANGE SOUND && SHORTER
		mcnAngle += angle;
		if(mcnAngle > 360)
			mcnAngle -= 360;
		else if (mcnAngle < 0)
			mcnAngle += 360;
	}
	
	public void setMcnAngle(int newAngle)
	{
		mcnAngle = newAngle;
	}
	
	/*
	 * OVERRIDDEN METHODS
	 */
	
	// Only acts when collides with Tank
	// When collides with item, item acts
	// When collides with bullet, bullet acts
	public void actCollision(Actor otherActor)
	{
		if(otherActor instanceof Tank)
		{
			Tank other = (Tank) otherActor;
				other.setVelX(-other.getVelX());
				other.setVelY(-other.getVelY());
				setVelX(-velX);
				setVelY(-velY);
				other.act();
				act();
		}		
	}
	
	public void act()
	{
		checkRestoreDefault();
		super.act();
	}
	
	public void setVelX(double dx)
	{
		super.setVelX(dx*mcnStatus.getSpeedUpFactor());
	}
	
	public void setVelY(double dy)
	{
		super.setVelY(dy*mcnStatus.getSpeedUpFactor());
	}
	
	/*
	 *  ITEM UPGRADE METHODS
	 */
	
	// Checks and restores to default if time over 
	private void checkRestoreDefault()
	{
		if(System.currentTimeMillis() - mcnStatus.getLastUpgrade() > mcnStatus.getUpgradeDuration())
			mcnStatus.restoreDefault();
	}
	
	// Called by item & randomly upgrades some stat
	public void upgrade()
	{
		mcnStatus.upgradeStatus();
	}
}
