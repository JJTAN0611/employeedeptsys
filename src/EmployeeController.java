
import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.Employee;
import sessionbean.EmployeeSessionBeanLocal;

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

		String id = request.getParameter("id");
		try {
			Employee emp = empbean.findEmployee(id);
			request.setAttribute("EMP", emp);
			RequestDispatcher req = request.getRequestDispatcher("EmployeeUpdate.jsp");
			req.forward(request, response);
		} catch (EJBException ex) {
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String eid = request.getParameter("id");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String gender = request.getParameter("gender");
		String dob = request.getParameter("dob");
		String hiredate = request.getParameter("hdate");
		PrintWriter out = response.getWriter();
		// this line is to package the whole values into one array string variable -
		// easier just pass one parameter object
		String[] s = { eid, fname, lname, gender, dob, hiredate };
		try {
			if (ValidateManageLogic.validateManager(request).equals("UPDATE")) {
				// call session bean updateEmployee method
				empbean.updateEmployee(s);
			} else if (ValidateManageLogic.validateManager(request).equals("DELETE")) {
				// call session bean deleteEmployee method
				empbean.deleteEmployee(eid);	
				// if ADD button is clicked
			} else {
				// call session bean addEmployee method
				empbean.addEmployee(s);
			}
			// this line is to redirect to notify record has been updated and redirect to
			// another page
			ValidateManageLogic.navigateJS(out);
		} catch (EJBException ex) {
		}
	}
}