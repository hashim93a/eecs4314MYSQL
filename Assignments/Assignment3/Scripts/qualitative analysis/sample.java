import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class sample {

	// Standard output, used for switching between file writers
	final static PrintStream stdOut = System.out;
	
	public sample() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// 1. Pull random sample size from total dependencies
		// 2. See what percentage of dependencies is from common, and exclusive
		// 3. Give example by going though mySQL files code (NO CODING REQUIRED)
		
		
		
		//generate random numbers
		Integer[] a = getRandom(382, 81983);
		//generate random dependencies from understand dependencies and print to txt file
		printSampleDependencies(a, "all_dependencies.txt", "sample_dependencies.txt");
		
		// Find number of sample dependencies present in: Understand, Include, and srcML.
		compare("sample_dependencies.txt");
		
	}
	
	 // returns array consisting of unique random numbers between 1 and totalDependencies.  
	// array size is sampleSize
	 public static Integer[] getRandom(int sampleSize, int totalDependencies) {                                    
		// Initialize variables, randomizer r and set of sample dependencies
			int s = sampleSize;
			int t = totalDependencies;
			Random r = new Random();
			// Start with set to get unique numbers
			Set<Integer> st = new HashSet<>();
			
			// Loop and insert random numbers into set until it reaches sample size
			while (st.size() < s + 1){
				// Adds 1 <= r <= totalDependencies (No duplicates. "Set")
			    st.add(r.nextInt(t));
			}
			//System.out.println("Set st has size: " + st.size());
			
			// Convert to array for dependency lookup
			Integer[] a = st.toArray(new Integer[st.size()]);
			return a;
	    }
	 
	 // prints array of dependency line numbers arr to file s
	 public static void printDependLines(Integer[] arr, String s) throws FileNotFoundException{
		// Print out random dependency lines to sample.txt
			File file = new File(s);
		    PrintStream ps = new PrintStream(file);
		    System.setOut(ps);
		    //System.out.println("To File");
			for(int i = 1; i < arr.length; i++){
				System.out.println(arr[i]);
			}
			
			// Set back to standard output
			System.setOut(stdOut);
			//System.out.println("Hi");
	 }

	 // prints sample dependencies based on dependency lines array and file s
	 public static void printSampleDependencies(Integer[] arr, String s, String s2) throws IOException{
		// The name of the file to open.
		    String fileName = s;
		    int counter = 0;
		    // Reference one line at a time
		    String line = null;
		    FileReader fileReader = null;    
		    fileReader = new FileReader(fileName);

		    // Wrap FileReader in BufferedReader.
		    BufferedReader bufferedReader = new BufferedReader(fileReader);

		     // Print out dependencies to sample_dependencies.txt
		     File file2 = new File(s2);
		     PrintStream ps2 = new PrintStream(file2);
		     System.setOut(ps2);
		     while((line = bufferedReader.readLine()) != null) {
		        for(int j = 0; j < arr.length; j++){
		         //When line numbers match print out associated dependency	
		         if(counter == arr[j])
			        {
			           System.out.println(bufferedReader.readLine());
			        }
		        	   }
		        counter++;
		        }
		        bufferedReader.close();
		        
		     // Set back to standard output
			 System.setOut(stdOut);
	 }
	 
	// compare dependencies in file "s" to understand, include, and srcML dependencies and print results to results.txt
		 public static void compare(String s) throws IOException{
			// The name of the file to open.
			    String fileName = s;
			    int counterUnderstand = 0;
			    int counterInclude = 0;
			    int countersrcML = 0;
			    int counterNot = 0;
			    // Reference one line at a time
			    String line = null;
			    String line2 = null;
			    String line3 = null;
			    String line4 = null;
			    FileReader fileReader = null;    
			    fileReader = new FileReader(fileName);
			    FileReader fileReader2 = null;    
			    fileReader2 = new FileReader("understand_dependencies.txt");
			    FileReader fileReader3 = null;    
			    fileReader3 = new FileReader("include_dependencies.txt");
			    FileReader fileReader4 = null;    
			    fileReader4 = new FileReader("srcML_dependencies.txt");

			    // Wrap FileReader in BufferedReader.
			    BufferedReader bufferedReader = new BufferedReader(fileReader);
			    BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			    BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			    BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			    

			     // Declare file to print results to
			     File file3 = new File("results.txt");
			     PrintStream ps2 = new PrintStream(file3);
			     System.setOut(ps2);
			     
			     // Declare ArrayList to store comparison data
			     ArrayList<String> arrList = new ArrayList<String>();
			     //Read in sample dependencies and store into arrayList
			     while((line = bufferedReader.readLine()) != null){
			    	 arrList.add(line);
			     }
			     bufferedReader.close();
			     
			     //See how many sample dependencies are in Understand dependencies
			     while((line2 = bufferedReader2.readLine()) != null){
			    	 if(arrList.contains(line2)){
			    		 counterUnderstand++;
			    	 }
			     }
			     bufferedReader2.close();
			     
			     // Number of sample dependencies not from Understand method
			     counterNot = 382 - counterUnderstand;
			     
			     
			     //See how many sample dependencies are in Include dependencies
			     while((line3 = bufferedReader3.readLine()) != null){
			    	 if(arrList.contains(line3)){
			    		 counterInclude++;
			    	 }
			     }
			     bufferedReader3.close();
			     
			     //See how many sample dependencies are in srcML dependencies
			     while((line4 = bufferedReader4.readLine()) != null){
			    	 if(arrList.contains(line4)){
			    		 countersrcML++;
			    	 }
			     }
			     bufferedReader4.close();
			     
			     
			     // Percentages
			     double uPercent = (counterUnderstand/382.0) * 100;
			     double iPercent = (counterInclude/382.0) * 100;
			     double sPercent = (countersrcML/382.0) * 100;
			     
			     int ustPercent = (int) uPercent;
			     int iclPercent = (int) iPercent;
			     int smlPercent = (int) sPercent;
			     
			     
			     // Precision and Recall for srcML method
			     
			     //True Positive = Sample dependencies also in Understand dependencies
			     double srcMLTP = counterUnderstand;
			     // False Positive = Sample dependencies also in Include dependencies
			     double srcMLFP = counterInclude;
			     // False Negative = Sample dependencies not in Understand dependencies
			     double srcMLFN = counterNot;
			     
			     double srcMLPrecision = (srcMLTP/(srcMLTP+srcMLFP)) * 100.0;
			     double srcMLRecall = (srcMLTP/(srcMLTP+srcMLFN)) * 100.0;
			     
			     
			     // Precision and Recall for Include method
			     
			     //True Positive = Sample dependencies also in Understand dependencies
			     double includeTP = counterUnderstand;
			     // False Positive = Sample dependencies also in srcML dependencies
			     double includeFP = countersrcML;
			     // False Negative = Sample dependencies not in Understand dependencies
			     double includeFN = counterNot;
			     
			     double includePrecision = (includeTP/(includeTP+includeFP)) * 100.0;
			     double includeRecall = (includeTP/(includeTP+includeFN)) * 100.0;
			     
			     
			     	//Print out results to results.txt
			     	System.out.println("Sample Size: " + arrList.size());
			     	System.out.println("Understand Dependencies in sample size: " + counterUnderstand);
			     	System.out.println("Percentage in sample size: " + ustPercent + "%");
			     	System.out.println("Include Dependencies in sample size: " + counterInclude);
			     	System.out.println("Percentage in sample size: " + iclPercent + "%");
			     	System.out.println("srcML Dependencies in sample size: " + countersrcML);
			     	System.out.println("Percentage in sample size: " + smlPercent + "%" + "\n");
			     	
			     	//Print out Precision and Recall for srcML method
			     	System.out.println("Precision and Recall for srcML method");
			     	System.out.println("TP: " + srcMLTP + ", FP: " + srcMLFP + ", FN: " + srcMLFN);
			     	System.out.println("Precision = TP/(TP + FP)");
			     	System.out.println("Precision = " + srcMLTP + "/(" + srcMLTP + " + " + srcMLFP + ")");
			     	System.out.println("Precision = " + (int) srcMLPrecision + "%");
			     	System.out.println("Recall = TP/(TP + FN)");
			     	System.out.println("Recall = " + srcMLTP + "/(" + srcMLTP + " + " + srcMLFN + ")");
			     	System.out.println("Recall = " + (int) srcMLRecall + "%" + "\n");
			     	
			     	//Print out Precision and Recall for Include method
			     	System.out.println("Precision and Recall for Include method");
			     	System.out.println("TP: " + includeTP + ", FP: " + includeFP + ", FN: " + includeFN);
			     	System.out.println("Precision = TP/(TP + FP)");
			     	System.out.println("Precision = " + includeTP + "/(" + includeTP + " + " + includeFP + ")");
			     	System.out.println("Precision = " + (int) includePrecision + "%");
			     	System.out.println("Recall = TP/(TP + FN)");
			     	System.out.println("Recall = " + includeTP + "/(" + includeTP + " + " + includeFN + ")");
			     	System.out.println("Recall = " + (int) includeRecall + "%" + "\n");
			        
			        
			     // Set back to standard output
				 System.setOut(stdOut);
		 }
	 
}
