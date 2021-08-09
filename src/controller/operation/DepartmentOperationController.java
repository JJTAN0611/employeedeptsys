package controller.operation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.postgresql.util.PSQLException;

import model.entity.Department;
import model.javabean.DepartmentJavaBean;
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
				String id = deptbean.getAutoId(); // invoke auto id checker. If "d" started id is no longer enough. "allUsed" will be return.
				DepartmentJavaBean dub = new DepartmentJavaBean();
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
				request.setAttribute("dub", new DepartmentJavaBean(dept));
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
				request.setAttribute("dub", new DepartmentJavaBean(dept));
				RequestDispatcher req = request.getRequestDispatcher("department_delete.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request,
						"Prepared department delete reference" + dept.getId() + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			}
		} catch (EJBException  ex) {
			// send to error page
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Abnormal process occur: " + ex.getMessage());
			LoggingGeneral.setExitPoints(request);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// PSQL exception (Unique Constraint & foreign key constraint, etc) will be try and catch here
		// Input validate done by javabean
		// Ensure "update" instead of "add" is done in session bean, will return false if related record not exist.
		// Ensure "delete" is done in session bean, will return false if related record not exist.

		
		String action = (String) request.getAttribute("action");

		if (action.compareTo("add") == 0) {

			// Prepare new use bean
			DepartmentJavaBean dub = new DepartmentJavaBean();
			try {
				// Fill in the use bean
				dub.setId(request.getParameter("id"));
				dub.setDept_name(request.getParameter("dept_name"));

				// Call for validate
				if (dub.validate()) {
					// When it success validate, write into database
					deptbean.addDepartment(dub);
					
					ControllerManagement.navigateSuccess(request, response);
					LoggingGeneral.setContentPoints(request, "Success add --> ID:" + dub.getId());
					LoggingGeneral.setExitPoints(request);
					return;
				}

			} catch (EJBException e) {
				// Normally is database PSQL violation, after validate
				errorSetting(e, dub);
			}

			request.setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_add.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed add.");

		} else if (action.compareTo("update") == 0) {

			// Prepare new use bean
			DepartmentJavaBean dub = new DepartmentJavaBean();

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
						dub.setId_error("Department not exist.");
						dub.setOverall_error("It might be sitmoutaneous use performed the same action. Please try again from department view.");
						dub.setExpress("department");
					}
				}
			} catch (EJBException e) {
				// Normally is database PSQL violation.
				errorSetting(e, dub);
			}

			request.setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_update.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed update.");

		} else if (action.compareTo("delete") == 0) {

			// Prepare new use bean
			DepartmentJavaBean dub = new DepartmentJavaBean();

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
						dub.setId_error("Department not exist. Try again on department view.");
						dub.setOverall_error("It might be sitmoutaneous user performed the same action. Try again on department view.");
						dub.setExpress("department");
					}
				}

			} catch (EJBException e) {
				// Normally is database PSQL violation.
				// Dont use user record for continuing displaying. It have risk to show not updated data
				dub = new DepartmentJavaBean(deptbean.findDepartment(dub.getId()));
				errorSetting(e, dub);
			}

			request.setAttribute("dub", dub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("department_delete.jsp");
			dispatcher.forward(request, response);
			LoggingGeneral.setContentPoints(request, "Failed update.");
		}

		LoggingGeneral.setExitPoints(request);
	}

	public void errorSetting(EJBException e, DepartmentJavaBean dub) {

		PSQLException psqle = ControllerManagement.unwrapCause(PSQLException.class, e);
		if (psqle != null) {
			if (psqle.getMessage().contains("violates foreign key constraint")) {
				// delete
				dub.setOverall_error("You may need to clear the related departmentemployee relation record.");
				dub.setId_error("This department is using in relation table and cannot be deleted.");
				dub.setExpress("departmentemployee");
			} else if (psqle.getMessage().contains("duplicate key value violates unique constraint")) {
				// add
				dub.setOverall_error("Duplicate error. Please change the input as annotated below.");
				if (psqle.getMessage().contains("primary"))
					dub.setId_error("Duplicate department id.");
				else if (psqle.getMessage().contains("dept_name"))
					dub.setDept_name_error("Duplicate department name.");
				else
					dub.setOverall_error("Error occur: " + psqle.getMessage());
			}
		} else { //Unexpected error.
			dub.setOverall_error("Try again on department view. Error occur: " + e.getMessage());
			dub.setId_error("Try again.");
			dub.setExpress("department");
		}
	}

}