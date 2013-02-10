import java.util.ArrayList;


/**
 * TODO Put here a description of what this class does.
 *
 * @author andrewca.
 *         Created Feb 6, 2013.
 */
public class User implements Persistable {
	public ArrayList<User> getFriends() {
		return this.friends;
	}

	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}

	@Override
	public String getID() {
		return this.id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group getMajor() {
		return this.major;
	}

	public void setMajor(Group major) {
		this.major = major;
	}

	public ArrayList<Group> getMeeting() {
		return this.meeting;
	}

	public void setMeeting(ArrayList<Group> meeting) {
		this.meeting = meeting;
	}

	public Group getYear() {
		return this.year;
	}

	public void setYear(Group year) {
		this.year = year;
	}

	public ArrayList<Group> getClubs() {
		return this.clubs;
	}

	public void setClubs(ArrayList<Group> clubs) {
		this.clubs = clubs;
	}

	public ArrayList<Group> getSport() {
		return this.sport;
	}

	public void setSport(ArrayList<Group> sport) {
		this.sport = sport;
	}

	public Group getResidence() {
		return this.residence;
	}

	public void setResidence(Group residence) {
		this.residence = residence;
	}

	public ArrayList<Group> getInterests() {
		return this.interests;
	}

	public void setInterests(ArrayList<Group> interests) {
		this.interests = interests;
	}

	public Group getJob() {
		return this.job;
	}

	public void setJob(Group job) {
		this.job = job;
	}

	private ArrayList<User> friends;
	private String id;
	private String name;
	private Group major;
	private ArrayList<Group> meeting;
	private Group year;
	private ArrayList<Group> clubs;
	private ArrayList<Group> sport;
	private Group residence;
	private ArrayList<Group> interests;
	private Group job;

	public User(String id){
		this.setID(id);
		this.setName(null);
		this.setFriends(new ArrayList<User>());
		this.setMajor(null);
		this.setMeeting(new ArrayList<Group>());
		this.setYear(null);
		this.setClubs(new ArrayList<Group>());
		this.setSport(new ArrayList<Group>());
		this.setResidence(null);
		this.setJob(null);
	}

	public User() {
	}
}
