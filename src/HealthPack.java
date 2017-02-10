import javax.swing.ImageIcon;


public class HealthPack extends Items 
{
	/*
	 *  OVERRIDE SETLIFEDURATION METHOD TO INCREASE LIFE
	 */
	
	public HealthPack(double positionX, double positionY, int actorWidth, int actorHeight) 
	{
		super(positionX, positionY, actorWidth, actorHeight);
	}

	@Override
	public void powerUp(Tank other) 
	{
		other.heal();
	}
	
	public void rotateImage() 
	{	
		if (angle < 150)
			angle += 15;
		else if (angle >= 150)
			angle = 0;
		setImage(new ImageIcon("Images/h_" + formatName(angle)+ ".gif").getImage());
	}
}
