package hw7.test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import hw7.*;
import static org.junit.Assert.*;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

public class CampusPathsTest { // Rename to the name of your "main" class
	
	CampusMap map1,map2; 
	Location loc1,loc2;
	HashMap<Location,HashSet<Location>> locations;
	String file1,file2,file3,file4;
	ArrayList<String> allBuildings;
	
	@Before
	public void setUp() throws IOException { 
		map1 = new CampusMap();
		map2 = new CampusMap(); 
		loc1 = loc2 = new Location("A",12,12,12);
		locations = new HashMap<Location,HashSet<Location>>();
		file1 = "hw7/data/RPI_map_data_Nodes.csv";
        file2 = "hw7/data/RPI_map_data_Edges.csv";
        file3 = "hw7/data/test4_nodes.csv";
        file4 = "hw7/data/test4_edges.csv";
		map1.createNewMap(file1,file2);
		map2.createNewMap(file3,file4); 
		allBuildings = new ArrayList<String>(Arrays.asList("Pittsburgh Building,1","Sharp Hall,3","West Hall,2"));
	} 
	
	/**
	 * @param file1 
	 * @param file2 
	 * @return true if file1 and file2 have the same content, false otherwise
	 * @throws IOException
	 */	
	/* compares two text files, line by line */
	private static boolean compare(String file1, String file2) throws IOException {
		BufferedReader is1 = new BufferedReader(new FileReader(file1)); // Decorator design pattern!
		BufferedReader is2 = new BufferedReader(new FileReader(file2));
		String line1, line2;
		boolean result = true;
		while ((line1=is1.readLine()) != null) {
			line2 = is2.readLine();
			if (line2 == null) { 
				System.out.println(file1+" longer than "+file2);
				result = false;
				break;
			}
			if (!line1.equals(line2)) {
				System.out.println("Lines: "+line1+" and "+line2+" differ.");
				result = false;
				break; 
			}
		}
		if (result == true && is2.readLine() != null) {
			System.out.println(file1+" shorter than "+file2);
			result = false;
		} 
		is1.close();
		is2.close();
		return result;		 
	}
	 
