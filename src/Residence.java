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

	public boolean addStudent(User student) {
		boolean success;
		ArrayList<User> members = this.getMembers();
		success = members.add(student);
		this.setMembers(members);
		success = success && this.save();
		student.setResidence(this);
		success = success && student.save();
		return success;
	}

	public boolean removeStudent(User student) {
		boolean success;
		ArrayList<User> members = this.getMembers();
		success = members.remove(student);
		this.setMembers(members);
		success = success && this.save();
		student.setResidence(this);
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
