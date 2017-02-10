import javax.swing.ImageIcon;


public class Obstacle extends Actor 
{

	public Obstacle(double positionX, double positionY, int obsWidth, int obsHeight) 
	{
		super(positionX, positionY, obsWidth, obsHeight, 3);
		int ratio = getWidth()-getHeight();
		
		if(ratio >= 50 && ratio <= 150)
			setImage(new ImageIcon("Images/ObstacleWide.jpg").getImage());
		else if(ratio > 150 )
			setImage(new ImageIcon("Images/ObstacleWideThin.jpg").getImage());
		else if(ratio < -150 )
			setImage(new ImageIcon("Images/ObstacleHighThin.jpg").getImage());
		else if(ratio <= -50 )
			setImage(new ImageIcon("Images/ObstacleHigh.jpg").getImage());
		else if (getWidth() <= 50 && getHeight() <= 50)
			setImage(new ImageIcon("Images/ObstacleSmall.jpg").getImage());
		else
			setImage(new ImageIcon("Images/ObstacleBlock.jpg").getImage());
	}

	public void act() 
	{
	}

	public void actCollision(Actor other) 
	{
	}

}
