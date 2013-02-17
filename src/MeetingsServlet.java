import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.TreeSet;

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
	private final String MEETINGADD = "MEETINGADD";
	private final String MEETINGTIME = "MEETINGTIME";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// check session
		HttpSession session = request.getSession(false);

		// check if user is already logged in, if so go to main search page, if
		// not allow sign up
		if (session != null) {
			User user = (User) session.getValue("user");

			String user2 = request.getParameter("user");
			String query = request.getParameter("query");
			if (!(user2 == "" || query == "")) {

				String newMeeting = request.getParameter("query");
				String otherUser = request.getParameter("user");
				String[] times = newMeeting.split(",");
				String month = times[0];
				String day = times[1];
				String startTime = times[2];
				String endTime = times[3];
				Meeting meeting = new Meeting(new Dated(
						Integer.parseInt(month), Integer.parseInt(day),
						Integer.parseInt(startTime)), new Dated(
						Integer.parseInt(month), Integer.parseInt(day),
						Integer.parseInt(endTime)));

				// get users specific meeting list
				TreeSet<String> meetingList = user.getMeetings();

				meetingList.add(meeting.toString());
				user.setMeetings(meetingList);

				User meet = User.find(otherUser);
				TreeSet<String> meetin = meet.getMeetings();
				meetin.add(meeting.toString());
				meet.setMeetings(meetin);
				meet.save();
				user.save();
			}
			this.doGet(request, response);

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

		User user = (User) session.getValue("user");
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
					+ "<div class=\"shift\" id=\"content\">"
					+ "<form id=\"searchForm\" action='/MeetingsServlet' method='post'>"
					+ "<select name='user'>"
					+ "<option value='' selected>Choose a User</option>";

			for (User major : User.all()) {
				if (major != null) {
					body += "<option value='" + major.getID() + "'>"
							+ major.getName() + "</option>";
				}
			}

			body += "</select>";

			body += "<input type=text input name='query' placeholder='Month, Day, Starting time, Ending time : 4, 21, 15'>"
					+ "<button type=submit>Add Meeting</button>"
					+ "</form>"
					+ "<div><ul id=\"currentSports\">"
					+ this.getListMeetings(user)
					+ "</ul></div>"
					+ "</div>"
					+ "</body>" + "</html>";

			out.println(body);

		} else {
			// redirect to login screen if not logged in
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);
		}

	}

	/**
	 * returns the comparison boolean for if a user edited thier paramters
	 * 
	 * @param actualYear
	 * @param currentValue
	 * @return
	 */
	private boolean checkParameter(HttpServletRequest request, String parameter) {
		String checkedParameter = request.getParameter(parameter);
		if (checkedParameter != null && checkedParameter.equals(parameter)) {
			return true;
		}
		return false;
	}

	/**
	 * returns the proper selected attribute for users current state of
	 * something
	 * 
	 * @param actualYear
	 * @param currentValue
	 * @return
	 */
	private String getProperSelector(String actual, String currentValue) {
		if (actual.equals(currentValue)) {
			return "selected = \"selected\"";
		}
		return "";
	}

	// method that returns an html list of all of a user's meetings
	private String getListMeetings(User user) {
		String output = "";
		for (String meetings : user.getMeetings()) {
			output += "<li>" + meetings + "</li>";
		}
		return output;
	}
}