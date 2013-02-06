import java.util.HashMap;

/**
 * SocialCircle is a class that holds and manipulates all Users, Groups, and
 * user input
 * 
 * @author campbeeg. Created Feb 3, 2013.
 */
public class SocialCircle {
	private HashMap<String, User> members;

	/**
	 * Constructs a new SocialCircle with an empty members HashMap
	 * 
	 */
	public SocialCircle() {
		this.members = new HashMap<String, User>();
	}

	private void add(User newMember) {
		this.members.put(newMember.getID(), newMember);
	}

	private void delete(User deleted) {
		this.members.remove(deleted.getID());
	}
	
	private User find(String name) {
		return this.members.get(name);
	}
}
