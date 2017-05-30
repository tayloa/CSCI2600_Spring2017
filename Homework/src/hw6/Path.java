package hw6;
import hw4.*;
import java.util.*;

//Path is an immutable class. It has a private ArrayList,path, of weighted EdgeString,Double) objects. It has a private 
//double cost representing a sum of all the edge weights in the path array. It also stores the start and end nodes of the path.
public class Path<X extends Comparable<X>>   {

	/** A path p is made up of an array of weighted edges. It also has a start node and end node, n1 and n2 respectively.
	 */
	X start;
	X end; 
	private Double cost; 
	private ArrayList<Edge<X,Double>> path;

	/**
     * @param ArrayList<Edge<String,Double>> p: list of weighted edges to represent a path
     * @param String Start: starting node for the path
     * @param String End: ending node for the graph
     * @requires none
     * @effects Creates a new path and calculates its total cost
     */
	public Path(X Start,X End, ArrayList<Edge<X,Double>> p) {
		start = Start;
		end = End;
		path = p; 
		cost = 0.00;
		for(Edge<X,Double> edge : path)
    		cost += edge.getLabel();		
	}
	
	/**
     * @param none
     * @requires none
     * @effects returns the representation of the path
     */		
	public ArrayList<Edge<X,Double>> getPath() {
		return new ArrayList<Edge<X,Double>>(path);
	}
	
	/**
     * @param none
     * @requires none
     * @effects returns the cost of the path
     */		
	public X getStart() {
		return start;
	}
	
	/**
     * @param none
     * @requires none
     * @effects returns the cost of the path
     */		
	public X getEnd() {
		return end;
	}
	
	/**
     * @param none
     * @requires none
     * @effects returns the cost of the path
     */		
	public Double getCost() {
		return cost;
	}
	
	/**
     * @param none
     * @requires none
     * @effects returns the representation of the path in string form
     */	
	public String toString() {
		String result = "";
     	for(Edge<X,Double> edge : path) {
    		String edge_string ="\n"+edge.getParent()+" to "+edge.getChild()+" with "+String.format("weight %.3f", edge.getLabel());        		
    		result+=(edge_string);
    	}
    	result += String.format("\ntotal cost: %.3f\n",cost);
     	return result;
	}
	
}
