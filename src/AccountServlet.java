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

	// class constants for easy reference
	private final String EMPTYVOID = "EMPTYVOID";

	private final String NAMECHANGE = "NAMECHANGE";
	private final String FIRSTNAME = "FIRSTNAME";
	private final String LASTNAME = "LASTNAME";

	private final String MAJORCHANGE = "MAJORCHANGE";
	private final String MAJOR = "MAJOR";
	private final String MAJORCHANGETYPE = "MAJORCHANGETYPE";
	private final String MAJORCHANGEADD = "MAJORCHANGEADD";
	private final String MAJORCHANGEDROP = "MAJORCHANGEDROP";

	private final String JOBCHANGE = "JOBCHANGE";
	private final String JOB = "JOB";
	private final String JOBCHANGETYPE = "JOBCHANGETYPE";
	private final String JOBCHANGEADD = "JOBCHANGEADD";
	private final String JOBCHANGEDROP = "JOBCHANGEDROP";

	private final String SPORTCHANGE = "SPORTCHANGE";
	private final String SPORT = "SPORT";
	private final String SPORTCHANGETYPE = "SPORTCHANGETYPE";
	private final String SPORTCHANGEADD = "SPORTCHANGEADD";
	private final String SPORTCHANGEDROP = "SPORTCHANGEDROP";

	private final String CLUBCHANGE = "CLUBCHANGE";
	private final String CLUB = "CLUB";
	private final String CLUBCHANGETYPE = "CLUBCHANGETYPE";
	private final String CLUBCHANGEADD = "CLUBCHANGEADD";
	private final String CLUBCHANGEDROP = "CLUBCHANGEDROP";

	private final String YEARCHANGE = "YEARCHANGE";
	private final String YEAR = "YEAR";
	private final String RESIDENCECHANGE = "RESIDENCECHANGE";
	private final String ACCOUNTDELETE = "ACCOUNTDELETE";

	/**
	 * takes in the user inputs for changing user information
	 * 
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 */
	@SuppressWarnings("unused")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// check session
		HttpSession session = request.getSession(false);

		// get user
		User user = (User) session.getValue("user");

		// check if session is null
		if (session == null) {

			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);

			// otherwise get any old parameters that are needed
			// check each possible type request and make any necessary changes
		} else {

			if (this.checkParameter(request, this.ACCOUNTDELETE)) {
				// delete the user
				user.delete();

				// terminate session
				session.invalidate();

				// redirect to login screen
				String url = response.encodeRedirectURL("/Home");
				response.sendRedirect(url);
			}

			// check users name change
			else if (this.checkParameter(request, this.NAMECHANGE)) {
				String firstName = request.getParameter(this.FIRSTNAME);
				String lastName = request.getParameter(this.LASTNAME);
				String fullName = firstName + " " + lastName;
				user.setName(fullName);

				// check users graduation year change
			} else if (this.checkParameter(request, this.YEARCHANGE)) {
				String year = request.getParameter(this.YEAR);
				String currentYear = "NONE";

				GraduatingClass currentGradYear = user.getGraduatingClass();
				if (currentGradYear != null) {
					currentYear = currentGradYear.getID();
				}
				if (!year.equals(currentYear)) {
					GraduatingClass graduatingClass = GraduatingClass
							.find(year);
					if (graduatingClass == null) {
						graduatingClass = new GraduatingClass(year);
					}
					graduatingClass.addStudent(user);
					graduatingClass.save();
				}

				// check changes in majors
			} else if (this.checkParameter(request, this.MAJORCHANGE)) {
				String majorString = request.getParameter(this.MAJOR);
				Major major = Major.find(majorString);
				if (major == null) {
					major = new Major(majorString);
				}
				ArrayList<Major> majorList = user.getMajors();
				if (majorList == null) {
					majorList = new ArrayList<Major>();
				}
				if (this.MAJORCHANGETYPE.equals(this.MAJORCHANGEADD)) {
					majorList.add(major);
				} else {
					majorList.remove(major);
				}
				user.setMajors(majorList);

				// check changes in clubs
			} else if (this.checkParameter(request, this.CLUBCHANGE)) {
				String clubString = request.getParameter(this.CLUB);
				Club club = Club.find(clubString);
				if (club == null) {
					club = new Club(clubString);
				}

				// check changes in sports
			} else if (this.checkParameter(request, this.SPORTCHANGE)) {
				String sportString = request.getParameter(this.SPORT);
				Sport sport = Sport.find(sportString);
				if (sport == null) {
					sport = new Sport(sportString);
				}

				// check changes in users job
			} else if (this.checkParameter(request, this.JOBCHANGE)) {

				// check changes in users residence
			} else if (this.checkParameter(request, this.RESIDENCECHANGE)) {
				//
			}

			// save the user and its changes
			user.save();

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
					+ "<div class=\"inline\"><fieldset id=\"changeName\"><legend>Change Your Name</legend>"
					+ "<form id=\"accountFormChangeName\" method=\"post\" action=\"AccountServlet\">"
					+ "<input type=\"hidden\" value=\""
					+ this.NAMECHANGE
					+ "\" name=\""
					+ this.NAMECHANGE
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

					// year form
					+ "<fieldset id=\"changeYear\"><legend>Graduation Year</legend>"
					+ "<form id=\"graduationFormChange\" method=\"post\" action=\"AccountServlet\">"
					+ "<input type=\"hidden\" value=\""
					+ this.YEARCHANGE
					+ "\" name=\""
					+ this.YEARCHANGE
					+ "\" />"
					+ "<select id=\"yearSelector\" name=\""
					+ this.YEAR
					+ "\">"
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
					+ "</select><input class=\"button\" type=\"submit\" value=\"Change Grad Year\" /></form></fieldset></div>"

					// managing majors
					+ "<fieldset id=\"majors\">"
					+ "<legend>Majors</legend>"
					+ "<fieldset class=\"inline\">"
					+ "<legend>Change Majors</legend>"
					+ "<form method=\"post\" action=\"/AccountServlet\">"
					+ "<input type=\"hidden\" value=\""
					+ this.MAJORCHANGE
					+ "\" name=\""
					+ this.MAJORCHANGE
					+ "\" />"
					+ "<select name=\""
					+ this.MAJOR
					+ "\">"
					+ "<option value=\""
					+ Major.SOFTWAREENGINEER
					+ "\">"
					+ Major.SOFTWAREENGINEER
					+ "</option>"
					+ "<option value=\""
					+ Major.COMPUTERSCIENCE
					+ "\">"
					+ Major.COMPUTERSCIENCE
					+ "</option>"
					+ "<option value=\""
					+ Major.MECHANICALENGINEER
					+ "\">"
					+ Major.MECHANICALENGINEER
					+ "</option>"
					+ "<option value=\""
					+ Major.ELECTRICALENGINEER
					+ "\">"
					+ Major.ELECTRICALENGINEER
					+ "</option>"
					+ "<option value=\""
					+ Major.BIOMEDICALENGINEER
					+ "\">"
					+ Major.BIOMEDICALENGINEER
					+ "</option>"
					+ "<option value=\""
					+ Major.CHEMICALENGINEER
					+ "\">"
					+ Major.CHEMICALENGINEER
					+ "</option>"
					+ "<option value=\""
					+ Major.OPTICALENGINEER
					+ "\">"
					+ Major.OPTICALENGINEER
					+ "</option>"
					+ "<option value=\""
					+ Major.CIVILENGINEER
					+ "\">"
					+ Major.CIVILENGINEER
					+ "</option>"
					+ "<option value=\""
					+ Major.APPLIEDBIOLOGY
					+ "\">"
					+ Major.APPLIEDBIOLOGY
					+ "</option>"
					+ "<option value=\""
					+ Major.MATH
					+ "\">"
					+ Major.MATH
					+ "</option>"
					+ "<option value=\""
					+ Major.PHYSICS
					+ "\">"
					+ Major.PHYSICS
					+ "</option>"
					+ "<option value=\""
					+ Major.ECONOMICS
					+ "\">"
					+ Major.ECONOMICS
					+ "</option>"
					+ "<option value=\""
					+ Major.CHEMISTRY
					+ "\">"
					+ Major.CHEMISTRY
					+ "</option>"
					+ "<option value=\""
					+ Major.BIOCHEMISTRY
					+ "\">"
					+ Major.BIOCHEMISTRY
					+ "</option>"
					+ "<option value=\""
					+ Major.COMPUTERENGINEER
					+ "\">"
					+ Major.COMPUTERENGINEER
					+ "</option>"
					+ "<option value=\""
					+ Major.MILITARYSCIENCE
					+ "\">"
					+ Major.MILITARYSCIENCE
					+ "</option>"
					+ "<option selected=\"selected\" value=\""
					+ Major.VOID
					+ ""
					+ Major.VOID
					+ "\"></option>"
					+ "</select>"
					+ "<div>"
					+ "<div class=\"adjusterAdd\">"
					+ "<input class=\"inline\" name=\""
					+ this.MAJORCHANGETYPE
					+ "\" id=\"add\" type=\"radio\" checked=\"checked\" value=\""
					+ this.MAJORCHANGEADD
					+ "\"/>"
					+ "<label class=\"inline\" for=\"add\">Add</label>"
					+ "<input class=\"button\" id=\"majorSubmitButton\" type=\"submit\" value=\"Go\">"
					+ "</div>"
					+ "<div>"
					+ "<input class=\"inline\" name=\""
					+ this.MAJORCHANGETYPE
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

					// managing clubs
					+ "<fieldset id=\"clubs\">"
					+ "<legend>Clubs</legend>"
					+ "<fieldset class=\"inline\">"
					+ "<legend>Change Clubs</legend>"
					+ "<form method=\"post\" action=\"/AccountServlet\">"
					+ "<input type=\"hidden\" value=\""
					+ this.CLUBCHANGE
					+ "\" name=\""
					+ this.CLUBCHANGE
					+ "\" />"
					+ "<select name=\""
					+ this.CLUB
					+ "\">"
					+ "<option value=\""
					+ Club.ALPHAOMICRONPI
					+ "\">"
					+ Club.ALPHAOMICRONPI
					+ "</option>"
					+ "<option value=\""
					+ Club.ALPHAPHIOMEGA
					+ "\">"
					+ Club.ALPHAPHIOMEGA
					+ "</option>"
					+ "<option value=\""
					+ Club.ATO
					+ "\">"
					+ Club.ATO
					+ "</option>"
					+ "<option value=\""
					+ Club.BAND
					+ "\">"
					+ Club.BAND
					+ "</option>"
					+ "<option value=\""
					+ Club.CHIOMEGA
					+ "\">"
					+ Club.CHIOMEGA
					+ "</option>"
					+ "<option value=\""
					+ Club.DELTASIG
					+ "\">"
					+ Club.DELTASIG
					+ "</option>"
					+ "<option value=\""
					+ Club.DRAMA
					+ "\">"
					+ Club.DRAMA
					+ "</option>"
					+ "<option value=\""
					+ Club.ECOCAR
					+ "\">"
					+ Club.ECOCAR
					+ "</option>"
					+ "<option value=\""
					+ Club.FIJI
					+ "\">"
					+ Club.FIJI
					+ "</option>"
					+ "<option value=\""
					+ Club.INTERVARSITYCHRISTIANS
					+ "\">"
					+ Club.INTERVARSITYCHRISTIANS
					+ "</option>"
					+ "<option value=\""
					+ Club.LAMBDACHIALPHA
					+ "\">"
					+ Club.LAMBDACHIALPHA
					+ "</option>"
					+ "<option value=\""
					+ Club.PIKE
					+ "\">"
					+ Club.PIKE
					+ "</option>"
					+ "<option value=\""
					+ Club.RISE
					+ "\">"
					+ Club.RISE
					+ "</option>"
					+ "<option value=\""
					+ Club.ROBOTICS
					+ "\">"
					+ Club.ROBOTICS
					+ "</option>"
					+ "<option value=\""
					+ Club.SIGMANU
					+ "\">"
					+ Club.SIGMANU
					+ "</option>"
					+ "<option value=\""
					+ Club.THETAXI
					+ "\">"
					+ Club.THETAXI
					+ "</option>"
					+ "<option value=\""
					+ Club.TRIANGLE
					+ "\">"
					+ Club.TRIANGLE
					+ "</option>"
					+ "<option selected=\"selected\" value=\""
					+ Club.VOID
					+ ""
					+ Club.VOID
					+ "\"></option>"
					+ "</select>"
					+ "<div>"
					+ "<div class=\"adjusterAdd\">"
					+ "<input class=\"inline\" name=\""
					+ this.CLUBCHANGETYPE
					+ "\" id=\"add\" type=\"radio\" checked=\"checked\" value=\""
					+ this.CLUBCHANGEADD
					+ "\"/>"
					+ "<label class=\"inline\" for=\"add\">Add</label>"
					+ "<input class=\"button\" id=\"clubSubmitButton\" type=\"submit\" value=\"Go\">"
					+ "</div>"
					+ "<div>"
					+ "<input class=\"inline\" name=\""
					+ this.CLUBCHANGETYPE
					+ "\" id=\"drop\" type=\"radio\" value=\""
					+ this.CLUBCHANGEDROP
					+ "\"/>"
					+ "<label class=\"inline\" for=\"drop\">Drop</label>"
					+ "</div>"
					+ "</div>"
					+ "</form>"
					+ "</fieldset>"
					+ "<fieldset class=\"inline\">"
					+ "<legend>Current Clubs</legend>"
					+ "<ul id=\"currentClubs\">"
					+ this.getListClubs(user)
					+ "</ul>"
					+ "</fieldset>"
					+ "</fieldset>"

					// delete account button form
					+ "<form method=\"post\" method=\"/AccountServlet\">"
					+ "<input type=\"hidden\" name=\""
					+ this.ACCOUNTDELETE
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

	// method that returns an html list of all of a user's majors
	private String getListMajors(User user) {
		ArrayList<Major> majors = user.getMajors();
		int numMajors = majors.size();
		String output = "";
		for (int i = 0; i < numMajors; i++) {
			output += "<li>" + majors.get(i).getID() + "</li>";
		}
		return output;
	}

	// method that returns an html list of all of a user's clubs
	private String getListClubs(User user) {
		ArrayList<Club> clubs = user.getClubs();
		int numClubs = clubs.size();
		String output = "";
		for (int i = 0; i < numClubs; i++) {
			output += "<li>" + clubs.get(i).getID() + "</li>";
		}
		return output;
	}

	// method that returns an html list of all of a user's sports
	private String getListSports(User user) {
		ArrayList<Sport> sports = user.getSports();
		int numSports = sports.size();
		String output = "";
		for (int i = 0; i < numSports; i++) {
			output += "<li>" + sports.get(i).getID() + "</li>";
		}
		return output;
	}
}