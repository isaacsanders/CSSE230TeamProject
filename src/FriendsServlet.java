import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

	/**
	 * takes in the user inputs from a "sign up"
	 * 
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");

		// check session
		HttpSession session = request.getSession(false);

		// check if user is already logged in, if so go to main search page, if
		// not allow sign up
		if (session != null) {
			User currentUser = (User) session.getValue("user");

			// PRODUCE HTML PAGE
			PrintWriter out = response.getWriter();
			// doc type
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\"http:"
					+ "//www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html lang=\"en\">");
			// head
			out.println("<head><meta http-equiv=\"Content-Type\" content=\"text/html; "
					+ "charset=utf-8\"/><meta name=\"created\" content=\"Tue, 05 Feb 2013 21:47:31 "
					+ "GMT\"><meta name=\"description\" content=\"\"><meta name=\"keywords\" content=\"\">"
					+ "<link href=\"styleSheet.css\" type=\"text/css\" rel=\"stylesheet\" />"
					+ "<title>Social Circle - Sign Up!</title></head>");
			// body
			out.println("<body>"
					+ "<div id=\"navigation\">"
					+ "<a href=\"/SearchServlet\">Search</a>"
					+ "<a href=\"FriendsServlet\">Friends</a>"
					+ "<a href=\"MeetingsServlet\">Meetings</a>"
					+ "<a href=\"AccountServlet\">Account</a>"
					+ "<a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+ "<a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>"
					+ "</div>" + "<div id=\"content\">"
					+ "<form action='/FriendsServlet' method='get'>"
					+ "<select name='major'>"
					+ "<option value='' selected>Choose a Major</option>");

			for (Major major : currentUser.getMajors()) {
				if (major != null) {
					out.println("<option value='" + major.getID() + "'>"
							+ major.getName() + "</option>");
				}
			}

			out.println("</select>"
					+ "<button type='submit'>Filter</button>"
					+ "</form>");

			out.println("<ul>");

			String major = request.getParameter("major");
			ArrayList<User> friends;
			if (major == null || major.isEmpty()) {
				friends = currentUser.getFriends();
			} else {
				friends = Major.find(major)
						.membersThatAreFriendsOf(currentUser);
			}

			for (User friend : friends) {
				out.println("<li>" + friend.getName() + "</li>");
			}

			out.println("</ul></div>" + "</body>"
					+ "</html>");

		} else {
			// redirect to login screen if not logged in
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);
		}

	}
}