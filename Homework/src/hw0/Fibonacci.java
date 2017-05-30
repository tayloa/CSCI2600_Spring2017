	/**
	 * This is part of HW0: Environment Setup and Java Introduction.
	 */
	package hw0;
	
	/**
	 * Fibonacci calculates the <var>n</var>th term in the Fibonacci sequence.
	 *
	 * The first two terms of the Fibonacci sequence are both 1,
	 * and each subsequent term is the sum of the previous two terms.
	 *
	 * @author mbolin
	 */
	public class Fibonacci {
	
	    /**
	     * Calculates the desired term in the Fibonacci sequence.
	     *
	     * @param n the index of the desired term; the first index of the sequence is 0
	     * @return the <var>n</var>th term in the Fibonacci sequence
	     * @throws IllegalArgumentException if <code>n</code> is not a nonnegative number
	     */
	    public int getFibTerm(int n) {
//	    	Changed from (n <= 0)to n < 0, also check if n =1 or 0 instead of n <=2
	        if (n < 0) {
	            throw new IllegalArgumentException(n + " is negative");
	        }else if (n == 0) {
	            return 1;
	        }else if (n == 1) {
	            return 1;
	        } else {
//	        	Fibonnaci should be getFibTerm(n-1) + getFibTerm(n-2) for the return
	            return getFibTerm(n - 1) + getFibTerm(n - 2);
	        }
	    }
	
	}
