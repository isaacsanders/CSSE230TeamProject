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
		this.setMembers(new TreeSet<String>());
		this.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeSet<String> getMembers() {
		return this.members;
	}

	public void setMembers(TreeSet<String> members) {
		this.members = members;
	}

	@Override
	public String getID() {
		return this.name;
	}

	public Group() {
		this.setMembers(new TreeSet<String>());
		this.setName(null);
	}

	public User findUser(String username) {
		if (this.getMembers().contains(username)) {
			return User.find(username);
		} else {
			return null;
		}
	}

	public TreeSet<String> membersThatAreFriendsOf(User user) {
		TreeSet<String> list = new TreeSet<String>();
		for (String memberId : this.getMembers()) {
			User member = User.find(memberId);
			if (user.isFriendsWith(member)) {
				list.add(memberId);
			}
		}
		return list;
	}
}
