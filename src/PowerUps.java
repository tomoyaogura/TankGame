import javax.swing.ImageIcon;


public class PowerUps extends Items 
{
	/*
	 *  OVERRIDE SETLIFEDURATION METHOD TO INCREASE LIFE
	 */
	
	public PowerUps(double positionX, double positionY, int actorWidth, int actorHeight) 
	{
		super(positionX, positionY, actorWidth, actorHeight);
	}

	@Override
	public void powerUp(Tank other) 
	{
		other.upgrade();
	}
	
	public void rotateImage() 
	{
		if (angle < 240)
			angle += 30;
		else if (angle >= 240)
			angle = 0;
		setImage(new ImageIcon("Images/s" + formatName(angle)+ ".gif").getImage());
	}
}
