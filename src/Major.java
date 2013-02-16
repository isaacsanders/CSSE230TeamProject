import java.io.File;
import java.util.ArrayList;


public class Major extends Group {

	public static Major find(String name) {
		Major major = new Major();
		major.setName(name);
		return (Major) new Persister(major).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	public Major(String id) {
		this.setName(id);
	}

	public Major() {
		super();
	}

	public void addStudent(User student) {
		ArrayList<User> list = this.getMembers();
		list.add(student);
		this.setMembers(list);
		this.save();
		student.getMajors().add(this);
		student.save();
	}

	public static ArrayList<Major> all() {
		ArrayList<Major> list = new ArrayList<Major>();
		File[] files = new Persister(new Major("ignore")).getDirectory().listFiles();
		for (File major : files) {
			String basename = major.getName().split(".xml")[0];
			list.add(Major.find(basename));
		}
		return list;
	}

}
