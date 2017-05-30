/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

/**
 * This is a simple object that has a volume.
 */
// You may not make Ball implement the Comparable interface.
public class Ball {

    private double volume;

    /**
     * Constructor that creates a new ball object with the specified volume.
     * @param volume Volume of the new object.
     */
    public Ball(double volume) {
        this.volume = volume;
    }

    /**
     * Returns the volume of the Ball.
     * @return the volume of the Ball.
    */
    public double getVolume() {
        return this.volume;
    }

}


