package hw5;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import hw4.*;

/*
MarvelPaths is an immutable class that holds a private Graph called Network. Network represents the connection between each 
Marvel character. Nodes are represented by characters, edges are stories both characters appear in.
*/

public class MarvelPaths {
	private Graph<String,String> Network;
	 
     /**
     * @param none
     * @requires none
     * @effects Constructs a new MarvelPaths object 
     */ 
	public MarvelPaths(){ 
	}
	
	/**
     * @param none
     * @requires none
     * @effects Adds characters nodes and book title edges to Network
     */
	public void createNewGraph(String filename){
		try {
			Network = new Graph<String,String>();
			// read  the data, parse the file, and add to the graph
    		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
            		MarvelParser.readData(filename,charsInBooks,Network);
    	} catch (IOException e) { 
    		e.printStackTrace(); 
    	}
	}
	
	/**
     * @param String node1: starting node for the search
     * @param String node2: end node for the search
     * @requires none
     * @effects none
     * @return String representing the path to get from node1 to node2
     */
	public String findPath(String node1, String node2) {
		// check if the nodes are in the Graph
		if (node1.equals(node2)){
			if(!Network.isNode(node1))
				return "unknown character "+ node1+ "\n";
		}
		if (!Network.isNode(node1) && !Network.isNode(node2))
			return "unknown character "+ node1 + "\n"+"unknown character "+ node2+ "\n";
		if(!Network.isNode(node1))
			return "unknown character "+ node1+ "\n";
		if(!Network.isNode(node2))
			return "unknown character "+ node2+ "\n";
		else{
			String current = node1;
			String result = new String();
			Queue<String> queue = new LinkedList<String>(); // list of nodes we haven't visited
			Map<String,HashSet<Edge<String,String>>> nodes = Network.getNodes(); // all nodes and edges in the graph
			// Each key in M is a visited node.
			// Each value is a path from start to that node.
			// A path is a list; you decide whether it is a list of nodes, or edges,
			// or node data, or edge data, or nodes and edges, or something else.
			Map<String,List<Edge<String,String>>> visited = new HashMap<String,List<Edge<String,String>>>();
			
			// put the start node in the queue and add it to the visited map
			queue.add(node1); 
            visited.put(node1,new ArrayList<Edge<String,String>>());
            
			// pop the node out the queue and add it to the visited map
			while (!queue.isEmpty()){
				current = queue.remove();
	            
	            if (current.equals(node2)){
	            	break; 
	            }
	            
	            // get the current node's children and add node's children to the queue
	            // sort it to preserve alphabetical order
	            HashSet<Edge<String,String>> children = nodes.get(current);
	            List<Edge<String,String>> sortedList = new ArrayList<Edge<String,String>>(children);
	            Collections.sort(sortedList);
	            for(Edge<String,String> edge : sortedList) {
//	 	            if child has not in visited	add it to the queue and make it's path the parent's path plus the 
//	            	edge that connects it to the parent
	            	if (!visited.containsKey(edge.getChild()))
	                {		            	
//	            		p is the path from the start node to the current node in the graph we 
//	            		the current edge and save it as a path from start to a child
	            		List<Edge<String,String>> p = new ArrayList<Edge<String,String>>(visited.get(current));
	            		p.add(edge);
	            		visited.put(edge.getChild(), p);
	                    // Add the child to the queue
		            	queue.add(edge.getChild());
	                }
	            }
			}
        	result+="path from "+node1+" to "+node2+":";
        	List<Edge<String,String>> shortest_path = null;
        	if (visited.containsKey(node2)) 
        		shortest_path = visited.get(node2);
        	
        	// if no path is found
        	if (shortest_path == null)
        		return result+"\nno path found\n";
        	for(Edge<String,String> edge : shortest_path) {
        		String edge_string ="\n"+edge.getParent()+" to "+edge.getChild()+" via "+edge.getLabel();
        		result+=(edge_string);
        	}
        	return result+"\n";
		}

	}
}
