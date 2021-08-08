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


import model.entity.Employee;
import model.usebean.EmployeeUseBean;
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

			if (action.compareTo("getByIdAjax") == 0) {

				// Get department
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));
				List<Employee> h = new ArrayList<Employee>();
				h.add(emp);

				// Set response type
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				PrintWriter out = response.getWriter();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, h);

				if (emp != null)
					LoggingGeneral.setContentPoints(request, "The employee ID: " + emp.getId() + ". Completed.");
				else
					LoggingGeneral.setContentPoints(request, "ID not found. Failed.");

				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("getByNameAjax") == 0) {

				// Get department

				Employee emp = empbean.getEmployeeByName(request.getParameter("name"));
				List<Employee> h = new ArrayList<Employee>();
				h.add(emp);
	
				// Set response type
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				PrintWriter out = response.getWriter();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, h);

				if (emp != null)
					LoggingGeneral.setContentPoints(request, "The employee ID: " + emp.getId() + ". Completed.");
				else
					LoggingGeneral.setContentPoints(request, "Name not found. Failed.");

				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("add") == 0) {

				// Prepare a empty use bean (except with id)
				request.setAttribute("eub", new EmployeeUseBean());

				// Forward
				RequestDispatcher req = request.getRequestDispatcher("employee_add.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request, "Done add preparation. Completed");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("update") == 0) {

				// Find the employee
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));

				// If not exist
				if (emp == null) {
					// Forward to error page
					RequestDispatcher req = request.getRequestDispatcher("error.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request, "Failed prepare employee update pre-input. Not exist.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				// If exist, prepare use bean with pre-input data
				request.setAttribute("eub", new EmployeeUseBean(emp));

				RequestDispatcher req = request.getRequestDispatcher("employee_update.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request,
						"Prepared employee update pre-input" + emp.getId() + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("delete") == 0) {

				// Find the employee
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));

				// If not exist
				if (emp == null) {
					// Forward to error page
					RequestDispatcher req = request.getRequestDispatcher("error.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request, "Failed prepare employee delete reference. Not exist.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				// If exist, prepare use bean with reference data
				request.setAttribute("eub", new EmployeeUseBean(emp));
				RequestDispatcher req = request.getRequestDispatcher("employee_delete.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request,
						"Prepared employee delete reference" + emp.getId() + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;
			} else if (action.compareTo("report") == 0) {
				/*
				 * check the validity of session. if found user do two things in once, set
				 * error. for report page usually will pass cause the search view(pagination)
				 * will automatic refresh once it pressed report button
				 */
				String verificationToken = (String) request.getSession().getAttribute("everificationToken");
				boolean ereportVerify;
				if (verificationToken == null || !verificationToken.equals(request.getParameter("verificationToken"))) {
					ereportVerify = false;
				} else {
					ereportVerify = true;
					int row = empbean.getNumberOfRows((String) request.getSession().getAttribute("ekeyword"));
					request.getSession().setAttribute("employeeReportSize", row);
				}
				request.getSession().setAttribute("ereportVerify", String.valueOf(ereportVerify));
				
				RequestDispatcher req = request.getRequestDispatcher("employee_report.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request, "Verification result: " + ereportVerify + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("download") == 0) {
				/*
				 * check the validity of session. if found user do two things in once, make
				 * report page show error. if found user do two things in once set error
				 */

				String verificationToken = (String) request.getSession().getAttribute("everificationToken");
				if (verificationToken == null || !verificationToken.equals(request.getParameter("verificationToken"))) {
					request.getSession().setAttribute("ereportVerify", "false");

					RequestDispatcher req = request.getRequestDispatcher("employee_report.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request,
							"Verification result: false. Report not generated. Completed.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				PrintWriter out = response.getWriter();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=EmployeeReport.xls; charset=UTF-8");

				String keyword = (String) request.getSession().getAttribute("ekeyword");
				String direction = (String) request.getSession().getAttribute("edirection");
				List<Object[]> list = empbean.getEmployeeReport(keyword, direction);
				if (list != null && list.size() != 0) {

					out.println("\tEmployee ID\tFirst Name\tLast Name\tGender\tBirth Date\tHire Date");
					for (int i = 0; i < list.size(); i++)
						out.println((i + 1) + "\t" + list.get(i)[0].toString() + "\t" + list.get(i)[1].toString() + "\t"
								+ list.get(i)[2].toString() + "\t" + list.get(i)[3].toString() + "\t"
								+ list.get(i)[4].toString() + "\t" + list.get(i)[5].toString());
				} else
					out.println("No record found");
				
				out.println("");
				out.println("");
				out.println("\tKeyword Filter:\t" + (keyword.equals("") ? "No filter" : keyword));
				out.println("\tOrder Direction:\t" + direction);
				out.println("\tTotal Records:\t" + request.getSession().getAttribute("employeeReportSize"));
			}

			LoggingGeneral.setContentPoints(request, "Verification result: true. Report generated. Completed.");
			LoggingGeneral.setExitPoints(request);
			return;

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
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				// Fill in the use bean
				eub.setFirst_name(request.getParameter("first_name"));
				eub.setLast_name(request.getParameter("last_name"));
				eub.setGender(request.getParameter("gender"));
				eub.setBirth_date(request.getParameter("birth_date"));
				eub.setHire_date(request.getParameter("hire_date"));

				// Call for validate
				if (eub.validate()) {
					empbean.addEmployee(eub);
					ControllerManagement.navigateJS(request, response);
					LoggingGeneral.setContentPoints(request, "Success add --> ID:" + eub.getId() + ". Completed.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				// Error in validate
				eub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				// Normally is database SQL violation, after validate
				errorRedirect(e, eub);
			}
			request.setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_add.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed add.");

		} else if (action.compareTo("update") == 0) {

			// Prepare new use bean
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				// Fill in the use bean
				eub.setId(request.getParameter("id"));
				eub.setFirst_name(request.getParameter("first_name"));
				eub.setLast_name(request.getParameter("last_name"));
				eub.setGender(request.getParameter("gender"));
				eub.setBirth_date(request.getParameter("birth_date"));
				eub.setHire_date(request.getParameter("hire_date"));

				// Call for validate
				if (eub.validate()) {
					// try to update. sessionbean will return false when id not exist
					if (empbean.updateEmployee(eub) == true) {
						ControllerManagement.navigateJS(request, response);
						LoggingGeneral.setContentPoints(request,
								"Success " + action + " --> ID:" + eub.getFirst_name() + ". Completed");
						return;
					} else {
						// Not exist
						eub.setId_error("Employee not exist");
						eub.setOverall_error("Please fix the error below");
					}
				}

				// Any errors
				eub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				// Normally is database SQL violation.
				errorRedirect(e, eub);
			}
			request.setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_update.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed update.");
			
		} else if (action.compareTo("delete") == 0) {

			// Prepare new use bean
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				// Fill in the use bean
				eub.setId(request.getParameter("id"));

				// Validate the id
				if (eub.validateId()) {
					// try to delete. sessionbean will return false when id not exist
					if (empbean.deleteEmployee(eub)) {
						ControllerManagement.navigateJS(request, response);
						LoggingGeneral.setContentPoints(request,
								"Success delete --> ID:" + eub.getId() + ". Completed.");
						LoggingGeneral.setExitPoints(request);
						return;
					} else {
						// Not exist
						eub.setId_error("Employee not exist");
						eub.setOverall_error("Please fix the error below");
					}
				}
			} catch (Exception e) {
				eub = new EmployeeUseBean(empbean.findEmployee(eub.getId()));
				errorRedirect(e, eub);
			}
			request.setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_delete.jsp");
			dispatcher.forward(request, response);

			LoggingGeneral.setContentPoints(request, "Failed delete.");
		}
		LoggingGeneral.setExitPoints(request);
	}

	public void errorRedirect(Exception e, EmployeeUseBean eub) {

		PSQLException psqle = ControllerManagement.unwrapCause(PSQLException.class, e);
		if (psqle != null) {
			if (psqle.getMessage().contains("violates foreign key constraint")) {
				//delete
				eub.setOverall_error("You may need to clear the related departmentemployee relation record");
				eub.setId_error("This employee is using in relation table and cannot be deleted");
				eub.setExpress("departmentemployee");
			} else if (psqle.getMessage().contains("dublicate key value violates unique constraint")) {
				//add
				eub.setOverall_error("Duplicate error. Please change the input as annotated below");
				if (psqle.getMessage().contains("primary"))
					eub.setId_error("dublicate employee id");
				else
					eub.setOverall_error(psqle.getMessage());
			}
		} else
			eub.setOverall_error(e.toString());
	}

}