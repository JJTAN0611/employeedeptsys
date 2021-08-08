package utilities;
import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName="MainFilter",urlPatterns="/MainServlet",dispatcherTypes= {DispatcherType.REQUEST})
public class ServletForwardValidate implements Filter{
	/*
	 * This class is to verify those compulsory servlets forwarding. The validate
	 * parameter is target, action. If not have related parameter, it will be filtered
	 */

	private boolean action(ServletRequest request, ServletResponse response) {
		//retrieve
		String action = request.getParameter("action");
		if(action==null)
			return false;
		
		//check
		switch(action) {
		case "getAutoId":
		case "getByIdAjax":
		case "getByNameAjax":
		case "ajax":
		case "view":
		case "add":
		case "update":
		case "delete":
		case "download":
		case "report":
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
		LoggingGeneral.setEntryPoints((HttpServletRequest) request);
		// TODO Auto-generated method stub
		if(action(request, response) && target(request, response)) {
			chain.doFilter(request, response);
			LoggingGeneral.setContentPoints((HttpServletRequest) request, "No obvious url error. Proceed.");
		}else {
			request.setAttribute("filtered", "is eliminated from main filter");
			RequestDispatcher dispatcher= request.getRequestDispatcher("error.jsp");
			dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
			LoggingGeneral.setContentPoints((HttpServletRequest) request, "Obvious url rrror found");
		}

	}



}
