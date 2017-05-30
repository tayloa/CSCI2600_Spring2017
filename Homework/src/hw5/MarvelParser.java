package hw5;
import hw4.Graph; 
import java.util.*;
import java.io.*;

public class MarvelParser {  

	/** @param: filename The path to the "CSV" file that contains the <hero, book> pairs                                                                                                
        @param: charsInBooks The Map that stores parsed <book, Set-of-heros-in-book> pairs;
        	    usually an empty Map
        @param: G, the graph to add character nodes and book edges to
        @effects: adds parsed <book, Set-of-heros-in-book> pairs to Map charsInBooks;
        		  adds parsed characters and books to the graph as nodes and edges
        @throws: IOException if file cannot be read of file not a CSV file                                                                                     
	 */
    public static void readData(String filename, Map<String,Set<String>> charsInBooks, Graph<String,String> G) 
    		throws IOException {  	  

    	BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        // Read the file and parse the line and get the character and the book they appear in
          while ((line = reader.readLine()) != null) {  
        	 int i = line.indexOf("\",\"");
             if ((i == -1) || (line.charAt(0)!='\"') || (line.charAt(line.length()-1)!='\"')) {
            	 throw new IOException("File "+filename+" not a CSV (\"HERO\",\"BOOK\") file.");
             }             
             String character = line.substring(1,i);
             String book = line.substring(i+3,line.length()-1);
             
             // Add the character to the Graph as a node.
             G.addNode(character);

             // Get the character set for  a specific book
             // If we haven't seen the book yet, add it to the map
             Set<String> s = charsInBooks.get(book);
             if (s == null) {
               s = new HashSet<String>();
               charsInBooks.put(book,s);
             }
              
             // Make a book edge in the graph between the new character and other characters in the same book
             // create an edge between the other characters and the new character too
 		     for (String character2 : s) {  
 	  				if(character2.equals(character))
 	  					continue; 
     	  			G.addEdge(character,character2,book);
     	  			G.addEdge(character2,character,book);
     	  	}
             // Adds the character to the set for specific book
             s.add(character);
        }
    }
    
    /**
    @param: filename The path to the "CSV" file that contains the <hero, book> pairs                                                                                                
    @param: charsInBooks The Map that stores parsed <book, Set-of-heros-in-book> pairs;
    	    usually an empty Map
    @param: G, the graph to add character nodes 
    @effects: adds parsed <book, Set-of-heros-in-book> pairs to Map charsInBooks;
    		  adds parsed characters as nodes to the graph 
    @throws: IOException if file cannot be read of file not a CSV file                                                                                     
 */
public static void readData2(String filename, Map<String,Set<String>> charsInBooks,Graph<String,Double> G) 
		throws IOException {  	 

	BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line = null;
	Set<String> chars = new HashSet<String>();

    // Read the file parse the line and get the character and the book they appear in
    // Add the character to the Graph as a node and add it to the character list.
      while ((line = reader.readLine()) != null) {  
    	  
         int i = line.indexOf("\",\"");
         if ((i == -1) || (line.charAt(0)!='\"') || (line.charAt(line.length()-1)!='\"')) {
        	 throw new IOException("File "+filename+" not a CSV (\"HERO\",\"BOOK\") file.");
         }             
         String character = line.substring(1,i);
         String book = line.substring(i+3,line.length()-1);
         
         G.addNode(character);
         Set<String> s = charsInBooks.get(book);
         if (s == null) {
           s = new HashSet<String>();
           charsInBooks.put(book,s);
         }
         s.add(character);
    } 
    
//  reverse the map, keys are chaacters, values are sets of books
    Map<String,Set<String>> booksInChar = new HashMap<String,Set<String>>();
    for (String book : charsInBooks.keySet()) {
		for (String char1 : charsInBooks.get(book)) {
			if (!booksInChar.containsKey(char1)) {	    		
				booksInChar.put(char1, new HashSet<String>());
			}
			booksInChar.get(char1).add(book);
		}
	}
	// Get two characters, every time the pair share the same book, increment the appearance number
	// We skip edges that have weight 0 (same character or no appearance together)
    for (String char1 : booksInChar.keySet()) {
    	for (String char2 : booksInChar.keySet()) {  
			double appearances = 0;
    		if (char1.equals(char2))
    			continue;
       		for (String book : booksInChar.get(char1)) {
    			if (booksInChar.get(char2).contains(book))
    				appearances++;
    		}
    		
    		if (appearances > 0) {
	    		G.addEdge(char1,char2,1/appearances);
    		}
    	}
	}
}

    public static void main(String[] arg) {

    	String file = arg[0];

    	try {
    		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
    		Graph<String,String> G = new Graph<String,String>();
    		readData(file,charsInBooks,G);
    		System.out.println("Read "+G.getNodes().size()+" characters who appear in "+charsInBooks.keySet().size() +" books.");

    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    }
}
