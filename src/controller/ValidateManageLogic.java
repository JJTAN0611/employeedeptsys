package controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateManageLogic {

	public static boolean convert(HttpServletRequest request, String parameter, int type, int length) {
		/*
		 * type 0="string", length is significant
		 * type 1="date", length is not significant
		 */
		String temp = request.getParameter(parameter);
		if (temp == null)
			return false;
		else if(type==0) {
			if(temp.length()>length||temp.length()<=0)
				return false;
			request.setAttribute(parameter, temp);
		}else {
			Date date = null;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(temp);
				java.sql.Date d = new java.sql.Date(date.getTime());
				request.setAttribute(parameter, temp);
			} catch (Exception ex) {
				return false;
			}

		}
		return true;
	}

	public static boolean departmentContent(HttpServletRequest request, HttpServletResponse response) {
		String dept_name = request.getParameter("dept_name");
		System.out.println(dept_name);
		if (dept_name == null || dept_name.length() > 40 || dept_name.length() <= 0)
			return false;

		request.setAttribute("dept_name", dept_name);
		return true;
	}

	public static boolean departmentID(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");

		if (id == null || id.length() > 4 || id.length() <= 0)
			return false;

		request.setAttribute("id", id);
		return true;
	}

	public static boolean employeeContent(HttpServletRequest request, HttpServletResponse response) {
		String dept_name = request.getParameter("dept_name");
		System.out.println(dept_name);

		if (convert(request, "first_name",0,14) && convert(request, "last_name",0,16) && convert(request, "gender",0,1)
				&& convert(request, "hire_date",1,0) && convert(request, "birth_date",1,0))
			return true;
		return false;
	}

	public static boolean employeeID(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if (id == null)
			return false;
		request.setAttribute("id", id);
		return true;
	}
	
	public static boolean departmentemployeeContent(HttpServletRequest request, HttpServletResponse response) {
		String dept_name = request.getParameter("dept_name");
		System.out.println(dept_name);

		if (convert(request,"from_date",1,0) && convert(request, "to_date",1,0))
			return true;
		return false;
	}

	public static boolean departmentemployeeID(HttpServletRequest request, HttpServletResponse response) {
		String emp_id = request.getParameter("emp_id");
		String dept_id = request.getParameter("dept_id");
		if (emp_id == null||dept_id==null)
			return false;
		request.setAttribute("emp_id", emp_id);
		request.setAttribute("dept_id", dept_id);
		return true;
	}

// this method is used to notify a user that a record has been updated and to
// redirect to another page
	public static void navigateJS(PrintWriter out, String location) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert(\"Record has been updated and url will be redirected\")");
		out.println("window.location.assign(\"MainServlet?table=" + location + "&action=view\")");
		out.println("</SCRIPT>");
	}

	public static void printErrorNotice(PrintWriter out, String error, HttpServletRequest request) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert('Error: " + error.replace("\n", "").replace("\r", "") + "');");
		out.println("alert(\"Redirected to previous page.\")");

		String page="table=" + request.getAttribute("table");

		if(request.getAttribute("id")!=null)
			page=page+"&action="+request.getAttribute("action");
		else
			page=page+"&action=view";
		
		if(request.getAttribute("action").toString().compareTo("update")==0||request.getAttribute("action").toString().compareTo("delete")==0)
				page=page+"&id="+(String) request.getAttribute("id");
		out.println("window.location.assign(\"MainServlet?"+page+"\")");
		out.println("</SCRIPT>");
	}

}