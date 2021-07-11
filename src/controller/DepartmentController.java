package controller;

import java.io.IOException;
import java.io.PrintWriter;

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

@WebServlet("/DepartmentController")
public class DepartmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentSessionBeanLocal deptbean;


	public DepartmentController() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		try {
			Department dept = deptbean.findDepartment(id);
			request.setAttribute("dept", dept);
		} catch (EJBException ex) {
		}

		RequestDispatcher req = request.getRequestDispatcher("department_update.jsp");
		req.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = (String) request.getAttribute("id");
		String action = (String) request.getAttribute("action");
		
		try {
			if (action.compareTo("remove")==0) {
				deptbean.deleteDepartment(id);	
			} else if (action.compareTo("update")==0) {
				String []dept= {id,request.getParameter("dept_name")};
				deptbean.updateDepartment(dept);
				ValidateManageLogic.navigateJS(response.getWriter());
			}
			
		} catch (EJBException ex) {
		}
		
	}
}