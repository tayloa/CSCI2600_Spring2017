package hw4.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import hw4.*; 

public class IntPriorityQueueTest {

	private static IntPriorityQueue queue = null;
	private static int[] elements = null; 
	private static int max = 0;
	
	private static int NUM_ELEMENTS_TO_TEST = 10;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		queue = new IntPriorityQueue();
		Random r = new Random();
		elements = new int[NUM_ELEMENTS_TO_TEST];
		
		for (int i=0; i<NUM_ELEMENTS_TO_TEST; i++) {
			elements[i] = r.nextInt(10);
			if (elements[i] > max) 
				max = elements[i];
		}
	}

	@Test
	public void testOneElement() {
		queue.insert(5); // queue.insert(10);
		assertEquals(5,queue.remove());
	}

	@Test
	public void testThreeElements() {
		queue.insert(5); queue.insert(10); queue.insert(11);
		assertEquals("remove does not return the largest element",11,queue.remove());
		assertEquals("remove does not decrement size",2,queue.size());
		assertEquals("remove does not return the largest element",10,queue.remove());
		assertEquals("remove does not return the largest element",5,queue.remove());
		assertEquals("queue must be empty",true,queue.isEmpty());
	}

	@Test
	public void testException() {
		boolean thrown = false;		
		queue.insert(5); queue.insert(10); queue.insert(11);
		assertEquals("remove does not return the largest element",11,queue.remove());
		assertEquals("remove does not decrement size",2,queue.size());
		assertEquals("remove does not return the largest element",10,queue.remove());
		assertEquals("remove does not return the largest element",5,queue.remove());
		assertEquals("queue must be empty",true,queue.isEmpty());
		
		try {
			queue.remove();
		} catch (RuntimeException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testMoreElements() {
		for (int i=0; i < NUM_ELEMENTS_TO_TEST; i++) {
			queue.insert(elements[i]);
			assertEquals("Size must be "+(i+1),i+1,queue.size());
		}
		assertEquals("Wrong value removed",max,queue.remove());
		assertEquals("Size is not 9",9,queue.size());
		while (!queue.isEmpty()) { queue.remove(); } // removes all elements to clear queue for next test.
	}
	
	@Test
	public void testMoreElementsAgain() {
		for (int i=0; i < NUM_ELEMENTS_TO_TEST; i++) {
			queue.insert(i);
			assertEquals("Size must be "+(i+1),i+1,queue.size());
		}
		for (int i=0; i < NUM_ELEMENTS_TO_TEST; i++) {
			assertEquals("Wrong value removed",NUM_ELEMENTS_TO_TEST - 1 - i,queue.remove());
			assertEquals("Size is not right",NUM_ELEMENTS_TO_TEST - 1 - i,queue.size());
		}
		
	}
	
	@Test
	public void testInsertAfterRemove() {
		queue.insert(1);
		queue.insert(5);
		queue.remove();
		queue.insert(10);
		queue.insert(11);
		assertEquals(11,queue.remove());
		assertEquals(2,queue.size());
		
		while (!queue.isEmpty()) queue.remove();
				
	}
	
}
