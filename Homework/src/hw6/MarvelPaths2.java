package hw6;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import hw4.Edge;
import hw4.Graph;
import hw5.MarvelParser;
/*
MarvelPaths2 is an immutable class that holds a private Graph called Network. Network represents the connection between each 
Marvel character. Nodes are represented by characters. Edges are weighted based on the multiplicative inverse of the number of stories
two characters appear in together.
*/

public class MarvelPaths2 {
	
	private Graph<String,Double> Network;
	  
	/**
     * @param none
     * @requires none
     * @effects Constructs a new MarvelPaths object
     */ 
	public MarvelPaths2() {
	}
	
	/**
     * @param String filename: file to pull characters and book titles from
     * @requires none
     * @effects Adds a new Network graph with nodes and weighted edges
     */ 
	public void createNewGraph(String filename) {
		Network = new Graph<String,Double>();
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		 try {
			MarvelParser.readData2(filename, charsInBooks,Network);	
		 } catch (IOException e) {
	            e.printStackTrace(); 
	    }	
	}
	
	/**
	 * An implementation of Dijkstra's Algorithm. It finds the shortest path between two characters in the graph
     * @param String CHAR1: starting character in the graph
     * @param String CHAR2: character we want to find a path to
     * @requires none
     * @effects none
     * @return a string representing the shortest path between the two characters
     */
	public String findPath(String CHAR1, String CHAR2) {
		// check if the nodes are in the Graph
		if (CHAR1.equals(CHAR2)){
			if(!Network.isNode(CHAR1))
				return "unknown character "+ CHAR1+ "\n";
		}
		if (!Network.isNode(CHAR1) && !Network.isNode(CHAR2))
			return "unknown character "+ CHAR1 + "\n"+"unknown character "+ CHAR2+ "\n";
		if(!Network.isNode(CHAR1))
			return "unknown character "+ CHAR1+ "\n";
		if(!Network.isNode(CHAR2))
			return "unknown character "+ CHAR2+ "\n";
		else {
			String result = ""; // output string
			Path<String> current;       // the current path we are using
			PriorityQueue<Path<String>> active = new PriorityQueue<Path<String>>(new PathComparator()); // queue of paths to visit		

			Map<String,HashSet<Edge<String,Double>>> nodes = Network.getNodes();  // all nodes and edges in the graph
			
			// Each key in finished is a visited node.
			// Each value is a Path from start to that node (A Path object is an ArrayList of edges)
			Map<String,Path<String>> finished = new HashMap<String,Path<String>>();

			// put the start node in the queue and add it to the finished map
			active.add(new Path<String>(CHAR1,CHAR1,new ArrayList<Edge<String,Double>>())); 
			finished.put(CHAR1,new Path<String>(CHAR1,CHAR1,new ArrayList<Edge<String,Double>>()));
            
			// if the queue is empty, there is no path between the two nodes
 			while (!active.isEmpty()){
 				
 	            // pop the node out the queue and add it to the visited map
 	            // get the current node's children and add the paths to node's children to the queue
 				current = active.remove();    				
 	            HashSet<Edge<String,Double>> children = nodes.get(current.getEnd());
 	            for(Edge<String,Double> edge : children) {
//         	 	     if child has not in visited add it to the queue and make it's path the parent's path plus the 
//         	         edge that connects it to the parent
// 	           		 else check if the current path to the child has a lower cost than the previous cost found. If so update it.
 	            	if (!finished.containsKey(edge.getChild()))
 	                {		            	
         	            // p is the path from the start node to the current node in the graph we 
         	            // get the current edge and save it as a path from start to a child
 	            		ArrayList<Edge<String,Double>> p = finished.get(edge.getParent()).getPath();
 	            		p.add(edge);
 	            		Path<String> min_path = new Path<String>(current.getStart(),edge.getChild(),p);
 	            		finished.put(edge.getChild(), min_path);
 		            	active.add(min_path);
 	                }
 	            	else {
 	            		ArrayList<Edge<String,Double>> p = finished.get(edge.getParent()).getPath();
 	            		p.add(edge);
 	            		Path<String> min_path = new Path<String>(current.getStart(),edge.getChild(),p);
 	            		if (min_path.getCost().compareTo(finished.get(edge.getChild()).getCost()) < 0)
 	 	            		finished.put(edge.getChild(), min_path);
 	            	}
 	            }
 			} 
 			
 			// build the result string
         	result+="path from "+CHAR1+" to "+CHAR2+":";
         	Path<String> shortest_path = null;
        	if (finished.containsKey(CHAR2)) 
        		shortest_path = finished.get(CHAR2);
        	
        	// if no path is found
        	if (shortest_path == null)
        		return result+"\nno path found\n";
        	result += shortest_path.toString();
        	return result;
		}
	}
	

public static void main(String[] arg) {

//	String file = arg[0]; 
//
//	MarvelPaths2 temp = new MarvelPaths2();
//	temp.createNewGraph(file);
//	System.out.println(temp.findPath("WISDOM, ROMANY","CAPTAIN AMERICA"));
//	System.out.println(temp.findPath("WOLVERINE/LOGAN ", "BEAST/HENRY &HANK& P"));
//		System.out.println(temp.findPath("Aaron", "Asia"));
//		System.out.println(temp.findPath("Aaron", "Boogie"));
//
//		System.out.println(temp.findPath("Aaron", "Jayshon"));
//		System.out.println(temp.findPath("Aaron", "Sam"));
//		System.out.println(temp.findPath("Aaron", "Sarah"));
//	    System.out.println(temp.findPath("Aaron", "Asia"));

}

}

