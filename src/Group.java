import java.util.ArrayList;

/**
 * The Group class contains an ArrayList that stores all of its members.
 * 
 * @author campbeeg. Created Feb 6, 2013.
 */
public class Group implements Persistable {

	public static Group find(String name) {
		Group group = new Group();
		group.setName(name);
		return (Group) new Persister(group).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	@Override
	public boolean delete() {
		return new Persister(this).delete();
	}

	private String name;
	private ArrayList<User> members;

	/**
	 * Constructs an empty group with the specified name
	 * 
	 * @param name
	 * 
	 */
	public Group(String name) {
		this.setMembers(new ArrayList<User>());
		this.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<User> getMembers() {
		return this.members;
	}

	public void setMembers(ArrayList<User> members) {
		this.members = members;
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
		this.setName(name);
		this.setMembers(members);
	}

	private boolean add(User newMember) {
		return this.members.add(newMember);
	}

	@Override
	public String getID() {
		return this.name;
	}

	public Group() {
		this.setMembers(new ArrayList<User>());
		this.setName(null);
	}
}
