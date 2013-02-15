import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class UserTest {

	private User user;

	@Before
	public void setUp() throws Exception {
		this.user = new User();
	}

	@Test
	public void testHasFriends() {
		assertFalse(this.user.hasFriends());
		User other = new User();
		this.user.addFriend(other);
		assertTrue(this.user.hasFriends() && other.hasFriends());
	}

	@Test
	public void testExists() {
		assertTrue(new User().exists());
		new User("sanderib").save();
		assertTrue(new User("sanderib").exists());
	}

}
