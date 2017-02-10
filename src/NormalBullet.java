import javax.swing.ImageIcon;



public class NormalBullet extends Bullet 
{
	private static final int DEFAULT_BULLET_WIDTH = 5;
	private static final int DEFAULT_BULLET_HEIGHT = 5;
    private static final int DEFAULT_BULLET_VEL = 5;
	
	public NormalBullet(double positionX, double positionY, Tank shooter) 
	{
		super(positionX,positionY, DEFAULT_BULLET_WIDTH, DEFAULT_BULLET_HEIGHT, DEFAULT_BULLET_VEL, shooter);
	}
	
	public void initializeImage()
	{
		if (getPlayerNum() == 1)
			setImage(new ImageIcon("Images/BlueBullet.gif").getImage());
		else if (getPlayerNum() == 2)
			setImage(new ImageIcon("Images/RedBullet.gif").getImage());
	}
}
