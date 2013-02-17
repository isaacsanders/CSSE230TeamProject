import java.util.ArrayList;
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
		this.friends = new TreeSet<String>(list);
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
		this.majors = new TreeSet<String>(list);
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
		GraduatingClass currentGraduatingClass = this.getGraduatingClass();
		if (currentGraduatingClass != null) {
			currentGraduatingClass.removeStudent(this);
		}
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
		this.clubs = new TreeSet<String>(list);
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
		this.sports = new TreeSet<String>(list);
	}

	public Residence getResidence() {
		return Residence.find(this.residence);
	}

	public void setResidence(Residence residence) {
		Residence currentResidence = this.getResidence();
		if (currentResidence != null) {
			currentResidence.removeStudent(this);
		}
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
		this.interests = new TreeSet<String>(list);
	}

	public Job getJob() {
		return Job.find(this.job);
	}

	public void setJob(Job job) {
		Job currentJob = this.getJob();
		if (currentJob != null) {
			currentJob.removeStudent(this);
		}
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

	private TreeSet<String> friends = new TreeSet<String>();
	private String id;
	private String name;
	private TreeSet<String> majors = new TreeSet<String>();
	private ArrayList<Meeting> meetings;
	private String graduatingClass;
	private TreeSet<String> clubs = new TreeSet<String>();
	private TreeSet<String> sports = new TreeSet<String>();
	private String residence;
	private TreeSet<String> interests = new TreeSet<String>();
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
