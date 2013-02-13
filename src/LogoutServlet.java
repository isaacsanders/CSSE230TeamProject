import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * terminates a users session on the site
 *
 * @author mccormjt.
 *         Created Feb 13, 2013.
 */
public class LogoutServlet  extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// check session
		HttpSession session = request.getSession(false);
		
		//invalidate the session
		session.invalidate();
		
		//redirect user to login screen
		String redirect = response.encodeRedirectURL("/");
		response.sendRedirect(redirect);
	}
}
