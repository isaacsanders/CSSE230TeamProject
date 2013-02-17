import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

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
			User currentUser = (User)session.getValue("user");
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
					+ "<a href=\"MeetingsServlet\">Meetings</a>"
					+ "<a href=\"AccountServlet\">Account</a>"
					+ "<a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+"</div>"
					+ "<div id=\"content\">"
					+ "  <a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>";

			String closingHtml = "</div>"
					+ "</body>"
					+ "</html>";

			String username = request.getParameter("query");
			TreeSet<String> result = new TreeSet<String>();

			if (username == "") {
				String major = request.getParameter("major");
				boolean filtered = false;

				if (major != "") {
					filtered = true;
					result.addAll(Major.find(major).getMembers());
				}

				String club = request.getParameter("club");

				if (club != "") {
					TreeSet<String> clubMembers = Club.find(club).getMembers();
					if (filtered) {
						result.retainAll(clubMembers);
					} else {
						filtered = true;
						result.addAll(clubMembers);
					}
				}

				String sport = request.getParameter("sport");

				if (sport != "") {
					TreeSet<String> sportMembers = Sport.find(sport).getMembers();
					if (filtered) {
						result.retainAll(sportMembers);
					} else {
						filtered = true;
						result.addAll(sportMembers);
					}
				}

				String residence = request.getParameter("residence");

				if (residence != "") {
					TreeSet<String> residenceMembers = Residence.find(residence).getMembers();
					if (filtered) {
						result.retainAll(residenceMembers);
					} else {
						filtered = true;
						result.addAll(residenceMembers);
					}
				}

				String job = request.getParameter("job");

				if (job != "") {
					TreeSet<String> jobMembers = Job.find(job).getMembers();
					if (filtered) {
						result.retainAll(jobMembers);
					} else {
						filtered = true;
						result.addAll(jobMembers);
					}
				}

				String graduatingClass = request.getParameter("graduatingClass");

				if (graduatingClass != "") {
					TreeSet<String> graduatingClassMembers = GraduatingClass.find(graduatingClass).getMembers();
					if (filtered) {
						result.retainAll(graduatingClassMembers);
					} else {
						filtered = true;
						result.addAll(graduatingClassMembers);
					}
				}

				String friendsOnly = request.getParameter("friends");
				if (friendsOnly != null) {
					TreeSet<String> friends = currentUser.getFriends();
					if (filtered) {
						result.retainAll(friends);
					} else {
						result.addAll(friends);
					}
				} else {
					TreeSet<String> users = new TreeSet<String>();
					for (User user : User.all()) {
						users.add(user.getID());
					}
					if (filtered) {
						result.retainAll(users);
					} else {
						result.addAll(users);
					}
				}
			} else {
				if (User.find(username).exists()) {
					result.add(username);
				}
			}

			String resultHtml = "<section>";

			// If user with the username doesn't exist
			if (result.isEmpty()) {
				if (username == "") {
					resultHtml += "<p>There are no users that match your criteria";
				} else {
					resultHtml += "<p>There are no users with the Rose-Hulman username " + username;
				}
			} else {
				for (String userId : result) {
					User user = User.find(userId);
					resultHtml += "<h2>" + user.getName() + "</h2><p>" + user.getName();

					if (currentUser.equals(user)) {
						resultHtml += " is you!";
					} else {
						// If the user isn't connected
						int degreesOfSeparationBetweenCurrentAndResult = SocialCircle.degreesOfSeparationBetweenTwoUsers(currentUser, user);
						if (degreesOfSeparationBetweenCurrentAndResult == -1) {
							resultHtml += " is not connected to you.";
							resultHtml += "<br>"
									+ "<form action='/FriendsServlet' method='post'>"
									+ "<input type='hidden' name='ID' value='" + user.getID() + "'>"
									+ "<button type='submit'>Add " + user.getName() + " as a friend</button>"
									+ "</form>";
						} else {
							resultHtml += " is ";
							if (degreesOfSeparationBetweenCurrentAndResult == 1) {
								resultHtml += "a friend of yours!";
							} else {
								resultHtml += degreesOfSeparationBetweenCurrentAndResult
										+ " degrees of separation from you.";
							}

						}
					}
					resultHtml += "</p>";
				}
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
					+ "<a href=\"MeetingsServlet\">Meetings</a>"
					+ "<a href=\"AccountServlet\">Account</a>"
					+ "<a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+ "<a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>"
					+"</div>"
					+ "<div id=\"content\">"
					+ "<form id=\"searchForm\" action='/SearchServlet' method='post'>"
					+ "<select name='major'>"
					+ "<option value='' selected>Choose a Major</option>";

			for (Major major : Major.all()) {
				if (major != null) {
					bodyHtml += "<option value='" + major.getID() + "'>"
							+ major.getName() + "</option>";
				}
			}

			bodyHtml += "</select>"
					+ "<select name='club'>"
					+ "<option value='' selected>Choose a Club</option>";

			for (Club club : Club.all()) {
				if (club != null) {
					bodyHtml += "<option value='" + club.getID() + "'>"
							+ club.getName() + "</option>";
				}
			}

			bodyHtml += "</select>"
					+ "<select name='sport'>"
					+ "<option value='' selected>Choose a Sport</option>";

			for (Sport sport : Sport.all()) {
				if (sport != null) {
					bodyHtml += "<option value='" + sport.getID() + "'>"
							+ sport.getName() + "</option>";
				}
			}

			bodyHtml += "</select>"
					+ "<select name='residence'>"
					+ "<option value='' selected>Choose a Residence</option>";

			for (Residence residence : Residence.all()) {
				if (residence != null) {
					bodyHtml += "<option value='" + residence.getID() + "'>"
							+ residence.getName() + "</option>";
				}
			}

			bodyHtml += "</select>"
					+ "<select name='job'>"
					+ "<option value='' selected>Choose a Job</option>";

			for (Job job : Job.all()) {
				if (job != null) {
					bodyHtml += "<option value='" + job.getID() + "'>"
							+ job.getName() + "</option>";
				}
			}

			bodyHtml += "</select>";

			bodyHtml += "<select name='graduatingClass'>"
					+ "<option value='' selected>Choose a Graduating Class</option>";

			for (GraduatingClass graduatingClass : GraduatingClass.all()) {
				if (graduatingClass != null) {
					bodyHtml += "<option value='" + graduatingClass.getID() + "'>"
							+ graduatingClass.getName() + "</option>";
				}
			}

			bodyHtml += "</select>";

			bodyHtml += "<input type=checkbox name='friends' value='off'>Friends only?</input>";

			bodyHtml += "<input type=text name='query' placeholder='Type your query here'>"
					+ "<button type=submit>Search</button>"
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
