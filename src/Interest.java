import java.io.File;
import java.util.ArrayList;


public class Interest extends Group {
	public static Interest find(String name) {
		Interest interest = new Interest();
		interest.setName(name);
		return (Interest) new Persister(interest).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	public Interest(String id) {
		this.setName(id);
	}

	public Interest() {
		super();
	}

	public void addStudent(User student) {
		this.getMembers().add(student);
		this.save();
		student.getInterests().add(this);
		student.save();
	}

	public static ArrayList<Interest> all() {
		ArrayList<Interest> list = new ArrayList<Interest>();
		File[] files = new Persister(new Interest("ignore")).getDirectory().listFiles();
		for (File interest : files) {
			String basename = interest.getName().split(".xml")[0];
			list.add(Interest.find(basename));
		}
		return list;
	}
}
