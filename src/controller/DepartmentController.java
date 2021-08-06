package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.postgresql.util.PSQLException;

import model.entity.Department;
import model.entity.DepartmentEmployee;
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
			} else if (action.compareTo("ajax") == 0) {
				PrintWriter out = response.getWriter();
				Department dept = deptbean.findDepartment(request.getParameter("id"));
				List<Department> h = new ArrayList<Department>();
				h.add(dept);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				if (h != null) {
					ObjectMapper mapper = new ObjectMapper();
					mapper.writeValue(out, h);
				} else

					return;
			} else if (action.compareTo("add") == 0) {
				request.getSession().setAttribute("dub", new DepartmentUseBean(getAutoId()));
				RequestDispatcher req = request.getRequestDispatcher("department_add.jsp");
				req.forward(request, response);
			} else if (action.compareTo("update") == 0) {
				Department dept = deptbean.findDepartment(request.getParameter("id"));
				request.getSession().setAttribute("dub", new DepartmentUseBean(dept));
				RequestDispatcher req = request.getRequestDispatcher("department_update.jsp");
				req.forward(request, response);
			} else if (action.compareTo("delete") == 0) {
				Department dept = deptbean.findDepartment(request.getParameter("id"));
				request.getSession().setAttribute("dub", new DepartmentUseBean(dept));
				RequestDispatcher req = request.getRequestDispatcher("department_delete.jsp");
				req.forward(request, response);
			} else {
				// download
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=Department.xls; charset=UTF-8");
				PrintWriter out = response.getWriter();
				List<Department> list = deptbean.getAllDepartment();
				if (list != null && list.size() != 0) {
					out.println("\tDepartment ID\tDepartment Name");
					for (int i = 0; i < list.size(); i++)
						out.println(i+"\t"+list.get(i).getId() + "\t" + list.get(i).getDeptName());
				}else
					out.println("No record found");
				
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

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
					ControllerManagement.navigateJS(request, response);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + dub.getId());
					return;
				}
				dub.setOverall_error("Please fix the error below");
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
						ControllerManagement.navigateJS(request, response);
						logger.setContentPoints(request, "Success " + action + " --> ID:" + dub.getId());
						return;
					} else
						dub.setId_error("Department not exist");
				}
				dub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				dub = new DepartmentUseBean(deptbean.findDepartment(dub.getId()));
				errorRedirect(e, dub);
			}

			request.getSession().setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_delete.jsp");
			dispatcher.forward(request, response);
		} else if (action.compareTo("update") == 0) {
			DepartmentUseBean dub = new DepartmentUseBean();
			try {
				dub.setId(request.getParameter("id"));
				dub.setDept_name(request.getParameter("dept_name"));
				if (dub.validate()) {
					if (deptbean.updateDepartment(dub)) {
						ControllerManagement.navigateJS(request, response);
						logger.setContentPoints(request, "Success " + action + " --> ID:" + dub.getId());
						return;
					} else
						dub.setId_error("Department not exist");
				}
				dub.setOverall_error("Please fix the error below");
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
		if (psqle != null) {
			if (psqle.getMessage().contains("violates foreign key constraint")) {
				dub.setOverall_error("You may need to clear the related departmentemployee relation record");
				dub.setId_error("This department is using in relation table and cannot be deleted");
				dub.setExpress("departmentemployee");
			} else if (psqle.getMessage().contains("duplicate key value violates unique constraint")) {
				dub.setOverall_error("Duplicate error. Please change the input as annotated below");
				if (psqle.getMessage().contains("primary"))
					dub.setId_error("Duplicate department id");
				else if (psqle.getMessage().contains("dept_name"))
					dub.setDept_name_error("Duplicate department name");
				else
					dub.setOverall_error(psqle.getMessage());
			}
		} else
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