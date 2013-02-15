import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet for managing a user's account
 * 
 * @author mccormjt. Created Feb 10, 2013.
 */
public class AccountServlet extends HttpServlet {

	private static final String EMPTY = "";

	/**
	 * takes in the user inputs for changing user information
	 * 
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// check session
		HttpSession session = request.getSession(false);
		String sessionID = session.getValue("ID").toString();

		// check for delete account
		String delete = request.getParameter("delete");
		if (delete == "delete") {
			// delete the user
			User u = User.find(sessionID);
			u.delete();

			// terminate session
			session.invalidate();

			// redirect to login screen
			String url = response.encodeRedirectURL("/Home");
			response.sendRedirect(url);

		} else {

			// delete old user
			User user = User.find(sessionID);

			// get any old parameters that are needed
			//get name
			String first = request.getParameter("first");
			String last = request.getParameter("last");
			String name = first + " " + last;
			//get grad year
			GraduatingClass year = new GraduatingClass(request.getParameter("year"));
			//get major change
			String change = request.getParameter("change");
			String major = request.getParameter("major");
			Major theMajor = new Major(major);
			ArrayList<Major> majorList = user.getMajors();
			if (majorList.equals(null)){
				majorList = new ArrayList<Major>();
			}

			if (first == null) {
				name = user.getName();
			}

			if (year.getID() == null) {
				year = user.getGraduatingClass();
			}
			
			if (change == "add"){
				majorList.add(theMajor);
			}
			else if (change == "drop"){
				majorList.remove(theMajor);
			}
			user.delete();

			// create new user
			User newUser = new User();
			newUser.setName(name);
			newUser.setID(sessionID);
			newUser.setGraduatingClass(year);
			newUser.setMajors(majorList);
			newUser.save();

			// end with redirect to account page
			doGet(request, response);
		}
	}

	/**
	 * takes in the user inputs for changing user information
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

			// get user and user values
			User user = User.find(session.getValue("ID").toString());

			// get full name
			String fullName = user.getName();
			int indexSplitter = fullName.indexOf(" ");
			String first = fullName.substring(0, indexSplitter);
			String last = fullName.substring(indexSplitter + 1,
					fullName.length());

			// get year
			String year = "";
			GraduatingClass grad = user.getGraduatingClass();
			if (grad != null) {
				year = grad.getID();
				if (year == null) {
					year = "";
				}
			}

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
					+ "<div class=\"inline\" id=\"navigation\">"
					+ "<a href=\"/SearchServlet\">Search</a>"
					+ "<a href=\"FriendsServlet\">Friends</a>"
					+ "<a href=\"MeetingsServlet\">Meetings</a>"
					+ "<a href=\"AccountServlet\">Account</a>"
					+ "<a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+ "<a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>"
					+ "</div>"
					+ "<div id=\"content\">"
					// change your name
					+ "<fieldset id=\"changeName\" class=\"inline\"><legend>Change Your Name</legend>"
					+ "<form id=\"accountFormChangeName\" method=\"post\" action=\"AccountServlet\">"
					+ "<label for=\"first\">Change First Name</label>"
					+ "<input name=\"first\" id=\"first\" type=\"text\" value=\""
					// add users first name
					+ first
					+ "\" />"
					+ "<label for=\"last\">Change Last Name</label>"
					+ "<input name=\"last\" id=\"last\" type=\"text\" value=\""
					// add users last name
					+ last
					+ "\"/>"
					+ "<input class=\"button\" value=\"Change Name\" type=\"submit\" />"
					+ "</form>"
					+ "</fieldset>"
					// managing majors
					+ "<fieldset id=\"majors\">"
					+ "<legend>Majors</legend>"
					+ "<fieldset class=\"inline\">"
					+ "<legend>Change Majors</legend>"
					+ "<form method=\"post\" action=\"/AccountServlet\">"
					+ "<select name=\"major\">"
					+ "<option value=\"Software Engineer\">Software Engineer</option>"
					+ "<option value=\"Computer Science\">Computer Science</option>"
					+ "<option value=\"Mechanical Engineer\">Mechanical Engineer</option>"
					+ "<option value=\"Electrical Engineer\">Electrical Engineer</option>"
					+ "<option value=\"Biomedical Engineer\">Biomedical Engineer</option>"
					+ "<option value=\"Chemical Engineer\">Chemical Engineer</option>"
					+ "<option value=\"Optical Engineer\">Optical Engineer</option>"
					+ "<option value=\"Civil Engineer\">Civil Engineer</option>"
					+ "<option value=\"Applied Biology\">Applied Biology</option>"
					+ "<option value=\"Math\">Math</option>"
					+ "<option value=\"Physics\">Physics</option>"
					+ "<option value=\"Economics\">Economics</option>"
					+ "<option value=\"Chemistry\">Chemistry</option>"
					+ "<option value=\"BioChemistry\">BioChemistry</option>"
					+ "<option value=\"Military Science\">Military Science</option>"
					+ "<option selected=\"selected\" value=\"\"></option>"
					+ "</select>"
					+ "<div>"
					+ "<div id=\"adjusterAdd\">"
					+ "<input class=\"inline\" name=\"change\" id=\"add\" type=\"radio\" value=\"add\"/>"
					+ "<label class=\"inline\" for=\"add\">Add</label>"
					+ "<input class=\"button\" id=\"majorSubmitButton\" type=\"submit\" value=\"Go\">"
					+ "</div>"
					+ "<div>"
					+ "<input class=\"inline\" name=\"change\" id=\"drop\" type=\"radio\" value=\"drop\"/>"
					+ "<label class=\"inline\" for=\"drop\">Drop</label>"
					+ "</div>"
					+ "</div>"
					+ "</form>"
					+ "</fieldset>"
					+ "<fieldset class=\"inline\">"
					+ "<legend>Current Majors</legend>"
					+ "<ul id=\"currentMajors\">"
					+ this.getListMajors(user)
					+ "</ul>"
					+ "</fieldset>"
					+ "</fieldset>"
					// year form
					+ "<fieldset id=\"changeYear\"><legend>Graduation Year</legend>"
					+ "<form id=\"graduationFormChange\" method=\"post\" action=\"AccountServlet\">"
					+ "<select id=\"yearSelector\" name=\"year\">"
					+ "<option "
					+ this.getProperSelector(year, "2013")
					+ "value=\"2013\">2013</option>"
					+ "<option "
					+ this.getProperSelector(year, "2014")
					+ "value=\"2014\">2014</option>"
					+ "<option "
					+ this.getProperSelector(year, "2015")
					+ "value=\"2015\">2015</option>"
					+ "<option "
					+ this.getProperSelector(year, "2016")
					+ "value=\"2016\">2016</option>"
					+ "<option "
					+ this.getProperSelector(year, "")
					+ "value=\"\"></option>"
					+ "</select><input class=\"button\" type=\"submit\" value=\"Change Grad Year\" /></form></fieldset>"
					// delete account button form
					+ "<form method=\"post\" method=\"/AccountServlet\">"
					+ "<input type=\"hidden\" name=\"delete\" value=\""
					+ year
					+ "delete\" />"
					+ "<input id=\"deleteAccountButton\" type=\"submit\" value=\"Delete Account\" /></form>"
					+ "</div>" + "</body>" + "</html>");

		} else {
			// redirect to login screen if not logged in
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);
		}

	}

	/**
	 * returns the proper selected attribute for grad year
	 * 
	 * @param actualYear
	 * @param currentValue
	 * @return
	 */
	private String getProperSelector(String actualYear, String currentValue) {
		if (actualYear.equals(currentValue)) {
			return "selected = \"selected\"";
		}
		return "";
	}

	private String getListMajors(User user) {
		ArrayList<Major> majors = user.getMajors();
		int numMajors = majors.size();
		String output = "";
		for (int i = 0; i < numMajors; i++) {
			output += "<li>" + majors.get(i).getID() + "</li>";
		}
		return output;
	}
}