import java.util.ArrayList;


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

	public Major getMajor() {
		return this.major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public ArrayList<Group> getMeeting() {
		return this.meeting;
	}

	public void setMeeting(ArrayList<Group> meeting) {
		this.meeting = meeting;
	}

	public GraduatingClass getGraduatingClass() {
		return this.graduatingClass;
	}

	public void setGraduatingClass(GraduatingClass graduatingClass) {
		this.graduatingClass = graduatingClass;
	}

	public ArrayList<Club> getClubs() {
		return this.clubs;
	}

	public void setClubs(ArrayList<Club> clubs) {
		this.clubs = clubs;
	}

	public ArrayList<Sport> getSport() {
		return this.sport;
	}

	public void setSport(ArrayList<Sport> sport) {
		this.sport = sport;
	}

	public Residence getResidence() {
		return this.residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

	public ArrayList<Interest> getInterests() {
		return this.interests;
	}

	public void setInterests(ArrayList<Interest> interests) {
		this.interests = interests;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	private ArrayList<User> friends;
	private String id;
	private String name;
	private Major major;
	private ArrayList<Group> meeting;
	private GraduatingClass graduatingClass;
	private ArrayList<Club> clubs;
	private ArrayList<Sport> sport;
	private Residence residence;
	private ArrayList<Interest> interests;
	private Job job;

	public User(String id){
		this.setID(id);
		this.setName(null);
		this.setFriends(new ArrayList<User>());
		this.setMajor(null);
		this.setMeeting(new ArrayList<Group>());
		this.setGraduatingClass(null);
		this.setClubs(new ArrayList<Club>());
		this.setSport(new ArrayList<Sport>());
		this.setResidence(null);
		this.setJob(null);
	}

	public User() {
		this.setClubs(new ArrayList<Club>());
		this.setFriends(new ArrayList<User>());
	}

	public boolean exists() {
		return User.find(this.getID()) != null;
	}

	public boolean hasFriends() {
		return !this.getFriends().isEmpty();
	}

	public void addFriend(User friend) {
		this.getFriends().add(friend);
		friend.getFriends().add(this);
	}
}
