import java.text.DecimalFormat;


public abstract class Items extends Actor 
{
	protected int angle;
    // Timer values
    private long timeCreated;
    private long lifeDuration;
    private static final long DEFAULT_LIFE = 10000;
    private static DecimalFormat decimalFormat;
    
	public Items(double positionX, double positionY, int actorWidth, int actorHeight) 
	{
		super(positionX, positionY, actorWidth, actorHeight, 0);
		timeCreated = System.currentTimeMillis();
		angle = 0;
		decimalFormat = new DecimalFormat("000");
		rotateImage();
		setLifeDuration(DEFAULT_LIFE);
	}

	public String formatName(int num)
	{
		return decimalFormat.format(num);
	}
	
	@Override
	public void act() 
	{
		if(System.currentTimeMillis() - timeCreated >= lifeDuration)
			takeDamage();
		rotateImage();
	}
	
	public void setLifeDuration(long i)
	{
		lifeDuration = i;
	}

	@Override
	public void actCollision(Actor other) 
	{
		if (other instanceof Tank)
		{
			takeDamage();
			powerUp((Tank) other);
		}
	}
	
	abstract public void rotateImage();
	abstract public void powerUp(Tank other);

}
