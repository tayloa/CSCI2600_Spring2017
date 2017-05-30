package hw7;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import hw4.Edge;
import hw4.Graph;

/*
 * MapParser gets all node and edge data from 2 given files
 */
public class MapParser { 
	  
	/**  
	@param String filename : The file we are creating locations from.
    @param HashMap<Location,HashSet<Integer>> map : Map in which keys are Location objects and values are sets of edges.
    @effects Pulls locations, coordinates, ids, and intersections from the file and stores them in a map.
	*/
	public static void readNodes(String filename,HashMap<Location,HashSet<Location>> map) 
    		throws IOException {  	  
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;  
          while ((line = reader.readLine()) != null) {    
             String[] data = line.split(",");
             String location = data[0];
             int id = Integer.valueOf(data[1]);
             int x = Integer.valueOf(data[2]);
             int y = Integer.valueOf(data[3]);
             map.put(new Location(location,id,x,y),new HashSet<Location>());
        }
    }
	
	/**  
	@param String filename : The file we are pulling edges from.
    @param HashMap<Location,HashSet<Integer>> map : Map in which keys are Location objects and values are sets of edges.
    @effects Pulls edges from the file and stores them in the map.
	*/ 
	public static void readEdges(String filename,HashMap<Location,HashSet<Location>> map) 
    		throws IOException {  	  
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
          while ((line = reader.readLine()) != null) {   
             /**
              * Find the Locations that match the ids and add them as a connection to each other
              */
             String[] data = line.split(",");
        	 Location building1 = null;
        	 Location building2 = null;
             int id1 = Integer.valueOf(data[0]);
             int id2 = Integer.valueOf(data[1]);
             for (Location n : map.keySet()) {
            	 if (n.getId() == id1) {
            		 building1 = n;
            	 }
            	 if (n.getId() == id2) {
            		 building2 = n;
            	 }
             }  
             map.get(building1).add(building2);
             map.get(building2).add(building1);
        }
    }
}

