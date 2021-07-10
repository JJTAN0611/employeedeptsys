
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.Department;
import model.entity.Employee;
import sessionbean.DepartmentSessionBeanLocal;
import sessionbean.EmployeeSessionBeanLocal;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private DepartmentSessionBeanLocal empbean;

	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("404.jsp");

		String action = ServletForwardValidate.action(request, response);
		String table = ServletForwardValidate.table(request, response);
		

		if(action!=null && table!=null)
		{
			if(table.compareTo("department")==0)
			switch (action) {
			case "view":
				if(ServletForwardValidate.departmentView(request,response))
					dispatcher = request.getRequestDispatcher("DepartmentPaginationServlet");
				break;
			case "edit":
			case "remove":
					if(ServletForwardValidate.departmentUpdate(request, response)) {
						dispatcher = request.getRequestDispatcher("DepartmentController");
						System.out.print("On here"+request.getAttribute("id"));
					}
				break;
			default:
				break;
			}
			else if(table.compareTo("employee")==0) {
				switch (action) {
				case "view":
					if(ServletForwardValidate.departmentView(request,response))
						dispatcher = request.getRequestDispatcher("EmployeePaginationServlet");
					break;
				case "edit":
				case "remove":
						if(ServletForwardValidate.employeeUpdate(request, response))
							dispatcher = request.getRequestDispatcher("EmployeeController");
					break;
				default:
					break;
				}
			}
		}

		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}