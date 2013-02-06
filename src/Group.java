import java.util.ArrayList;

/**
 * The Group class contains an ArrayList that stores all of its members.
 *
 * @author campbeeg.
 *         Created Feb 6, 2013.
 */
public class Group {
	private ArrayList<User> members;
	
	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 */
	public Group() {
		this.members = new ArrayList<User>();
	}
	
	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param members
	 */
	public Group(ArrayList<User> members) {
		this.members = members;
	}
	
	private boolean add(User newMember) {
		
	}
}
