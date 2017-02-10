import javax.swing.ImageIcon;


public class PlasmaBullet extends Bullet 
{
	
	private static final int PLASMA_BULLET_WIDTH = 20;
	private static final int PLASMA_BULLET_HEIGHT = 20;
	private static final int PLASMA_BULLET_VEL = 3;
	private static final int PLASMA_BULLET_DAMAGE = 2;
	
	public PlasmaBullet(double positionX, double positionY, Tank shooter) 
	{
		super(positionX,positionY, PLASMA_BULLET_WIDTH, PLASMA_BULLET_HEIGHT,PLASMA_BULLET_VEL, shooter);
		setDamageCount(PLASMA_BULLET_DAMAGE);
		MusicPlayer.playChargeShotSE();
	}
	
	public void initializeImage()
	{
		if (getPlayerNum() == 1)
			setImage(new ImageIcon("Images/BluePlasma.gif").getImage());
		if (getPlayerNum() == 2)
			setImage(new ImageIcon("Images/RedPlasma.gif").getImage());
	}
	
	public void handleXCollision() 
	{
	}

	@Override
	public void handleYCollision() 
	{
	}

	public void movePos()
	{
	    setPosY(getPosY() + velY);
	    setPosX(getPosX() + velX);    
	    if(getPosX() <= 0 || getPosX() >= TankFrame.SCREEN_WIDTH)
	    	removeSelf();
	    if(getPosY() <= 0 || getPosY() >= TankFrame.SCREEN_HEIGHT)
	    	removeSelf();
	}


}
