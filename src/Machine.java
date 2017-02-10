public abstract class Machine extends Actor
{     
	
    // LifePoints && Public for HP Display - Reason why here is because of heal method
	// Subs don't need life points
    public static final int DEFAULT_TANK_LIFE = 8;
    public static final int DEFAULT_MACHINE_LIFE = 1;
    private int lifePoint;
    
    protected double velX;        
    protected double velY;           
    private static final double DEFAULT_VELX = 0;
    private static final double DEFAULT_VELY = 0; 
    
    private long lastPlayed;
    private static long DEFAULT_MUSIC_INTERVAL = 1000;
    
    public Machine(double positionX, double positionY, int mcnWidth, int mcnHeight, int playerID)
    {
    	super(positionX, positionY,mcnWidth, mcnHeight,playerID);
    	velX = DEFAULT_VELX;
    	velY = DEFAULT_VELY;
    	lifePoint = DEFAULT_MACHINE_LIFE;
    	lastPlayed = 0;
    }
       
    public void act()
    {
    	movePos();
    }
    
    public void movePos()
    {
    	if(((MapInfo)data.get(TankFrame.MAP)).isOpen(getPosX(),getPosY()+velY,getWidth(),getHeight()))
    		setPosY(getPosY() + velY);
    	else
    		handleYCollision();
    	
    	if(((MapInfo)data.get(TankFrame.MAP)).isOpen(getPosX()+velX,getPosY(),getWidth(),getHeight()))
    		setPosX(getPosX() + velX);    		   		
    	else
    		handleXCollision();
    }
    
    public void handleXCollision()
    {
    	//Default is do nothing - over ridden by bullet
    }
    public void handleYCollision()
    {
    	//Default is do nothing - over ridden by bullet
    }
    
	public void movePosSound()
    {
    	if(System.currentTimeMillis() - lastPlayed > DEFAULT_MUSIC_INTERVAL)
    	{
    		MusicPlayer.playTankLoopSE();
    		lastPlayed = System.currentTimeMillis();
    	}	
    }
    
    public abstract void actCollision(Actor other);
             
    public void takeDamage()
    {
    	lifePoint--;
    	if (lifePoint <= 0)
    	{
    		new Explosion(getPosX(),getPosY(),getWidth(),getHeight());
    		removeSelf();
    	}
    }   
    
    public void takeDamage(int i)
    {
    	lifePoint -= i;
    	if (lifePoint <= 0)
    	{
    		new Explosion(getPosX(),getPosY(),getWidth(),getHeight());
    		removeSelf();
    	}
    }   
    
    public double getVelX()
    {
    	return velX;
    }
    
    public double getVelY()
    {
    	return velY;
    }
    
    public void setVelX(double dx)
    {
    	if (dx != 0)
    		movePosSound(); // ADD VARIETY
    	velX = dx;
    }
    public void setVelY(double dy)
    {
    	if(dy != 0)
    		movePosSound(); // ADD VARIETY
    	velY = dy;
    }
    
    public void heal()
    {
    	if(lifePoint < DEFAULT_TANK_LIFE)
    		lifePoint++;
    }
    
    public int getLifePoint()
    {
    	return lifePoint;
    }
    
    public void setLifePoint(int life)
    {
    	lifePoint = life;
    }
   
}
