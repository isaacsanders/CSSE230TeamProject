import java.util.ArrayList;

/**
 * The Group class contains an ArrayList that stores all of its members.
 * 
 * @author campbeeg. Created Feb 6, 2013.
 */
public class Group implements Persistable {

	private String ID;
	private String name;
	private ArrayList<User> members;

	/**
	 * Constructs an empty group
	 * 
	 */
	public Group() {
		this.ID = null;
		this.name = null;
		this.members = null;
	}

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

	/**
	 * Returns the value of the field called 'name'.
	 * 
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the field called 'name' to the given value.
	 * 
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the value of the field called 'members'.
	 * 
	 * @return Returns the members.
	 */
	public ArrayList<User> getMembers() {
		return this.members;
	}

	/**
	 * Sets the field called 'members' to the given value.
	 * 
	 * @param members
	 *            The members to set.
	 */
	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}

	/**
	 * Adds a user to the group
	 * 
	 * @param newMember
	 * @return true on insertion, false otherwise
	 */
	public boolean add(User newMember) {
		return this.members.add(newMember);
	}

	@Override
	public String getID() {
		return this.ID;
	}
}
