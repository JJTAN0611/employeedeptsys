package controller;


import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;

public class ValidateManageLogic {


// this method is used to notify a user that a record has been updated and to
// redirect to another page
	public static void navigateJS(PrintWriter out) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert(\"Record has been updated and url will be redirected\")");
		out.println("window.location.assign(\"MainServlet?table=department&action=view\")");
		out.println("</SCRIPT>");
	}
	
	public static void check(PrintWriter out, String check) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert(\""+check+"\");");
		out.println("</SCRIPT>");
	}
}