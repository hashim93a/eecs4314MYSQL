import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This is a helper class that maps file names to relative file paths and
 * outputs in cvs format. This was written to ease the workload for EECS4314
 * Assignment 3, specifically for formatting the dependencies files that only
 * come with a file name
 * 
 * @author Prestige Worldwide
 */

public class GetRelativeFilepaths {

	private static String paths = "rel_paths.txt";
	private static String[] dependencies = { "include_dependencies.csv", "srcML_dependencies.csv" };
	private static String src = "src_files/";
	private static String dst = "dst_files/";

	private static ArrayList<String> pathNames = new ArrayList<String>();

	/**
	 * Loads the relative paths into memory
	 * 
	 * @throws FileNotFoundException
	 */

	private static void loadPathNames() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(src + paths));

		while (sc.hasNextLine()) {
			pathNames.add(sc.nextLine());
		}
		sc.close();
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
	 * Match the filenames to file paths
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	private static void findFileNames(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(src + filename));
		System.out.println("Loading file: " + filename);
		ArrayList<String> firstTokens = new ArrayList<String>();
		ArrayList<String> secondTokens = new ArrayList<String>();

		/*
		 * Expecting csv files, comma delim Break into tokens, append file names
		 * to second token Rebuild with commas, print to file
		 */

		// BREAK INTO MANY TOKENS
		while (sc.hasNextLine()) {
			ArrayList<String> tokens = stringToTokensArray(sc.nextLine(), ",");

			firstTokens.add(tokens.get(0));

			// Not all lines have a second token, need a guard
			if (tokens.size() > 1) {
				// Need to check this second token for slashes
				// Then tokenize it further
				ArrayList<String> temp = stringToTokensArray(tokens.get(1), "/");

				if (temp.size() != 1) {
					// if more than one, only add the very last element, should
					// be just the filename
					secondTokens.add(temp.get(temp.size() - 1));
				} else {
					secondTokens.add(tokens.get(1));
				}
			} else {
				System.out.println("Missing dependency: line " + firstTokens.size() + 1);
				secondTokens.add(" ");
			}
		}
		sc.close();

		// FIND FILE NAMES
		System.out.print("Finding file paths...");
		for (int i = 0; i < secondTokens.size(); i++) {
			for (int j = 0; j < pathNames.size(); j++) {
				if (pathNames.get(j).endsWith(secondTokens.get(i))) {
					secondTokens.set(i, pathNames.get(j));
				}
			}
		}
		System.out.println("Done!");

		// REBUILD FILE
		System.out.print("Rebuilding file...");
		PrintStream out = new PrintStream(new File(dst + filename));
		for (int i = 0; i < firstTokens.size(); i++) {
			out.println(firstTokens.get(i) + "," + secondTokens.get(i));
		}
		System.out.println("Done!");

	}

	/**
	 * Main method
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		loadPathNames();
		for (int i = 0; i < dependencies.length; i++) {
			findFileNames(dependencies[i]);
		}
	}

}
