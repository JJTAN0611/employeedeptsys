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

import model.entity.DepartmentEmployee;
import sessionbean.DepartmentEmployeeSessionBeanLocal;
import utilities.LoggingGeneral;
import utilities.PaginationValidate;

@WebServlet("/DepartmentEmployeePaginationServlet")
public class DepartmentEmployeePaginationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private DepartmentEmployeeSessionBeanLocal deptembbean;
	
	public DepartmentEmployeePaginationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		response.setContentType("text/html;charset=UTF-8");
			
		RequestDispatcher dispatcher = null;
		
		try {
			/* Verify and process the parameter. 
			 * Check with session, if parameter==null & not exist in session, do default
			 * if parameter==null, exist in session, use existing(the session)
			 * if parameter have value, reset the session parameter.
			 * */
			if(!PaginationValidate.multiplePageView(request, response))
				throw new EJBException();
		
			int nOfPages = 0;
			int currentPage = (int) request.getSession().getAttribute("decurrentPage");
			int recordsPerPage = (int) request.getSession().getAttribute("derecordsPerPage");
			String keyword = (String) request.getSession().getAttribute("dekeyword");
			String direction = (String) request.getSession().getAttribute("dedirection");
	
			int rows = deptembbean.getNumberOfRows(keyword);
			nOfPages = rows / recordsPerPage;
			if (rows % recordsPerPage != 0) {
				nOfPages++;
			}
			if (currentPage > nOfPages && nOfPages != 0) {
				currentPage = nOfPages; //if larger than total page, set to maximum
			}
			
			List<DepartmentEmployee> list = deptembbean.readDepartmentEmployee(currentPage, recordsPerPage, keyword,
					direction); // Ask bean to give list
			
			request.setAttribute("departmentEmployeeList", list);
			request.setAttribute("nOfPages", nOfPages);
			dispatcher = request.getRequestDispatcher("departmentemployee_view.jsp");
			
			//set checker for report
			request.getSession().setAttribute("deverificationToken", String.valueOf(System.currentTimeMillis()));
			
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