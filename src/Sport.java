import java.io.File;
import java.util.ArrayList;


public class Sport extends Group {
	public static Sport find(String name) {
		Sport sport = new Sport();
		sport.setName(name);
		return (Sport) new Persister(sport).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		persister.save();
		persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	public Sport(String id) {
		this.setName(id);
	}

	public Sport() {
		super();
	}

	public void addStudent(User student) {
		this.getMembers().add(student);
		this.save();
		student.getSports().add(this);
		student.save();
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
