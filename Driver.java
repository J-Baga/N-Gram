import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Driver {
		
	public static void main(String args[]) throws FileNotFoundException, IllegalArgumentException {
		
		if(args.length != 1) {	//User did not input any source or inputed more than one source.
			System.out.println("Error: No source or too many sources detected. "
					+ "Please load the program again, following this format: java Driver *insert source here*"
					+ "\nExample: java Driver C:\\Users\\Shawn\\Desktop\\CS310\\studentstofail.txt"); 
			System.exit(0);
		}
	
		List<String> text = fileToString(args[0]); 

		demoOne(text);
		demoFour(text);						
	} 
	
	private static List<String> fileToString(String textFileLocation) throws FileNotFoundException, IllegalArgumentException { 
		/*
		 * Parses a text file and constructs and returns an ArrayList based on its individual Strings, including numbers and symbols.
		 * @param textFileLocation: The location of the text file to be constructed into an List.
		 */
		ArrayList<String> textList = new ArrayList<>(); //Source studied: https://www.java67.com/2016/07/how-to-read-text-file-into-arraylist-in-java.html
		File textFile = new File(textFileLocation);
		if(!textFile.exists()) {						//User did not input a valid file or file location.
			System.out.println("Error: The source file does not exist. "
					+ "Please load the program again, following this format: java Driver *insert source here*"
					+ "\nExample: java Driver C:\\Users\\Shawn\\Desktop\\LectureScripts\\program5.txt"); 
			System.exit(0);
		}
		Scanner scan = new Scanner(textFile);
		while(scan.hasNext()) { 						//Source studied: https://www.youtube.com/watch?v=78tYnmGKdM4
			String word = scan.next();
			textList.add(word.toLowerCase());
		}
		scan.close();
		return textList;
	}
	
	public static void demoOne(List<String> textList) {
		/*
		 * Builds a FrequencyCount object using the List of pre-processed tokens extracted from the text file, with a degree of one. 
		 * Displays the 20 most and least frequent words from the text, along with their respective word counts
		 * and percentages based on the cumulative word count.
		 */
		System.out.println("---------------------Demo One Text Statistics---------------------");
		
		FrequencyCount<String, Integer> demoOneText = new FrequencyCount<>(textList);

		System.out.println("20 Most Frequent Keys, with Count and Percentage:");
		
		demoOneText.head();
		
		System.out.println();
		
		System.out.println("20 Least Frequent Keys, with Count and Percentage:");
		
		demoOneText.tail();

		
		System.out.println("------------------------------------------------------------------" + "\n");
	}
	
	public static void demoFour(List<String> textList) {
		/*
		 * Similar to the demoOne() method, but the FrequencyCount object will be built with a degree of four instead of one.
		 * Instead of displaying statistics about the map's 20 most and least frequent keys, four random keys will be extracted
		 * from the object and  formatted into a poem.
		 */
		System.out.println("-----------------------Demo Four Random Poem---------------------- \n");

		FrequencyCount<String, Integer> demoFourText = new FrequencyCount<>(textList, 4);
		
		System.out.println(demoFourText.randomToken());
		System.out.print(demoFourText.randomToken() + " ");
		System.out.println(demoFourText.randomToken());
		System.out.println(demoFourText.randomToken() + "\n");
		System.out.println("                       - Anonymous, 2020");
		System.out.println("------------------------------------------------------------------" + "\n");
	}
				
} 
