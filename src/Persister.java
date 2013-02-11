import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Persister {
	public static File DATABASE = new File("db");
	public File directory;
	private Persistable object;
	private File file;

	public static void main(String[] args) {
		User isaac = new User();
		isaac.setID("barf");
		isaac.setName("Foo Bar");
		Persister userPersistance = new Persister(isaac);
		userPersistance.save();
		isaac = (User) userPersistance.find("User", "sanderib");
		isaac.setName("Isaac Foo Bar");
		userPersistance.save();

		Group cs = new Group("CS");
		Persister group = new Persister(cs);
		group.save();
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

	private File getDirectory(String klass) {
		this.directory = new File(Persister.DATABASE, klass);
		return this.directory;
	}

	private File getFile() {
		if (this.file == null) {
			this.file = new File(this.getDirectory(), this.object.getID() + ".xml");
		}

		return this.file;
	}

	private File getFile(String klass, String id) {
		this.file = new File(this.getDirectory(klass), id + ".xml");
		return this.file;
	}

	private String getFilename() {
		return this.getFile().getAbsolutePath();
	}

	private String getFilename(String klass, String id) {
		return this.getFile(klass, id).getAbsolutePath();
	}

	/**
	 * When the persister persists an object, it XML encodes it to a file /db/:classname/:id.xml
	 */
	public void save() {
		try {
			XMLEncoder xmle = new XMLEncoder(new FileOutputStream(this.getFilename()));
			xmle.writeObject(this.object);
			xmle.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Persistable find(String klass, String id) {
		try {
			XMLDecoder xmld = new XMLDecoder(new FileInputStream(this.getFilename(klass, id)));
			this.object = (Persistable) xmld.readObject();
			xmld.close();
			return this.object;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
