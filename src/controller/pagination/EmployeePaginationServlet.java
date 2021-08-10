package controller.pagination;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.Employee;
import sessionbean.EmployeeSessionBeanLocal;
import utilities.LoggingGeneral;
import utilities.PaginationValidate;

@WebServlet("/EmployeePaginationServlet")
public class EmployeePaginationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private EmployeeSessionBeanLocal empbean;

	public EmployeePaginationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Verify and process the parameter. Check with session, if parameter==null &
		 * not exist in session, do default if parameter==null, exist in session, use
		 * existing(the session) if parameter have value, reset the session parameter.
		 */

		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = null;

		try {

			if (!PaginationValidate.multiplePageView(request, response))
				throw new EJBException();

			int nOfPages = 0;
			int currentPage = (int) request.getSession().getAttribute("ecurrentPage");
			int recordsPerPage = (int) request.getSession().getAttribute("erecordsPerPage");
			String keyword = (String) request.getSession().getAttribute("ekeyword");
			String direction = (String) request.getSession().getAttribute("edirection");

			int rows = empbean.getNumberOfRows(keyword);
			nOfPages = rows / recordsPerPage;
			if (rows % recordsPerPage != 0) {
				nOfPages++;
			}
			if (currentPage > nOfPages && nOfPages != 0) {
				currentPage = nOfPages; // if larger than total page, set to maximum
			}
			request.getSession().setAttribute("ecurrentPage",currentPage);//save back
			List<Employee> list = empbean.readEmployee(currentPage, recordsPerPage, keyword, direction); // Ask bean to
																											// give list
			request.setAttribute("employeeList", list);
			request.setAttribute("nOfPages", nOfPages);

			dispatcher = request.getRequestDispatcher("employee_view.jsp");

			// set checker for report
			request.getSession().setAttribute("everificationToken", String.valueOf(System.currentTimeMillis()));

			LoggingGeneral.setContentPoints(request, "Success view");
		} catch (EJBException ex) {
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