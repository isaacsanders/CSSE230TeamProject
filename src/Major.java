import java.io.File;
import java.util.ArrayList;


public class Major extends Group {

	public final static String SOFTWAREENGINEER = "Software Engineer";
	public final static String MECHANICALENGINEER = "Mechanical Engineer";
	public final static String COMPUTERENGINEER = "Computer Engineer";
	public final static String COMPUTERSCIENCE = "Computer Science";
	public final static String ELECTRICALENGINEER = "Electrical Engineer";
	public final static String BIOMEDICALENGINEER = "Biomedical Engineer";
	public final static String CHEMICALENGINEER = "Chemical Engineer";
	public final static String OPTICALENGINEER = "Optical Engineer";
	public final static String CIVILENGINEER = "Civil Engineer";
	public final static String APPLIEDBIOLOGY = "Applied Biology";
	public final static String MATH = "Math";
	public final static String PHYSICS = "Physics";
	public final static String ECONOMICS = "Economics";
	public final static String CHEMISTRY = "Chemistry";
	public final static String BIOCHEMISTRY = "BioChemistry";
	public final static String MILITARYSCIENCE = "Military Science";
	public final static String VOID = "Void";

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

	public boolean addStudent(User student) {
		boolean success;
		ArrayList<User> members = this.getMembers();
		success = members.add(student);
		this.setMembers(members);
		this.save();
		ArrayList<Major> majors = student.getMajors();
		success = success && majors.add(this);
		student.setMajors(majors);
		student.save();
		return success;
	}

	public boolean removeStudent(User student) {
		boolean success;
		ArrayList<User> members = this.getMembers();
		success = members.remove(student);
		this.setMembers(members);
		this.save();
		ArrayList<Major> majors = student.getMajors();
		success = success && majors.remove(this);
		student.setMajors(majors);
		student.save();
		return success;
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
