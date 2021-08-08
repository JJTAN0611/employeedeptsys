package controller.pagination;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.Department;
import sessionbean.DepartmentSessionBeanLocal;
import utilities.LoggingGeneral;
import utilities.PaginationValidate;

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
		/* Verify and process the parameter. 
		 * Check with session, if parameter==null & not exist in session, do default
		 * if parameter==null, exist in session, use existing(the session)
		 * if parameter have value, reset the session parameter.
		 * */

		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = null;
		try {
			if (!PaginationValidate.singlePageView(request, response))
				throw new Exception();

			String direction = (String) request.getSession().getAttribute("ddirection");
			List<Department> list = deptbean.readDepartment(direction); // Ask bean to give list
			request.setAttribute("departmentList",list);		
			
			dispatcher = request.getRequestDispatcher("department_view.jsp");
			
			//set checker for report
			request.getSession().setAttribute("dverificationToken", String.valueOf(System.currentTimeMillis()));
			LoggingGeneral.setContentPoints(request, "Success view");
			
		} catch (Exception ex) {
			dispatcher = request.getRequestDispatcher("error.jsp");
			LoggingGeneral.setContentPoints(request, "Unsuccess view" + ex.getMessage());
		} finally {
			dispatcher.forward(request, response);
			LoggingGeneral.setExitPoints(request);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}