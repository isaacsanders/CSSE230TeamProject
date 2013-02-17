import java.io.File;
import java.util.ArrayList;


public class Residence extends Group {

	public final static String BLUMBERG = "Blumberg";
	public final static String MEES = "Mees";
	public final static String SCHARPENBERG = "Scharpenberg";
	public final static String PERCOPO = "Percopo";
	public final static String APARTMENTSEAST = "Apartments East";
	public final static String APARTMENTSWEST = "Apartments West";
	public final static String DEMING = "Deming";
	public final static String LAKESIDE = "Lakeside";
	public final static String SPEED = "Speed";
	public final static String OFFCAMPUS = "Off Campus";
	public final static String FRATHOUSE = "Fraternity House";
	public final static String BSB = "BSB";
	public final static String SKINNER = "Skinner";

	public static Residence find(String name) {
		Residence residence = new Residence();
		residence.setName(name);
		return (Residence) new Persister(residence).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	public Residence(String id) {
		this.setName(id);
	}

	public Residence() {
		super();
	}

	public boolean addStudent(User student) {
		boolean success = this.getMembers().add(student.getID()) && this.save();
		student.setResidence(this.getID());
		success = success && student.save();
		return success;
	}

	public boolean removeStudent(User student) {
		boolean success = this.getMembers().remove(student.getID()) && this.save();
		student.setResidence(null);
		success = success && student.save();
		return success;
	}

	public static ArrayList<Residence> all() {
		ArrayList<Residence> list = new ArrayList<Residence>();
		File[] files = new Persister(new Major("ignore")).getDirectory().listFiles();
		for (File residence : files) {
			String basename = residence.getName().split(".xml")[0];
			list.add(Residence.find(basename));
		}
		return list;
	}
}
