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

import model.entity.Employee;
import model.entity.Employee;
import sessionbean.EmployeeSessionBeanLocal;

@WebServlet("/EmployeeController")
public class DepartmentEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private EmployeeSessionBeanLocal empbean;


	public DepartmentEmployeeController() {
		super();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String action = (String) request.getAttribute("action");
		
		try {
			
			if(action.compareTo("getAutoId")==0){
				response.getWriter().print(getAutoId());
			}else if(action.compareTo("add")==0) {
				request.setAttribute("id",getAutoId());
				RequestDispatcher req;
				req=request.getRequestDispatcher("employee_add.jsp");
				req.forward(request, response);
			}else if(action.compareTo("update")==0) {
				RequestDispatcher req;
				String id = (String) request.getAttribute("id");
				Employee emp = empbean.findEmployee(id);
				request.setAttribute("emp", emp);
				req=request.getRequestDispatcher("employee_update.jsp");
				req.forward(request, response);
			}else {
				RequestDispatcher req;
				String id = (String) request.getAttribute("id");
				Employee emp = empbean.findEmployee(id);
				request.setAttribute("emp", emp);
				req=request.getRequestDispatcher("employee_remove.jsp");
				req.forward(request, response);
			}
		
			
		} catch (EJBException ex) {
		}

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");
		
		try {
			if(action.compareTo("add")==0) {
				String[] s = {(String) request.getParameter("id"), 
						(String) request.getParameter("first_name"),
						(String) request.getParameter("last_name"),
						(String) request.getParameter("gender"),
						(String) request.getParameter("birth_date"),
						(String) request.getParameter("hire_date")
						};
				System.out.println("a"+Long.parseLong(s[0]));
				empbean.addEmployee(s);
			}
			else if (action.compareTo("delete")==0) {
				String id = (String) request.getParameter("id");
				empbean.deleteEmployee(id);	
			} else if (action.compareTo("update")==0) {
				String[] s = {(String) request.getParameter("id"), 
						(String) request.getParameter("first_name"),
						(String) request.getParameter("last_name"),
						(String) request.getParameter("gender"),
						(String) request.getParameter("birth_date"),
						(String) request.getParameter("hire_date")
						};
				empbean.updateEmployee(s);
			}

			ValidateManageLogic.navigateJS(response.getWriter(),"employee");
		} catch (EJBException ex) {
		}
		
	}
	
	private int getAutoId() {
		List<Employee> ds = empbean.readEmployee(1, 1,"","DESC");
		int id=ds.get(0).getId().intValue();
		return id+1;
	}
}