package controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import model.entity.Department;
import model.entity.Employee;
import model.entity.Employee;
import sessionbean.EmployeeSessionBeanLocal;
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
		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("getAutoId") == 0) {
				response.getWriter().print(getAutoId());
			} else if (action.compareTo("getEmployee") == 0) {
				String id = (String) request.getParameter("id");
				Employee emp = empbean.findEmployee(id);
				logger.setContentPoints(request, "hi"+id);
				JsonObject jo = Json.createObjectBuilder().add("id", emp.getId()).add("first_name", emp.getFirstName())
						.add("last_name", emp.getLastName()).add("gender", emp.getGender())
						.add("birth_date", emp.getBirthDate().toString()).add("hire_date", emp.getHireDate().toString())
						.build();
				PrintWriter out = response.getWriter();
				out.print(jo);
				out.flush();
			} else if (action.compareTo("add") == 0) {
				RequestDispatcher req;
				req = request.getRequestDispatcher("employee_add.jsp");
				req.forward(request, response);
			} else if (action.compareTo("update") == 0) {
				RequestDispatcher req;
				String id = (String) request.getParameter("id");
				Employee emp = empbean.findEmployee(id);
				request.setAttribute("emp", emp);
				req = request.getRequestDispatcher("employee_update.jsp");
				req.forward(request, response);
			} else {
				RequestDispatcher req;
				String id = (String) request.getParameter("id");
				Employee emp = empbean.findEmployee(id);
				request.setAttribute("emp", emp);
				req = request.getRequestDispatcher("employee_remove.jsp");
				req.forward(request, response);
			}

		} catch (EJBException ex) {
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		logger.setEntryPoints(request);

		String action = (String) request.getAttribute("action");

		try {
			if (action.compareTo("add") == 0) {
				if (!ValidateManageLogic.employeeContent(request, response)) {
					ValidateManageLogic.printErrorNotice(response.getWriter(), "Invalid employee content", request);
				} else if (!ValidateManageLogic.employeeID(request, response)) {
					ValidateManageLogic.printErrorNotice(response.getWriter(), "Invalid department id", request);
				} else {
					String[] s = { (String) request.getAttribute("id"), (String) request.getAttribute("first_name"),
							(String) request.getAttribute("last_name"), (String) request.getAttribute("gender"),
							(String) request.getAttribute("birth_date"), (String) request.getAttribute("hire_date") };
					empbean.addEmployee(s);
					ValidateManageLogic.navigateJS(response.getWriter(), request);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + s[0]);
				}

			} else if (action.compareTo("delete") == 0) {
				if (!ValidateManageLogic.employeeID(request, response)) {
					ValidateManageLogic.printErrorNotice(response.getWriter(), "Invalid employee",request);
				} else {
					String id = (String) request.getAttribute("id");
					empbean.deleteEmployee(id);
					ValidateManageLogic.navigateJS(response.getWriter(), request);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + id);
				}

			} else if (action.compareTo("update") == 0) {
				if (!ValidateManageLogic.employeeID(request, response)) {
					ValidateManageLogic.printErrorNotice(response.getWriter(), "Invalid employee ID", request);
				}else if (!ValidateManageLogic.employeeContent(request, response)) {
					ValidateManageLogic.printErrorNotice(response.getWriter(), "Invalid employee content.", request);
				} else {
					String[] s = { (String) request.getAttribute("id"), (String) request.getAttribute("first_name"),
							(String) request.getAttribute("last_name"), (String) request.getAttribute("gender"),
							(String) request.getAttribute("birth_date"), (String) request.getAttribute("hire_date") };
					empbean.updateEmployee(s);
					ValidateManageLogic.navigateJS(response.getWriter(), request);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + s[0]);
				}
			}
		} catch (EJBTransactionRolledbackException rollback) {
			ValidateManageLogic.printErrorNotice(response.getWriter(),
					"Duplicate record or extremely large value given!! ", request);
		} catch (EJBException invalid) {
			if (invalid.toString().contains("NullPointerException"))
				ValidateManageLogic.printErrorNotice(response.getWriter(), "Empty input!! " + invalid.toString(),
						request);
			else
				ValidateManageLogic.printErrorNotice(response.getWriter(), "Invalid input!!. " + invalid.toString(),
						request);
			logger.setContentPoints(request, "Unsuccess --> " + action + invalid.getStackTrace().toString());
		} finally {
			logger.setExitPoints(request);
		}

	}

	private int getAutoId() {
		List<Employee> ds = empbean.readEmployee(1, 1, "", "DESC");
		int id = ds.get(0).getId().intValue();
		return id + 1;
	}
}