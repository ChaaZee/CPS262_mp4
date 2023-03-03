package stack_indentation_checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class FileProcessor{
	public SortedMap<Integer, String> code = new TreeMap<Integer, String>();
	private FileReader file;
	
	public FileProcessor(String filename){
		try {
			file = new FileReader(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Expetion: " + filename + " file not found");
		}	
	}
	
	public void mapCode() throws IOException{
		String line = "";
		int c = file.read();
		int i = 1;
		
		while(c != -1) {
			//System.out.print(c);
			line += (char)c;
			c = file.read();
			
			if((char)c == '\n') {
				code.put(i, line);
				//System.out.print(line);
				c = file.read();
				line = "";
				i++;

			} else if(c == -1){
				code.put(i, line);
			} 
		}	
	}
	
	public void getCode() {
		Set<Integer> integers = code.keySet();
		Iterator<Integer> iter = integers.iterator();
		int lineNumber;
		
		
		while(iter.hasNext()) {
			lineNumber = iter.next();
			System.out.print(lineNumber + ": " + code.get(lineNumber));
		}
	}
	
	public SortedMap<Integer, String> getMap(){
		return code;
	}
}
