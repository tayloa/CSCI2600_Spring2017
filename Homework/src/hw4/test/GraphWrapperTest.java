package hw4.test;
import hw4.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;


public class GraphWrapperTest<T> {
	Graph<String,String> graph1,graph2, graph3;
	GraphWrapper<String,String> wrapper1, wrapper2, wrapper3, wrapper4;
			
	 @Before
	  public void setUp(){
		graph1 = new Graph<String,String>(); 
		graph2 = new Graph<String,String>(); 
		graph3 = new Graph<String,String>(); 
	    wrapper1 = new GraphWrapper<String,String>();
	    wrapper2 = new GraphWrapper<String,String>();
	    wrapper3 = new GraphWrapper<String,String>();
	    wrapper4 = new GraphWrapper<String,String>();
	  }
	
	@Test 
	public void ConstructorTest() { 
		// an empty Graph
		new Graph<String,String>();
		new GraphWrapper<String,String>();
	}
	@Test
	public void InsertNodeTest() {
		// insert on an empty Graph
		wrapper1.addNode("A");
		wrapper2.addNode("1");
		Iterator<String> itr = wrapper1.listNodes();
		Iterator<String> itr2 = wrapper2.listNodes();
		assertEquals(itr.next(),"A");
		assertEquals(itr2.next(),"1");

		// insert on a graph with nodes and no edges
		wrapper1.addNode("B");
		wrapper2.addNode("2");
		itr = wrapper1.listNodes();
		itr2 = wrapper2.listNodes();
		itr.next();
		itr2.next();
		assertEquals(itr.next(),"B");
		assertEquals(itr2.next(),"2");
		
		// insert on a graph with edges
		wrapper1.addEdge("A","B","Label1");
		wrapper2.addEdge("1","2","Label2");
		wrapper1.addNode("C");
		wrapper2.addNode("3");
		itr = wrapper1.listNodes();
		itr2 = wrapper2.listNodes();
		itr.next();
		itr2.next();
		itr.next();
		itr2.next();
		assertEquals(itr.next(),"C");
		assertEquals(itr2.next(),"3");
		
		// insert a duplicate node
		wrapper1.addNode("A");
		wrapper2.addNode("1");
		itr = wrapper1.listNodes();
		itr2 = wrapper2.listNodes();
		assertEquals(itr.next(),"A");
		assertEquals(itr2.next(),"1");
		assertEquals(itr.next(),"B");
		assertEquals(itr2.next(),"2");
	}
	
	@Test
	public void InsertEdgeTest() {
		// insert edge on an empty graph
		graph1.addEdge("A","B","label1");
		assertFalse(graph1.isNode("A"));
		assertFalse(graph1.isNode("B"));
		assertFalse(graph1.isEdge("A","B","Label2"));
		
		// insert on a graph with nodes and no edges 
		wrapper3.addNode("X");
		wrapper3.addNode("Y");
		wrapper3.addEdge("X","Y","Label1");
		Iterator<String> itr = wrapper3.listChildren("X");
		assertEquals(itr.next(),"Y(Label1)");
		
		// insert on a graph with edges
		wrapper3.addNode("W");
		wrapper3.addNode("Z");
		wrapper3.addEdge("Z","W","Label2");
		itr = wrapper3.listChildren("Z");
		assertEquals(itr.next(),"W(Label2)");
		
		// add another edge to a node
		wrapper3.addEdge("X","W","Label2");
		itr = wrapper3.listChildren("X");
		assertEquals(itr.next(),"W(Label2)");

		// insert a duplicate edge 
		wrapper3.addEdge("X","W","Label2");
		itr = wrapper3.listChildren("X");
		assertEquals(itr.next(),"W(Label2)");
		itr.next();
		assertEquals(itr.next(),"Y(Label1)");

	}
	@Test
	public void NodeSearchTest() {
		// look for a node in an empty graph
		assertFalse(graph2.isNode("A"));
		
		// look for a node in a graph with nodes and no edges
		graph2.addNode("A");
		graph2.addNode("B");
		graph2.addNode("C");
		assertTrue(graph2.isNode("B"));
		assertTrue(graph2.isNode("C"));
		
		// look for a node in a graph with edges
		graph2.addEdge("A","C","Label1");
		assertTrue(graph2.isNode("C"));
		
		// look for a node that is not in a graph at all
		assertFalse(graph2.isNode("D"));
	}
	
	@Test
	public void EdgeSearchTest() {
		// look for an edge in an empty graph		
		assertFalse(graph3.isEdge("C", "D","LABEL1"));
		
		// look for an edge in a graph with nodes and no edges
		graph3.addNode("c");
		graph3.addNode("d");
		graph3.addEdge("c", "d", "label");
		assertTrue(graph3.isEdge("c", "d"));
		assertTrue(graph3.isEdge("c", "d","label"));

		
		// look for an edge in a graph with edges
		graph3.addNode("a");
		graph3.addEdge("a", "d", "label2");
		assertTrue(graph3.isEdge("a", "d"));
		assertTrue(graph3.isEdge("a", "d","label2"));
		
		// look for an edge that is not in  a graph at all
		assertFalse(graph3.isEdge("C", "D","LABEL1"));
	}
	
	@Test
	public void GetChildrenTest() {
		// look for all children of a node in an empty graph
		Iterator<String> itr = wrapper3.listChildren("X");
		assertFalse(itr.hasNext());
		
		// look for all children of a node in a graph
		wrapper4.addNode("W");
		wrapper4.addNode("X");
		wrapper4.addNode("Y");
		wrapper4.addNode("Z");
		wrapper4.addEdge("W","X","L1");
		wrapper4.addEdge("W","Y","L2");
		wrapper4.addEdge("W","Z","L3");

		// look for all children of a node not in the graph
		itr = wrapper4.listChildren("V");
		assertFalse(itr.hasNext());

		// look for all children of a node with no children in a graph
		itr = wrapper4.listChildren("Z");
		assertFalse(itr.hasNext());
	}
	
}
