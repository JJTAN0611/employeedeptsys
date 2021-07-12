package controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import model.entity.DepartmentEmployee;
import model.entity.Employee;
import model.entity.Employee;
import sessionbean.DepartmentEmployeeSessionBeanLocal;
import sessionbean.EmployeeSessionBeanLocal;

@WebServlet("/DepartmentEmployeeController")
public class DepartmentEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentEmployeeSessionBeanLocal deptempbean;

	public DepartmentEmployeeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {
			if (action.compareTo("add") == 0) {
				RequestDispatcher req;
				req = request.getRequestDispatcher("departmentemployee_add.jsp");
				req.forward(request, response);
			} else if (action.compareTo("update") == 0) {
				RequestDispatcher req;
				String id[]={(String) request.getAttribute("dept_id"),(String) request.getAttribute("emp_id")};
				DepartmentEmployee deptemp = deptempbean.findDepartmentEmployee(id);
				request.setAttribute("deptemp", deptemp);
				req = request.getRequestDispatcher("departmentemployee_update.jsp");
				req.forward(request, response);
			} else {
				RequestDispatcher req;
				String id[] = {(String) request.getAttribute("dept_id"),(String) request.getAttribute("emp_id")};

				DepartmentEmployee deptemp = deptempbean.findDepartmentEmployee(id);
				request.setAttribute("deptemp", deptemp);
				req = request.getRequestDispatcher("departmentemployee_remove.jsp");
				req.forward(request, response);
			}

		} catch (EJBException ex) {
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {
			if (action.compareTo("add") == 0) {
				String[] s = {(String) request.getAttribute("dept_id")
						,(String) request.getAttribute("emp_id")
				    	,(String) request.getParameter("from_date")
				    	,(String) request.getParameter("to_date") };
				
				deptempbean.addDepartmentEmployee(s);
			} else if (action.compareTo("delete") == 0) {
				String[] id =  {(String) request.getAttribute("dept_id"),
								(String) request.getAttribute("emp_id")};
				
				deptempbean.deleteDepartmentEmployee(id);
			} else if (action.compareTo("update") == 0) {
				String[] s = {(String) request.getAttribute("dept_id")
							,(String) request.getAttribute("emp_id")
					    	,(String) request.getParameter("from_date")
					    	,(String) request.getParameter("to_date") };
				System.out.println(s[0]+s[1]);
				System.out.println(s[2]);
				deptempbean.updateDepartmentEmployee(s);
			}

			ValidateManageLogic.navigateJS(response.getWriter(), "departmentemployee");
		} catch (EJBException ex) {
		}

	}

}