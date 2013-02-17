import java.io.File;
import java.util.ArrayList;


public class Club extends Group {

	public final static String DRAMA = "Drama";
	public final static String LAMBDACHIALPHA = "Lambda Chi Alpha";
	public final static String TRIANGLE = "Triangle";
	public final static String DELTASIG = "Delta Sig";
	public final static String FIJI = "Fiji";
	public final static String SIGMANU = "Sigma Nu";
	public final static String ATO = "ATO";
	public final static String PIKE = "PIKE";
	public final static String CHIOMEGA = "CHIOMEGA";
	public final static String ALPHAOMICRONPI = "Alpha Omicron Pi";
	public final static String ALPHAPHIOMEGA = "ALPHAPHIOMEGA";
	public final static String THETAXI = "Theta Xi";
	public final static String RISE = "RISE";
	public final static String ROBOTICS = "ROBOTICS";
	public final static String ECOCAR = "Eco Car";
	public final static String BAND = "Band";
	public final static String INTERVARSITYCHRISTIANS = "Intervarsity Christians";
	public final static String VOID = "Void";

	public static Club find(String name) {
		Club club = new Club();
		club.setName(name);
		return (Club) new Persister(club).find();
	}

	@Override
	public boolean save() {
		Persister persister = new Persister(this);
		persister.delete();
		return persister.save();
	}

	public Club(String id) {
		this.setName(id);
	}

	public Club() {
		super();
	}

	public boolean addStudent(User student) {
		return this.getMembers().add(student.getID()) && this.save() &&
				student.getClubs().add(this.getID()) && student.save();
	}

	public boolean removeStudent(User student) {
		return this.getMembers().remove(student.getID()) && this.save()
				&& student.getClubs().remove(this.getID()) && student.save();
	}

	public static ArrayList<Club> all() {
		ArrayList<Club> list = new ArrayList<Club>();
		File[] files = new Persister(new Club("ignore")).getDirectory().listFiles();
		for (File club : files) {
			String basename = club.getName().split(".xml")[0];
			list.add(Club.find(basename));
		}
		return list;
	}
}
