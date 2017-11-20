import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AnalyzeDependencies {
	
	private static String[] FILES = {"include_dependencies.raw.ta","srcML_dependencies.raw.ta","understand_dependencies.raw.ta"};
	private static String src = "src_files/";
	private static String dst = "dst_files/comparison_output.txt";
	private static PrintStream f_out;
	
	private static String FILE_KEY = "$INSTANCE";
	private static String LINK_KEY = "cLinks";
	
	
	/**
	 * Main method
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		f_out = new PrintStream(new File(dst));
		
		Data d0 = loadDependencies(FILES[0]);
		Data d1 = loadDependencies(FILES[1]);
		Data d2 = loadDependencies(FILES[2]);
		
		compareData(d0, d1);
		compareData(d1, d2);
		compareData(d0, d2);
		
		getExclusive(d1,d2);
		getCommon(d1,d2);
		
		getExclusive(d0,d2);
		getCommon(d0,d2);
		
		getExclusive(d0,d1);
		getCommon(d0,d1);
	}
	
	/**
	 * Load in the dependencies 
	 * 
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	private static Data loadDependencies(String filename) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(src + filename));
		ArrayList<String> temp_file = new ArrayList<String>();
		ArrayList<String> temp_dep = new ArrayList<String>();
		
		while(sc.hasNextLine()){
			ArrayList<String> temp = stringToTokensArray(sc.nextLine(), " ");
			
			if(temp.get(0).equals(FILE_KEY)){
				temp_file.add(temp.get(1));
			} else if(temp.get(0).equals(LINK_KEY)){
				if(temp.size() < 3){
					temp_dep.add(temp.get(1) + "->");
				} else {
					temp_dep.add(temp.get(1) + "->" + temp.get(2));
				}
			} else {
				// Should not get here
			}
		}
		sc.close();
		return new Data(filename, temp_file, temp_dep);
	}
	
	/**
	 * Converts a string into an arraylist of tokens
	 * 
	 * @param input
	 *            The input string
	 * @param delim
	 *            The delimeter
	 * @return
	 */
	private static ArrayList<String> stringToTokensArray(String input, String delim) {
		ArrayList<String> output = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(input, delim);

		while (st.hasMoreTokens()) {
			output.add(st.nextToken());
		}

		return output;
	}
	
	/**
	 * Every element in A1 exists in A2
	 * Every element in A2 exists in A1
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	private static void compareArrayLists(ArrayList<String> a1, ArrayList<String> a2){
		
		int sim = 0;
		int dif1 = 0;
		int dif2 = 0;
		
		Collection<String> src1 = new ArrayList(a1);
		Collection<String> src2 = new ArrayList(a2);
		
		List<String> dst1 = new ArrayList(a1);
		List<String> dst2 = new ArrayList(a2);
		
		outln("A1 Size: " + a1.size());
		outln("A2 Size: " + a2.size());
		out("Comparing...");
		
		dst1.removeAll(src2);
		dst2.removeAll(src1);
		
//		for(int i = 0; i < a1.size(); i++){
//			if(a2.contains(a1.get(i))){
//				sim++;
//				a2.remove(a2.indexOf(a1.get(i)));
//			} else {
//				dif1++;
//			}
//		}
//		for(int i = 0; i < a2.size(); i++){
//			if(a1.contains(a2.get(i))){
//				sim++;
//			} else {
//				dif2++;
//			}
//		}
		outln("Done!");
		outln("Common Elements:........." + (src1.size()+src2.size()-dst1.size()-dst2.size())/2);
		outln("A1-Exclusive Elements:..." + dst1.size());
		outln("A2-Exclusive Elements:..." + dst2.size());
	}
	
private static void getExclusive(Data d1, Data d2) throws FileNotFoundException{
		
		Collection<String> src1 = new ArrayList(d1.getDependencies());
		Collection<String> src2 = new ArrayList(d2.getDependencies());
		
		List<String> dst1 = new ArrayList(d1.getDependencies());
		List<String> dst2 = new ArrayList(d2.getDependencies());
		
		dst1.removeAll(src2);
		dst2.removeAll(src1);
		
		f_out = new PrintStream(new File("dst_files/ALL_"+d1.getSrc()+d2.getSrc()+".txt"));
		
		//outln("Comparing: " + d1.getSrc() + " and " + d2.getSrc());
		//outln("Unique Elements in " + d1.getSrc());
		for(int i = 0; i < dst1.size(); i++){
			outln(dst1.get(i));
		}
		
		//f_out = new PrintStream(new File("dst_files/unique_"+d2.getSrc()+d1.getSrc()));
		//outln("Unique Elements in " + d2.getSrc());
		for(int i = 0; i < dst2.size(); i++){
			outln(dst2.get(i));
		}
		
	}

private static void getCommon(Data d1, Data d2) throws FileNotFoundException{
	
	Collection<String> src1 = new ArrayList(d1.getDependencies());
	Collection<String> src2 = new ArrayList(d2.getDependencies());
	
	List<String> dst1 = new ArrayList(d1.getDependencies());
	List<String> dst2 = new ArrayList(d2.getDependencies());
	
	dst1.retainAll(dst2);
	
	//f_out = new PrintStream(new File("dst_files/common_"+d1.getSrc()+d2.getSrc()));
	
	//outln("Comparing: " + d1.getSrc() + " and " + d2.getSrc());
	//outln("Common Elements in " + d1.getSrc() + " and " + d2.getSrc());
	for(int i = 0; i < dst1.size(); i++){
		outln(dst1.get(i));
	}
	
}
	
	/**
	 * Compares two data objects
	 * 
	 * @param d1
	 * @param d2
	 */
	private static void compareData(Data d1, Data d2){
		Data cp_d1 = new Data(d1);
		Data cp_d2 = new Data(d2);
		
		outln("============= DATA START =============");
		outln("A1: " + cp_d1.getSrc());
		outln("A2: " + cp_d2.getSrc());
		
		outln("Comparing filenames");
		compareArrayLists(d1.getFilenames(), cp_d2.getFilenames());
		
		outln("Comparing dependencies");
		compareArrayLists(d1.getDependencies(), cp_d2.getDependencies());
		
		outln("============= DATA END =============");
	}
	
	/**
	 * Convenience print method
	 * @param output
	 */
	private static void out(String output){
		System.out.print(output);
		f_out.print(output);
	}
	
	/**
	 * Convenience print method
	 * @param output
	 */
	private static void outln(String output){
		System.out.println(output);
		f_out.println(output);
	}
}

/**
 * Anonymous class to handle the data
 * 
 * @author Ante Pimentel
 *
 */
class Data {
	
	private String src = "";
	private ArrayList<String> filenames;
	private ArrayList<String> dependencies;
	
	// DEEP COPY
	public Data(Data d){
		this.src = d.getSrc();
		this.filenames = new ArrayList<String>(d.getFilenames());
		this.dependencies = new ArrayList<String>(d.getDependencies());
	}
	
	public Data(String src, ArrayList<String> files, ArrayList<String> dep){
		this.src = src;
		this.filenames = files;
		this.dependencies = dep;
	}
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
	public ArrayList<String> getFilenames() {
		return filenames;
	}

	public void setFilenames(ArrayList<String> filesnames) {
		this.filenames = filesnames;
	}

	public ArrayList<String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(ArrayList<String> dependencies) {
		this.dependencies = dependencies;
	}
}
