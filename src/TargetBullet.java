import javax.swing.ImageIcon;

public class TargetBullet extends Bullet 
{

	public TargetBullet(double positionX, double positionY, Tank shooter) 
	{
		super(positionX, positionY, 0, 0, 0, shooter);
	}

	@Override
	public void initializeImage() 
	{
		setImage(new ImageIcon("Images/Invisible.gif").getImage());
	}
	
	public void playSE()
	{
	}
	
	public void setPopulation()
	{
		population = 0;
		getParent().increaseBulletNum(population);
	}

}
