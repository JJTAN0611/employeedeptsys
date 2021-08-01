import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.ValidateManageLogic;

public class ServletForwardValidate {
	/*
	 * This class is to verify those compulsory servlets forwarding The validate
	 * parameter is target, action
	 */

	
	public static String action(HttpServletRequest request, HttpServletResponse response) {
		//retrieve
		String action = request.getParameter("action");
		request.setAttribute("action", action);
		
		//check
		if(action==null)
			return null;
		else if (action.compareTo("getAutoId") == 0) {
			return "getAutoId";
		} else if (action.compareTo("getDepartment") == 0) {
			return "getDepartment";
		} else if (action.compareTo("getEmployee") == 0) {
			return "getEmployee";
		} else if (action.compareTo("view") == 0) {
			return "view";
		} else if (action.compareTo("add") == 0)
			return "add";
		else if (action.compareTo("update") == 0)
			return "update";
		else if (action.compareTo("delete") == 0)
			return "delete";
		else if (action.compareTo("download") == 0)
			return "download";
		else
			return null; // abnormal value
	}

	public static String target(HttpServletRequest request, HttpServletResponse response) {
		
		//retrieve
		String target = request.getParameter("target");
		request.setAttribute("target", target);
		
		//check
		if(target==null)
			return null;
		else if (target.compareTo("department") == 0)
			return "department";
		else if (target.compareTo("departmentemployee") == 0)
			return "departmentemployee";
		else if (target.compareTo("employee") == 0)
			return "employee";
		else if (target.compareTo("log") == 0)
			return "log";
		else
			return null; // abnormal value
	}



}
