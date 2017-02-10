import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;

public class UpgradeReader
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
            InputStream is = getClass().getResource("UpgradeFile.txt").openStream();
            InputStreamReader isr = new InputStreamReader(is);
            seqReader = new BufferedReader(isr);
            success = true;
        }
        catch(Exception e)
        {
            System.out.println("CannotFind UpgradeFile");
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
            return "UpgradeFile Error";
        }
    }
    
    public ArrayList<JCheckBoxMenuItem> getJCheckBoxUpgrades()
    {
    	ArrayList<JCheckBoxMenuItem> myUpgrades = new ArrayList<JCheckBoxMenuItem>();
    	if(openSeqFile())
    	{	
    		while(!endReached)
    		{
    			String line = readSeq();
    			if(line.equalsIgnoreCase("end"))
    				endReached = true;
    			else 
    				myUpgrades.add(new JCheckBoxMenuItem(line));
    		}
    	}
		else
		{
			System.out.println("File open error");
		}
    	
    	return myUpgrades;
    }


	
}