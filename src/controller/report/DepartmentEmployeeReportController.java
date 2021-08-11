package controller.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionbean.DepartmentEmployeeSessionBeanLocal;
import utilities.LoggingGeneral;

@WebServlet("/DepartmentEmployeeReportController")
public class DepartmentEmployeeReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentEmployeeSessionBeanLocal deptempbean;

	public DepartmentEmployeeReportController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {
			if (action.compareTo("report") == 0) {
				/*
				 * check the validity of session. if found user do two things in once, set
				 * error. for report page usually will pass cause the search view(pagination)
				 * will automatic refresh once it pressed report button
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
				Integer[] summary = deptempbean
						.getDepartmentEmployeeInvolvedSummary((String) request.getSession().getAttribute("dekeyword"));
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

				//verify, if false, return error message
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

				//set response
				PrintWriter out = response.getWriter();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment; filename=DepartmentEmployeeRelationReport.xls; charset=UTF-8");

				//get parameter and get some summary
				String keyword = (String) request.getSession().getAttribute("dekeyword");
				String direction = (String) request.getSession().getAttribute("dedirection");
				Integer[] summary = (Integer[]) request.getSession().getAttribute("dereportSummary");// Brief summary
																										// (The involved
																										// foreign key)
				// prepare result
				List<Object[]> list = deptempbean.getDepartmentEmployeeReport(keyword, direction); // Get the list

				if (list != null && list.size() != 0) {
					out.println("\tDepartment ID\tDepartment Name (Reference)\tEmployee ID\tEmployee Name (Reference)\tFrom Date\tTo Date");
					for (int i = 0; i < list.size(); i++)
						out.println((i + 1) + "\t" + list.get(i)[0].toString() + "\t" + list.get(i)[1].toString() + "\t"
								+ list.get(i)[2].toString() + "\t" + list.get(i)[3].toString()+" "+list.get(i)[4].toString()+"\t"
								+ list.get(i)[5].toString()+ "\t" + list.get(i)[6].toString());

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
				out.println("\tTotal Records:\t" + request.getSession().getAttribute("departmentEmployeeReportSize"));

				LoggingGeneral.setContentPoints(request, "Verification result: true. Report generated. Completed.");
				LoggingGeneral.setExitPoints(request);
				return;

			}
		} catch (EJBException ex) {
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