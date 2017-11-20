import java.io.*;
import java.util.*;

/*DONT FORGET TO SPECIFY WHERE YOUR MYSQL FOLDER PATH IS. */
public class Paths
{
	public static BufferedWriter writer;
	//Change the MySQLPath to your mySQL code directory.
	public static String mySQLPath = "C:\\Users\\DRH\\Documents\\mysql-server-mysql-8.0.2";
	public static String outputFileName = "Paths.txt";
	
	/*searches through the mySQL Project to find all the c/cc/h/hpp files and reads them to see if #include statements exist */
	public static void searchForCFiles(File root) 
		{
		if(root == null ) return; //just for safety   
		if(root.isDirectory()) 
		{
			for(File file : root.listFiles()) 
			{
				searchForCFiles(file);
			}
		} else if(root.isFile() && root.getName().endsWith(".c")) 
		{
		//	System.out.println("found: " + root.getPath());
			readFile(root);
		}
		else if(root.isFile() && root.getName().endsWith(".cc")) 
		{
		//	System.out.println("found: " + root.getPath());
			readFile(root);
		}
		else if(root.isFile() && root.getName().endsWith(".h")) 
		{
		//	System.out.println("found: " + root.getPath());
			readFile(root);
		}
		else if(root.isFile() && root.getName().endsWith(".cpp")) 
		{
		//	System.out.println("found: " + root.getPath());
			readFile(root);
		}
		else if(root.isFile() && root.getName().endsWith(".hpp")) 
		{
		//	System.out.println("found: " + root.getPath());
			readFile(root);
		}
	}
	
	//MAIN
	
	public static void main(String[] args) throws IOException
	{
		File root = new File(mySQLPath);
		//File listFile = new File(lsTAPath);
		List<String> instances = new ArrayList<String>();
		List<String> outputt = new ArrayList<String>();
		List<File> javaFiles = new ArrayList<File>();
		
		writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(outputFileName), "utf-8")); 
		searchForCFiles(root);
		
		
		
		writer.flush();
		writer.close();
	}

	
	/* Reads each file and checks if it contains an #include statement. 
		*/
	@SuppressWarnings("deprecation")
	public static void readFile(File file)
	{
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
 
		try
		{
			fis = new FileInputStream(file);
	 
			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
	 
			// dis.available() returns 0 if the file does not have more lines.
			while (dis.available() != 0) 
			{
				String line = dis.readLine();
				if (dis.available() == 0)
				{
					writer.write(file.getParent()+ "\\" + file.getName());
					writer.newLine();
				}
			}
			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close(); 
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
		  e.printStackTrace();
		}		
	}
}