package hw5.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import hw4.*;
import hw5.*;

public class MarvelPathsTest {
	MarvelPaths tempGraph, tempGraph2, graph1, graph2, graph3, graph4;
	
	@Before 
	  public void setUp(){
		graph1 = new MarvelPaths(); 
		graph2 = new MarvelPaths(); 
		graph3 = new MarvelPaths(); 
		graph4 = new MarvelPaths(); 
		// small file with regular input
	    graph2.createNewGraph("hw5/data/test.csv");
		// small file with duplicate character appearances in the same book
	    graph3.createNewGraph("hw5/data/test_two_paths.csv");
	    // medium file with regular input
	    graph4.createNewGraph("hw5/data/test1.csv");
	  }
	 
	@Test 
	public void ConstructorTest() {
		tempGraph = new MarvelPaths(); 
	}
	 
	
	@Test
	public void findPathTest() { 
		// node 1 and node 2  are the same and don't exist
		assertEquals(graph2.findPath("aaab", "aaab"),"unknown character aaab\n");
		// node 1 and node 2   don't exist
		assertEquals(graph2.findPath("aaab", "aaac"),"unknown character aaab\nunknown character aaac\n");
		// node 1 doesn't exist
		assertEquals(graph2.findPath("aaab", "Aaron"),"unknown character aaab\n");
		// node 2 doesn't exist
		assertEquals(graph2.findPath("Aaron", "aaab"),"unknown character aaab\n");
		// no path between given nodes
		assertEquals(graph2.findPath("Aaron", "Joy"),"path from Aaron to Joy:\nno path found\n");
		// short path found
		assertEquals(graph2.findPath("Aaron","Josh"),"path from Aaron to Josh:\nAaron to Josh via NSBE\n");
		// long path found 
		assertEquals(graph4.findPath("Aaron","Malaia"),"path from Aaron to Malaia:\nAaron to Josh via book 1\nJosh to "
				+ "Tyler via book 2\nTyler to Joy via book 3"
				+ "\nJoy to Rising via book 4\nRising to Kissa via book 5\nKissa to Malaia via book 6\n");
	}

}
