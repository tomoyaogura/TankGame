

public class ActorInfo extends Info
{

	/*
	 * 	Item Constants
	 */
	public static int ITEM_WIDTH = 15;
	public static int ITEM_HEIGHT = 15;
	
	/*
	 * 	Portal Constants
	 */
	public static final int PORTAL_NEGY = 0;
	public static final int PORTAL_PLUSX = 1;
	public static final int PORTAL_PLUSY = 2;
	public static final int PORTAL_NEGX = 3;
	
	public static final int EXIT = 2;
	public static final int ENTRANCE = 1;
	
	private static Portal entrance_p1;
	private static Portal exit_p1;
	private static Portal entrance_p2;
	private static Portal exit_p2;

	public ActorInfo()
	{
		entrance_p1 = null;
		exit_p1 = null;
		entrance_p2 = null;
		exit_p2 = null;
	}
	
	public void setThisType(Portal p)
	{
		if(p.getOwner() == 1)
		{
			if(p.getType() == ENTRANCE)
			{
				entrance_p1 = p;
				return;
			}
			if(p.getType() == EXIT)
			{
				exit_p1 = p;
				return;
			}
			System.out.println("Error: setThisType()");
		}
		if(p.getOwner() == 2)
		{
			if(p.getType() == ENTRANCE)
			{
				entrance_p2 = p;
				return;
			}
			if(p.getType() == EXIT)
			{
				exit_p2 = p;
				return;
			}
		}
		System.out.println("Error: setThisType()");
	}
	
	public Portal getOther(Portal p)
	{
		if(p == entrance_p1)
			return exit_p1;
		if(p == entrance_p2)
			return exit_p2;
		if(p == exit_p1)
			return entrance_p1;
		if(p == exit_p2)
			return entrance_p2;
		System.out.println("Error: getOther() - ActorInfo");
		return null;
	}
	
	public void removeThisType(int type, int player)
	{
		if(player == 1)
		{
			if(type == ENTRANCE)
			{
				if(entrance_p1 != null)
					entrance_p1.removeSelf();
				return;
			}
			if(type == EXIT)
			{
				if(exit_p1 != null)
					exit_p1.removeSelf();
				return;
			}
			System.out.println("Error: removeThisType()");
		}
		if(player == 2)
		{
			if(type == ENTRANCE)
			{
				if(entrance_p2 != null)
					entrance_p2.removeSelf();
				return;
			}
			if(type == EXIT)
			{
				if(exit_p2 != null)	
					exit_p2.removeSelf();
				return;
			}
		}
		System.out.println("Error: removeThisType()");
	}
	
	/*
	 *  Old method
	 *
	
	public void setEntrance(Portal p, int player)
	{
		if (player == 1)
		{
			entrance_p1 = p;//.clone();
			//entrance_p1.initializeImage();
		}
		else if (player == 2)
		{
			entrance_p2 = p;//.clone();
			//entrance_p2.initializeImage();
		}
		else
			System.out.println("Error: setEntrance() - ActorInfo");
		//p.removeSelf();
	}
	
	public void setExit(Portal p, int player)
	{
		if (player == 1)
		{
			exit_p1 = p;//.clone();
			//exit_p1.initializeImage();
		}
		else if (player == 2)
		{
			exit_p2 = p;//.clone();
			//exit_p2.initializeImage();
		}
		else
			System.out.println("Error: setExit() - ActorInfo");
		//p.removeSelf();
	}
	
	public Portal getOther(int player, Portal self)
	{
		if(self == entrance_p1)
			return exit_p1;
		if(self == entrance_p2)
			return exit_p2;
		if(self == exit_p1)
			return entrance_p1;
		if(self == exit_p2)
			return entrance_p2;
		System.out.println("Error: getOther() - ActorInfo");
		return null;
	}
	

	

	public Portal getEntrance(int player)
	{
		if (player == 1)
			return entrance_p1;
		if (player == 2)
			return entrance_p2;
		System.out.println("Error");
		return null;
	}
	
	public Portal getExit(int player)
	{
		if(player == 1)
			return exit_p1;
		if(player == 2)
			return exit_p2;
		System.out.println("Error");
		return null;
	}
	
	public void removeEntrance(int player)
	{
		if(entrance_p1 != null && player == 1)
			entrance_p1.removeSelf();
		if(entrance_p2 != null && player == 2)
			entrance_p2.removeSelf();
	}
	
	public void removeExit(int player)
	{
		if(exit_p1 != null && player == 1)
			exit_p1.removeSelf();
		if(exit_p2 != null && player == 2)
			exit_p2.removeSelf();
	}*/
	
	
}
