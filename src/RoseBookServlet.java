import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RoseBookServlet extends HttpServlet {

	private String baseURL = "resources/";

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<title>CSSE 230 RoseBook</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		String query = request.getParameter("queryString");

		process(query, out);

		out.println("<form action=\"RoseBookServlet\" method=\"post\">");
		out.println("Please enter your query:");
		out.println("<p><input type=\"text\" size=\"40\" name=\"queryString\">");
		out.println("<p><input type=\"submit\" value=\"Submit\">");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
	}

	public void process(String name, PrintWriter out) {
		out.println("Hello: " + name);
	}

}
