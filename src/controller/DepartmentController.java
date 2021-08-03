package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import javax.persistence.RollbackException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.postgresql.util.PSQLException;

import model.entity.Department;
import model.entity.Employee;
import model.usebean.DepartmentUseBean;
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
		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");

		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("getAutoId") == 0) {
				JsonObject jo = Json.createObjectBuilder().add("autoId", getAutoId()).build();
				PrintWriter out = response.getWriter();
				out.print(jo);
				out.flush();
			} else if (action.compareTo("getDepartment") == 0) {
				String id = (String) request.getParameter("id");
				Department dept = deptbean.findDepartment(id);
				JsonObject jo;
				if (dept != null)
					jo = Json.createObjectBuilder().add("id", dept.getId()).add("name", dept.getDeptName()).build();
				else
					jo = Json.createObjectBuilder().add("id", "").add("name", "").build();
				PrintWriter out = response.getWriter();
				out.print(jo);
				out.flush();
			} else if (action.compareTo("add") == 0) {
				request.getSession().setAttribute("dub", new DepartmentUseBean(getAutoId()));
				RequestDispatcher req = request.getRequestDispatcher("department_add.jsp");
				req.forward(request, response);
			} else if (action.compareTo("update") == 0) {
				String id = (String) request.getParameter("id");
				Department dept = deptbean.findDepartment(id);
				request.getSession().setAttribute("dub", new DepartmentUseBean(dept.getId(), dept.getDeptName()));
				RequestDispatcher req = request.getRequestDispatcher("department_update.jsp");
				req.forward(request, response);
			} else if (action.compareTo("delete") == 0) {
				String id = (String) request.getParameter("id");
				Department dept = deptbean.findDepartment(id);
				request.getSession().setAttribute("dub", new DepartmentUseBean(dept.getId(), dept.getDeptName()));
				RequestDispatcher req = request.getRequestDispatcher("department_remove.jsp");
				req.forward(request, response);
			} else {
				// download
			}
		} catch (EJBException ex) {
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		logger.setEntryPoints(request);

		String action = (String) request.getAttribute("action");

		if (action.compareTo("add") == 0) {
			DepartmentUseBean dup = new DepartmentUseBean();
			try {
				dup.setId(request.getParameter("id"));
				dup.setDept_name(request.getParameter("dept_name"));
				if (dup.validate(request)) {
					deptbean.addDepartment(dup);
					ValidateManageLogic.navigateJS(response.getWriter(), request);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + dup.getId());
					return;
				}
			} catch (Exception e) {
				errorRedirect(e, dup);
			}
			request.getSession().setAttribute("dub", dup);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_add.jsp");
			dispatcher.forward(request, response);
		} else if (action.compareTo("delete") == 0) {
			DepartmentUseBean dup = new DepartmentUseBean();

			try {
				dup.setId(request.getParameter("id"));
				if (dup.validateId(request)) {
					if (deptbean.deleteDepartment(dup)) {
						ValidateManageLogic.navigateJS(response.getWriter(), request);
						logger.setContentPoints(request, "Success " + action + " --> ID:" + dup.getId());
						return;
					} else
						dup.setId_error("Department not exist");
				}
			} catch (Exception e) {
				errorRedirect(e, dup);
			}
			request.getSession().setAttribute("dub", dup);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_remove.jsp");
			dispatcher.forward(request, response);
		} else if (action.compareTo("update") == 0) {
			DepartmentUseBean dup = new DepartmentUseBean();
			try {
				dup.setId(request.getParameter("id"));
				dup.setDept_name(request.getParameter("dept_name"));
				if (dup.validate(request)) {
					if (deptbean.updateDepartment(dup)) {
						ValidateManageLogic.navigateJS(response.getWriter(), request);
						logger.setContentPoints(request, "Success " + action + " --> ID:" + dup.getId());
						return;
					} else
						dup.setId_error("Department not exist");
				}
			} catch (Exception e) {
				errorRedirect(e, dup);
			}
			request.getSession().setAttribute("dub", dup);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_update.jsp");
			dispatcher.forward(request, response);

		}

		logger.setExitPoints(request);
	}

	public void errorRedirect(Exception e, DepartmentUseBean dup) {
		PSQLException psqle = unwrapCause(PSQLException.class, e);
		if (psqle.getMessage().contains("duplicate key value violates unique constraint")) {
			if (psqle.getMessage().contains("primary"))
				dup.setId_error("Duplicate department id");
			else if (psqle.getMessage().contains("dept_name"))
				dup.setDept_name_error("Duplicate department name");
			else
				dup.setOverall_error(e.getMessage());
			return;
		}
		dup.setOverall_error(e.getMessage());
	}

	private String getAutoId() {
		List<Department> ds = deptbean.getAllDepartment();
		int t = 0;
		for (Department d : ds)
			if (Integer.valueOf(d.getId().substring(1, 4)) > t)
				t = Integer.valueOf(d.getId().substring(1, 4));
		return "d" + String.format("%03d", t + 1);
	}

	public <T> T unwrapCause(Class<T> clazz, Throwable e) {
		while (!clazz.isInstance(e) && e.getCause() != null && e != e.getCause()) {
			e = e.getCause();
		}
		return clazz.isInstance(e) ? clazz.cast(e) : null;
	}
}