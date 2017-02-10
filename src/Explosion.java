import java.awt.Graphics;

import javax.swing.ImageIcon;


public class Explosion extends Actor 
{

	private int explosionPeriod;
	
	public Explosion(double x, double y, int expWidth, int expHeight) 
	{	
		super(x,y,expWidth,expHeight,0);
		explosionPeriod = 30;
		MusicPlayer.playExplosionSE();
	}

    public void paintScreen(Graphics g)
    {
    	if(explosionPeriod --> 0)
    	{	int i = (int) (Math.random()*4);
    		switch (i)
    		{
    		case 0:
    			g.drawImage(new ImageIcon("Images/09.gif").getImage(), (int)getPosX(), (int)getPosY(), getWidth(), getHeight(), null);
    			break;
    		case 1:
    			g.drawImage(new ImageIcon("Images/10.gif").getImage(), (int)getPosX(), (int)getPosY(), getWidth(), getHeight(), null);
    			break;
    		case 2:
    			g.drawImage(new ImageIcon("Images/11.gif").getImage(), (int)getPosX(), (int)getPosY(), getWidth(), getHeight(), null);
    			break;
    		case 3:
    			g.drawImage(new ImageIcon("Images/12.gif").getImage(), (int)getPosX(), (int)getPosY(), getWidth(), getHeight(), null);
    			break;
    		}
    	} else
    		removeSelf();
    }
    
	public boolean getCollision(Machine other)
    {
    	return false;
    }

	void handleXOffScreen() 
	{
		// TODO Auto-generated method stub
		
	}

	void handleYOffScreen() {
		
	}

	@Override
	public
	void act() 
	{
		//explosionPeriod--;
	}

	@Override
	public void actCollision(Actor other) 
	{
		// TODO Auto-generated method stub	
	}


}
