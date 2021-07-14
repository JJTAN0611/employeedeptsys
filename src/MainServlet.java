
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

import controller.ValidateManageLogic;
import model.entity.Department;
import model.entity.Employee;
import sessionbean.DepartmentSessionBeanLocal;
import sessionbean.EmployeeSessionBeanLocal;

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

		RequestDispatcher dispatcher = request.getRequestDispatcher("404.jsp");

		// compulsory to check
		String action = ServletForwardValidate.action(request, response);
		String table = ServletForwardValidate.table(request, response);

		
		if (action != null && table != null) {
			//department
			if (table.compareTo("department") == 0)
				switch (action) {
				case "getAutoId":dispatcher = request.getRequestDispatcher("DepartmentController");break;
				case "view": // check display's parameter
					if (ServletForwardValidate.departmentView(request, response))
						dispatcher = request.getRequestDispatcher("DepartmentPaginationServlet");
					break;
				case "add":
					dispatcher = request.getRequestDispatcher("DepartmentController");
				case "update": // check edit's parameter
				case "delete": // check remove's parameter
					if (ServletForwardValidate.departmentUpdateRemove(request, response)) {
						dispatcher = request.getRequestDispatcher("DepartmentController");
					}
					break;
				default:
					break;
				}
			//employee
			else if (table.compareTo("employee") == 0) {
				switch (action) {
				case "getAutoId":dispatcher = request.getRequestDispatcher("EmployeeController");break;
				case "view": // check display's parameter
					if (ServletForwardValidate.employeeView(request, response)) {
						dispatcher = request.getRequestDispatcher("EmployeePaginationServlet");
					}
					break;
				case "add":
					dispatcher = request.getRequestDispatcher("EmployeeController");
				case "update": // check edit's parameter
				case "delete": // check remove's parameter
					if (ServletForwardValidate.employeeUpdateRemove(request, response)) {
						dispatcher = request.getRequestDispatcher("EmployeeController");
					}
					break;
				default:
					break;
				}
			//departmentemployee
			}else if (table.compareTo("departmentemployee") == 0) {
				switch (action) {
				case "getAutoId":dispatcher = request.getRequestDispatcher("DepartmentEmployeeController");break;
				case "getDepartment":
					if (ServletForwardValidate.departmentUpdateRemove(request, response)) {
						dispatcher=request.getRequestDispatcher("DepartmentController");break;
					}
				case "getEmployee":
					if (ServletForwardValidate.employeeUpdateRemove(request, response)) {
						dispatcher=request.getRequestDispatcher("EmployeeController");break;
					}
				case "view": // check display's parameter
					if (ServletForwardValidate.departmentemployeeView(request, response)) {
						dispatcher = request.getRequestDispatcher("DepartmentEmployeePaginationServlet");
					}
					break;
				case "add":
					dispatcher = request.getRequestDispatcher("DepartmentEmployeeController");
				case "update": // check edit's parameter
				case "delete": // check remove's parameter
					if (ServletForwardValidate.departmentemployeeUpdateRemove(request, response)) {
						dispatcher = request.getRequestDispatcher("DepartmentEmployeeController");
					}
					break;
				default:
					break;
				}
			}
		}

		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}