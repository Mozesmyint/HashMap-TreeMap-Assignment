package mru.application;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * COMP 2503 Fall 2023 Assignment 4
 * 
 * This program must read a input stream and keeps track of the frequency at
 * which an avenger is mentioned either by name or alias or performer's last name. The program must use HashMaps
 * for keeping track of the Avenger Objects, and it must use TreeMaps
 * for storing the data. 
 * 
 * @author Maryam Elahi and Mozes Aung
 * @date Fall 2023
 */

public class A4 {

	public String[][] avengerRoster = { { "captainamerica", "rogers", "evans" }, { "ironman", "stark", "downey" },
			{ "blackwidow", "romanoff", "johansson" }, { "hulk", "banner", "ruffalo" },
			{ "blackpanther", "tchalla", "boseman" }, { "thor", "odinson", "hemsworth" },
			{ "hawkeye", "barton", "renner" }, { "warmachine", "rhodes", "cheadle" },
			{ "spiderman", "parker", "holland" }, { "wintersoldier", "barnes", "stan" } };

	private int topN = 4;
	private int totalwordcount = 0;
	private Scanner input = new Scanner(System.in);

	HashMap<Avenger, String> hMap = new HashMap<Avenger, String>();
	
	TreeMap<Avenger, String> alphabticalMap = new TreeMap<Avenger, String>(Comparator.naturalOrder());
	TreeMap<Avenger, String> mentionMap = new TreeMap<Avenger, String>(new AvengerMentionComparator());
	TreeMap<Avenger, String> mostPopularAvenger = new TreeMap<Avenger, String>(new AvengerComparator());
	TreeMap<Avenger, String> mostPopularPerformer = new TreeMap<Avenger, String>(new PerformerComparator());
	
	/**
	 * Main method to start the program
	 */
	public static void main(String[] args) {
		A4 a4 = new A4();
		a4.run();
	}

	/**
	 * runs the program by calling methods to first read input and then
	 * call an ordering method and finally the print method
	 */
	public void run() {
		readInput();
		createdOrderedTreeMaps();
		printResults();
	}

	/**
	 * Iterates through the hashmap and adds them into the corresponding treemaps
	 * which use different ordering through the comparators that were initialized earlier
	 */
	private void createdOrderedTreeMaps() {
		for(Avenger a : hMap.keySet()) {
			alphabticalMap.put(a, hMap.get(a));
			mentionMap.put(a, hMap.get(a));
			mostPopularAvenger.put(a, hMap.get(a));
			mostPopularPerformer.put(a, hMap.get(a));
		}
	}

	/**
	 * read the input stream and keep track how many times avengers are mentioned by
	 * alias or last name.
	 */
	private void readInput() {
		while(input.hasNext()) {
			String word = input.next();
			word = cleanWord(word);
			
			if(!word.isEmpty()) {
				totalwordcount++;
				updateAvengerMap(word);
			}
		}
	}
	
	/**
	 * Reads the given parameter and checks if it matches with the given roster array
	 * to then add the name or if it already exists in the hashmap add to the 
	 * corresponding frequency
	 * @param word
	 */
	private void updateAvengerMap(String word) {
		//Iterate through the given roster to check for matches
		for(int i = 0; i < avengerRoster.length; i++) {
			if(word.equals(avengerRoster[i][0]) || word.equals(avengerRoster[i][1]) || word.equals(avengerRoster[i][2])) {
				
				//Create an object with the matching word
				Avenger newA = new Avenger();
				newA.setHeroAlias(avengerRoster[i][0]);
				newA.setHeroName(avengerRoster[i][1]);
				newA.setPerformer(avengerRoster[i][2]);
				
				//Check if the Avenger exists in the hashmap
				Avenger a = findA(word);
				
				//If the search returns null then it means it already exists in the hashmap
				//therefore we add in frequency depending on the given parameter match
				if(a != null) {
					if(word.equals(avengerRoster[i][0]))
						a.setAliasFreq(a.getAliasFreq() + 1);
					else if(word.equals(avengerRoster[i][1]))
						a.setNameFreq(a.getNameFreq() + 1);
					else if(word.equals(avengerRoster[i][2]))
						a.setPerformerFreq(a.getPerformerFreq() + 1);
				} 
				//If null it will create a new member on the list 
				else {
					a = newA;
					
                    if (word.equals(avengerRoster[i][0])) 
                    	a.setAliasFreq(1);
                    else if (word.equals(avengerRoster[i][1])) 
                    	a.setNameFreq(1);
                    else if (word.equals(avengerRoster[i][2])) 
                    	a.setPerformerFreq(1);
                    
                    a.setMentionOrder(hMap.size() + 1);
                    hMap.put(a, a.getHeroAlias());					
				}
			}
		}
	}

	/**
	 * Iterates through the hashmap and matches with an existing avenger otherwise return null
	 * @param a
	 * @return
	 */
	private Avenger findA(String a) {
		for(Map.Entry<Avenger, String> e : hMap.entrySet()) {
			Avenger foundA = e.getKey();
			
	        if (foundA.getHeroAlias().equalsIgnoreCase(a) || foundA.getHeroName().equalsIgnoreCase(a) || foundA.getPerformer().equalsIgnoreCase(a)) {
				return foundA;
			}
		}
		return null;
	}

	/**
	 * takes a word and cuts off any unnecessary add-ons
	 * @param next
	 * @return ret
	 */
	private String cleanWord(String next) {
		// First, if there is an apostrophe, the substring
		// before the apostrophe is used and the rest is ignored.
		// Words are converted to all lowercase.
		// All other punctuation and numbers are skipped.
		String ret;
		int inx = next.indexOf('\'');
		if (inx != -1)
			ret = next.substring(0, inx).toLowerCase().trim().replaceAll("[^a-z]", "");
		else
			ret = next.toLowerCase().trim().replaceAll("[^a-z]", "");
		return ret;
	}

	/**
	 * print the results
	 */
	private void printResults() {
		//Prints the total number of words after being cleaned of numbers or special characters
		System.out.println("Total number of words: " + totalwordcount);
		//Prints the number of avengers mentioned through the size of the TreeMap due to duplicates counting as frequency
		System.out.println("Number of Avengers Mentioned: " + mentionMap.size());
		System.out.println();

		//Prints each list through the given comparator on initialization
		System.out.println("All avengers in the order they appeared in the input stream:");
		printMap(mentionMap);
		System.out.println();

		System.out.println("Top " + topN + " most popular avengers:");
		printTopN(mostPopularAvenger);
		System.out.println();

		System.out.println("Top " + topN + " most popular performers:");
		printTopN(mostPopularPerformer);
		System.out.println();

		System.out.println("All mentioned avengers in alphabetical order:");
		printMap(alphabticalMap);
		System.out.println();
	}

	/**
	 * Takes a list and prints without a limit
	 * @param Map
	 */
	private void printMap(TreeMap<Avenger, String> Map) {
		for(Map.Entry<Avenger, String> e : Map.entrySet()) {
			System.out.println(e.getKey());
		}
	}
	
	/**
	 * Takes a list and loops until the count reaches the given topN
	 * @param Map
	 */
	private void printTopN(TreeMap<Avenger, String> Map) {
		Iterator<Entry<Avenger, String>> i = Map.entrySet().iterator();
		int count = 0;
		
		while(i.hasNext() && count < topN) {
			Map.Entry<Avenger, String> e = i.next();
			System.out.println(e.getKey());
			
			count++;
		}
	}
}