
import java.io.*;
import java.util.*;

/****
 * This File create the dependencies based on the include Statements. It does so by examining each file and extract the include statements within it
 * if the header file exist in the mysql then a dependency is formed.
 * 
 ****/

/*DONT FORGET TO SPECIFY WHERE YOUR MYSQL FOLDER PATH IS. */
public class Include
{
	private static BufferedWriter writer;
	//Change the MySQLPath to your mySQL code directory.
	private static String mySQLPath = "C:\\Users\\DRH\\Documents\\mysql-server-mysql-8.0.2";
	private static String outputFileName = "IncludeDependencies.txt";
	private static HashSet<String> headers_In_System = new HashSet<String>(); // we will store all header files found in the project here.
	
	/*searches through the mySQL Project to find all the c/cc/h/cpp/hpp files and reads them to see if #include statements exist */
	public static void searchForCFiles(File root) 
	{
		if(root == null ) return;    
		if(root.isDirectory()) 
		{
			for(File file : root.listFiles()) 
			{
				searchForCFiles(file);
			}
		} else if(root.isFile() && root.getName().endsWith(".c")) 
		{
			readFile(root);
		}
		else if(root.isFile() && root.getName().endsWith(".cc")) 
		{
			readFile(root);
		}
		else if(root.isFile() && root.getName().endsWith(".h")) 
		{
			readFile(root);
		}
		else if(root.isFile() && root.getName().endsWith(".cpp")) 
		{
			readFile(root);
		}
		else if(root.isFile() && root.getName().endsWith(".hpp")) 
		{
			readFile(root);
		}
	}
	
	/**
	 * This method will search the mysql directory for all header .h/.hpp files and add it to a list 
	 *  
	 **/
	public static void search_And_AddHeader(File root) {
	
		if(root == null ) return; //just for safety   
		if(root.isDirectory()) 
		{
			for(File file : root.listFiles()) 
			{
				search_And_AddHeader(file);
			}
		} else if(root.isFile() && root.getName().endsWith(".h")) 
		{
			headers_In_System.add(root.getName());
		}		
		else if(root.isFile() && root.getName().endsWith(".hpp")) 
		{
			headers_In_System.add(root.getName());
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		File root = new File(mySQLPath);
		writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(outputFileName), "utf-8"));
	
		System.out.println("Please Wait....");
		search_And_AddHeader(root);
		System.out.println("step 1 done");
		System.out.println("Please Wait....");
		
	//	System.out.println(headers_In_System.size());
		searchForCFiles(root);
		System.out.println("step 2 done");	
		System.out.println("DONE :)");
		
/*	for (String e:	headers_In_System) {
		
		
		System.out.println(e);
	}
		*/
		
		writer.flush();
		writer.close();
	}

	
	/** Reads each file and checks if it contains the #include statement and sanitizes it and check for dependency existence. **/
	
	@SuppressWarnings("deprecation")
	public static void readFile(File file)
	{
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
 
		try
		{
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);			
			while (dis.available() != 0) 
			{
				String line = dis.readLine();
				if (line.startsWith("#include")) //Checks if the  line begins with include
				{
					/* Here we will format the input to extract the header name only */
					line = line.replace("#include", "");
					line = line.trim();
					line = line.replace("<", "");
					line = line.replace(">", "");
					line = line.replace("\"", "");// removing quotes here
						
					if (headers_In_System.contains(line)) {// check if header exist
					
					/*
					for (String e : headers_In_System) 
					{
						
						//System.out.println(e.equals(line));
						if (e.contains(line)) 
						{
						//	System.out.println(line +" " + e);						
							writer.write(file.getParent()+ "\\" + file.getName() +  " " +line);
							writer.newLine();	
						}
						
					}	*/	
						writer.write(file.getParent()+ "\\" + file.getName() +  " " +line); // write the dependency
						writer.newLine();
					}
				}
			}
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
