
public abstract class Bullet extends Machine 
{
	private int reflectCount;
	private int maxReflection;
	protected int population;
	private int damageCount;
	
    private static final int DEFAULT_POPULATION_COUNT = 1;
    private static final int DEFAULT_REFLECTION_COUNT = 2;
    private static final int DEFAULT_BULLET_LIFE = 1;
    private static final int DEFAULT_DAMAGE = 1;
    
	public Bullet(double positionX, double positionY, int width, int height, int bulletVel, Tank shooter) 
	{
		super(positionX + (-width/2 + shooter.getWidth()/2),positionY + (-height/2 + shooter.getHeight()/2),width, height, shooter.getPlayerNum());
	
		initializeImage();
		
		velX =  (Math.cos(Math.toRadians((double) shooter.getMcnAngle())) * bulletVel);
		velY =  (Math.sin(Math.toRadians((double) shooter.getMcnAngle())) * -bulletVel);
		
		playSE();
		reflectCount = 0;
		damageCount = DEFAULT_DAMAGE;
		
		setPopulation();
		setReflectionCount(DEFAULT_REFLECTION_COUNT);
		setLifePoint(DEFAULT_BULLET_LIFE);
	}
	
	public Tank getParent()
	{
		return ((MachineInfo)data.get(getPlayerNum())).getPlayer();
	}
	
	public Tank getTarget()
	{
		return ((MachineInfo)data.get(getPlayerNum())).getOther();
	}
	
	// If overwridden different music
	public void playSE()
	{
		MusicPlayer.playBulletSE();
	}
	
	// If Overridden, that means it is either counts more or does not
	public void setPopulation()
	{
		population = DEFAULT_POPULATION_COUNT;
		getParent().increaseBulletNum();
	}
	
	public void setDamageCount(int i)
	{
		damageCount = i;
	}
	
	public void setReflectionCount(int i)
	{
		maxReflection = i;
	}
	
	public abstract void initializeImage();

	@Override
	public void handleXCollision() 
	{
		reflectCount++;
		if(reflectCount >= maxReflection)
			takeDamage();
		if(!isDead())
		{
			velX = -velX;
			MusicPlayer.playReflectionSE();
		}
	}

	@Override
	public void handleYCollision() 
	{
		reflectCount++;
		if(reflectCount >= maxReflection)
			takeDamage();
		if(!isDead())
		{
			velY = -velY;
			MusicPlayer.playReflectionSE();
		}
	}
	
	/*
	 * Must Override for decreasing Bullet Count(non-Javadoc)
	 * @see Actor#removeSelf()
	 */
	
	public void removeSelf()
	{
		getParent().decreaseBulletNum(population);
		super.removeSelf();
	}
	
	public void actCollision(Actor other)
	{
		/*
		 * Bullet to Bullet interaction must be written in two codes
		 * Just like PlasmaBullet
		 */
		
		/*
		 *  Using the this instanceof Method means 
		 *  this bullet should ovveride all bullet actions (giving damage etc)
		 */
		if(this instanceof PortalBullet)
		{
			if(other instanceof Bullet)
				takeDamage();
			return;
		}

		if(other instanceof PlasmaBullet)
		{
			takeDamage();
			return;
		}
				
		if(other instanceof Tank)
		{
			other.takeDamage(damageCount);
			takeDamage();
			return;
		}		
		

		if(other instanceof Bullet)
		{
			other.takeDamage();
			takeDamage();
			return;
		}
		
	}
	
	
		
}

