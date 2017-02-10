
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class MusicPlayer
{
	/* 00 - Main Music
	 * 01 - Reflection
	 * 02~ 04 - Shot
	 * 05 - MoveLong (Not Used)
	 * 06 - ShortMove
	 * 07~14 - Explode
	 * 15 - Fanfare
	 * 16-21 Tank Hit
	 * 22-24 RifleFire
	 * 25 TankLoop(1s)
	 * 26 Dreadnaught (1s)
	 * 27-29 laser shot
	 * 30-31 buzz shot
	 * 32 different buzz
	 * 33- 36 teleport
	 * 37 different teleport
	 * as Should not be long, for it cannot be stopped
	 */
	private static InputStream in;
	private static AudioStream as;
	private static AudioStream main;
	
	public static void playPortalGunSE()
	{
		playMusic(32);
	}
	
	public static void playTankLoopSE()
	{
		playMusic(25);
	}
	
	
	public static void playMainMusic()
	{
		try
		{
			in = new FileInputStream("Music/00.au");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Music not found");
		}
		
		try 
		{
			main = new AudioStream(in);
			AudioPlayer.player.start(main);
		} 
		catch (IOException e) 
		{
			System.out.println("IO Error");
			e.printStackTrace();
		}	
	}
	
	public static void stopMainMusic()
	{
		AudioPlayer.player.stop(main);
	}

	public static void playBulletSE()
	{
		// Random number 2-4
		playMusic((int) (Math.random()*3)+2);
	}
	
	public static void playExplosionSE()
	{
		// Random number 7-14
		playMusic( (int)(Math.random()*7) + 7);
	}

	public static void playReflectionSE()
	{
		playMusic(1);
	}
	
	public static void playMovingSE()
	{
		playMusic(6);
	}
	
	public static void playVictorySE()
	{
		playMusic(15);
	}
	
	
	public static void playDreadMoveSE()
	{
		playMusic(26);
	}
	
	public static void playTeleportSE()
	{
		// Random number 33-36
		playMusic((int) (Math.random()*4)+33);
	}
	
	public static void playChargeShotSE()
	{
		// Random number 30-31
		playMusic((int) (Math.random()*2)+30);
	}
	
	private static void playMusic(int id)
	{
		DecimalFormat formatter = new DecimalFormat("00");
		
		try
		{
			in = new FileInputStream("Music/" + formatter.format(id) + ".au");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Music not found");
		}
		
		try 
		{
			as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} 
		catch (IOException e) 
		{
			System.out.println("IO Error");
			e.printStackTrace();
		}	
	}
	
	public static void stopAllMusic()
	{
		AudioPlayer.player.stop(main);
		AudioPlayer.player.stop(as);
	}

}
