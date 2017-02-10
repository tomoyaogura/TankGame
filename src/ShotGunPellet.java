
public class ShotGunPellet extends NormalBullet 
{

	public ShotGunPellet(double positionX, double positionY, Tank shooter) 
	{
		super(positionX,positionY, shooter);
		setReflectionCount(1);
	}
	
	public void setPopulation()
	{
		population = 0;
	}
}
