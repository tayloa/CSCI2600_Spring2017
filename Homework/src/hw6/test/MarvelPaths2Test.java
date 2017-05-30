package hw6.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hw6.MarvelPaths2;

public class MarvelPaths2Test { 
	
	MarvelPaths2 temp,graph1,graph2,graph3; 
	
	@Before
	public void setUp() throws IOException { 
		temp = new MarvelPaths2();
		graph1 = new MarvelPaths2();  
		graph2 = new MarvelPaths2();	
		graph3 = new MarvelPaths2();	
		graph1.createNewGraph("hw6/data/paths2_test1.csv");
		graph2.createNewGraph("hw6/data/paths2_test2.csv");
		graph3.createNewGraph("hw6/data/paths2_test3.csv");
	}  
	 
	@Test
	public void findPathTest() throws IOException {
		// node 1 and node 2  are the same and don't exist
		assertEquals(graph1.findPath("aaab", "aaab"),"unknown character aaab\n");
		// node 1 and node 2   don't exist
		assertEquals(graph1.findPath("aaab", "aaac"),"unknown character aaab\nunknown character aaac\n");
		// node 1 doesn't exist
		assertEquals(graph1.findPath("aaab", "Aaron"),"unknown character aaab\n");
		// node 2 doesn't exist
		assertEquals(graph1.findPath("Aaron", "aaab"),"unknown character aaab\n");
		// no path between given nodes
		assertEquals(graph2.findPath("Aaron", "Boogie"),"path from Aaron to Boogie:\nno path found\n");
		// path from node to itself
		assertEquals(graph1.findPath("Aaron", "Aaron"),"path from Aaron to Aaron:\ntotal cost: 0.000\n");
		// short path (1 edge)
		assertEquals(graph1.findPath("Aaron", "Asia"),"path from Aaron to Asia:\nAaron to Asia with weight 0.500\ntotal cost: 0.500\n");
		// 2 different paths to node
		assertEquals(graph3.findPath("Aaron", "Sam"),"path from Aaron to Sam:\nAaron to Asia with weight 0.333\n"
				+ "Asia to Sam with weight 0.500\ntotal cost: 0.833\n");
	} 
	
}
