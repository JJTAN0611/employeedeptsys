package controller.main;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionbean.DepartmentSessionBeanLocal;
import utilities.LoggingGeneral;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private DepartmentSessionBeanLocal empbean;

	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		
		response.setContentType("text/html;charset=UTF-8");
		
		RequestDispatcher dispatcher = null;

		// check process done from filter
		String target = (String) request.getAttribute("target");
		String action = (String) request.getAttribute("action");

		// department
		if (target.compareTo("department") == 0)
			switch (action) {
			case "getAutoId":
			case "getByIdAjax":
			case "getByNameAjax":
				dispatcher = request.getRequestDispatcher("DepartmentQueryServlet");
				break;
			case "report":
			case "download":
				dispatcher = request.getRequestDispatcher("DepartmentReportController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("DepartmentPaginationServlet");
				break;
			// main cud operation function
			case "add":
			case "update": // check edit's parameter
			case "delete": // check remove's parameter
				dispatcher = request.getRequestDispatcher("DepartmentOperationController");
				break;
			}
		// employee
		else if (target.compareTo("employee") == 0) {
			switch (action) {
			case "getAutoId":
			case "getByIdAjax":
			case "getByNameAjax":
				dispatcher = request.getRequestDispatcher("EmployeeQueryServlet");
				break;
			case "report":
			case "download":
				dispatcher = request.getRequestDispatcher("EmployeeReportController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("EmployeePaginationServlet");
				break;
			// main cud operation function
			case "add":
			case "update": // check edit's parameter
			case "delete": // check remove's parameter
				dispatcher = request.getRequestDispatcher("EmployeeOperationController");
				break;
			}
			// departmentemployee
		} else if (target.compareTo("departmentemployee") == 0) {
			switch (action) {
			case "getAutoId":
			case "getByIdAjax":
			case "getByNameAjax":
				dispatcher = request.getRequestDispatcher("DepartmentEmployeeQueryServlet");
				break;
			case "report":
			case "download":
				dispatcher = request.getRequestDispatcher("DepartmentEmployeeReportController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("DepartmentEmployeePaginationServlet");
				break;
			// main cud operation function
			case "add":
			case "update": // check edit's parameter
			case "delete": // check remove's parameter
				dispatcher = request.getRequestDispatcher("DepartmentEmployeeOperationController");
				break;
			}
		} else if (target.compareTo("log") == 0) {
			switch (action) {
			case "view":
			case "download": //this no need report
			case "delete":
				dispatcher = request.getRequestDispatcher("LogOperationController");
				break;
			}
		}

		if (dispatcher == null) {
			request.setAttribute("filtered", "is eliminated from servlet");
			dispatcher = request.getRequestDispatcher("error.jsp");
			LoggingGeneral.setContentPoints(request, "Denied, Not valid");
		}else
			LoggingGeneral.setContentPoints(request, "Success: target: "+target+" | action: "+action+". Proceed.");

		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}