package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.postgresql.util.PSQLException;

import model.entity.Department;
import model.entity.Employee;
import model.usebean.DepartmentUseBean;
import model.usebean.EmployeeUseBean;
import model.entity.Employee;
import sessionbean.EmployeeSessionBeanLocal;
import utilities.ControllerManagement;
import utilities.LoggingGeneral;

@WebServlet("/EmployeeController")
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private EmployeeSessionBeanLocal empbean;

	public EmployeeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("getAutoId") == 0) {
				response.getWriter().print(getAutoId());
			} else if (action.compareTo("getEmployee") == 0) {
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));

				JsonObject jo = Json.createObjectBuilder().add("id", emp.getId()).add("first_name", emp.getFirstName())
						.add("last_name", emp.getLastName()).add("gender", emp.getGender())
						.add("birth_date", emp.getBirthDate().toString()).add("hire_date", emp.getHireDate().toString())
						.build();
				PrintWriter out = response.getWriter();
				out.print(jo);
				out.flush();
			} else if (action.compareTo("add") == 0) {
				request.getSession().setAttribute("eub", new EmployeeUseBean());
				RequestDispatcher req = request.getRequestDispatcher("employee_add.jsp");
				req.forward(request, response);
			} else if (action.compareTo("update") == 0) {
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));
				request.getSession().setAttribute("eub", new EmployeeUseBean(emp));
				RequestDispatcher req = request.getRequestDispatcher("employee_update.jsp");
				req.forward(request, response);
			} else if (action.compareTo("delete") == 0) {
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));
				request.getSession().setAttribute("eub", new EmployeeUseBean(emp));
				RequestDispatcher req = request.getRequestDispatcher("employee_delete.jsp");
				req.forward(request, response);
			} else {

			}

		} catch (Exception ex) {
			RequestDispatcher req = request.getRequestDispatcher("error.jsp");
			req.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		logger.setEntryPoints(request);

		String action = (String) request.getAttribute("action");

		if (action.compareTo("add") == 0) {
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				eub.setFirst_name(request.getParameter("first_name"));
				eub.setLast_name(request.getParameter("last_name"));
				eub.setGender(request.getParameter("gender"));
				eub.setBirth_date(request.getParameter("birth_date"));
				eub.setHire_date(request.getParameter("hire_date"));

				if (eub.validate()) {
					empbean.addEmployee(eub);
					ControllerManagement.navigateJS(response.getWriter(), request);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + eub.getFirst_name());
					return;
				}
				eub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				errorRedirect(e, eub);
				logger.setContentPoints(request, e.getMessage());
			}
			request.getSession().setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_add.jsp");
			dispatcher.forward(request, response);

		} else if (action.compareTo("delete") == 0) {
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				eub.setId(request.getParameter("id"));
				logger.setContentPoints(request, "aa" + eub.getId());
				if (empbean.deleteEmployee(eub)) {
					ControllerManagement.navigateJS(response.getWriter(), request);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + eub.getId());
					return;
				} else {
					eub.setId_error("Employee not exist");
					eub.setOverall_error("Please fix the error below");
				}
			} catch (Exception e) {
				eub=new EmployeeUseBean(empbean.findEmployee(eub.getId()));
				errorRedirect(e, eub);
			}
			request.getSession().setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_delete.jsp");
			dispatcher.forward(request, response);

		} else if (action.compareTo("update") == 0) {
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				eub.setId(request.getParameter("id"));
				eub.setFirst_name(request.getParameter("first_name"));
				eub.setLast_name(request.getParameter("last_name"));
				eub.setGender(request.getParameter("gender"));
				eub.setBirth_date(request.getParameter("birth_date"));
				eub.setHire_date(request.getParameter("hire_date"));

				if (eub.validate()) {
					if (empbean.updateEmployee(eub) == true) {
						ControllerManagement.navigateJS(response.getWriter(), request);
						logger.setContentPoints(request, "Success " + action + " --> ID:" + eub.getFirst_name());
						return;
					}

				}
				eub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				errorRedirect(e, eub);
			}
			request.getSession().setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_update.jsp");
			dispatcher.forward(request, response);
		}
		logger.setExitPoints(request);
	}

	public void errorRedirect(Exception e, EmployeeUseBean eub) {

		PSQLException psqle = ControllerManagement.unwrapCause(PSQLException.class, e);
		if (psqle != null) {
			if(psqle.getMessage().contains("violates foreign key constraint")) {

				eub.setOverall_error("You may need to clear the related departmentemployee relation record");
				eub.setId_error("This employee is using in relation table and cannot be deleted");
				eub.setExpress("departmentemployee");
			}
			else if (psqle.getMessage().contains("dublicate key value violates unique constraint")) {
				eub.setOverall_error("Duplicate error. Please change the input as annotated below");
				if (psqle.getMessage().contains("primary"))
					eub.setId_error("dublicate employee id");
				else
					eub.setOverall_error(psqle.getMessage());
				return;
			}
		} else
			eub.setOverall_error(e.toString());
	}

	private int getAutoId() {
		List<Employee> ds = empbean.readEmployee(1, 1, "", "DESC");
		int id = ds.get(0).getId().intValue();
		return id + 1;
	}
}