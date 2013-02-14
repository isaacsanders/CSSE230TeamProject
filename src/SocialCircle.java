import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TimeZone;

/**
 * SocialCircle is a class that holds and manipulates all Users, Groups, and
 * user input
 * 
 * @author campbeeg. Created Feb 3, 2013.
 */
public class SocialCircle {
	private static final int FIRST_HAPPENS_BEFORE_SECOND = -1;
	private static final int SECOND_HAPPENS_BEFORE_FIRST = 1;
	private HashMap<String, User> members;

	public static void main(String[] args) {
		TimeZone timezone = TimeZone.getTimeZone("America/Indianapolis");
	}

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

	public static Meeting optimalMeetingTime(Meeting first, Meeting second) {
		Date startTime;
		switch (first.getStartTime().compareTo(second.getStartTime())) {
		case FIRST_HAPPENS_BEFORE_SECOND:
			startTime = second.getStartTime();
			break;
		case SECOND_HAPPENS_BEFORE_FIRST:
			startTime = first.getStartTime();
			break;
		default:
			startTime = first.getStartTime();
		}
		Date endTime;
		switch (first.getEndTime().compareTo(second.getEndTime())) {
		case FIRST_HAPPENS_BEFORE_SECOND:
			endTime = first.getEndTime();
			break;
		case SECOND_HAPPENS_BEFORE_FIRST:
			endTime = second.getEndTime();
			break;
		default:
			endTime = first.getEndTime();
		}
		return new Meeting(startTime, endTime);
	}
}