	private void runTest(String filename) throws IOException {
		InputStream in = System.in; 
		PrintStream out = System.out;				
		String inFilename = "hw7/data/"+filename+".test"; // Input filename: [filename].test here  
		String expectedFilename = "hw7/data/"+filename+".expected"; // Expected result filename: [filename].expected
		String outFilename = "hw7/data/"+filename+".out"; // Output filename: [filename].out
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(inFilename));
		System.setIn(is); // redirects standard input to a file, [filename].test
		PrintStream os = new PrintStream(new FileOutputStream(outFilename));
		System.setOut(os); // redirects standard output to a file, [filename].out 
		CampusPaths.main(null); // Call to YOUR main. May have to rename.
		System.setIn(in); // restores standard input 
		System.setOut(out); // restores standard output 
		assertTrue(compare(expectedFilename,outFilename));  
		// TODO: More informative file comparison will be nice.
		
	}
	
	@Test
	public void testListBuildings() throws IOException {
		// Regular test file for commands
		runTest("test1"); 
	}
	
	@Test
	public void testPathErrors() throws IOException{
		// Test paths between unknown locations and paths with the same start and end
		runTest("test2"); 
	}
	
	@Test
	public void testfindPaths() throws IOException {
		// Test a no path,a short path, a slightly longer path, a medium path, and a long path
		runTest("test3");
	}
	
	@Test 
	public void testLocationComparator() {
		assertEquals(loc1.compareTo(loc2),0); 
	} 
	
	@Test(expected=IOException.class)
	public void testMapParserReadNodes() throws IOException {
		MapParser.readNodes("", locations);  
	}
	
	@Test(expected=IOException.class)
	public void testMapParserReadEdges() throws IOException {
		MapParser.readEdges("", locations);
	} 
	
	@Test
	public void testfindShortestPaths() { 
		// 2 intersection titles/ blank titles
		assertEquals(map1.findShortestPath("",""),"Unknown building: []");
		// building 1 and building 2 are the same and don't exist
		assertEquals(map1.findShortestPath("aaab", "aaab"),"Unknown building: [aaab]");
		// building 1 and building 2 don't exist
		assertEquals(map1.findShortestPath("aaab", "aaac"),"Unknown building: [aaab]\nUnknown building: [aaac]");
		// building 1 doesn't exist
		assertEquals(map1.findShortestPath("Hall Hall","aaac"),"Unknown building: [aaac]");
		// building 2 doesn't exist
		assertEquals(map1.findShortestPath("aaac","Hall Hall"),"Unknown building: [aaac]");
		// no path between given nodes
		assertEquals(map1.findShortestPath("Polytechnic Residence Commons", "Blitman Residence Commons"),
				"There is no path from Polytechnic Residence Commons to Blitman Residence Commons.");
		// path from node to itself 
		assertEquals(map1.findShortestPath("Sharp Hall", "Sharp Hall"),"Path from Sharp Hall to Sharp Hall:\n"
				+ "Total distance: 0.000 pixel units.");
		// short path  
		assertEquals(map1.findShortestPath("Russell Sage Laboratory","Greene Building"),"Path from Russell Sage Laboratory to "
				+ "Greene Building:\n\tWalk SouthEast to (Greene Building)\nTotal distance: 111.212 pixel units.");
		// long path
		assertEquals(map1.findShortestPath("Field House Houston", "EMPAC"),"Path from Field House Houston to EMPAC:"
				+ "\n\tWalk West to (Intersection 113)"
				+ "\n\tWalk West to (Intersection 111)"
				+ "\n\tWalk West to (Intersection 117)"
				+ "\n\tWalk West to (Intersection 112)"
				+ "\n\tWalk SouthWest to (2021 Peoples Avenue)"
				+ "\n\tWalk West to (Beman Park Firehouse)"
				+ "\n\tWalk West to (Alumni House)"
				+ "\n\tWalk SouthWest to (H Building)"
				+ "\n\tWalk South to (North Hall)"
				+ "\n\tWalk SouthWest to (Intersection 132)"
				+ "\n\tWalk SouthWest to (Troy Building)"
				+ "\n\tWalk SouthWest to (Intersection 133)"
				+ "\n\tWalk SouthWest to (Intersection 134)"
				+ "\n\tWalk SouthWest to (Lally Hall)"
				+ "\n\tWalk South to (Folsom Library)"
				+ "\n\tWalk SouthWest to (EMPAC)"
				+ "\nTotal distance: 1473.118 pixel units.");
		assertEquals(map1.findShortestPath("Folsom Library","Russell Sage Laboratory"),"Path from Folsom Library to Russell Sage Laboratory:\n"
				+ "\tWalk North to (Amos Eaton Hall)\n\tWalk NorthEast to (Russell Sage Laboratory)\nTotal distance: 205.279 pixel units.");
		assertEquals(map1.findShortestPath("Russell Sage Laboratory","Folsom Library"),"Path from Russell Sage Laboratory to Folsom Library:"
				+ "\n\tWalk SouthWest to (Amos Eaton Hall)"
				+ "\n\tWalk South to (Folsom Library)"
				+ "\nTotal distance: 205.279 pixel units.");
		assertEquals(map1.findShortestPath("","Barton Hall"),"Unknown building: []");
		// building and intersection name
		assertEquals(map1.findShortestPath("Barton Hall",""),"Unknown building: []");
		// intersection id and building name
		assertEquals(map1.findShortestPath("124","Barton Hall"),"Unknown building: [124]");
		// building name and intersection id
		assertEquals(map1.findShortestPath("Barton Hall","124"),"Unknown building: [124]");
		// building id and intersection id
		assertEquals(map1.findShortestPath("15","124"),"Unknown building: [124]");
		// intersection id and building id	
		assertEquals(map1.findShortestPath("108","15"),"Unknown building: [108]");
	}
	 
	@Test
	public void testgetAllBuildings() {
		assertEquals(map2.getAllBuildings(),allBuildings);
	}
	
}
