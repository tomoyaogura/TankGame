import javax.swing.ImageIcon;


public class ShotGunBullet extends Bullet 
{

	private static final int DEFAULT_BULLET_WIDTH = 5;
	private static final int DEFAULT_BULLET_HEIGHT = 5;
    private static final int DEFAULT_BULLET_VEL = 5;
    private static final int DEFAULT_BULLET_SPREAD = 10;
	
	public ShotGunBullet(double positionX, double positionY, Tank shooter) 
	{
		// Shoots it self, creates four other pellets
		super(positionX,positionY, DEFAULT_BULLET_WIDTH, DEFAULT_BULLET_HEIGHT, DEFAULT_BULLET_VEL, shooter);
		setReflectionCount(1);
		shooter.rotate(DEFAULT_BULLET_SPREAD);
		new ShotGunPellet(positionX,positionY, shooter);
		shooter.rotate(DEFAULT_BULLET_SPREAD);
		new ShotGunPellet(positionX,positionY, shooter);
		shooter.rotate(-3*DEFAULT_BULLET_SPREAD);
		new ShotGunPellet(positionX,positionY, shooter);
		shooter.rotate(-DEFAULT_BULLET_SPREAD);
		new ShotGunPellet(positionX,positionY, shooter);
		shooter.rotate(2*DEFAULT_BULLET_SPREAD);
	}
	
	@Override
	public void initializeImage() 
	{
		if (getPlayerNum() == 1)
			setImage(new ImageIcon("Images/BlueBullet.gif").getImage());
		else if (getPlayerNum() == 2)
			setImage(new ImageIcon("Images/RedBullet.gif").getImage());
	}
	


}
