import java.util.Enumeration;
import java.util.Hashtable;


public abstract class Info 
{
	private Hashtable<Integer, Actor> actors;
	
	/*
	 * 	Initializes a hashtable of the actors
	 */
	public Info()
	{
		actors = new Hashtable<Integer,Actor>();
	}
	
	/*
	 * 	Returns the hashtable of the actors
	 */
	public Hashtable<Integer,Actor> getHashtable()
	{
		return actors;
	}
	
	/*
	 * Removes all elements
	 */
	public void destroy()
	{
		Enumeration<Actor>  a = actors.elements();
		Actor etemp;
		
		while(a.hasMoreElements())
		{
			etemp = (Actor)a.nextElement();
			etemp.removeSelf();
		}
	}
	
	/*
	 * 	Calls the act method of all items in the hash.
	 * 	OVERRIDDEN BY MAPINFO - In that case creates new Item, objects do not act
	 */
	public void act()
	{
		Enumeration<Actor> e = getHashtable().elements();
		Actor temp;
		while(e.hasMoreElements())
		{
			temp = (Actor)e.nextElement();
			temp.act();
		}
	}
}
