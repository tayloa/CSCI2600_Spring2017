package hw7; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import hw4.Edge;
import hw4.Graph;
import hw6.Path;
import hw6.PathComparator;

/**
 * CampusMap is a representation of a map of buildings. It has a private Graph called map. It it are Locations which represent nodes
 * Edges are set of Location objects.
 *
 */
public class CampusMap { 
	Graph<Location,Double> map; 
	
	/**  
	@param none
	@return none
    @effects Constructs a new CampusMap object.
	*/
	public CampusMap() {
		
	} 
	
	/**  
	@param String filename1 : files to pull node data from.
	@param String filename2 : files to pull edge data from.
  	@return none
    @effects Constructs a new Map object.
	*/ 
	public void createNewMap(String filename1, String filename2) {
		try { 
			map = new Graph<Location,Double>();
			HashMap<Location,HashSet<Location>> locations = new HashMap<Location,HashSet<Location>>();
			MapParser.readNodes(filename1, locations);
			MapParser.readEdges(filename2, locations);
			for (Location x : locations.keySet())
				map.addNode(x);
			for (Location x : locations.keySet()) {
				HashSet<Location> children = locations.get(x);
				for(Location y : children) {
					map.addEdge(x,y,x.getDistance(y));
				}
			}
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
	
	/**  
	@param String building1 : the start of the path
	@param String building2 : the end of the path
  	@return String representing the shortest path between two buildings
    @effects none.
	*/
	public String findShortestPath(String building1,String building2){
		Map<Location, HashSet<Edge<Location, Double>>> nodes = map.getNodes(); // all nodes and edges in the graph
		HashSet<Location> buildings = new HashSet<Location>(nodes.keySet());  // all the building names in the map
		Location building1Info = null; // the object form of the buildings
		Location building2Info = null;
		// find the building name or ID, skip intersections
		for (Location x : buildings) { 
			if ((x.getTitle().equals(building1) || Integer.toString(x.getId()).equals(building1)) && !x.getTitle().equals("")) { 
				building1Info = x;
			}
			if ((x.getTitle().equals(building2) || Integer.toString(x.getId()).equals(building2)) && !x.getTitle().equals("")) {
				building2Info = x;
			}
		}
		// case for intersection titles and ids
		if (building1.equals("")) { 
			building1Info = null; 
		}
		if (building2.equals("")) {
			building2Info = null; 
		}
		// if the buildings have no object form,they are not in the map
		if (building1.equals(building2)){
			if(building1Info == null) {
				return "Unknown building: ["+ building1+ "]";
			}
		}
		if (building1Info == null && building2Info == null) {
			return "Unknown building: ["+ building1 + "]\n"+"Unknown building: ["+ building2+ "]";
		}
		if(building1Info == null) { 
			return "Unknown building: ["+ building1+ "]";
		}
		if(building2Info == null) {
			return "Unknown building: ["+ building2+ "]";
		}
		else {
			String result = ""; // the path we took in string form
			Path<Location> current;   // the current path we are using (A Path object is an ArrayList of edges)
			PriorityQueue<Path<Location>> active = new PriorityQueue<Path<Location>>(new PathComparator()); // queue of paths to visit	
			/*
			 Each key in finished is a the id of a building
			 Each value is a Path from our start location to that building (
			 */
			Map<Integer,Path<Location>> finished = new HashMap<Integer,Path<Location>>();
			// put the start location in the queue and add it to the finished map
			active.add(new Path<Location>(building1Info,building1Info,new ArrayList<Edge<Location,Double>>())); 
			finished.put(building1Info.getId(),new Path<Location>(building1Info,building1Info,new ArrayList<Edge<Location,Double>>()));
			// if the queue is empty, there is no path between the two buildings
 			while (!active.isEmpty()){  
 	            // pop the current path out the queue and add it to the finished map
 	            // get the current Locations's edges, make their paths, and add them to the queue
 				// if the current path ends at our building, we will break
 				current = active.remove();
				if (current.getEnd().equals(building2Info)) {
					break;
	 	        }
 	            HashSet<Edge<Location, Double>> children = nodes.get(current.getEnd());
 	            for(Edge<Location,Double> edge : children) {
 	            	/*
         	 	     if child is in finished check if the current path to it has a lower cost. If so replace it
         	 	     else add it to the queue and make it's path the parent's path plus the edge that connects it to the parent
 	           		 */
 	            	if (finished.containsKey(edge.getChild().getId())) {
 		            	ArrayList<Edge<Location,Double>> p = finished.get(edge.getParent().getId()).getPath();
 	            		p.add(edge);
 	            		Path<Location> min_path = new Path<Location>(current.getStart(),edge.getChild(),p);
 	            		if (min_path.getCost().compareTo(finished.get(edge.getChild().getId()).getCost()) < 0) {
 	 	            		finished.put(edge.getChild().getId(), min_path);
 	            		}
 	            	}
 	            	else{		            	
         	            // p is the path from the start node to the current node in the graph we 
         	            // get the current edge and save it as a path from start to a child
 	            		ArrayList<Edge<Location, Double>> p = finished.get(edge.getParent().getId()).getPath();
 	            		p.add(edge);
 	            		Path<Location> min_path = new Path<Location>(current.getStart(),edge.getChild(),p);
 	            		finished.put(edge.getChild().getId(), min_path);
 		            	active.add(min_path);
 	                }
 	            }
 			}
 			
 			// build the result string
         	Path<Location> shortest_path = null; 
        	if (finished.containsKey(building2Info.getId())) {
             	result+="Path from "+building1Info.getTitle()+" to "+building2Info.getTitle()+":";
        		shortest_path = finished.get(building2Info.getId());
        		ArrayList<Edge<Location,Double>> route = shortest_path.getPath();
        		for (Edge<Location,Double> e : route){
        			Location a = e.getParent();
        			Location b = e.getChild();
        			String direction = a.getDirection(b); 
        			if (b.getTitle().equals("")) { // if we're at an intersection
        				result+= "\n\tWalk "+direction+" to (Intersection "+b.getId()+")";
        			}        
        			else { 
        				result += "\n\tWalk "+direction+" to ("+ b.getTitle()+")";
        			}
        		}
        		result += String.format("\nTotal distance: %.3f pixel units.",shortest_path.getCost());
			}
        	// if no path is found
        	else {
        		result+="There is no path from "+building1Info.getTitle()+" to "+building2Info.getTitle()+"."; 
        		}
        		
        	return result;
		}
	}
		/**  
		@param none
	  	@return String representing all buildings in the map
	    @effects none
	    @return a list of all building in the file
		*/
		public ArrayList<String> getAllBuildings() {   
			ArrayList<String> result = new ArrayList<String>(); 
			for (Location x : map.getNodes().keySet()) { 
				if (x.getTitle().equals("")) {
					continue;  
				}
				String building = x.getTitle()+","+x.getId();
				result.add(building);
			}
			Collections.sort(result);
			return result;  
		}

	
	public static void main(String[] arg) {
//    	String file1 = "hw7/data/RPI_map_data_Nodes.csv";
//    	String file2 = "hw7/data/RPI_map_data_Edges.csv";
//		CampusMap temp = new CampusMap();
//		temp.createNewMap(file1,file2);  
//		System.out.println(temp.findShortestPath("Barton Hall",""));

//		System.out.println(temp.findShortestPath("Sharp Hall", "Sharp Hall"));
//		System.out.println(temp.findShortestPath("", ""));

//		System.out.println(temp.findShortestPath("Sharp Hall", "aaa"));
//		System.out.println(temp.findShortestPath("Pittsburgh Building", "Carnegie Building"));
//		System.out.println(temp.findShortestPath("Barton Hall","Field House Houston"));
//		System.out.println(temp.findShortestPath("Barton Hall","124"));

//		System.out.println(temp.findShortestPath("Hall Hall","Warren Hall"));
//		System.out.println(temp.findShortestPath("Russell Sage Laboratory","Folsom Library"));
//		System.out.println(temp.findShortestPath("Folsom Library","Russell Sage Laboratory"));
//		System.out.println(temp.findShortestPath("", ""));

 
//		System.out.println(temp.findShortestPath("Field House Houston","EMPAC"));
//		System.out.println(temp.findShortestPath("", "2"));
//		System.out.println(temp.findShortestPath("Polytechnic Residence Commons", "Blitman Residence Commons"));

    }
}
