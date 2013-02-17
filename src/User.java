import java.util.TreeSet;


/**
 * TODO Put here a description of what this class does.
 *
 * @author andrewca.
 *         Created Feb 6, 2013.
 */
public class User implements Persistable {

	public static User find(String id) {
		User user = new User();
		user.setID(id);
		return (User) new Persister(user).find();
	}

	@Override
	public boolean save() {
		return new Persister(this).save();
	}

	@Override
	public boolean delete() {
		return new Persister(this).delete();
	}

	public TreeSet<String> getFriends() {
		return this.friends;
	}

	public void setFriends(TreeSet<String> friends) {
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

	public TreeSet<String> getMajors() {
		return this.majors;
	}

	public void setMajors(TreeSet<String> majors) {
		this.majors = majors;
	}

	public TreeSet<String> getMeetings() {
		return this.meetings;
	}

	public void setMeetings(TreeSet<String> meetings) {
		this.meetings = meetings;
	}

	public String getGraduatingClass() {
		return this.graduatingClass;
	}

	public void setGraduatingClass(String graduatingClass) {
		this.graduatingClass = graduatingClass;
	}

	public TreeSet<String> getClubs() {
		return this.clubs;
	}

	public void setClubs(TreeSet<String> clubs) {
		this.clubs = clubs;
	}

	public TreeSet<String> getSports() {
		return this.sports;
	}

	public void setSports(TreeSet<String> sports) {
		this.sports = sports;
	}

	public String getResidence() {
		return this.residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public TreeSet<String> getInterests() {
		return this.interests;
	}

	public void setInterests(TreeSet<String> interests) {
		this.interests = interests;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public TreeSet<String> getGroups() {
		TreeSet<String> list = new TreeSet<String>(this.getClubs());
		list.addAll(this.getInterests());
		list.addAll(this.getSports());
		list.addAll(this.getMajors());
		list.add(this.getGraduatingClass());
		list.add(this.getResidence());
		list.add(this.getJob());
		return list;
	}

	private TreeSet<String> friends = new TreeSet<String>();
	private String id;
	private String name;
	private TreeSet<String> majors = new TreeSet<String>();
	private TreeSet<String> meetings;
	private String graduatingClass;
	private TreeSet<String> clubs = new TreeSet<String>();
	private TreeSet<String> sports = new TreeSet<String>();
	private String residence;
	private TreeSet<String> interests = new TreeSet<String>();
	private String job;

	public User(String id){
		this.setID(id);
		this.setFriends(new TreeSet<String>());
		this.setMajors(new TreeSet<String>());
		this.setMeetings(new TreeSet<String>());
		this.setClubs(new TreeSet<String>());
		this.setSports(new TreeSet<String>());
		this.setInterests(new TreeSet<String>());
	}

	public User() {
		this.setName(null);
		this.setFriends(new TreeSet<String>());
		this.setMajors(new TreeSet<String>());
		this.setMeetings(new TreeSet<String>());
		this.setClubs(new TreeSet<String>());
		this.setSports(new TreeSet<String>());
		this.setInterests(new TreeSet<String>());
	}

	public boolean exists() {
		return User.find(this.getID()) != null;
	}

	public boolean hasFriends() {
		return !this.getFriends().isEmpty();
	}

	public void addFriend(User friend) {
		this.getFriends().add(friend.getID());
		this.save();
		friend.getFriends().add(this.getID());
		friend.save();
	}

	public boolean isFriendsWith(User other) {
		return this.getFriends().contains(other.getID());
	}

	@Override
	public boolean equals(Object other) {
		return this.getID().equals(((User) other).getID());
	}
}
