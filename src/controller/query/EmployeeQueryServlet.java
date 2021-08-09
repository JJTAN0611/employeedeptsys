package controller.query;

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
import model.javabean.EmployeeJavaBean;
import sessionbean.EmployeeSessionBeanLocal;
import utilities.ControllerManagement;
import utilities.LoggingGeneral;

@WebServlet("/EmployeeQueryServlet")
public class EmployeeQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private EmployeeSessionBeanLocal empbean;

	public EmployeeQueryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {

			if (action.compareTo("getByIdAjax") == 0) {

				// Get department
				Employee emp;
				try {
					emp = empbean.findEmployee(Long.valueOf(request.getParameter("id")));
				} catch (Exception e) {
					emp = null;
				}
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

		doGet(request, response);
	}

}