package hw7;

import java.util.ArrayList;
import java.util.Scanner;

public class CampusPaths {  
		 
	public static void main(String[] arg) {  
		CampusMap map = new CampusMap();
	    map.createNewMap("hw7/data/RPI_map_data_Nodes.csv","hw7/data/RPI_map_data_Edges.csv");
	    Scanner scanner = new Scanner( System.in );
	    String input = scanner.nextLine();
	    while (input != null) {  
	    	if (input.equals("b")) {
	    		ArrayList<String> buildings = map.getAllBuildings();
	    		for(String building : buildings) {
	    			System.out.println(building); 
	    		} 
	    	}
	    	else if (input.equals("r")) {
	    		System.out.print("First building id/name, followed by Enter: ");
	    	    String building1 = scanner.nextLine();
	    		System.out.print("Second building id/name, followed by Enter: ");
	    	    String building2 = scanner.nextLine();
	    	    String result = map.findShortestPath(building1, building2);
	    	    System.out.println(result); 
	    	}    
	    	else if (input.equals("q")) { 
	    		return;  
	    	} 
	    	else if (input.equals("m")) {
	    		System.out.println("Menu Options:\n\tb: List all buildings\n\tr: Shortest route between two buildings\n\tq: quit the program");
	    	}
	    	else {
	    		System.out.println("Unknown option");
	    	}	
		    input = scanner.nextLine(); 
	    } 
    }
}
