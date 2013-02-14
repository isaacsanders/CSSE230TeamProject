import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for talking to the web page to take in a user's signup information
 * 
 * @author mccormjt. Created Feb 10, 2013.
 */
public class SearchServlet extends HttpServlet {

	private final String EMPTY = "";

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// check session
		HttpSession session = request.getSession(false);

		// check if user is already logged in, if so go to main search page, if
		// not allow sign up
		if (session != null) {
			User currentUser = User.find((String) session.getValue("ID"));
			// PRODUCE HTML PAGE
			PrintWriter out = response.getWriter();
			// doc type
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\"http:"
					+ "//www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html lang=\"en\">");
			// head
			String headHtml = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; "
					+ "charset=utf-8\"/><meta name=\"created\" content=\"Tue, 05 Feb 2013 21:47:31 "
					+ "GMT\"><meta name=\"description\" content=\"\"><meta name=\"keywords\" content=\"\">"
					+ "<link href=\"styleSheet.css\" type=\"text/css\" rel=\"stylesheet\" />"
					+ "<title>Social Circle - Search</title></head>";
			out.println(headHtml);
			// body
			String bodyHtml = "<body>"
					+ "<div id=\"navigation\">"
					+ "  <a href=\"/SearchServlet\">Search</a>"
					+ "  <a href=\"friends.html\">Friends</a>"
					+ "  <a href=\"meetings.html\">Meetings</a>"
					+ "  <a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+"</div>"
					+ "<div id=\"content\">"
					+ "  <a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>";

			String closingHtml = "</div>"
					+ "</body>"
					+ "</html>";

			String username = request.getParameter("query");
			User result = User.find(username);
			String resultHtml = "<section>";

			// If user with the username doesn't exist
			if (result == null) {
				resultHtml += "<p>There are no users with the Rose-Hulman username " + username;
			} else {

				resultHtml += "<h2>" + result.getName() + "</h2><p>" + result.getName();

				if (currentUser.equals(result)) {
					resultHtml += " is you!";
				} else {
					// If the user isn't connected
					int degreesOfSeparationBetweenCurrentAndResult = SocialCircle.degreesOfSeparationBetweenTwoUsers(currentUser, result);
					if (degreesOfSeparationBetweenCurrentAndResult == -1) {
						resultHtml += " is not connected to you.";
						resultHtml += "<br>"
								+ "<form action='/FriendsServlet' method='post'>"
								+ "<input type='hidden' name='ID' value='" + result.getID() + "'>"
								+ "<button type='submit'>Add " + result.getName() + " as a friend</button>"
								+ "</form>";
					} else {
						resultHtml += " is " + degreesOfSeparationBetweenCurrentAndResult
								+ " degrees of separation from you.";
					}
				}
				resultHtml += "</p>";
			}
			resultHtml +=  "</section>";
			out.println(bodyHtml + resultHtml + closingHtml);
		} else {
			// redirect to login screen if not logged in
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);
		}
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

			// PRODUCE HTML PAGE
			PrintWriter out = response.getWriter();
			// doc type
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\"http:"
					+ "//www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html lang=\"en\">");
			// head
			String headHtml = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; "
					+ "charset=utf-8\"/><meta name=\"created\" content=\"Tue, 05 Feb 2013 21:47:31 "
					+ "GMT\"><meta name=\"description\" content=\"\"><meta name=\"keywords\" content=\"\">"
					+ "<link href=\"styleSheet.css\" type=\"text/css\" rel=\"stylesheet\" />"
					+ "<title>Social Circle - Search</title></head>";
			out.println(headHtml);
			// body
			String bodyHtml = "<body>"
					+ "<div id=\"navigation\">"
					+ "<a href=\"/SearchServlet\">Search</a>"
					+ "<a href=\"FriendsServlet\">Friends</a>"
					+ "<a href=\"MeetingsServlet\">Meetings</a>"
					+ "<a href=\"AccountServlet\">Account</a>"
					+ "<a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+ "<a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>"
					+"</div>"
					+ "<div id=\"content\">"
					+ "<form action='/SearchServlet' method='post'>"
					+ "<input type=text name='query' placeholder='Type your query here'>"
					+ "</form>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
			out.println(bodyHtml);

		} else {
			// redirect to login screen if not logged in
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);
		}

	}
}
