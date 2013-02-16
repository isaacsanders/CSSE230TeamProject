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

	private final String EMPTY = "";
	private final String NAMECHANGE = "NAMECHANGE";
	private final String FIRSTNAME = "FIRSTNAME";
	private final String LASTNAME = "LASTNAME";
	private final String MAJORCHANGE = "MAJORCHANGE";
	private final String MAJOR = "MAJOR";
	private final String MAJORCHANGEACTUAL = "MAJORCHANGEACTUAL";
	private final String MAJORCHANGEADD = "MAJORCHANGEADD";
	private final String MAJORCHANGEDROP = "MAJORCHANGEDROP";
	private final String YEARCHANGE = "YEARCHANGE";
	private final String SPORTCHANGE = "SPORTCHANGE";
	private final String CLUBCHANGE = "CLUBCHANGE";
	private final String ACCOUNTDELETE = "ACCOUNTDELETE";
	private final String TYPE = "TYPE";

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

		// get user
		User user = (User) session.getValue("user");

		// check the type of requested change
		String type = request.getParameter(this.TYPE);

		// check first for deletion of an account
		if (type == this.ACCOUNTDELETE) {
			// delete the user
			user.delete();

			// terminate session
			session.invalidate();

			// redirect to login screen
			String url = response.encodeRedirectURL("/Home");
			response.sendRedirect(url);

		}

		// get any old parameters that are needed
		// check each possible type request and make any necessary changes
		else if (type == this.NAMECHANGE) {
			String firstName = request.getParameter(this.FIRSTNAME);
			String lastName = request.getParameter(this.LASTNAME);
			String fullName = firstName + " " + lastName;
			user.setName(fullName);

		} else if (type == this.YEARCHANGE) {
			String year = request.getParameter(this.YEARCHANGE);
			GraduatingClass graduatingClass = GraduatingClass.find(year);
			if (graduatingClass == null) {
				graduatingClass = new GraduatingClass(year);
			}
			user.setGraduatingClass(graduatingClass);

		} else if (type == this.MAJORCHANGE) {
			String majorString = request.getParameter(this.MAJOR);
			Major major = Major.find(majorString);
			if (major == null) {
				major = new Major(majorString);
			}

		} else if (type == this.SPORTCHANGE) {

		} else if (type == this.CLUBCHANGE) {

		}

		// save the user and its changes
		user.save();

		// end with redirect to account page
		doGet(request, response);

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
			User user = (User) session.getValue("user");

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
					+ "<input type=\"hidden\" value=\""
					+ this.NAMECHANGE
					+ "\" name=\""
					+ this.TYPE
					+ "\" />"

					// add users first name
					+ "<label for=\""
					+ this.FIRSTNAME
					+ "\">Change First Name</label>"
					+ "<input name=\""
					+ this.FIRSTNAME
					+ "\" id=\"first\" type=\"text\" value=\""
					+ first

					// add users last name
					+ "\" />"
					+ "<label for=\""
					+ this.LASTNAME
					+ "\">Change Last Name</label>"
					+ "<input name=\""
					+ this.LASTNAME
					+ "\" id=\"last\" type=\"text\" value=\""
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
					+ "<input type=\"hidden\" value=\""
					+ this.MAJORCHANGE
					+ "\" name=\""
					+ this.TYPE
					+ "\" />"
					+ "<select name=\""
					+ this.MAJOR
					+ "\">"
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
					+ "<input class=\"inline\" name=\""
					+ this.MAJORCHANGEACTUAL
					+ "\" id=\"add\" type=\"radio\" value=\""
					+ this.MAJORCHANGEADD
					+ "\"/>"
					+ "<label class=\"inline\" for=\"add\">Add</label>"
					+ "<input class=\"button\" id=\"majorSubmitButton\" type=\"submit\" value=\"Go\">"
					+ "</div>"
					+ "<div>"
					+ "<input class=\"inline\" name=\""
					+ this.MAJORCHANGEACTUAL
					+ "\" id=\"drop\" type=\"radio\" value=\""
					+ this.MAJORCHANGEDROP
					+ "\"/>"
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
					+ "<input type=\"hidden\" value=\""
					+ this.YEARCHANGE
					+ "\" name=\""
					+ this.TYPE
					+ "\" />"
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
					+ "<input type=\"hidden\" name=\""
					+ this.TYPE
					+ "\" value=\""
					+ this.ACCOUNTDELETE
					+ "\" />"
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