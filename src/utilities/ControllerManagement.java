package utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
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


	// this method is used to notify a user that a record has been updated and to
	// redirect to another page
	public static void navigateJS(PrintWriter out, HttpServletRequest request) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert(\"Your [" + request.getAttribute("action") + "] action to [" + request.getAttribute("target")
				+ "] is successful.\")");
		out.println(
				"window.location.assign(\"MainServlet?target=" + request.getAttribute("target") + "&action=view\")");
		out.println("</SCRIPT>");
	}

	public static void test(HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("<SCRIPT type=\"text/javascript\">");
			out.println("alert(\"Your  is successful.\")");
			out.println("</SCRIPT>");
			response.sendRedirect("https://www.google.com/search?q=");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}