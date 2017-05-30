package hw4;
import java.util.*;

/** Edge is an immutable object that represents a connection 
between two nodes in a graph. Each edge will have a label 
value to represent it. It can hold any node and label type. 
*/

public class Edge <T extends Comparable<T>,S extends Comparable<S>> implements java.lang.Comparable<Edge<T,S>>{ //implements Comparable<T>{

	private T parent;
	private T child;
	private S l;
	
	// Abstraction Function:
    // An Edge, e, is a connection between two nodes, parent and child, with the label of the edge being 
	// a string, l.

    // Representation invariant
	// parent. child, and l can never be null/

	/**  
	@param node_1 : The parent node in the new Edge.
    @param node_2 : The child node in the new Edge.
    @param label : The label of the new Edge
    @effects Constructs a new Edge made up of two nodes, parent and child, with distance d.
	*/
	public Edge(T node_1, T node_2,S label) {
		parent = node_1;
		child = node_2;
		l = label;
		checkRep();
	}
	
	/** 
	@param none
	@effects none
    @return returns the parent node for the Edge.
	*/
	public T getParent() {
		return parent;
	}
	
	/** 
	@param none
    @effects none
    @return returns the child node for the edge
	*/
	public T getChild() {
		return child;
	}
	
	/** 
	@param none
    @effects none
    @return returns the label of the edge
	*/
	public S getLabel() {
		return l;
	}
	
	// Makes sure the representation invariant holds
	public void checkRep() {
		if (parent == null || child == null || l == null) {
			throw new RuntimeException("Edge values can never be null.");
		}
	}
	
	public int compare(T element1,T element2)
	{
	    return element1.compareTo(element2);
	}
	
	public int compareTo(S element1,S element2)
	{
	    return element1.compareTo(element2);
	}
	
	/** 
	@param compareEdge: the edge we are comparing our current  edge to
    @effects none
    @return if the edge is less than or greater than the compared edge
	*/
//	 comparator for Edges
	public int compareTo(Edge<T,S> compareEdge) { 
		int compare = compare(this.getChild(),compareEdge.getChild());
		if (compare == 0){
			return compareTo(this.getLabel(),compareEdge.getLabel());
		}
		// ascending order
		return compare; 
	}	
	
	
}
