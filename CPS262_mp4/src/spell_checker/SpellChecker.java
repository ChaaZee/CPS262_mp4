package spell_checker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class SpellChecker {
	
	public SortedMap<Integer, String> text = new TreeMap<Integer, String>();
	HashSet<String> dictionary = new HashSet<String>();
	TreeSet<String> missSpelledWords = new TreeSet<String>();
	String filename;
	
	public SpellChecker(String filename) throws IOException {
		text.clear();
		missSpelledWords.clear();
		
		if(dictionary.isEmpty())
			setDictionary("dictionary.txt");
		
		this.filename = filename;
		
		FileProcessor file = new FileProcessor(filename);
		file.mapText();
		text = file.getMap();
	}
	
	public void setDictionary(String filename) {
		Scanner file = null;
		
		try {
			file = new Scanner(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			System.out.println(filename + " file not found");
		}
		
		while(file.hasNext()) {
			String word = file.next();
			//System.out.println(word);
			dictionary.add(word);
		}
	}
	
	public void spellCheck() {
		
		System.out.println("---------- Spell checking " + filename + " ----------");
		
		Set<Integer> keys = text.keySet();
		Iterator<Integer> iter = keys.iterator();
		int lineNumber;
		
		while(iter.hasNext()) {
			lineNumber = iter.next();
			Boolean a = true;
			Scanner input = new Scanner(System.in);
			
			StringTokenizer st = new StringTokenizer(text.get(lineNumber), " \t,.;:-%'\")");
			while(st.hasMoreTokens()) {
				String word = st.nextToken().toLowerCase();
				if(word.charAt(0) != '0' && word.charAt(0) != '1' && word.charAt(0) != '2' && word.charAt(0) != '3' && word.charAt(0) != '4' && word.charAt(0) != '5' && word.charAt(0) != '6' && word.charAt(0) != '7' &&
						word.charAt(0) != '8' && word.charAt(0) != '9' && word.charAt(word.length()-1) != (char)13 && word.charAt(0) != '(') {
					if(!dictionary.contains(word) && !missSpelledWords.contains(word)) {
						if(a) {
							System.out.print(lineNumber + ": " + text.get(lineNumber));
							a = false;
						}
						
						System.out.println("'" + word + "' is not in the dictionary. Do you want to add to the dictionary (y/n)? ");
						String kbinput = input.next();
						
						if(kbinput.equals("y")) {
							dictionary.add(word);
						} else if(kbinput.equals("n")) {
							missSpelledWords.add(word);
						}
					}
				}
			}
		}
		printSet(missSpelledWords, "Miss spelled wordsn");
	}
	
	public <T> void printSet(Set<T> set, String name) {
		Iterator<T> s = set.iterator();
		
		System.out.println("------------ " + name + " ------------");
		
		while(s.hasNext()) {
			T element = s.next();
			System.out.println(element);
		}
	}

	public static void main(String[] args) throws IOException {
		SpellChecker file1 = new SpellChecker(args[0]);
		file1.spellCheck();
		
		SpellChecker file2 = new SpellChecker(args[1]);
		file2.spellCheck();
		
	}

}
