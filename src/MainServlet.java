
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("404.html");

		String action = ServletForwardValidate.actionValidate(request, response);
		String table = ServletForwardValidate.tableValidate(request, response);
		
		if(action!=null && table!=null)
		{
			
			switch (action) {
			case "view":
				if(ServletForwardValidate.departmentValidate(request,response))
					dispatcher = request.getRequestDispatcher("DepartmentPaginationServlet");
				break;
			case "edit":
			case "remove":
				dispatcher = request.getRequestDispatcher("DepartmentController");
				break;
			default:
				break;
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