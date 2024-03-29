import java.io.File;
import java.util.ArrayList;


public class Sport extends Group {

	public final static String TENNIS = "Tennis";
	public final static String BASKETBALL = "Basketball";
	public final static String SOCCER = "Soccer";
	public final static String BASEBALL = "Baseball";
	public final static String RAQUETBALL = "Raquetball";
	public final static String VOLLEYBALL = "Volleyball";
	public final static String ULTIMATEFRISBEE = "Ultimate Frisbee";
	public final static String FLAGFOOTBALL = "Flag Football";
	public final static String TACKLEFOOTBALL = "Tackle Football";
	public final static String WALLEYBALL = "Walleyball";
	public final static String CHEERLEADING = "Cheer Leading";
	public final static String BADMINTON = "Badminton";
	public final static String HOCKEY = "Hockey";
	public final static String SOFTBALL = "Softball";
	public final static String TRACK = "Track";
	public static final String VOID = "Void";


	public static Sport find(String name) {
		Sport sport = new Sport();
		sport.setName(name);
		return (Sport) new Persister(sport).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	public Sport(String id) {
		this.setName(id);
	}

	public Sport() {
		super();
	}

	public boolean addStudent(User student) {
		return this.getMembers().add(student.getID()) && this.save() &&
				student.getSports().add(this.getID()) && student.save();
	}

	public boolean removeStudent(User student) {
		return this.getMembers().remove(student.getID()) && this.save() &&
				student.getSports().remove(this.getID()) && student.save();
	}


	public static ArrayList<Sport> all() {
		ArrayList<Sport> list = new ArrayList<Sport>();
		File[] files = new Persister(new Sport("ignore")).getDirectory().listFiles();
		for (File sport : files) {
			String basename = sport.getName().split(".xml")[0];
			list.add(Sport.find(basename));
		}
		return list;
	}
}
