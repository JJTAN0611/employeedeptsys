
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

		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		logger.setContentPoints(request,request.getParameter("id")+"az");
		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = null;

		// compulsory to check

		String target = (String) request.getAttribute("target");
		String action = (String) request.getAttribute("action");
		// department
		if (target.compareTo("department") == 0)
			switch (action) {
			case "getAutoId":
			case "ajax":
			case "download":
				dispatcher = request.getRequestDispatcher("DepartmentController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("DepartmentPaginationServlet");
				break;
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
			case "ajax":
				dispatcher = request.getRequestDispatcher("EmployeeController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("EmployeePaginationServlet");
				break;
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
			case "ajax":
				dispatcher = request.getRequestDispatcher("DepartmentEmployeeController");
				break;
			case "getDepartment":
				dispatcher = request.getRequestDispatcher("DepartmentController");
				break;
			case "getEmployee":
				dispatcher = request.getRequestDispatcher("EmployeeController");
				break;
			case "view": // check display's parameter
				dispatcher = request.getRequestDispatcher("DepartmentEmployeePaginationServlet");
				break;
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
		}

		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}