
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
		persister.save();
		persister = new Persister(this);
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
		this.getMembers().add(student);
		this.save();
		student.getMajors().add(this);
		student.save();
	}

}
