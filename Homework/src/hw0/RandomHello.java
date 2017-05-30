package hw0;
import java.util.Random;
/**
 * RandomHello selects a random greeting to display to the user.
 */
public class RandomHello {
	 /**
     * Uses a RandomHello object to print
     * a random greeting to the console.
     */
    public static void main(String[] argv) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }
    /**
     * @return a random greeting from a list of five different greetings.
     */
    public String getGreeting() {
        // YOUR CODE GOES HERE	
    	// Create a generator and a variable to store the random number
    	Random randomGenerator = new Random();
    	int index = randomGenerator.nextInt(4);
    	// Set up return statements
    	String[] greetings = new String[5];
    	greetings[0] = "Hello World";
    	greetings[1] = "Hola Mundo";
    	greetings[2] = "Bonjour Monde";
    	greetings[3] = "Hallo Welt";
    	greetings[4] = "Ciao Mondo";
    	
    	// Return a random statement
    	return greetings[index];
    }

}
