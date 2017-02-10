import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;



public class Map
{
	
	/*
	 * 	Map Building Constants
	 */
	public static int DEFAULT_FRAME = 10;
	
	public static int WALL_WIDTH = 20;
	public static int SMALL_BOX = 50;
	/*
	 *  Text file reading tools
	 */
	private boolean endReached;	
	private BufferedReader seqReader;
    
    public boolean openSeqFile(String fileName)
    {
        boolean success;
        try{
        	if(seqReader != null)
        			seqReader.close();
        	
            InputStream is = getClass().getResource(fileName).openStream();
            InputStreamReader isr = new InputStreamReader(is);
            seqReader = new BufferedReader(isr);
            success = true;
        }
        catch(Exception e)
        {
            System.out.println("CannotFind MapFile");
            success = false;
        };
        return success;
    }
    
    public String readSeq()
    {
        String str;
        try
        {
            str = seqReader.readLine();
            return str;
        }
        catch(Exception e)
        {
            return "MapFile Error";
        }
    }
    
    public void setMap()
    {
    	setBorder();
    	String selection = TankFrame.getMapSelection();
    	if(!selection.equals("Blank.txt"))
    	{	
    		if(openSeqFile(selection))  	
    		while(!endReached)
    		{
    			StringTokenizer reader = new StringTokenizer(readSeq());
    			String command = reader.nextToken();
    			if(command.equals("end"))
    				endReached = true;
    			else if(command.equals("BarX"))
    			{
    				int posX = Integer.parseInt(reader.nextToken());
    				int posY = Integer.parseInt(reader.nextToken());
    				int length = Integer.parseInt(reader.nextToken());
    				new Obstacle(posX, posY, length, DEFAULT_FRAME);
    			}
    			else if(command.equals("BarY"))
    			{
        			int posX = Integer.parseInt(reader.nextToken());
        			int posY = Integer.parseInt(reader.nextToken());
        			int length = Integer.parseInt(reader.nextToken());
        			new Obstacle(posX, posY, DEFAULT_FRAME, length);
    			}
    			else if(command.equals("Box"))
    			{
    				int posX = Integer.parseInt(reader.nextToken());
    				int posY = Integer.parseInt(reader.nextToken());
    				int length = Integer.parseInt(reader.nextToken());
    				new Obstacle(posX,posY, length,length);
    			}
    			else
    			{
    				System.out.println("Map Text has no end: Terminating read");
    				endReached = true;
    			}
    		}
    		else
    		{
    			System.out.println("File open error");
    		}
    	}
    }

	public void setBorder()
	{
		new Obstacle(0,0,										TankFrame.SCREEN_WIDTH,		DEFAULT_FRAME);
		new Obstacle(TankFrame.SCREEN_WIDTH-DEFAULT_FRAME,0,	DEFAULT_FRAME,				TankFrame.SCREEN_HEIGHT);
		new Obstacle(0,TankFrame.SCREEN_HEIGHT-DEFAULT_FRAME,	TankFrame.SCREEN_HEIGHT,	DEFAULT_FRAME);
		new Obstacle(0,0,										DEFAULT_FRAME,				TankFrame.SCREEN_HEIGHT);
	}
	
}