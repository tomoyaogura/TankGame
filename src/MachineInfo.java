
public class MachineInfo extends Info
{
	private static Tank player1Pos;
	private static Tank player2Pos;
	private int playerID;
	

	public MachineInfo(int player)
	{
		player1Pos = null;
		player2Pos = null;
		playerID = player;
	}
	
	public void addTank(Tank player)
	{
		if(playerID == 1)
			player1Pos = player;
		else if(playerID == 2)
			player2Pos = player;
		else
			System.out.println("Error: addTank() - MachineInfo");
	}
	
	public Tank getPlayer()
	{
		if(playerID == 1)
			return player1Pos;
		else if(playerID == 2)
			return player2Pos;
		System.out.println("Error: getPlayer() - MachineInfo");
		return null;
	}
	
	public Tank getOther()
	{
		if(playerID == 1)
			return player2Pos;
		else if(playerID == 2)
			return player1Pos;
		System.out.println("Error: getPlayer() - MachineInfo");
		return null;
	}
	
	public void setPlayerPos(Tank tankPosition)
	{
		if(playerID == 1)
			player1Pos = tankPosition;
		if(playerID == 2)
			player2Pos = tankPosition;
		System.out.println("Error: setPlayerPos() - MachineInfo");
	}
	
/*
	public void updatePlayer1Pos(Tank tank1Pos)
	{
		player1Pos = tank1Pos;
	}
	
	public void updatePlayer2Pos(Tank tank2Pos)
	{
		player2Pos = tank2Pos;
	}
	public static Tank getPlayer1()
	{
		return player1Pos;
	}
	
	public static Tank getPlayer2()
	{
		return player2Pos;
	}*/


	
	
}
