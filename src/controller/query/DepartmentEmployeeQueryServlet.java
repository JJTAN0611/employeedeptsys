package controller.query;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import model.entity.DepartmentEmployee;
import sessionbean.DepartmentEmployeeSessionBeanLocal;
import utilities.LoggingGeneral;

@WebServlet("/DepartmentEmployeeQueryServlet")
public class DepartmentEmployeeQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentEmployeeSessionBeanLocal deptempbean;

	public DepartmentEmployeeQueryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//AJAX request process
		
		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("getByIdAjax") == 0) {

				// Get department
				DepartmentEmployee deptemp;
				try {
					deptemp = deptempbean.findDepartmentEmployee(request.getParameter("dept_id"),
							Long.valueOf(request.getParameter("emp_id")));
				} catch (EJBException e) {
					deptemp = null;
				}
				List<DepartmentEmployee> h = new ArrayList<DepartmentEmployee>();
				h.add(deptemp);

				// Set response type
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				PrintWriter out = response.getWriter();
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, h); //No need if else for null, it will have null in json object

				if (deptemp != null)
					LoggingGeneral.setContentPoints(request,
							"The departmentEmployee ID: " + deptemp.getId().getDepartmentId() + " | "
									+ deptemp.getId().getEmployeeId() + ". Completed.");
				else
					LoggingGeneral.setContentPoints(request, "ID not found." + request.getParameter("dept_id")
							+ request.getParameter("emp_id") + " Failed.");

				LoggingGeneral.setExitPoints(request);
				return;

			}
		} catch (EJBException ex) {

			LoggingGeneral.setContentPoints(request, "Abnormal process occur: " + ex.getMessage());
			LoggingGeneral.setExitPoints(request);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}