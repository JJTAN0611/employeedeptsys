package utilities;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName="MainFilter",urlPatterns="/MainServlet",dispatcherTypes= {DispatcherType.REQUEST})
public class ServletForwardValidate implements Filter{
	/*
	 * This class is to verify those compulsory servlets forwarding The validate
	 * parameter is target, action
	 */

	private boolean action(ServletRequest request, ServletResponse response) {
		//retrieve
		String action = request.getParameter("action");
		if(action==null)
			return false;
		
		//check
		switch(action) {
		case "getAutoId":
		case "getDepartment":
		case "getEmployee":
		case "ajax":
		case "view":
		case "add":
		case "update":
		case "delete":
		case "download":
			request.setAttribute("action", action);
			return true;
		default:
			return false;
		}
	}

	private boolean target(ServletRequest request, ServletResponse response) {
		
		//retrieve
		String target = request.getParameter("target");
		if(target==null)
			return false;
		
		switch(target) {
		case "department":
		case "departmentemployee":
		case "employee":
		case "log":
			request.setAttribute("target", target);
			return true;
		default:
			return false;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// TODO Auto-generated method stub
		if(action(request, response) && target(request, response))
			chain.doFilter(request, response);
		else {
			request.setAttribute("filtered", "is eliminated from filter");
			RequestDispatcher dispatcher= request.getRequestDispatcher("error.jsp");
			dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
		}

	}



}
