package controller.report;

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
import model.javabean.DepartmentJavaBean;
import sessionbean.DepartmentSessionBeanLocal;
import utilities.ControllerManagement;
import utilities.LoggingGeneral;

@WebServlet("/DepartmentReportController")
public class DepartmentReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentSessionBeanLocal deptbean;

	public DepartmentReportController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {
			if (action.compareTo("report") == 0) {
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
		doGet(request, response);
	}
}