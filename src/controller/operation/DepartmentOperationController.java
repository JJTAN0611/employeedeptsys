package controller.operation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.postgresql.util.PSQLException;

import model.entity.Department;
import model.usebean.DepartmentUseBean;
import sessionbean.DepartmentSessionBeanLocal;
import utilities.ControllerManagement;
import utilities.LoggingGeneral;

@WebServlet("/DepartmentOperationController")
public class DepartmentOperationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentSessionBeanLocal deptbean;

	public DepartmentOperationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		
		String action = (String) request.getAttribute("action");

		try {
			if (action.compareTo("add") == 0) {

				// Prepare a empty use bean (except with id)
				String id = getAutoId(); // invoke auto id checker. If id is no longer enough. "allUsed" will be return.
				DepartmentUseBean dub = new DepartmentUseBean();
				if (id.compareTo("allUsed") == 0) {
					dub.setId_error("The id starting from 'd' (dxxx) is used all. Please start with other character.");
					
				} else {
					dub.setId(id);
					dub.setId_error("Please try to use the automate id (start with d) provided.");
				}
				request.setAttribute("dub", dub);
				
				// Forward
				RequestDispatcher req = request.getRequestDispatcher("department_add.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request, "Prepared auto id " + id + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("update") == 0) {

				// Find the department and prepare use bean with pre-input data
				Department dept = deptbean.findDepartment(request.getParameter("id"));

				// If not exist
				if (dept == null) {
					// Forward to error page
					RequestDispatcher req = request.getRequestDispatcher("error.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request, "Failed prepare department update pre-input. Not exist.");
					LoggingGeneral.setExitPoints(request);
				}

				// If exist
				request.setAttribute("dub", new DepartmentUseBean(dept));
				RequestDispatcher req = request.getRequestDispatcher("department_update.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request,
						"Prepared department update pre-input" + dept.getId() + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("delete") == 0) {

				// Find the department and prepare use bean with reference data
				Department dept = deptbean.findDepartment(request.getParameter("id"));
				if (dept == null) {
					// Forward to error page
					RequestDispatcher req = request.getRequestDispatcher("error.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request, "Failed prepare department delete reference. Not exist.");
					LoggingGeneral.setExitPoints(request);
				}

				// If exist
				request.setAttribute("dub", new DepartmentUseBean(dept));
				RequestDispatcher req = request.getRequestDispatcher("department_delete.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request,
						"Prepared department delete reference" + dept.getId() + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			}
		} catch (Exception ex) {
			// send to error page
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Abnormal process occur: " + ex.getMessage());
			LoggingGeneral.setExitPoints(request);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		if (action.compareTo("add") == 0) {

			// Prepare new use bean
			DepartmentUseBean dub = new DepartmentUseBean();
			try {
				// Fill in the use bean
				dub.setId(request.getParameter("id"));
				dub.setDept_name(request.getParameter("dept_name"));

				// Call for validate
				if (dub.validate()) {
					// When it success, write into database
					deptbean.addDepartment(dub);
					ControllerManagement.navigateSuccess(request, response);
					LoggingGeneral.setContentPoints(request, "Success add --> ID:" + dub.getId());
					LoggingGeneral.setExitPoints(request);
					return;
				}

				// Error in validate
				dub.setOverall_error("Please fix the error below");

			} catch (Exception e) {
				// Normally is database SQL violation, after validate
				errorRedirect(e, dub);
			}

			request.setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_add.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed add.");

		} else if (action.compareTo("update") == 0) {

			// Prepare new use bean
			DepartmentUseBean dub = new DepartmentUseBean();

			try {
				// Fill in the use bean
				dub.setId(request.getParameter("id"));
				dub.setDept_name(request.getParameter("dept_name"));

				// Call for validate
				if (dub.validate()) {
					// try to update. sessionbean will return false when id not exist
					if (deptbean.updateDepartment(dub)) {
						ControllerManagement.navigateSuccess(request, response);
						LoggingGeneral.setContentPoints(request, "Success update --> ID:" + dub.getId());
						LoggingGeneral.setExitPoints(request);
						return;
					} else {
						// Not exist
						dub.setId_error("Department not exist");
					}
				}

				// Any errors
				dub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				// Normally is database SQL violation.
				errorRedirect(e, dub);
			}

			request.setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_update.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed update.");

		} else if (action.compareTo("delete") == 0) {

			// Prepare new use bean
			DepartmentUseBean dub = new DepartmentUseBean();

			try {
				// Fill in the use bean
				dub.setId(request.getParameter("id"));

				// Validate the id
				if (dub.validateId()) {
					// try to delete. session bean will return false when id not exist
					if (deptbean.deleteDepartment(dub)) {
						ControllerManagement.navigateSuccess(request, response);
						LoggingGeneral.setContentPoints(request, "Success delete --> ID:" + dub.getId());
						LoggingGeneral.setExitPoints(request);
						return;
					} else {
						// Not exist
						dub.setId_error("Department not exist");
					}
				}

			} catch (Exception e) {
				dub = new DepartmentUseBean(deptbean.findDepartment(dub.getId()));
				errorRedirect(e, dub);
			}

			request.setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_delete.jsp");
			dispatcher.forward(request, response);
			LoggingGeneral.setContentPoints(request, "Failed update.");
		}

		LoggingGeneral.setExitPoints(request);
	}

	public void errorRedirect(Exception e, DepartmentUseBean dub) {

		PSQLException psqle = ControllerManagement.unwrapCause(PSQLException.class, e);
		if (psqle != null) {
			if (psqle.getMessage().contains("violates foreign key constraint")) {
				// delete
				dub.setOverall_error("You may need to clear the related departmentemployee relation record");
				dub.setId_error("This department is using in relation table and cannot be deleted");
				dub.setExpress("departmentemployee");
			} else if (psqle.getMessage().contains("duplicate key value violates unique constraint")) {
				// add
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

		// Get id list
		List<Object> idNumber = deptbean.getSortedDepartmentStartWithD();

		// Fully occupied
		if (idNumber.size() >= 999)
			return "allUsed";

		// Check still empty
		for (int i = 1; i <= idNumber.size(); i++) {
			int temp = Integer.valueOf(idNumber.get(i - 1).toString());
			if (i != temp)
				return "d" + String.format("%03d", i);

		}

		// Fully occupied in loop, but have remain (<999)
		return "d" + String.format("%03d", idNumber.size() + 1);

	}

}