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

	@Override
	public boolean delete() {
		return new Persister(this).delete();
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

	public ArrayList<Major> getMajors() {
		return this.majors;
	}

	public void setMajors(ArrayList<Major> majors) {
		this.majors = majors;
	}

	public ArrayList<Meeting> getMeetings() {
		return this.meetings;
	}

	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
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

	public ArrayList<Sport> getSports() {
		return this.sports;
	}

	public void setSports(ArrayList<Sport> sports) {
		this.sports = sports;
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

	public ArrayList<Group> getGroups() {
		ArrayList<Group> list = new ArrayList<Group>(this.getClubs());
		list.addAll(this.getInterests());
		list.addAll(this.getSports());
		list.addAll(this.getMajors());
		list.add(this.getGraduatingClass());
		list.add(this.getResidence());
		list.add(this.getJob());
		return list;
	}

	private ArrayList<User> friends = new ArrayList<User>();
	private String id;
	private String name;
	private ArrayList<Major> majors = new ArrayList<Major>();
	private ArrayList<Meeting> meetings;
	private GraduatingClass graduatingClass;
	private ArrayList<Club> clubs = new ArrayList<Club>();
	private ArrayList<Sport> sports = new ArrayList<Sport>();
	private Residence residence;
	private ArrayList<Interest> interests = new ArrayList<Interest>();
	private Job job;

	public User(String id){
		this.setID(id);
		this.setFriends(new ArrayList<User>());
		this.setMajors(new ArrayList<Major>());
		this.setMeetings(new ArrayList<Meeting>());
		this.setClubs(new ArrayList<Club>());
		this.setSports(new ArrayList<Sport>());
		this.setInterests(new ArrayList<Interest>());
	}

	public User() {
		this.setName(null);
		this.setFriends(new ArrayList<User>());
		this.setMajors(new ArrayList<Major>());
		this.setMeetings(new ArrayList<Meeting>());
		this.setGraduatingClass(null);
		this.setClubs(new ArrayList<Club>());
		this.setSports(new ArrayList<Sport>());
		this.setResidence(null);
		this.setJob(null);
		this.setInterests(new ArrayList<Interest>());
	}

	public boolean exists() {
		return User.find(this.getID()) != null;
	}

	public boolean hasFriends() {
		return !this.getFriends().isEmpty();
	}

	public void addFriend(User friend) {
		this.getFriends().add(friend);
		this.save();
		friend.getFriends().add(this);
		friend.save();
	}

	public boolean isFriendsWith(User other) {
		int index = this.getFriends().indexOf(other);
		return index > -1;
	}

	@Override
	public boolean equals(Object other) {
		return this.getID().equals(((User) other).getID());
	}
}
