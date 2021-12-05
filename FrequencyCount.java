import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class FrequencyCount<K,V> {
	
	 private static HashMap<String, Integer> textMap; //Source studied: https://www.baeldung.com/java-initialize-hashmap
	 static {
		 textMap = new HashMap<>();
	 }
	
	//CONSTRUCTORS
	 
	FrequencyCount(List<String> text) {
		textMap.clear();
		for(int i = 0; i < text.size(); i++) {
			if(textMap.containsKey(text.get(i))) {						 //Addition of keys to the map.
				textMap.put(text.get(i), textMap.get(text.get(i)) + 1);  //Keys already inside the map will have it
			}															 //value incremented every time it is found 
			else {														 //within the list of text.
				textMap.put(text.get(i), 1);
			}
		}
	}
	
	FrequencyCount(List<String> text, int degree) {
		textMap.clear();
		ArrayList<String> phraseList = new ArrayList<>();
		for(int i = 0; i <= text.size() - degree; i++) { 	  			//text.size() - (degree) is the last possible index
			String phrase = text.get(i);					  			//position to obtain a specified n-gram.
			for(int x = 0, y = i + 1; x < degree - 1; x++) {
				phrase = phrase + " " + text.get(y);		 			//Creates a phrase based on List text's position i to 
				y++;										 			//degree - 1.
			}
			phraseList.add(phrase);
		}
		for(int i = 0; i < phraseList.size(); i++) {				    	
			if(textMap.containsKey(phraseList.get(i))) {
				textMap.put(phraseList.get(i), textMap.get(phraseList.get(i)) + 1);
			}
			else {
				textMap.put(phraseList.get(i), 1);
			}
		}
	}
	
	//REQUIRED METHODS
	
	List<String> head() {
		/*
		 * Returns the 20 most frequent keys from the text file in the form of an ordered list. 
		 * The first key in the list will be the most frequent.
		 * Utilizes the tokensHeadOrTail() and reverseList() Helper Methods.
		 */
		ArrayList<String> keys = new ArrayList<>(textMap.keySet());
		ArrayList<Integer> values = new ArrayList<>(textMap.values());
		values.sort(null);
		values = (ArrayList<Integer>) reverseList(values);								//ArrayList of values is now sorted in descending order.
		ArrayList<String> head = (ArrayList<String>) keysMostOrLeast(keys, values);
		displayStats(head);
		return head;	
	}

	List<String> tail() {
		/*
		 * Returns the 20 least frequent keys from the text file in the form of an ordered list.
		 * The first key in the list will be the most frequent of the least frequent.
		 * Utilizes the tokensHeadOrTail() and reverseList() Helper Methods. 
		 */
		ArrayList<String> keys = new ArrayList<>(textMap.keySet());
		ArrayList<Integer> values = new ArrayList<>(textMap.values());
		values.sort(null);
		ArrayList<String> tempTail = (ArrayList<String>) keysMostOrLeast(keys, values);  //tempTail was initialized such that the respective 
		ArrayList<String> finalTail = (ArrayList<String>) reverseList(tempTail); 		 //values of the keys are in ascending order,
		displayStats(finalTail);														 //so it needs to be reversed.
		return finalTail;						 										
	}
		
	String randomToken() {
		/*
		 * Returns a random word from the text file.
		 */
		ArrayList<String> keys = new ArrayList<>(textMap.keySet());
		int randomRange = textMap.size();
		String randomToken = keys.get((int) (Math.random() * (randomRange)));
		return randomToken;
	}
	
	int count(String token) {
		/*
		 * Returns the amount of time the specified word appears in the text file.
		 * @param token: The specified word.
		 */
		if(!textMap.containsKey(token)) {
			return 0;
		}
		int counter = textMap.get(token);
		return counter;
	}
	
	double percent(String token) {
		/*
		 * Calculates and returns specified word's percentage of the input text file's total word count.
		 * Method type changed to double as to the original int to provide more accurate percentages.
		 * Utilizes the wordCount() Helper Method. 
		 * @param token: The desired word to find a percentage of.
		 */
		if(!textMap.containsKey(token)) {
			return 0;
		}
		double percentage =((double) textMap.get(token) / (double) wordCount());
		return percentage;
	}
		
	int add(String token) {
		/*
		 * Inserts a token into the map with a value that depends on whether that token is currently
		 * part of the map or not.
		 * Returns the token's value after its insertion into the map.
		 * @param token: The desired word to add.
		 */
		if(textMap.containsKey(token)) {
			textMap.put(token, textMap.get(token) + 1);
		}
		else {
			textMap.put(token, 1);
		}
		return textMap.get(token);	
	}
	
	//HELPER METHODS
	
	private void displayStats(List<String> headOrTail) {
		/*
		 * Displays the keys from either the Head or Tail list
		 * along with their respective values and percentages.
		 * @param headOrTail: The list that represents the Head or the Tail.
		 */
		for(int i = 0; i < headOrTail.size(); i++) {
				System.out.printf("%-14s", headOrTail.get(i));
			System.out.printf("%-6d", textMap.get(headOrTail.get(i)));
			System.out.print(" ");
			System.out.print("(");
			System.out.printf("%.5f", percent(headOrTail.get(i)));
			System.out.println(")");
		}
	}
		
	private <T> List<T> reverseList(List<T> list) {
		/*
		 * Returns a reversed version of a specified List.
		 * @param list: A generic list to reverse.
		 */
		ArrayList<T> reversed = new ArrayList<>();
		for(int i = list.size() - 1; i >= 0; i--) {
			reversed.add(list.get(i));
		}
		return reversed;
	}

	private List<String> keysMostOrLeast(List<String> keyList, List<Integer> sortedValueList) {
		/*
		 * Returns a list of the 20 most frequent OR 20 least frequent words in a text file.
		 * A sortedValueList passed in descending order will return the 20 most frequent words in value-descending order (head).
		 * A sortedValueList passed in ascending order will return the 20 least frequent words in value-ascending order(reverse order of tail).
		 * Ties are disregarded so keys will not be inserted into the list once it has been filled with 20 of them.
		 * @param keyList: The list containing all the keys from the Hash Map.
		 * @param sortedValueList: A sorted list containing all the values from the Hash Map.
		 */
		ArrayList<String> headOrTail = new ArrayList<>();
		while(headOrTail.size() != 20) {									//Keep iterating through the entire keyList for 
			for(int i = 0; i < keyList.size(); i++) {					 	//each possible iteration of the while loop.
				if(textMap.get(keyList.get(i)) == sortedValueList.get(0)) { 
					if(headOrTail.contains(keyList.get(i))) {                
						continue;											//Find the keys whose respective values match the current 
					}														//largest/smallest value in sortedValueList, which will always
					headOrTail.add(keyList.get(i));						    //be located in index 0, and add it to the head/tail list.	  
					sortedValueList.remove(0);							    //This simulates an "alignment" of the head/tail list's keys to  
					break;												    //their respective values given by the sortedValueList parameter.
				}															
			}																
			if(headOrTail.size() == keyList.size()) {					    //In the event the number of keys is less than 20, the
				break;														//headOrTail list will include all of the keys sorted in
			}																//value-descending order.
		}
		return headOrTail;
	}
	
	private int wordCount() {
		/*
		 * Calculates and returns the total word count of the input text file.
		 */
		ArrayList<Integer> valueList = new ArrayList<>(textMap.values());
		int sum = 0;
		for(int i = 0; i < valueList.size(); i++) {
			sum = sum + valueList.get(i);
		}
		return sum;
	}
	

	
	
	
} //class
