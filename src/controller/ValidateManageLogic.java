package controller;


import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;

public class ValidateManageLogic {


// this method is used to notify a user that a record has been updated and to
// redirect to another page
	public static void navigateJS(PrintWriter out,String location) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert(\"Record has been updated and url will be redirected\")");
		out.println("window.location.assign(\"MainServlet?table="+location+"&action=view\")");
		out.println("</SCRIPT>");
	}
	
	public static void printErrorNotice(PrintWriter out, String error, String location) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert('Error: "+error+"');");
		out.println("alert(\"Redirected\")");
		out.println("window.location.assign(\"MainServlet?table="+location+"&action=view\")");
		out.println("</SCRIPT>");
	}
}