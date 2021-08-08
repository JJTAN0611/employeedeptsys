package main;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.Department;
import model.entity.Employee;
import sessionbean.DepartmentSessionBeanLocal;
import sessionbean.EmployeeSessionBeanLocal;
import utilities.ControllerManagement;
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
			case "report":
			case "download":
				dispatcher = request.getRequestDispatcher("DepartmentController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("DepartmentPaginationServlet");
				break;
			// main cud function
			case "add":
			case "update": // check edit's parameter
			case "delete": // check remove's parameter
				dispatcher = request.getRequestDispatcher("DepartmentController");
				break;
			}
		// employee
		else if (target.compareTo("employee") == 0) {
			switch (action) {
			case "getAutoId":
			case "getByIdAjax":
			case "getByNameAjax":
			case "report":
			case "download":
				dispatcher = request.getRequestDispatcher("EmployeeController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("EmployeePaginationServlet");
				break;
			// main cud function
			case "add":
			case "update": // check edit's parameter
			case "delete": // check remove's parameter
				dispatcher = request.getRequestDispatcher("EmployeeController");
				break;
			}
			// departmentemployee
		} else if (target.compareTo("departmentemployee") == 0) {
			switch (action) {
			case "getAutoId":
			case "getByIdAjax":
			case "getByNameAjax":
			case "report":
			case "download":
				dispatcher = request.getRequestDispatcher("DepartmentEmployeeController");
				break;
			case "getDepartmentAjax":
				dispatcher = request.getRequestDispatcher("DepartmentController");
				break;
			case "getEmployeeAjax":
				dispatcher = request.getRequestDispatcher("EmployeeController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("DepartmentEmployeePaginationServlet");
				break;
			// main cud function
			case "add":
			case "update": // check edit's parameter
			case "delete": // check remove's parameter
				dispatcher = request.getRequestDispatcher("DepartmentEmployeeController");
				break;
			}
		} else if (target.compareTo("log") == 0) {
			switch (action) {
			case "view":
			case "download":
			case "delete":
				dispatcher = request.getRequestDispatcher("log");
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