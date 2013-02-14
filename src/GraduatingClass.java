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

	public void addStudent(User student) {
		this.getMembers().add(student);
		this.save();
		student.setGraduatingClass(this);
		student.save();
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
