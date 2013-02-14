import java.io.File;
import java.util.ArrayList;


public class Club extends Group {

	public static Club find(String name) {
		Club club = new Club();
		club.setName(name);
		return (Club) new Persister(club).find();
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

	public Club(String id) {
		this.setName(id);
	}

	public Club() {
		super();
	}

	public void addStudent(User student) {
		this.getMembers().add(student);
		this.save();
		student.getClubs().add(this);
		student.save();
	}

	public static ArrayList<Club> all() {
		ArrayList<Club> list = new ArrayList<Club>();
		File[] files = new Persister(new Club("ignore")).getDirectory().listFiles();
		for (File major : files) {
			String basename = major.getName().split(".xml")[0];
			list.add(Club.find(basename));
		}
		return list;
	}
}
