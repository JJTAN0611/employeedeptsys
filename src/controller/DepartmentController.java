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
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.persistence.NoResultException;
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

		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("getAutoId") == 0) {
				JsonObject jo = Json.createObjectBuilder().add("autoId", getAutoId()).build();
				PrintWriter out = response.getWriter();
				out.print(jo);
				out.flush();
			} else if (action.compareTo("getDepartment") == 0) {
				String id = (String) request.getAttribute("id");
				Department dept = deptbean.findDepartment(id);
				JsonObject jo = Json.createObjectBuilder().add("id", dept.getId()).add("name", dept.getDeptName())
						.build();
				PrintWriter out = response.getWriter();
				out.print(jo);
				out.flush();
			} else if (action.compareTo("add") == 0) {
				RequestDispatcher req;
				request.setAttribute("id", getAutoId());
				req = request.getRequestDispatcher("department_add.jsp");
				req.forward(request, response);
			} else if (action.compareTo("update") == 0) {
				RequestDispatcher req;
				String id = (String) request.getAttribute("id");
				Department dept = deptbean.findDepartment(id);
				request.setAttribute("dept", dept);
				req = request.getRequestDispatcher("department_update.jsp");
				req.forward(request, response);
			} else {
				RequestDispatcher req;
				String id = (String) request.getAttribute("id");
				Department dept = deptbean.findDepartment(id);
				request.setAttribute("dept", dept);
				req = request.getRequestDispatcher("department_remove.jsp");
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
			logger.setEntryPoints(request);

			if (action.compareTo("add") == 0) {
				String[] s = { (String) request.getParameter("id"), (String) request.getParameter("dept_name") };
				deptbean.addDepartment(s);
				ValidateManageLogic.navigateJS(response.getWriter(), "department");
				logger.setContentPoints(request, "Success " + action + " --> ID:" + s[0]);
			} else if (action.compareTo("delete") == 0) {

				String id = (String) request.getParameter("id");
				deptbean.deleteDepartment(id);
				ValidateManageLogic.navigateJS(response.getWriter(), "department");
				logger.setContentPoints(request, "Success " + action + " --> ID:" + id);
			} else if (action.compareTo("update") == 0) {
				String[] dept = { (String) request.getParameter("id"), request.getParameter("dept_name") };
				deptbean.updateDepartment(dept);
				ValidateManageLogic.navigateJS(response.getWriter(), "department");
				logger.setContentPoints(request, "Success " + action + " --> ID:" + dept[0]);
			} else {
				logger.setContentPoints(request, "Invalid action --> " + action);
			}

		} catch (EJBTransactionRolledbackException rollback) {
			ValidateManageLogic.printErrorNotice(response.getWriter(),
					"Duplicate record or extremely large value given!! " + rollback.getStackTrace().toString(),
					"department");
			logger.setContentPoints(request, "Unsuccess --> " + action + rollback.getStackTrace().toString());
		} catch (EJBException invalid) {
			if (invalid.toString().contains("NullPointerException"))
				ValidateManageLogic.printErrorNotice(response.getWriter(), "Empty input!! " + invalid.toString(),
						"department");
			else
				ValidateManageLogic.printErrorNotice(response.getWriter(), "Invalid input!!. " + invalid.toString(),
						"department");
			logger.setContentPoints(request, "Unsuccess --> " + action + invalid.getStackTrace().toString());
		} finally {
			logger.setExitPoints(request);
		}

	}

	private String getAutoId() {
		List<Department> ds = deptbean.getAllDepartment();
		int t = 0;
		for (Department d : ds)
			if (Integer.valueOf(d.getId().substring(1, 4)) > t)
				t = Integer.valueOf(d.getId().substring(1, 4));
		return "d" + String.format("%03d", t + 1);
	}
}