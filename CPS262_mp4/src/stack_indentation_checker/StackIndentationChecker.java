package stack_indentation_checker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class StackIndentationChecker {
	SortedMap<Integer, Integer> indentations = new TreeMap<Integer, Integer>();
	SortedMap<Integer, String> code;
	ArrayList<Integer> formattedIndentations = new ArrayList<Integer>();
	FileProcessor file;
	String filename;
	
	public StackIndentationChecker(String filename) throws IOException {
		this.filename = filename;
		file = new FileProcessor(filename);
		file.mapCode();
		code = file.getMap();
	}
	
	public void findIndentations() {
		Set<Integer> keys = code.keySet();
		Iterator<Integer> iter = keys.iterator();
		int lineNumber;
		
		while(iter.hasNext()) {
			lineNumber = iter.next();
			indentations.put(lineNumber, findIndent(code.get(lineNumber)));
		}
	}
	
	private int findIndent(String line) {
		int i = 0;
		
		if(line.charAt(0) == (char)13) {
			return -1;
		}
		
		if(line != null) {
			while(line.charAt(i) != -1) {
				if(line.charAt(i) == ' ') {
					i++;
				} else {
					break;
				}
			}
		}
	
		return i;
	}
	
	public void printIndentations() {
		Set<Integer> integers = code.keySet();
		Iterator<Integer> iter = integers.iterator();
		int lineNumber;
		
		
		while(iter.hasNext()) {
			lineNumber = iter.next();
			System.out.println(lineNumber + ": " + indentations.get(lineNumber));
		}
	}
	
	public void getCode() {
		file.getCode();
	}
	
	public void formatIndentations() {
		Set<Integer> integers = code.keySet();
		Iterator<Integer> iter = integers.iterator();
		int lineNumber;
		
		while(iter.hasNext()) {
			lineNumber = iter.next();
			if(indentations.get(lineNumber) != -1) {
				if(lineNumber == 1) {
					formattedIndentations.add(indentations.get(lineNumber));
				} else if(indentations.get(lineNumber - 1) != -1) {
					if(lineNumber > 1) {
						if(indentations.get(lineNumber) != indentations.get(lineNumber - 1)) {
							formattedIndentations.add(indentations.get(lineNumber));
						}
					}
				} else if(lineNumber == 2 && indentations.get(lineNumber - 1) == -1){
					formattedIndentations.add(indentations.get(lineNumber));
				} else if(indentations.get(lineNumber - 1) == -1 && indentations.get(lineNumber - 2) == -1) {
					if(indentations.get(lineNumber) != indentations.get(lineNumber - 3)) {
						formattedIndentations.add(indentations.get(lineNumber));
					}
				}
			}
		}
	}
	
	public void printFormattedIndentations() {
		for(int i = 0; i < formattedIndentations.size(); i++) {
			System.out.print(formattedIndentations.get(i) + " ");
		}
	}
	
	public boolean isIdentCorrect() {
		findIndentations();
		formatIndentations();
		
		System.out.println("Proccessing file: " + filename + " . . .");
		getCode();
		System.out.println();
		
		for(int i = 0; i < formattedIndentations.size()-1; i++) {
			if(i > 0 && formattedIndentations.get(i + 1) < formattedIndentations.get(i) && formattedIndentations.get(i - 1) < formattedIndentations.get(i)) {
				
				int x = i;
				while(x > 0 && formattedIndentations.get(x) > formattedIndentations.get(x - 1)) {
					x--;
				}
				
				int y = i;
				while(y != formattedIndentations.size() - 1 && (formattedIndentations.get(y) != 0 || formattedIndentations.get(y + 1) < formattedIndentations.get(y))) {
					y++;
				}
				
				for(int k = i + 1; k <= y; k++) {
					boolean b = false;
					for(int f = i - 1; f >= x; f--) {
						if(formattedIndentations.get(k) == formattedIndentations.get(f)) {
							b = true;
						}
					}
					if(!b) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		StackIndentationChecker file1 = new StackIndentationChecker(args[0]);
		StackIndentationChecker file2 = new StackIndentationChecker(args[1]);
		StackIndentationChecker file3 = new StackIndentationChecker(args[2]);
		StackIndentationChecker file4 = new StackIndentationChecker(args[3]);
		StackIndentationChecker file5 = new StackIndentationChecker(args[4]);
		
		if(file1.isIdentCorrect()) {
			System.out.println("--------- " + file1.filename +" is properly indented ---------");
			System.out.println();
		} else {
			System.out.println("--------- " + file1.filename +" is not properly indented ---------");
			System.out.println();
		}
		
		if(file2.isIdentCorrect()) {
			System.out.println("--------- " + file2.filename +" is properly indented ---------");
			System.out.println();
		} else {
			System.out.println("--------- " + file2.filename +" is not properly indented ---------");
			System.out.println();
		}
		
		if(file3.isIdentCorrect()) {
			System.out.println("--------- " + file3.filename +" is properly indented ---------");
			System.out.println();
		} else {
			System.out.println("--------- " + file3.filename +" is not properly indented ---------");
			System.out.println();
		}
		
		if(file4.isIdentCorrect()) {
			System.out.println("--------- " + file4.filename +" is properly indented ---------");
			System.out.println();
		} else {
			System.out.println("--------- " + file4.filename +" is not properly indented ---------");
			System.out.println();
		}
		
		if(file5.isIdentCorrect()) {
			System.out.println("--------- " + file5.filename +" is properly indented ---------");
			System.out.println();
		} else {
			System.out.println("--------- " + file5.filename +" is not properly indented ---------");
			System.out.println();
		}

	}

}
