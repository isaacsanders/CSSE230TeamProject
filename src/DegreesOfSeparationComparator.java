import java.util.Collection;
import java.util.Comparator;


public class DegreesOfSeparationComparator implements Comparator<Collection<User>> {

	@Override
	public int compare(Collection<User> first, Collection<User> second) {
		return ((Integer) first.size()).compareTo(second.size());
	}

}