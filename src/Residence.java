import java.io.File;
import java.util.ArrayList;


public class Residence extends Group {
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

	public void addStudent(User student) {
		this.getMembers().add(student);
		this.save();
		student.setResidence(this);
		student.save();
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
