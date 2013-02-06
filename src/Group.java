import java.util.ArrayList;

/**
 * The Group class contains an ArrayList that stores all of its members.
 * 
 * @author campbeeg. Created Feb 6, 2013.
 */
public class Group {

	private String name;
	private ArrayList<User> members;

	/**
	 * Constructs an empty group with the specified name
	 * 
	 * @param name
	 * 
	 */
	public Group(String name) {
		this.members = new ArrayList<User>();
		this.name = name;
	}

	/**
	 * Constructs a group with the members in the arrayList and with the
	 * specified name
	 * 
	 * @param name
	 * 
	 * @param members
	 */
	public Group(String name, ArrayList<User> members) {
		this.name = name;
		this.members = members;
	}

	private boolean add(User newMember) {
		return this.members.add(newMember);
	}
}
