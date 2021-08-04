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
import utilities.ControllerManagement;
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
				request.getSession().setAttribute("dub", dept);
				RequestDispatcher req = request.getRequestDispatcher("department_update.jsp");
				req.forward(request, response);
			} else if (action.compareTo("delete") == 0) {
				String id = (String) request.getParameter("id");
				Department dept = deptbean.findDepartment(id);
				request.getSession().setAttribute("dub", dept);
				RequestDispatcher req = request.getRequestDispatcher("department_remove.jsp");
				req.forward(request, response);
			} else {
				// download
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
			DepartmentUseBean dub = new DepartmentUseBean();
			try {
				dub.setId(request.getParameter("id"));
				dub.setDept_name(request.getParameter("dept_name"));
				if (dub.validate()) {
					deptbean.addDepartment(dub);
					ControllerManagement.navigateJS(response.getWriter(), request);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + dub.getId());
					return;
				}
			} catch (Exception e) {
				errorRedirect(e, dub);
			}
			request.getSession().setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_add.jsp");
			dispatcher.forward(request, response);
		} else if (action.compareTo("delete") == 0) {
			DepartmentUseBean dub = new DepartmentUseBean();

			try {
				dub.setId(request.getParameter("id"));
				if (dub.validateId()) {
					if (deptbean.deleteDepartment(dub)) {
						ControllerManagement.navigateJS(response.getWriter(), request);
						logger.setContentPoints(request, "Success " + action + " --> ID:" + dub.getId());
						return;
					} else
						dub.setId_error("Department not exist");
				}
			} catch (Exception e) {
				errorRedirect(e, dub);
			}
			request.getSession().setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_remove.jsp");
			dispatcher.forward(request, response);
		} else if (action.compareTo("update") == 0) {
			DepartmentUseBean dub = new DepartmentUseBean();
			try {
				dub.setId(request.getParameter("id"));
				dub.setDept_name(request.getParameter("dept_name"));
				if (dub.validate()) {
					if (deptbean.updateDepartment(dub)) {
						ControllerManagement.navigateJS(response.getWriter(), request);
						logger.setContentPoints(request, "Success " + action + " --> ID:" + dub.getId());
						return;
					} else
						dub.setId_error("Department not exist");
				}
			} catch (Exception e) {
				errorRedirect(e, dub);
			}
			request.getSession().setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_update.jsp");
			dispatcher.forward(request, response);

		}

		logger.setExitPoints(request);
	}

	public void errorRedirect(Exception e, DepartmentUseBean dub) {
		PSQLException psqle = ControllerManagement.unwrapCause(PSQLException.class, e);
		if (psqle != null)
			if (psqle.getMessage().contains("dublicate key value violates unique constraint")) {
				if (psqle.getMessage().contains("primary"))
					dub.setId_error("dublicate department id");
				else if (psqle.getMessage().contains("dept_name"))
					dub.setDept_name_error("dublicate department name");
				else
					dub.setOverall_error(e.getMessage());
				return;
			}
		dub.setOverall_error(e.getMessage());
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