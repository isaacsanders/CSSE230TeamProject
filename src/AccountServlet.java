import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet for managing a user's account
 * 
 * @author mccormjt. Created Feb 10, 2013.
 */
public class AccountServlet extends HttpServlet {

	private static final String EMPTY = "";
	private static final String CHANGENAME = "changeName";
	private static final String ADDFRIEND = "addFriend";

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
		
		//get necessary parameters
		String type = request.getParameter(CHANGENAME);
		if (type == CHANGENAME){
			//get exact paramters
			String first = request.getParameter("firstName");
			String last = request.getParameter("lastName");
			//get and update user
			 User user = User.find(session.getValue("ID").toString());
			 user.setName(first + " " + last);
			 user.save();
		}
		
		//end with redirect to account page
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
		
		//get user
		User user = User.find(session.getValue("ID").toString());
		String fullName = user.getName();
		int indexSplitter = fullName.indexOf(" ");
		String first = fullName.substring(0, indexSplitter);
		String last =  fullName.substring(indexSplitter + 1, fullName.length());
		

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
			out.println("<body>"
					+ "<div id=\"navigation\">"
					+ "<a href=\"/SearchServlet\">Search</a>"
					+ "<a href=\"FriendsServlet\">Friends</a>"
					+ "<a href=\"MeetingsServlet\">Meetings</a>"
					+ "<a href=\"AccountServlet\">Account</a>"
					+ "<a href=\"LogoutServlet\"><button class=\"logoutButton\" type=\"submit\">Log Out</button></a>"
					+ "<a href=\"/Home\"><img class=\"socialCircleTitle\" src=\"socialCircle.png\" width=\"\" height=\"\" alt=\"Social Circle\"/></a>"
					+ "</div>" 
					+ "<div id=\"content\">"
					+ "<fieldset><legend>Change Your Name</legend>"
					+ "<form id=\"accountFormChangeName\" method=\"post\" action=\"AccountServlet\">"
					+ "<label for=\"changeFirstName\">Change First Name</label>"
					+ "<input name=\"changeFirstName\" id=\"changeFirstName\" type=\"text\" value=\""
					//add users first name
					+ first
					+ "\" />"
					+ "<label for=\"changeLastName\">Change Last Name</label>"
					+ "<input name=\"changeLastName\" id=\"changeLastName\" type=\"text\" value=\" " 
					//add users last name
					+ last
					+ "\"/>"
					+ "</legend>"
					+ "<input type=\"hidden\" name=\"type\" value=\"changeName\" />"
					+ "<input class=\"button\" value=\"Change Name\" type=\"submit\" />"
					+ "</form>"
					+ "</div>" 
					+ "</body>"
					+ "</html>");

		} else {
			// redirect to login screen if not logged in
			String redirect = response.encodeRedirectURL("/Home");
			response.sendRedirect(redirect);
		}

	}
}