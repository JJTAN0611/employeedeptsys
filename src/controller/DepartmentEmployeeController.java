package controller;

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
import model.usebean.DepartmentEmployeeUseBean;
import sessionbean.DepartmentEmployeeSessionBeanLocal;
import utilities.ControllerManagement;
import utilities.LoggingGeneral;

@WebServlet("/DepartmentEmployeeController")
public class DepartmentEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentEmployeeSessionBeanLocal deptempbean;

	public DepartmentEmployeeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("getByIdAjax") == 0) {

				// Get department
				DepartmentEmployee deptemp = deptempbean.findDepartmentEmployee(request.getParameter("dept_id"),
						Long.valueOf(request.getParameter("emp_id")));
				List<DepartmentEmployee> h = new ArrayList<DepartmentEmployee>();
				h.add(deptemp);

				// Set response type
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				PrintWriter out = response.getWriter();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, h);

				if (deptemp != null)
					LoggingGeneral.setContentPoints(request,
							"The departmentEmployee ID: " + deptemp.getId().getDepartmentId() + " | "
									+ deptemp.getId().getEmployeeId() + ". Completed.");
				else
					LoggingGeneral.setContentPoints(request, "ID not found. Failed.");

				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("add") == 0) {

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

			} else if (action.compareTo("report") == 0) {
				/*
				 * check the validity of session. if found user do two things in once, set error.
				 * for report page usually will pass cause the search view(pagination) will
				 * automatic refresh once it pressed report button
				 */

				String verificationToken = (String) request.getSession().getAttribute("deverificationToken");
				boolean dereportVerify;
				if (verificationToken == null || !verificationToken.equals(request.getParameter("verificationToken"))) {
					dereportVerify = false;
				} else {
					dereportVerify = true;
					int row = deptempbean.getNumberOfRows((String) request.getSession().getAttribute("dekeyword"));
					request.getSession().setAttribute("departmentEmployeeReportSize", row);
				}
				request.getSession().setAttribute("dereportVerify", String.valueOf(dereportVerify));
				
				// Brief summary (The involved foreign key)
				Integer[] summary=deptempbean.getDepartmentEmployeeSummary((String) request.getSession().getAttribute("dekeyword"));
				request.getSession().setAttribute("dereportSummary", summary);
				
				RequestDispatcher req = request.getRequestDispatcher("departmentemployee_report.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request, "Verification result: " + dereportVerify + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("download") == 0) {
				/*
				 * check the validity of session. if found user do two things in once, make
				 * report page show error. if found user do two things in once set error
				 */

				String verificationToken = (String) request.getSession().getAttribute("deverificationToken");
				if (verificationToken == null || !verificationToken.equals(request.getParameter("verificationToken"))) {
					request.getSession().setAttribute("dereportVerify", "false");

					RequestDispatcher req = request.getRequestDispatcher("departmentemployee_report.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request,
							"Verification result: false. Report not generated. Completed.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				PrintWriter out = response.getWriter();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment; filename=DepartmentEmployeeRelationReport.xls; charset=UTF-8");

				String keyword = (String) request.getSession().getAttribute("dekeyword");
				String direction = (String) request.getSession().getAttribute("dedirection");
				Integer[] summary=(Integer[]) request.getSession().getAttribute("dereportSummary");// Brief summary (The involved foreign key)
				List<Object[]> list = deptempbean.getDepartmentEmployeeReport(keyword, direction); //Get the list

				if (list != null && list.size() != 0) {
					out.println("\tDepartment ID\tEmployee ID\tFrom Date\tTo Date");
					for (int i = 0; i < list.size(); i++)
						out.println((i + 1) + "\t" + list.get(i)[0].toString() + "\t" + list.get(i)[1].toString() + "\t"
								+ list.get(i)[2].toString() + "\t" + list.get(i)[3].toString());

				} else
					out.println("No record found");
				
				out.println("");
				out.println("");
				out.println("\tInvolved Department:\t" + summary[0]);
				out.println("\tInvolved Employee:\t" + summary[1]);
				out.println("");
				out.println("");
				out.println("\tKeyword Filter:\t" + (keyword.equals("") ? "No filter" : keyword));
				out.println("\tOrder Direction:\t" + direction);
				out.println(
						"\tTotal Records:\t" + request.getSession().getAttribute("departmentEmployeeReportSize"));

				LoggingGeneral.setContentPoints(request, "Verification result: true. Report generated. Completed.");
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
				if (deub.validateId()) {
					// When it success, write into database
					deptempbean.addDepartmentEmployee(deub);
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
				if (deub.validateId()) {
					// try to update. sessionbean will return false when id not exist
					if (deptempbean.updateDepartmentEmployee(deub)) {
						ControllerManagement.navigateSuccess(request, response);
						LoggingGeneral.setContentPoints(request, "Success update --> ID:" + deub.getDept_id() + " | "
								+ deub.getEmp_id() + ". Completed.");
						LoggingGeneral.setExitPoints(request);
						return;
					}else {
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