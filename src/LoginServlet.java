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

		// Create new temporary user for a check
		User user = new User();

		// set user parameters
		String ID = request.getParameter("username");
		user.setID(ID);

		// check validation
		if (ID != this.EMPTY) {
			// add session to users browser
			HttpSession session = request.getSession(true);

			// finish by redirecting to main search screen
			String redirect = response.encodeRedirectURL("/SearchServlet");
			response.sendRedirect(redirect);
		} else {
			// redirect back to blank login
			String redirect = response.encodeRedirectURL("/");
			response.sendRedirect(redirect);
		}

	}

}