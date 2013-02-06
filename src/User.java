import java.util.ArrayList;


/**
 * TODO Put here a description of what this class does.
 *
 * @author andrewca.
 *         Created Feb 6, 2013.
 */
public class User {
	private ArrayList<User> firends;
	private String id;
	private String name;
	private Group major;
	private ArrayList<Group> meeting;
	private Group year;
	private ArrayList<Group> club;
	private ArrayList<Group> sport;
	private Group residence;
	private Group interests;
	private Group job;
	public User(String id){
		this.setId(id);
		this.name=null;
		this.firends=new ArrayList<User>();
		this.major=null;
		this.meeting=new ArrayList<Group>();
		this.year=null;
		this.club=new ArrayList<Group>();
		this.sport=new ArrayList<Group>();
		this.residence=null;
		this.job=null;
	}
	/**
	 * Returns the value of the field called 'id'.
	 * @return Returns the id.
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * Sets the field called 'id' to the given value.
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
}
