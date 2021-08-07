package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.codehaus.jackson.map.ObjectMapper;
import org.postgresql.util.PSQLException;

import model.entity.Department;
import model.entity.Employee;
import model.usebean.DepartmentUseBean;
import model.usebean.EmployeeUseBean;
import model.entity.Employee;
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

		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		logger.setEntryPoints(request);

		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("getEmployeeAjax") == 0) {
				PrintWriter out = response.getWriter();
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));
				List<Employee> h = new ArrayList<Employee>();
				h.add(emp);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, h);

				return;
			} else if (action.compareTo("add") == 0) {
				request.setAttribute("eub", new EmployeeUseBean());
				RequestDispatcher req = request.getRequestDispatcher("employee_add.jsp");
				req.forward(request, response);
			} else if (action.compareTo("update") == 0) {
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));
				request.setAttribute("eub", new EmployeeUseBean(emp));
				RequestDispatcher req = request.getRequestDispatcher("employee_update.jsp");
				req.forward(request, response);
			} else if (action.compareTo("delete") == 0) {
				Employee emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));
				request.setAttribute("eub", new EmployeeUseBean(emp));
				RequestDispatcher req = request.getRequestDispatcher("employee_delete.jsp");
				req.forward(request, response);
			} else if (action.compareTo("report") == 0) {
				// check the validity of session
				// if found user do two things in once set error
				// usually will pass cause the search view(pagination) will automatic refresh
				// once it pressed report button
				String verificationToken = (String) request.getSession().getAttribute("everificationToken");
				
				if (verificationToken == null || !verificationToken.equals(request.getParameter("verificationToken"))) {
					request.getSession().setAttribute("ereportVerify", "false");
				} else {
					request.getSession().setAttribute("ereportVerify", "true");
					int row = empbean.getNumberOfRows((String) request.getSession().getAttribute("ekeyword"));
					if (row > 0) {
						request.getSession().setAttribute("employeeReportSize", row);
					} else
						request.getSession().setAttribute("employeeReportSize", 0);
				}
				RequestDispatcher req = request.getRequestDispatcher("employee_report.jsp");
				req.forward(request, response);
			} else if (action.compareTo("download") == 0) {
				// check the validity of session
				// if found user do two things in once set error
				String verificationToken = (String) request.getSession().getAttribute("everificationToken");
				if (verificationToken == null || !verificationToken.equals(request.getParameter("verificationToken"))) {
					request.getSession().setAttribute("ereportVerify", "false");
					RequestDispatcher req = request.getRequestDispatcher("employee_report.jsp");
					req.forward(request, response);
					return;
				}

				PrintWriter out = response.getWriter();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=EmployeeReport.xls; charset=UTF-8");
				
				String keyword=(String) request.getSession().getAttribute("ekeyword");
				String direction=(String) request.getSession().getAttribute("edirection");
				List<Object[]> list = empbean.getEmployeeReport(keyword, direction);
				if (list != null && list.size() != 0) {
					System.out.println("heree");
					out.println("\tEmployee ID\tFirst Name\tLast Name\tGender\tBirth Date\tHire Date");
					for (int i = 0; i < list.size(); i++)
						out.println((i+1) + "\t" + list.get(i)[0].toString() + "\t" + list.get(i)[1].toString() + "\t"
								+ list.get(i)[2].toString() + "\t" + list.get(i)[3].toString() + "\t"
								+ list.get(i)[4].toString() + "\t" + list.get(i)[5].toString());
					out.println("");
					out.println("");
					out.println("\tKeyword Filter:\t" + (keyword.equals("")?"No filter":keyword) );
					out.println("\tOrder Direction:\t" + direction);
					out.println("\tTotal Records:\t" + request.getSession().getAttribute("employeeReportSize"));
				} else
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
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				eub.setFirst_name(request.getParameter("first_name"));
				eub.setLast_name(request.getParameter("last_name"));
				eub.setGender(request.getParameter("gender"));
				eub.setBirth_date(request.getParameter("birth_date"));
				eub.setHire_date(request.getParameter("hire_date"));

				if (eub.validate()) {
					empbean.addEmployee(eub);
					ControllerManagement.navigateJS(request, response);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + eub.getFirst_name());
					return;
				}
				eub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				errorRedirect(e, eub);
				logger.setContentPoints(request, e.getMessage());
			}
			request.setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_add.jsp");
			dispatcher.forward(request, response);

		} else if (action.compareTo("delete") == 0) {
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				eub.setId(request.getParameter("id"));
				logger.setContentPoints(request, "aa" + eub.getId());
				if (empbean.deleteEmployee(eub)) {
					ControllerManagement.navigateJS(request, response);
					logger.setContentPoints(request, "Success " + action + " --> ID:" + eub.getId());
					return;
				} else {
					eub.setId_error("Employee not exist");
					eub.setOverall_error("Please fix the error below");
				}
			} catch (Exception e) {
				eub = new EmployeeUseBean(empbean.findEmployee(eub.getId()));
				errorRedirect(e, eub);
			}
			request.setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_delete.jsp");
			dispatcher.forward(request, response);

		} else if (action.compareTo("update") == 0) {
			EmployeeUseBean eub = new EmployeeUseBean();
			try {
				eub.setId(request.getParameter("id"));
				eub.setFirst_name(request.getParameter("first_name"));
				eub.setLast_name(request.getParameter("last_name"));
				eub.setGender(request.getParameter("gender"));
				eub.setBirth_date(request.getParameter("birth_date"));
				eub.setHire_date(request.getParameter("hire_date"));

				if (eub.validate()) {
					if (empbean.updateEmployee(eub) == true) {
						ControllerManagement.navigateJS(request, response);
						logger.setContentPoints(request, "Success " + action + " --> ID:" + eub.getFirst_name());
						return;
					}

				}
				eub.setOverall_error("Please fix the error below");
			} catch (Exception e) {
				errorRedirect(e, eub);
			}
			request.setAttribute("eub", eub);
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee_update.jsp");
			dispatcher.forward(request, response);
		}
		logger.setExitPoints(request);
	}

	public void errorRedirect(Exception e, EmployeeUseBean eub) {

		PSQLException psqle = ControllerManagement.unwrapCause(PSQLException.class, e);
		if (psqle != null) {
			if (psqle.getMessage().contains("violates foreign key constraint")) {
				eub.setOverall_error("You may need to clear the related departmentemployee relation record");
				eub.setId_error("This employee is using in relation table and cannot be deleted");
				eub.setExpress("departmentemployee");
			} else if (psqle.getMessage().contains("dublicate key value violates unique constraint")) {
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