package hw4;

import java.util.*;

public class IntPriorityQueue {

	/** <b>IntPriorityQueue</b> is a <b>mutable</b> priority queue of integers.                                                                                       
    It uses the heap data structure, represented as array, to implement the                                                                                       
    a subset of the operations of a priority queue ADT. The array is 1-based.                                                                                     
    */

    private List<Integer> heap;
    private int size;

    // TODO: Fill in the abstraction function and representation invariant.                                                                                       

    // Abstraction Function:                                                                                                                                      
	//    IntPriorityQueue, q, represents a max heap in which each element has 
	//    priority over another. Each position in the heap is determined by a priority.
	//    The highest priority elements are placed in the front. If there is one 
	//    element in the heap, there is only a root, heap[0] = Integer.MAX_VALUE. Every  
	//    insertion and removal can reorder the queue.                                                                                                                                     

    // Representation Invariant:                                                                                                                                  
    // heap[0] = Integer.MAX_VALUE;                                                                                                                                
	//    if heap.length = 0
	//    		heap[0] = int.MAX_VALUE
	//    heap.max == heap[0]
	//    size = heap.size() - 1
	//    foreach i, 0<=i<heap.length: heap[i] != null:
	//		 if i != 0 // Parent is greater than child
	//	          heap[(i-1)/ 2] > heap[i]
	//	     if heap[2i + 1] != null //  Left child is less than parent
	//	          heap[2i + 1] < heap[i]
	//	     if heap[2i + 2] != null // Right child is less than parent
	//	          heap[2i + 2] < heap[i]
	//	     else
	//	          size = heap.length - 1 &&
	//	          heap[size] != 0      
    // size = heap.length - 1

    /** @effects: creates a new empty IntPriorityQueue                                                                                                            
     */
    public IntPriorityQueue() {
        heap = new ArrayList<Integer>();
        heap.add(Integer.MAX_VALUE);
        size = 0;
        checkRep();
    }

    /** @param: heap The list containing the priority queue elements                                                                                              
        @param: size The size of the queue                                                                                                                        
        @requires: heap and size comply to rep invariant described above                                                                                          
        @effects: creates a new IntPriorityQueue of size size with elements heap                                                                                  
     */
    public IntPriorityQueue(List<Integer> heap, int size) {
        this.heap = heap;
        this.size = size;
        checkRep();
    }
    
    /** Checks that the representation invariant isn't violated
	*/
    // Throws a RuntimeException if the rep invariant is violated.
    private void checkRep() throws RuntimeException {
        if (heap.get(0)!= Integer.MAX_VALUE) {
            throw new RuntimeException("Top of heap isn't the max integer value.");
        }
        if (size == heap.size()-1) {
            throw new RuntimeException("The size of the heap is incorrect.");
        }
        for(int i=0;i < heap.size();i++){
        	 if (heap.get(i) != null){
                  if (heap.get((i-1)/ 2) < heap.get(i)) {
                      throw new RuntimeException("Top of heap isn't the max integer value.");
                  }
        	 }
             if (heap.get(2*i + 1) != null){
                  if (heap.get(2*i + 1) > heap.get(i)) {
                      throw new RuntimeException("Top of heap isn't the max integer value.");
                  }
             }
             if (heap.get(2*i + 2) != null){
                  if (heap.get(2*i + 2) > heap.get(i)) {
                      throw new RuntimeException("Top of heap isn't the max integer value.");
                  }
             }
       }
    }
    
    /** @param: index The index for which heap property must be restored                                                                                          
    	@requires: 0 < index <= size                                                                                                                              
    	@modifies: this                                                                                                                                           
    	@effects: restores heap property for index                                                                                                                
     */
    private void upheap(int index) {
    	if ((0 >= index) || (index > size))
    		throw new IllegalArgumentException("0 < index <= size");
    	else {
    		int value = heap.get(index);
    		int k = index;
    		// restores the heap property for index moving elements                                                                                                   
    		// smaller than heap[index] down the heap                                                                                                                 
    		while (heap.get(k/2) <= value) {
    			heap.set(k, heap.get(k/2)); k = k/2;
    		}
    		// inserts value at appropriate place                                                                                                                     
    		heap.set(k,value);
    		checkRep();
    	}
    	
    }

    /** @param: value The new integer value to insert in the queue                                                                                                
    	@modifies: this                                                                                                                                           
    	@effects: inserts value at appropriate place, increases size by one                                                                                       
     */
    public void insert(int value) {
    	size = size + 1;
    	// add value at the end of array                                                                                                                          
    	if (size == heap.size()) {
    		heap.add(size, value);
    	}
    	else {
    		heap.set(size, value);
    	}
    	// now restore the heap property moving value up if necessary                                                                                            
    	upheap(size);
    	checkRep();
    }

    /** @param: index The index for which heap property must be restored                                                                                          
    	@requires: 0 < index <= size                                                                                                                              
    	@modifies: this                                                                                                                                           
    	@effects: moves heap[index] down the heap until heap property is restored                                                                                 
    */
    private void downheap(int index) {
    	if ((0 >= index) || (index > size)) {
    		throw new IllegalArgumentException("index must be: 0 < index <= size");
    	}
    	else {
    		int value = heap.get(index);
    		int k = index;
    		while (k <= size/2) {
    			int j = k+k;
    			// heap[j] and heap[j+1] are the two children of heap[k]                                                                                              
    			// we need the larger of the two                                                                                                                      
    			if (j < size && heap.get(j) < heap.get(j+1)) j = j+1;
    			// if value is larger then the larger of k's children, we have                                                                                        
    			// found place for value, exit the loop                                                                                                               
    			if (value >= heap.get(j)) break;
    			// otherwise, replace heap[k] with the larger of its children                                                                                         
    			heap.set(k, heap.get(j));
    			// and move k down the heap                                                                                                                           
    			k = j;
    		}
    		// insert value at appropriate place                                                                                                                      
    		heap.set(k,value);
        	checkRep();
    	}

    }
    
    /** @return: The largest value in heap. If priority queue has duplicate keys,                                                                                
    			 we take "largest" to mean "any largest value"                                                                                                   
		@modifies: this                                                                                                                                           
		@effects: Removes largest value, restores heap property, reduces size by one                                                                              
		@throws: QueueEmpty exception if this queue is empty.
     */

    public int remove() {
    	if (isEmpty()) {
    		throw new QueueEmpty("Cannot remove element of an empty queue!"); 
    	}
    	else {
    		int value = heap.get(1);
    		heap.set(1,heap.get(size));
    		size = size - 1;
    		if (!isEmpty()) downheap(1);
        	checkRep();
    		return value;
    	}
    }

    /** @returns: true if heap is empty
     * 			  false otherwise
     */
    public boolean isEmpty() {
    	if (size == 0) return true;
    	else return false;
    }
    
    /** @returns: size of heap
     */
    public int size() {
    	return size;
    }
 
}

class QueueEmpty extends RuntimeException {
	public QueueEmpty(String string) {
		super(string);
	}
}
