import java.io.Serializable;


/**
 * @author sanderib
 *
 * This interface is to allow interaction with the Persister class.
 */
public interface Persistable extends Serializable {

	/**
	 * @return This returns a unique identifier for the object.
	 */
	String getID();
	boolean save();
}
