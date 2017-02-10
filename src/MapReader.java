import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JRadioButtonMenuItem;


public class MapReader 
{	
	/*
	 *  Text file reading tools
	 */
		private boolean endReached;	
    	private BufferedReader seqReader;
    
    	public boolean openSeqFile()
	    {
	        boolean success;
	        try{
	        	if(seqReader != null)
	        			seqReader.close();
	        	
	            InputStream is = getClass().getResource("MapFile.txt").openStream();
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
	    
	    public ArrayList<JRadioButtonMenuItem> getJRadioButtonMaps()
	    {
	    	ArrayList<JRadioButtonMenuItem> myMap = new ArrayList<JRadioButtonMenuItem>();
	    	if(openSeqFile())
	    	{	
	    		while(!endReached)
	    		{
	    			String line = readSeq();
	    			if(line.equalsIgnoreCase("end"))
	    				endReached = true;
	    			else
	    				myMap.add(new JRadioButtonMenuItem(line));
	    		}
	    	}
			else
			{
				System.out.println("File open error");
			}
	    	
	    	return myMap;
	    }
}
