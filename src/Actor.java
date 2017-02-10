
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public abstract class Actor {
	private int playerNum;
    private double posX;          
    private double posY;    
    
    private int width;    
    private int height;          
    
    private boolean isDead;
      
    /*
     *  Player 0: Actors
     *  Player 1: Player 1
     *  Player 2: Player 2
     *  Player 3: Obstacles
     */
    
    //private static int [] numCreated = new int[4];
    private static int totalCreated = 0;
    private int id;
    
    protected static ArrayList<Info> data;
    
    private Image image;
    
    /**
     * precondition playerID = 1, 2
     */
    
    public Actor(double positionX, double positionY, int actorWidth, int actorHeight, int playerID)
    {
    	playerNum = playerID;
    	
    	image = new ImageIcon("Images/Default.jpg").getImage();
    	isDead = false;
    	
    	//numCreated[playerNum]++;
    	totalCreated++;
    	id = totalCreated;
    	 
    	width = actorWidth;
    	height = actorHeight;
    	posX = positionX;
    	posY = positionY;
		addSelf();
    }
    
    public static void setData(ArrayList<Info> info)
    {
    	data = info;
    	
    }
    
    public void setImage(Image picture)
    {
    	image = picture;
    }
    
    abstract public void act();
    abstract public void actCollision(Actor other);
    
    public void removeSelf()
    {
    	isDead = true;
    	data.get(playerNum).getHashtable().remove(id);
    }

    public void addSelf()
    {
    	data.get(playerNum).getHashtable().put(id, this);
    }
        
    /*
     * These two methods will be overridden by Machine
     */
    public void takeDamage()
    {
    	removeSelf();
    }   
    
    public void takeDamage(int i)
    {
    	removeSelf();
    }   
    
	public boolean isDead()
    {	
    	return isDead;
    }
    
    public double getPosX()
    {
    	return posX;
    }
   
    public double getPosY()
    {
    	return posY;
    }
    
    public void setPosX(double newX)
    {
    	posX = newX;
    }
    
    public void setPosY(double newY)
    {
    	posY = newY;
    }
    public int getID()
    {
    	return id;
    }
    
    public Image getImage()
    {
    	return image;
    }
    
    public int getWidth()
    {
    	return width;
    }

    public int getHeight()
    {
    	return height;
    }
    
    public int getPlayerNum()
    {
    	return playerNum;
    }
    
    public int getEnemyNum()
    {
    	if(playerNum == 1)
    		return 2;
    	else
    		return 1;
    }
    
    public void paintScreen(Graphics g)
    {
    	g.drawImage(image, (int)posX, (int)posY, width, height, null);   	 
    }
	
	public Rectangle2D.Double getBounds()
	{
		return new Rectangle2D.Double(posX, posY, (double) width,(double) height);
	}
	
	public boolean getCollision(Actor other)
	{
		if(getBounds().intersects(other.getBounds()))
		{
			actCollision(other);
			return true;
		}
		return false;
	}
	
	public double getCenterX()
	{
		return posX + width/2;
	}
	
	public double getCenterY()
	{
		return posY + height/2;
	}
	
	public Point2D.Double getPoint()
	{
		return new Point2D.Double(posX, posY);
	}
}
