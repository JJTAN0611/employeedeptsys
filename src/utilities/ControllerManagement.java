package utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.Employee;

public class ControllerManagement {

	public static <T> T unwrapCause(Class<T> clazz, Throwable e) {
		while (!clazz.isInstance(e) && e.getCause() != null && e != e.getCause()) {
			e = e.getCause();
		}
		return clazz.isInstance(e) ? clazz.cast(e) : null;
	}

	// this method is used to notify a user that a record has been updated and
	// redirect to success page. The success page will hold 5 seconds(can skip) and
	// redirect to previous view page.
	public static void navigateJS(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
		dispatcher.forward(request, response);
	}

	// This method can be used to test
	/*
	 * public static void test(HttpServletResponse response) { PrintWriter out; try
	 * { out = response.getWriter();
	 * out.println("<SCRIPT type=\"text/javascript\">");
	 * out.println("alert(\"Your  is successful.\")"); out.println("</SCRIPT>");
	 * response.sendRedirect("https://www.google.com/search?q="); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */
}