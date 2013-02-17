import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for managing a user's Friends
 * 
 * @author mccormjt. Created Feb 10, 2013.
 */
public class FriendsServlet extends HttpServlet {

	private final String EMPTY = "";

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");

		// check session
		HttpSession session = request.getSession(false);

		// check if user is already logged in, if so go to main search page, if
		// not allow sign up
		if (session != null) {
			User currentUser = (User) session.getValue("user");
			User friend = User.find(request.getParameter("ID"));

			currentUser.addFriend(friend);
		}
		this.doGet(request, response);
	}
}