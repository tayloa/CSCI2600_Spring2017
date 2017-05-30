package hw6;
import java.util.Comparator;

public class PathComparator implements Comparator<Path<?>> {
	 public int compare(Path<?> p1, Path<?> p2) {
		    return p1.getCost().compareTo(p2.getCost()); 
		  }
}
