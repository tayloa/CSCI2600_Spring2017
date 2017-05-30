/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

import java.lang.Iterable;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Collections;

/**
 * This is a container can be used to contain Balls.
 * A given Ball may only appear in a BallContainer once.
 */
public class BallContainer implements Iterable<Ball> {

    // Contents of the BallContainer.
    private Set<Ball> contents;
    private double volume;

    /**
     * Constructor that creates a new ballcontainer.
     */
    public BallContainer() {
        this.contents = new LinkedHashSet<Ball>();
        this.volume = 0;
    }

    /**
     * Implements the Iterable interface for this container.
     * @return an Iterator over the Ball objects contained
     * in this container.
     */
    public Iterator<Ball> iterator() {
        // If we just returned the iterator of "contents", a client
        // could call the remove() method on the iterator and modify
	// it behind our backs.  Instead, we wrap contents in an
	// "unmodifiable set"; calling remove() on this iterator
	// throws an exception.  This is an example of avoiding
	// "representation exposure."  You will learn more about this
	// concept later in the course.
	return Collections.unmodifiableSet(contents).iterator();
    }

    /**
     * Adds a ball to the container. This method returns <tt>true</tt>
     * if ball was successfully added to the container, i.e. ball is
     * not already in the container. Of course, you are allowed to put
     * a Ball into a container only once. Hence, this method returns
     * <tt>false</tt>, if ball is already in the container.
     * @param b Ball to be added.
     * @return true if ball was successfully added to the container,
     * i.e. ball is not already in the container. Returns false, if ball is
     * already in the container.
     */
    public boolean add(Ball b) {
    	// if the ball is in the set don's add it, else add it
    	if (this.contents.contains(b)){
    		return false;
    	}
    	else
    		this.contents.add(b);
    	// add the new ball's volume to the container's volume
    		this.volume += b.getVolume();
    		return true;
    }

    /**
     * Removes a ball from the container. This method returns
     * <tt>true</tt> if ball was successfully removed from the
     * container, i.e. ball is actually in the container. You cannot
     * remove a Ball if it is not already in the container and so ths
     * method will return <tt>false</tt>, otherwise.
     * @param b Ball to be removed.
     * @return true if ball was successfully removed from the container,
     * i.e. ball is actually in the container. Returns false, if ball is not
     * in the container.
     */
    public boolean remove(Ball b) {
    	// if the container is empty return false
    	if (this.contents.isEmpty()){
    		return false;
    	// if the ball is found, remove it and adjust the volume
    	}else if (this.contents.contains(b)){
    		this.volume = this.volume - b.getVolume();
    		this.contents.remove(b);
    		return true;
    	}
	    return false;
    }

    /**
     * Each Ball has a volume. This method returns the total volume of
     * all the Balls in the container.
     * @return the volume of the contents of the container.
     */
    public double getVolume() {
        return this.volume;
    }

    /**
     * Returns the number of Balls in this container.
     * @return the number of Balls in this container.
     */
    public int size() {
        return this.contents.size();	
    }

    /**
     * Empties the container, i.e. removes all its contents.
     */
    public void clear() {
        this.contents.clear();
        // reset the volume to 0
        this.volume = 0;
    }

    /**
     * This method returns <tt>true</tt> if this container contains
     * the specified Ball. It will return <tt>false</tt> otherwise.
     * @param b Ball to be checked if its in container
     * @return true if this container contains the specified Ball. Returns
     * false, otherwise.
     */
    public boolean contains(Ball b) {
    	if (this.contents.contains(b)){
	        return true;
	    }       
    	return false;
    }

}