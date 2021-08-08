package controller;

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

				// invoke auto id checker. If id is no longer enough. "allUsed" will be return.
				String id = getAutoId();

				// Response
				JsonObject jo = Json.createObjectBuilder().add("autoId", id).build();
				PrintWriter out = response.getWriter();
				out.print(jo);
				out.flush();

				LoggingGeneral.setContentPoints(request, "Given ID: " + id + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("getByIdAjax") == 0) {

				// Get department
				Department dept = deptbean.findDepartment(request.getParameter("id"));
				List<Department> h = new ArrayList<Department>();
				h.add(dept);

				// Set response type
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				PrintWriter out = response.getWriter();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, h);
				if (dept != null)
					LoggingGeneral.setContentPoints(request, "The department ID: " + dept.getId() + ". Completed.");
				else
					LoggingGeneral.setContentPoints(request, "ID not found. Failed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("getByNameAjax") == 0) {

				// Get department
				Department dept = deptbean.getDepartmentByName(request.getParameter("name"));
				List<Department> h = new ArrayList<Department>();
				h.add(dept);

				// Set response type
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				PrintWriter out = response.getWriter();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, h);
				if (dept != null)
					LoggingGeneral.setContentPoints(request, "The department ID: " + dept.getId() + ". Completed.");
				else
					LoggingGeneral.setContentPoints(request, "Name not found. Failed.");
				LoggingGeneral.setExitPoints(request);
				return;

			}else if (action.compareTo("add") == 0) {

				// Prepare a empty use bean (except with id)
				String id = getAutoId(); // invoke auto id checker. If id is no longer enough. "allUsed" will be return.
				if(id.compareTo("allUsed")==0)
					request.setAttribute("dub", new DepartmentUseBean());
				else
					request.setAttribute("dub", new DepartmentUseBean(id));

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

			} else if (action.compareTo("report") == 0) {
				/*
				 * check the validity of session if found user do two things in once, set error.
				 * for report page usually will pass cause the search view(pagination) will
				 * automatic refresh once it pressed report button
				 */

				String verificationToken = (String) request.getSession().getAttribute("dverificationToken");
				boolean dreportVerify;
				if (verificationToken == null || !verificationToken.equals(request.getParameter("verificationToken"))) {
					dreportVerify = false;
				} else {
					dreportVerify = true;
					request.getSession().setAttribute("departmentReportSize", deptbean.getNumberOfRows());
				}
				request.getSession().setAttribute("dreportVerify", String.valueOf(dreportVerify));

				// Forward
				RequestDispatcher req = request.getRequestDispatcher("department_report.jsp");
				req.forward(request, response);

				LoggingGeneral.setContentPoints(request, "Verification result: " + dreportVerify + ". Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			} else if (action.compareTo("download") == 0) {
				/*
				 * check the validity of session. if found user do two things in once, make
				 * report page show error. if found user do two things in once set error
				 */

				String verificationToken = (String) request.getSession().getAttribute("dverificationToken");
				if (verificationToken == null || !verificationToken.equals(request.getParameter("verificationToken"))) {
					request.getSession().setAttribute("dreportVerify", "false");

					RequestDispatcher req = request.getRequestDispatcher("department_report.jsp");
					req.forward(request, response);

					LoggingGeneral.setContentPoints(request,
							"Verification result: false. Report not generated. Completed.");
					LoggingGeneral.setExitPoints(request);
					return;
				}

				PrintWriter out = response.getWriter();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=DepartmentReport.xls; charset=UTF-8");

				List<Object[]> list = deptbean
						.getDepartmentReport((String) request.getSession().getAttribute("ddirection"));
				if (list != null && list.size() != 0) {
					out.println("\tDepartment ID\tDepartment Name");
					for (int i = 0; i < list.size(); i++)
						out.println((i + 1) + "\t" + list.get(i)[0].toString() + "\t" + list.get(i)[1].toString());
			
				} else
					out.println("No record found");
				
				out.println("");
				out.println("");
				out.println("\tKeyword Filter:\t No filter");
				out.println("\tOrder Direction:\t" + request.getSession().getAttribute("ddirection"));
				out.println("\tTotal Records:\t" + request.getSession().getAttribute("departmentReportSize"));
				LoggingGeneral.setContentPoints(request, "Verification result: true. Report generated. Completed.");
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
					ControllerManagement.navigateJS(request, response);
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
						ControllerManagement.navigateJS(request, response);
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
					// try to delete. sessionbean will return false when id not exist
					if (deptbean.deleteDepartment(dub)) {
						ControllerManagement.navigateJS(request, response);
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

		//Get id list
		List<Object> idNumber = deptbean.getSortedDepartmentStartWithD();

		//Fully occupied
		if (idNumber.size() >= 999)
			return "allUsed";
		
		//Check still empty
		for (int i = 1; i <= idNumber.size(); i++) {
			int temp = Integer.valueOf(idNumber.get(i-1).toString());
			if (i != temp)
				return "d" + String.format("%03d", i);
			
		}

		//Fully occupied in loop, but have remain (<999)
		return "d" + String.format("%03d", idNumber.size() + 1);

	}

}