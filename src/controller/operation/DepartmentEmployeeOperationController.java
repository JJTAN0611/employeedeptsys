package controller.operation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.postgresql.util.PSQLException;

import model.entity.DepartmentEmployee;
import model.javabean.DepartmentEmployeeUseBean;
import sessionbean.DepartmentEmployeeSessionBeanLocal;
import utilities.ControllerManagement;
import utilities.LoggingGeneral;

@WebServlet("/DepartmentEmployeeOperationController")
public class DepartmentEmployeeOperationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentEmployeeSessionBeanLocal deptempbean;

	public DepartmentEmployeeOperationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("add") == 0) {

				// Prepare a empty use bean (except with id)
				request.setAttribute("deub", new DepartmentEmployeeUseBean());

				// Forward
				RequestDispatcher req = request.getRequestDispatcher("departmentemployee_add.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request, "Done add preparation. Completed");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("update") == 0) {

				// Find the department
				DepartmentEmployee deptemp = deptempbean.findDepartmentEmployee(request.getParameter("dept_id"),
						Long.valueOf(request.getParameter("emp_id")));

				// If not exist
				if (deptemp == null) {
					// Forward to error page
					RequestDispatcher req = request.getRequestDispatcher("error.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request,
							"Failed prepare departmentemployee update pre-input. Not exist.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				// If exist, prepare use bean with pre-input data
				request.setAttribute("deub", new DepartmentEmployeeUseBean(deptemp));
				RequestDispatcher req = request.getRequestDispatcher("departmentemployee_update.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request, "Prepared departmentemployee update pre-input"
						+ deptemp.getId().getDepartmentId() + " | " + deptemp.getId().getEmployeeId() + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("delete") == 0) {

				// Find the department
				DepartmentEmployee deptemp = deptempbean.findDepartmentEmployee(request.getParameter("dept_id"),
						Long.valueOf(request.getParameter("emp_id")));

				// If not exist
				if (deptemp == null) {
					// Forward to error page
					RequestDispatcher req = request.getRequestDispatcher("error.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request,
							"Failed prepare departmentemployee delete reference. Not exist.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				// If exist, prepare use bean with reference data
				request.setAttribute("deub", new DepartmentEmployeeUseBean(deptemp));
				RequestDispatcher req = request.getRequestDispatcher("departmentemployee_delete.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request, "Prepared department delete reference"
						+ deptemp.getId().getDepartmentId() + " | " + deptemp.getId().getEmployeeId() + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			}
		} catch (Exception ex) {
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
			DepartmentEmployeeUseBean deub = new DepartmentEmployeeUseBean();
			try {
				// Fill in the use bean
				deub.setDept_id(request.getParameter("dept_id"));
				deub.setEmp_id(request.getParameter("emp_id"));
				deub.setFrom_date(request.getParameter("from_date"));
				deub.setTo_date(request.getParameter("to_date"));

				// Call for validate
				if (deub.validate()) {
					// When it success, write into database
					deptempbean.addDepartmentEmployee(deub);
					
					// pagination set to new department employee record
					request.getSession().setAttribute("dekeyword",deub.getDept_id()+"%"+deub.getEmp_id()+"%"+deub.getFrom_date()+" "+deub.getTo_date());
					
					ControllerManagement.navigateSuccess(request, response);
					LoggingGeneral.setContentPoints(request,
							"Success add --> ID:" + deub.getDept_id() + " | " + deub.getEmp_id() + ". Completed.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				// Error in validate
				deub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				// Normally is database SQL violation, after validate
				errorRedirect(e, deub);
			}
			request.setAttribute("deub", deub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("departmentemployee_add.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed add.");

		} else if (action.compareTo("update") == 0) {

			// Prepare new use bean
			DepartmentEmployeeUseBean deub = new DepartmentEmployeeUseBean();
			try {
				// Fill in the use bean
				deub.setDept_id(request.getParameter("dept_id"));
				deub.setEmp_id(request.getParameter("emp_id"));
				deub.setFrom_date(request.getParameter("from_date"));
				deub.setTo_date(request.getParameter("to_date"));

				// Call for validate
				if (deub.validate()) {
					// try to update. sessionbean will return false when id not exist
					if (deptempbean.updateDepartmentEmployee(deub)) {
						ControllerManagement.navigateSuccess(request, response);
						LoggingGeneral.setContentPoints(request, "Success update --> ID:" + deub.getDept_id() + " | "
								+ deub.getEmp_id() + ". Completed.");
						LoggingGeneral.setExitPoints(request);
						return;
					} else {
						// Not exist
						deub.setDept_id_error("Department and employee combination not exist");
						deub.setEmp_id_error("Department and employee combination not exist");
						deub.setOverall_error("This combination not exist");
					}
				}

				// Any errors
				deub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				// Normally is database SQL violation.
				errorRedirect(e, deub);
			}

			request.setAttribute("deub", deub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("departmentemployee_update.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed update.");

		} else if (action.compareTo("delete") == 0) {

			// Prepare new use bean
			DepartmentEmployeeUseBean deub = new DepartmentEmployeeUseBean();
			try {
				// Fill in the use bean
				deub.setDept_id(request.getParameter("dept_id"));
				deub.setEmp_id(request.getParameter("emp_id"));

				// Validate the id
				if (deub.validateId()) {
					// try to delete. sessionbean will return false when id not exist
					if (deptempbean.deleteDepartmentEmployee(deub)) {
						ControllerManagement.navigateSuccess(request, response);

						LoggingGeneral.setContentPoints(request, "Success delete --> ID:" + deub.getDept_id() + " | "
								+ deub.getEmp_id() + ". Completed.");
						LoggingGeneral.setExitPoints(request);
						return;
					} else {
						// Not exist
						deub.setDept_id_error("Department and employee combination not exist");
						deub.setEmp_id_error("Department and employee combination not exist");
						deub.setOverall_error("This combination not exist");
					}
				}

			} catch (Exception e) {
				deub = new DepartmentEmployeeUseBean(
						deptempbean.findDepartmentEmployee(deub.getDept_id(), deub.getEmp_id()));
				errorRedirect(e, deub);
			}
			request.setAttribute("deub", deub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("departmentemployee_delete.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed delete.");

		}
		LoggingGeneral.setExitPoints(request);
	}

	public void errorRedirect(Exception e, DepartmentEmployeeUseBean deub) {
		PSQLException psqle = ControllerManagement.unwrapCause(PSQLException.class, e);
		if (psqle != null) {
			if (psqle.getMessage().contains("duplicate key value violates unique constraint")) {
				// add
				deub.setOverall_error("Duplicate error. Please change the input as annotated below");
				deub.setDept_id_error("Duplicate combination of department id and employee id");
				deub.setEmp_id_error("Duplicate combination of department id and employee id");
				return;
			} else if (psqle.getMessage().contains("violates foreign key constraint")) {
				// add
				deub.setOverall_error("No related records. Please change the input as annotated below");
				if (psqle.getMessage().contains("\"department\"")) {
					deub.setDept_id_error("Department ID not exist in department table");
					deub.setExpress("department");
				} else if (psqle.getMessage().contains("\"employee\"")) {
					deub.setEmp_id_error("Employee ID not exist in employee table");
					deub.setExpress("employee");
				} else
					deub.setOverall_error(psqle.getMessage());
			}
		} else
			deub.setOverall_error(e.toString());
	}

}