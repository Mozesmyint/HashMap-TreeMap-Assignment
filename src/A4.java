package src;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * COMP 2503 Fall 2023 Assignment 4
 * 
 * This program must read a input stream and keeps track of the frequency at
 * which an avenger is mentioned either by name or alias or performer's last name. The program must use HashMaps
 * for keeping track of the Avenger Objects, and it must use TreeMaps
 * for storing the data. 
 * 
 * @author Maryam Elahi
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
	
	public static void main(String[] args) {
		A4 a4 = new A4();
		a4.run();
	}

	public void run() {
		readInput();
		createdOrderedTreeMaps();
		printResults();
	}

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
	
	private void updateAvengerMap(String word) {
		for(int i = 0; i < avengerRoster.length; i++) {
			if(word.equals(avengerRoster[i][0]) || word.equals(avengerRoster[i][1]) || word.equals(avengerRoster[i][2])) {
				Avenger newA = new Avenger();
				newA.setHeroAlias(avengerRoster[i][0]);
				newA.setHeroName(avengerRoster[i][1]);
				newA.setPerformer(avengerRoster[i][2]);
				
				Avenger a = findA(word);
				
				if(a != null) {
					if(word.equals(avengerRoster[i][0]))
						a.setAliasFreq(a.getAliasFreq() + 1);
					else if(word.equals(avengerRoster[i][1]))
						a.setNameFreq(a.getNameFreq() + 1);
					else if(word.equals(avengerRoster[i][2]))
						a.setPerformerFreq(a.getPerformerFreq() + 1);
				} 
				// if null it will create a new member on the list 
				else {
					a = newA;
					
                    if (word.equals(avengerRoster[i][0])) 
                    	a.setAliasFreq(1);
                    else if (word.equals(avengerRoster[i][1])) 
                    	a.setNameFreq(1);
                    else if (word.equals(avengerRoster[i][2])) 
                    	a.setPerformerFreq(1);
                    
                    //creates an index to easily search on the list 
                    a.setMentionOrder(hMap.size() + 1);
                    hMap.put(a, a.getHeroAlias());					
				}
			}
		}
	}

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
		/*
		 * Please first read the documentation for TreeMap to see how to 
		 * iterate over a TreeMap data structure in Java.
		 *  
		 * Hint for printing the required list of avenger objects:
		 * Note that the TreeMap class does not implement
		 * the Iterable interface at the top level, but it has
		 * methods that return Iterable objects.
		 * You must either create an iterator over the 'key set',
		 * or over the values 'collection' in the TreeMap.
		 * 
		 */
		
		
		System.out.println("Total number of words: " + totalwordcount);
		//System.out.println("Number of Avengers Mentioned: " + ??);
		System.out.println();

		System.out.println("All avengers in the order they appeared in the input stream:");
		// Todo: Print the list of avengers in the order they appeared in the input
		// Make sure you follow the formatting example in the sample output
		System.out.println();

		System.out.println("Top " + topN + " most popular avengers:");
		// Todo: Print the most popular avengers, see the instructions for tie breaking
		// Make sure you follow the formatting example in the sample output
		System.out.println();

		System.out.println("Top " + topN + " most popular performers:");
		// Todo: Print the most popular performer, see the instructions for tie breaking
		// Make sure you follow the formatting example in the sample output
		System.out.println();

		System.out.println("All mentioned avengers in alphabetical order:");
		// Todo: Print the list of avengers in alphabetical order
		System.out.println();
	}
}
