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
		ArrayList<User> list = new ArrayList<User>();
		for (String friendId : this.friends) {
			list.add(User.find(friendId));
		}
		return list;
	}

	public void setFriends(ArrayList<User> friends) {
		ArrayList<String> list = new ArrayList<String>();
		for (User friend : friends) {
			list.add(friend.getID());
		}
		this.friends = list;
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
		ArrayList<Major> list = new ArrayList<Major>();
		for (String majorId : this.majors) {
			list.add(Major.find(majorId));
		}
		return list;
	}

	public void setMajors(ArrayList<Major> majors) {
		ArrayList<String> list = new ArrayList<String>();
		for (Major major : majors) {
			list.add(major.getID());
		}
		this.majors = list;
	}

	public ArrayList<Meeting> getMeetings() {
		return this.meetings;
	}

	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
	}

	public GraduatingClass getGraduatingClass() {
		return GraduatingClass.find(this.graduatingClass);
	}

	public void setGraduatingClass(GraduatingClass graduatingClass) {
		this.graduatingClass = graduatingClass.getID();
	}

	public ArrayList<Club> getClubs() {
		ArrayList<Club> list = new ArrayList<Club>();
		for (String clubId : this.clubs) {
			list.add(Club.find(clubId));
		}
		return list;
	}

	public void setClubs(ArrayList<Club> clubs) {
		ArrayList<String> list = new ArrayList<String>();
		for (Club club : clubs) {
			list.add(club.getID());
		}
		this.clubs = list;
	}

	public ArrayList<Sport> getSports() {
		ArrayList<Sport> list = new ArrayList<Sport>();
		for (String sportId : this.sports) {
			list.add(Sport.find(sportId));
		}
		return list;
	}

	public void setSports(ArrayList<Sport> sports) {
		ArrayList<String> list = new ArrayList<String>();
		for (Sport sport : sports) {
			list.add(sport.getID());
		}
		this.sports = list;
	}

	public Residence getResidence() {
		return Residence.find(this.residence);
	}

	public void setResidence(Residence residence) {
		this.residence = residence.getID();
	}

	public ArrayList<Interest> getInterests() {
		ArrayList<Interest> list = new ArrayList<Interest>();
		for (String interestId : this.interests) {
			list.add(Interest.find(interestId));
		}
		return list;
	}

	public void setInterests(ArrayList<Interest> interests) {
		ArrayList<String> list = new ArrayList<String>();
		for (Interest interest : interests) {
			list.add(interest.getID());
		}
		this.interests = list;
	}

	public Job getJob() {
		return Job.find(this.job);
	}

	public void setJob(Job job) {
		this.job = job.getID();
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

	private ArrayList<String> friends = new ArrayList<String>();
	private String id;
	private String name;
	private ArrayList<String> majors = new ArrayList<String>();
	private ArrayList<Meeting> meetings;
	private String graduatingClass;
	private ArrayList<String> clubs = new ArrayList<String>();
	private ArrayList<String> sports = new ArrayList<String>();
	private String residence;
	private ArrayList<String> interests = new ArrayList<String>();
	private String job;

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
		this.setClubs(new ArrayList<Club>());
		this.setSports(new ArrayList<Sport>());
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
