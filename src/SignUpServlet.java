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
public class SignUpServlet extends HttpServlet {

	private final String FULL = "FULL";
	private final String EMPTY = "";

	/**
	 * takes in the user inputs from a "sign up"
	 * 
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");

		// check session
		HttpSession session = request.getSession(false);

		// get parameters
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String ID = request.getParameter("ID");

		// create new user
		User user = new User();
		user.setName(firstName + " " + lastName);
		user.setID(ID);


		if (!(firstName == this.EMPTY || lastName == this.EMPTY || ID == this.EMPTY || user.exists()) && session == null) {

			user.save();

			// redirect to login screen once account is created
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);

		} else if (session != null) {
			// redirect to search screen if already logged in
			String redirect = response.encodeRedirectURL("/SearchServlet");
			response.sendRedirect(redirect);
		}

		else {
			this.doGet(request, response);
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");

		// check session
		HttpSession session = request.getSession(false);

		// get parameters
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String ID = request.getParameter("ID");
		String answer = "*Please Enter Your: ";
		String answerOriginal = answer;

		// check parameters
		if (firstName == this.EMPTY || firstName == null) {
			firstName = "";
			answer += " - - - First Name ";
		}
		if (lastName == this.EMPTY || lastName == null) {
			lastName = "";
			answer += " - - - Last Name ";
		}
		if (ID == this.EMPTY || ID == null) {
			ID = "";
			answer += " - - - Rose-Hulman Username ";
		}

		// check if user already exists
		if (answer.equals(answerOriginal)) {
			answer = "User Info Already Registered - Try Logging In";
		}

		// check if user is already logged in, if so go to main search page, if
		// not allow sign up
		if (session == null) {

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
					+ "<div id=\"navigation\"></div>"
					+ "<div class=\"center\"><a href=\"/Home\"><img id=\"socialCircleTitleLogin\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></div></a>"
					+ "<div id=\"content\">" + "<div id=signupSection>"
					+ "<div id=\"signupMessage\">"
					// add in checked parameters message
					+ answer
					+ "</div>"
					+ "<form class=\"signupForm\" method=\"post\" action=\"SignUpServlet\">"
					+ "<div class=\"formItem\">"
					+ "<label for=\"firstName\" >First Name</label>"
					+ "<input value= \""
					// add any previously entered first name
					+ firstName
					+ "\" name=\"firstName\" id=\"firstName\" type=\"text\" size=\"25\" />"
					+ "</div>"
					+ "<div class=\"formItem\">"
					+ "<label for=\"lastName\" >Last Name</label>"
					+ "<input value= \""
					// add any previously entered last name
					+ lastName
					+ "\" name=\"lastName\" id=\"lastName\" type=\"text\" size=\"25\" />"
					+ "</div>"
					+ "<div class=\"formItem\">"
					+ "<label for=\"ID\">Rose-Hulman Username</label>"
					+ "<input value= \""
					// add any previously entered ID
					+ ID
					+ "\" type=\"text\" name=\"ID\" id=\"ID\" size=\"25\" />"
					+ "</div>"
					+ "<div>"
					+ "<input class=\"button\" type=\"submit\" value=\"Sign Up!\" />"
					+ "</div>"
					+ "</form>"
					+ "</div>"
					+ "</div>"
					+ "</body>"
					+ "</html>");

		} else {
			// redirect to search screen if already logged in
			String redirect = response.encodeRedirectURL("/SearchServlet");
			response.sendRedirect(redirect);
		}
	}
}
