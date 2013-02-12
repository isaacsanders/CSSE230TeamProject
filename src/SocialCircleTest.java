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
		assertEquals(-1, SocialCircle.degreesOfSeparationBetweenTwoUsers(this.isaac, new User()));
		assertEquals(1, SocialCircle.degreesOfSeparationBetweenTwoUsers(this.isaac, this.john));
		assertEquals(2, SocialCircle.degreesOfSeparationBetweenTwoUsers(this.isaac, this.ethan));
		assertEquals(3, SocialCircle.degreesOfSeparationBetweenTwoUsers(this.isaac, this.chris));
		assertEquals(3, SocialCircle.degreesOfSeparationBetweenTwoUsers(this.chris, this.isaac));
		assertEquals(2, SocialCircle.degreesOfSeparationBetweenTwoUsers(this.john, this.chris));
	}

}
