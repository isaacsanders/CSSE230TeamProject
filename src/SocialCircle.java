import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

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

	public static int degreesOfSeparationBetweenTwoUsers(User first, User second) {
		if (first.hasFriends() && second.hasFriends()) {
			PriorityQueue<Stack<User>> queue = new PriorityQueue<Stack<User>>(first.getFriends().size(), new DegreesOfSeparationComparator());
			Stack<User> stack = new Stack<User>();
			stack.push(first);
			queue.add(stack);
			while (!queue.isEmpty()) {
				stack = queue.poll();
				Stack<User> temp;
				for (User friend : stack.peek().getFriends()) {
					if (friend.equals(second)) {
						return stack.size();
					} else {
						temp = (Stack<User>) stack.clone();
						temp.push(friend);
						queue.add(temp);
					}
				}
			}
		}
		return -1;
	}
}
