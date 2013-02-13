import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet for talking to the web page to take in a user's signup information
 * 
 * @author mccormjt. Created Feb 10, 2013.
 */
public class LoginServlet extends HttpServlet {

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
		String ID = request.getParameter("ID");
		user.setID(ID);

			
		//add session to users browser
		HttpSession session = request.getSession(true);

		// finish by redirecting to main search screen
		String redirect = response.encodeRedirectURL("/search.html");
		response.sendRedirect(redirect);
	}

}