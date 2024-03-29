import java.io.File;
import java.util.ArrayList;


public class Job extends Group {

	public final static String TA = "Teacher's Assistant";
	public final static String GRADER = "Grader";
	public final static String SRC = "SRC";
	public final static String MAILROOM = "Mail Room";
	public final static String LIBRARY = "Library";
	public final static String HOMEWORKHOTLINE = "Homework Hotline";

	public static Job find(String name) {
		Job job = new Job();
		job.setName(name);
		return (Job) new Persister(job).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	public Job(String id) {
		this.setName(id);
	}

	public Job() {
		super();
	}

	public boolean addStudent(User student) {
		boolean success = this.getMembers().add(student.getID()) && this.save();
		student.setJob(this.getID());
		success = success && student.save();
		return success;
	}

	public boolean removeStudent(User student) {
		boolean success = this.getMembers().remove(student.getID()) && this.save();
		student.setJob(null);
		success = success && student.save();
		return success;
	}

	public static ArrayList<Job> all() {
		ArrayList<Job> list = new ArrayList<Job>();
		File[] files = new Persister(new Job("ignore")).getDirectory().listFiles();
		for (File job : files) {
			String basename = job.getName().split(".xml")[0];
			list.add(Job.find(basename));
		}
		return list;
	}
}
