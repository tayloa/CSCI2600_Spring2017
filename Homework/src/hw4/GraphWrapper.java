package hw4;
import java.util.*;
import hw4.*;

/**
 * This class is a wrapper class for the Graph class. It will help us test the Graph 
 * class by reducing the number of component.
 * @param <S>
 */
public class GraphWrapper<T extends Comparable<T>,S extends Comparable <S>> {
	private Graph<T,S> G;
	
	/**
     * @param none
     * @requires none
     * @effects none
     * @returns a GraphWrapper object with private Graph g  
     */
	public GraphWrapper() {
		G = new Graph<T,S>();
	}
	
	/**
     * @param node : string representing a node
     * @requires none
     * @effects adds a node to graph G
     * @returns none 
     */
	public void addNode(T node){
		G.addNode(node);
	}
	
	/**
     * @param parent : string representing a parent node
     * @param child : string representing a child node
     * @param label : string representing an edge label
     * @requires none
     * @effects adds an edge to graph G
     * @returns none
     */
	public void addEdge(T parent,T child,S label) {
		G.addEdge(parent,child,label);
	}
	
	/**
     * @param none
     * @requires none
     * @effects none
     * @returns an iterator to a sorted list of nodes in Graph G
     */
	public Iterator<T> listNodes() {
		// get the nodes from the graph, sort all the keys and return an iterator to the list
		List<T> nodes = new ArrayList<T>(G.getNodes().keySet());
//		Collections.sort(nodes);
		Iterator<T> itr = nodes.iterator();
		return itr;
	}
	
	/**
     * @param node : a String representing a node
     * @requires none
     * @effects none
     * @returns an iterator to a sorted list of a node's children 
     */
	public Iterator<String> listChildren(T node) {
		// get all the edges in the graph
		Set<Edge<T,S>> edges = G.getNodes().get(node);
		List<String> children = new ArrayList<String>();
		// match edge with label and create and build a list of  specially formated strings for output
		if (edges != null){
			for (Edge<T,S> e: edges) {
				String child = e.getChild().toString();
				String label = e.getLabel().toString();
	  			String rep = child+"("+label+")";
	  			children.add(rep);
	    	}
		}
		// sort the list and return an iterator to it
		Collections.sort(children);
  		Iterator<String> itr = children.iterator();
		return itr;
	}
}
