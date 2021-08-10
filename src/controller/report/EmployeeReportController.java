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

import sessionbean.EmployeeSessionBeanLocal;
import utilities.LoggingGeneral;

@WebServlet("/EmployeeReportController")
public class EmployeeReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private EmployeeSessionBeanLocal empbean;

	public EmployeeReportController() {
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
				response.setContentType("text/csv");
				response.setHeader("Content-Disposition", "attachment; filename=EmployeeReport.csv; charset=UTF-8");

				String keyword = (String) request.getSession().getAttribute("ekeyword");
				String direction = (String) request.getSession().getAttribute("edirection");
				List<Object[]> list = empbean.getEmployeeReport(keyword, direction);
				if (list != null && list.size() != 0) {

					out.println(",Employee ID,First Name,Last Name,Gender,Birth Date,Hire Date");
					for (int i = 0; i < list.size(); i++)
						out.println((i + 1) + "," + list.get(i)[0].toString() + ",\"" + list.get(i)[1].toString() + "\",\""
								+ list.get(i)[2].toString() + "\"," + list.get(i)[3].toString() + ","
								+ list.get(i)[4].toString() + "," + list.get(i)[5].toString());
				} else
					out.println("No record found");

				out.println("");
				out.println("");
				out.println(",Keyword Filter:," + (keyword.equals("") ? "No filter" : keyword));
				out.println(",Order Direction:," + direction);
				out.println(",Total Records:," + request.getSession().getAttribute("employeeReportSize"));

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