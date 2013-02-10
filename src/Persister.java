import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Persister {
	public static File DATABASE = new File("db");
	public File directory;
	private Persistable object;
	private File file;

	public static void main(String[] args) {
		User isaac = new User("sanderib", "Isaac Sanders");
		Persister userPersistance = new Persister(isaac);
		userPersistance.persist();
	}

	public Persister(Persistable object) {
		this.object = object;
	}

	private File getDirectory() {
		if (this.directory == null) {
			this.directory = new File(Persister.DATABASE, this.object.getClass().getName());
			this.directory.mkdirs();
		}

		return this.directory;
	}

	private File getFile() {
		if (this.file == null) {
			this.file = new File(this.getDirectory(), this.object.getID() + ".xml");
		}

		return this.file;
	}

	private String getFileName() {
		return this.getFile().getAbsolutePath();
	}

	/**
	 * When the persister persists an object, it XML encodes it to a file /db/:classname/:id.xml
	 */
	public void persist() {
		try {
			XMLEncoder xmle = new XMLEncoder(new FileOutputStream(this.getFileName()));
			xmle.writeObject(this.object);
			xmle.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
