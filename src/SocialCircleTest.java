import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class SocialCircleTest {

	private User isaac;
	private User john;
	private User ethan;
	private User chris;

	@Before
	public void setUp() throws Exception {
		this.isaac = new User();
		this.john = new User();
		this.ethan = new User();
		this.chris = new User();
		this.isaac.addFriend(this.john);
		this.john.addFriend(this.ethan);
		this.ethan.addFriend(this.chris);
	}

	@Test
	public void testDegreesOfSeparationBetweenTwoUsers() {
		this.assertDegreesOfSeparation(-1, this.isaac, new User());
		this.assertDegreesOfSeparation(1, this.isaac, this.john);
		this.assertDegreesOfSeparation(2, this.isaac, this.ethan);
		this.assertDegreesOfSeparation(3, this.isaac, this.chris);
		this.assertDegreesOfSeparation(3, this.chris, this.isaac);
		this.assertDegreesOfSeparation(2, this.john, this.chris);
	}

	@Test
	public void testSeparatedWithTwoDifferentSizedPaths() {
		User a, b, c;
		a = new User();
		b = new User();
		c = new User();
		a.addFriend(b);
		a.addFriend(c);
		b.addFriend(c);
		this.assertDegreesOfSeparation(1, a, c);
		this.assertDegreesOfSeparation(1, a, b);
		this.assertDegreesOfSeparation(1, b, c);

	}

	public void assertDegreesOfSeparation(int degrees, User first, User second) {
		assertEquals(degrees, SocialCircle.degreesOfSeparationBetweenTwoUsers(first, second));
	}

	@Test
	public void testBiggerNetwork() {
		User a, b, c, d;
		a = new User();
		b = new User();
		c = new User();
		d = new User();
		a.addFriend(b);
		a.addFriend(c);
		a.addFriend(d);
		b.addFriend(c);
		this.assertDegreesOfSeparation(2, d, b);
	}

	@Test
	public void testLudicrousNetwork() {
		User a, b, c, d, e, f;
		a = new User();
		b = new User();
		c = new User();
		d = new User();
		e = new User();
		f = new User();
		a.addFriend(b);
		a.addFriend(c);
		b.addFriend(d);
		b.addFriend(e);
		c.addFriend(d);
		c.addFriend(e);
		d.addFriend(f);
		d.addFriend(e);
		e.addFriend(f);
		this.assertDegreesOfSeparation(3, a, f);
	}

	@Test
	public void testOptimalMeetingTimes() {
		Date newYears = new Date();
		assertEqual();
	}
}
