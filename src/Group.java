import java.util.ArrayList;
import java.util.TreeSet;

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
	private TreeSet<String> members;

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
		ArrayList<User> list = new ArrayList<User>();
		for (String memberId : this.members) {
			list.add(User.find(memberId));
		}
		return list;
	}

	public void setMembers(ArrayList<User> members) {
		ArrayList<String> list = new ArrayList<String>();
		for (User member : members) {
			list.add(member.getID());
		}
		this.members = new TreeSet<String>(list);
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

	@Override
	public String getID() {
		return this.name;
	}

	public Group() {
		this.setMembers(new ArrayList<User>());
		this.setName(null);
	}

	public User findUser(String username) {
		int index = this.getMembers().indexOf(User.find(username));
		if (index > -1) {
			return this.getMembers().get(index);
		} else {
			return null;
		}
	}

	public ArrayList<User> membersThatAreFriendsOf(User user) {
		ArrayList<User> list = new ArrayList<User>();
		for (User member : this.getMembers()) {
			if (user.isFriendsWith(member)) {
				list.add(member);
			}
		}
		return list;
	}
}
