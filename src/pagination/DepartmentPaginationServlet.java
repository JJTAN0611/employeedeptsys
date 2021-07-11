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
		response.setContentType("text/html;charset=UTF-8");

		String direction=(String) request.getAttribute("direction");
		List<Department> lists = deptbean.readDepartment(direction); //Ask bean to give list
		request.setAttribute("departments", lists);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("department_view.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}