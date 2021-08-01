package pagination;

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
import utilities.LoggingGeneral;

@WebServlet("/DepartmentPaginationServlet")
public class DepartmentPaginationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private DepartmentSessionBeanLocal deptbean;

	public DepartmentPaginationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		logger.setEntryPoints(request);

		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = null;
		try {
			if (!PaginationValidate.singlePageView(request, response))
				throw new Exception();

			String direction = (String) request.getAttribute("direction");

			List<Department> lists = deptbean.readDepartment(direction); // Ask bean to give list
			request.setAttribute("departments", lists);
			
			dispatcher = request.getRequestDispatcher("department_view.jsp");
			logger.setContentPoints(request, "Success view");
			
		} catch (Exception ex) {
			dispatcher = request.getRequestDispatcher("error.jsp");
			logger.setContentPoints(request, "Unsuccess view" + ex.getMessage());
		} finally {
			dispatcher.forward(request, response);
			logger.setExitPoints(request);
		}

		logger.setContentPoints(request, "Success view");
		logger.setExitPoints(request);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}