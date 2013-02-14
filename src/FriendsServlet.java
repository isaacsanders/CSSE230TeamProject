import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet for managing a user's Friends
 * 
 * @author mccormjt. Created Feb 10, 2013.
 */
public class FriendsServlet extends HttpServlet {

	private final String EMPTY = "";

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
					+ "<a href=\"AccountServlet\">Meetings</a>"
					+ "<a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+ "<a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>"
					+ "</div>" + "<div id=\"content\">" + "</div>" + "</body>"
					+ "</html>");

		} else {
			// redirect to login screen if not logged in
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);
		}

	}
}