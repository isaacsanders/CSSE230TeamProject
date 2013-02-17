import java.io.File;
import java.util.ArrayList;


public class GraduatingClass extends Group {
	public static GraduatingClass find(String name) {
		GraduatingClass graduatingClass = new GraduatingClass();
		graduatingClass.setName(name);
		return (GraduatingClass) new Persister(graduatingClass).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	public GraduatingClass(String id) {
		this.setName(id);
	}

	public GraduatingClass() {
		super();
	}

	public boolean addStudent(User student) {
		boolean success;
		ArrayList<User> members = this.getMembers();
		success = members.add(student);
		this.setMembers(members);
		success = success && this.save();
		student.setGraduatingClass(this);
		success = success && student.save();
		return success;
	}

	public boolean removeStudent(User student) {
		boolean success;
		ArrayList<User> members = this.getMembers();
		success = members.remove(student);
		this.setMembers(members);
		success = success && this.save();
		student.setGraduatingClass(this);
		success = success && student.save();
		return success;
	}

	public static ArrayList<GraduatingClass> all() {
		ArrayList<GraduatingClass> list = new ArrayList<GraduatingClass>();
		File[] files = new Persister(new Major("ignore")).getDirectory().listFiles();
		for (File graduatingClass : files) {
			String basename = graduatingClass.getName().split(".xml")[0];
			list.add(GraduatingClass.find(basename));
		}
		return list;
	}
}
