import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for managing a user's meetings
 * 
 * @author mccormjt. Created Feb 10, 2013.
 */
public class MeetingsServlet extends HttpServlet {

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
			String body = "<body>"
					+ "<div id=\"navigation\">"
					+ "<a href=\"/SearchServlet\">Search</a>"
					+ "<a href=\"MeetingsServlet\">Meetings</a>"
					+ "<a href=\"AccountServlet\">Account</a>"
					+ "<a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+ "<a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>"
					+ "</div>"
					+ "<div id=\"content\">"
					+ "<form id=\"searchForm\" action='/SearchServlet' method='post'>"
					+ "<select name='user'>"
					+ "<option value='' selected>Choose a User</option>";

			for (User major : User.all()) {
				if (major != null) {
					body += "<option value='" + major.getID() + "'>"
							+ major.getName() + "</option>";
				}
			}

			body += "</select>";

			body += "<input type=text name='query' placeholder='Type your query here'>"
					+ "<button type=submit>Search</button>"
					+ "</form>"
					+ "</div>" + "</body>" + "</html>";
			out.println(body);

		} else {
			// redirect to login screen if not logged in
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);
		}

	}
}