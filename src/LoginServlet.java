import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet for talking to the web page to take in a user's signup information
 * 
 * @author mccormjt. Created Feb 10, 2013.
 */
public class LoginServlet extends HttpServlet {

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

		// get user parameters
		String ID = request.getParameter("username");
		
		//find the user
		User user = User.find(ID);
		
		// check validation
		if (user != null && user.exists()) {
			// add session to users browser
			HttpSession session = request.getSession(true);

			// Add user session parameters
			session.putValue("user", user);

			// finish by redirecting to main search screen
			String redirect = response.encodeRedirectURL("/SearchServlet");
			response.sendRedirect(redirect);
		} else {
			// redirect back to blank login
			doGet(request, response);
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String answer = "";

		// check session
		HttpSession session = request.getSession(false);

		// get parameters
		String ID = request.getParameter("username");

		// check for real user
		User user = new User();
		user.setID(ID);

		if (ID == null || ID == "") {
			ID = "";
			answer = "Please Enter A Username";
		}

		if (!user.exists() && ID != "") {
			answer = "Invalid Username";
		}

		// check if user is already logged in - if so redirect to main search
		// page - otherwise show main login screen
		if (session == null) {

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""
					+ "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
					+ "<html lang=\"en\">"
					+ "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>"
					+ "<meta xmlns=\"http://www.w3.org/1999/xhtml\">"
					+ "<meta name=\"created\" content=\"Tue, 05 Feb 2013 21:47:31 GMT\">"
					+ "<meta name=\"description\" content=\"\">"
					+ "<meta name=\"keywords\" content=\"\">"
					+ "<link href=\"/styleSheet.css\" type=\"text/css\" rel=\"stylesheet\" />"
					+ "<title>Social Circle</title>"
					+ "</head>"
					+ "<body>"
					+ "<div id=\"navigation\"></div>"
					+ "<div class=\"center\">"
					+ "<img id=\"socialCircleTitleLogin\" src=\"/socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/>"
					+ "</div>"
					+ "<div id=\"login\">"
					+ "<div id=\"loginSection\">"
					+ "<form class=\"loginForm\" method=\"post\" action=\"/Home\">"
					+ "<div class=\"formItem\">"
					+ "<label class=\"regularBlackText\" for=\"username\" >Rose-Hulman Username</label>"
					+ "<input value=\""
					// value for any previously attempted ID
					+ ID
					+ "\" name=\"username\" id=\"username\" type=\"text\" size=\"25\" />"
					+ "<label id=\"loginValidation\">"
					// user name validation response
					+ answer
					+ "</label>"
					+ "</div>"
					+ "<div>"
					+ "<input class=\"button\" type=\"submit\" value=\"Login\" />"
					+ "</div>"
					+ "</form>"
					+ "</div>"
					+ "<div id=\"signupSection\">"
					+ "<a href=\"/SignUpServlet\">"
					+ "<button class=\"signupButton\" type=\"button\">"
					+ "Sign Up!"
					+ "</button></a>"
					+ "</div>"
					+ "</div>"
					+ "</body>" 
					+ "</html>");

		} else {
			// redirect back to main SearchServlet
			String redirect = response.encodeRedirectURL("/SearchServlet");
			response.sendRedirect(redirect);
		}
	}

}