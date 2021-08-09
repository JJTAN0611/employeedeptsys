package controller.query;

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

@WebServlet("/DepartmentQueryServlet")
public class DepartmentQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentSessionBeanLocal deptbean;

	public DepartmentQueryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {
			if (action.compareTo("getAutoId") == 0) {

				// invoke auto id checker. If "d" started id is no longer enough. "allUsed" will be return.
				String id = deptbean.getAutoId();

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