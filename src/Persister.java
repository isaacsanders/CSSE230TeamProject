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
		isaac.setID("sanderib");
		isaac.setName("Isaac Sanders");
		isaac.save();

		User foundUser = User.find("sanderib");
		System.out.println(foundUser.getName());

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

	private File getFile() {
		if (this.file == null) {
			this.file = new File(this.getDirectory(), this.object.getID() + ".xml");
		}

		return this.file;
	}

	private String getFilename() {
		return this.getFile().getAbsolutePath();
	}

	public boolean delete() {
		return this.getFile().delete();
	}

	/**
	 * When Persister persists an object, it XML encodes it to a file /db/:classname/:id.xml
	 */
	public boolean save() {
		try {
			XMLEncoder xmle = new XMLEncoder(new FileOutputStream(this.getFilename()));
			xmle.writeObject(this.object);
			xmle.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public Persistable find() {
		try {
			XMLDecoder xmld = new XMLDecoder(new FileInputStream(this.getFilename()));
			this.object = (Persistable) xmld.readObject();
			xmld.close();
			return this.object;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
