package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.postgresql.util.PSQLException;

import com.ibm.wsdl.util.StringUtils;

import model.entity.DepartmentEmployee;
import model.entity.Employee;
import model.entity.Employee;
import sessionbean.DepartmentEmployeeSessionBeanLocal;
import sessionbean.EmployeeSessionBeanLocal;
import utilities.LoggingGeneral;

@WebServlet("/DepartmentEmployeeController")
public class DepartmentEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private DepartmentEmployeeSessionBeanLocal deptempbean;

	public DepartmentEmployeeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = (String) request.getAttribute("action");

		try {
			if (action.compareTo("add") == 0) {
				RequestDispatcher req;
				req = request.getRequestDispatcher("departmentemployee_add.jsp");
				req.forward(request, response);
			} else if (action.compareTo("update") == 0) {
				RequestDispatcher req;
				String id[] = { (String) request.getAttribute("dept_id"), (String) request.getAttribute("emp_id") };
				DepartmentEmployee deptemp = deptempbean.findDepartmentEmployee(id);
				request.setAttribute("deptemp", deptemp);
				req = request.getRequestDispatcher("departmentemployee_update.jsp");
				req.forward(request, response);
			} else {
				RequestDispatcher req;
				String id[] = { (String) request.getAttribute("dept_id"), (String) request.getAttribute("emp_id") };

				DepartmentEmployee deptemp = deptempbean.findDepartmentEmployee(id);
				request.setAttribute("deptemp", deptemp);
				req = request.getRequestDispatcher("departmentemployee_remove.jsp");
				req.forward(request, response);
			}

		} catch (EJBException ex) {
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		logger.setEntryPoints(request);

		String action = (String) request.getAttribute("action");
		try {

			if (action.compareTo("add") == 0) {
				String[] s = { (String) request.getAttribute("dept_id"), (String) request.getAttribute("emp_id"),
						(String) request.getParameter("from_date"), (String) request.getParameter("to_date") };

				deptempbean.addDepartmentEmployee(s);
				ValidateManageLogic.navigateJS(response.getWriter(), "departmentemployee");

				logger.setContentPoints(request, "Success " + action + " --> ID:" + s[0]);
			} else if (action.compareTo("delete") == 0) {
				String[] id = { (String) request.getAttribute("dept_id"), (String) request.getAttribute("emp_id") };

				deptempbean.deleteDepartmentEmployee(id);
				ValidateManageLogic.navigateJS(response.getWriter(), "departmentemployee");

				logger.setContentPoints(request, "Success " + action + " --> ID:" + id[0] + "|" + id[1]);
			} else if (action.compareTo("update") == 0) {
				String[] s = { (String) request.getAttribute("dept_id"), (String) request.getAttribute("emp_id"),
						(String) request.getParameter("from_date"), (String) request.getParameter("to_date") };
				System.out.println(s[0] + s[1]);
				System.out.println(s[2]);

				deptempbean.updateDepartmentEmployee(s);
				ValidateManageLogic.navigateJS(response.getWriter(), "departmentemployee");
				logger.setContentPoints(request, "Success " + action + " --> ID:" + s[0]);
			}
		} catch (EJBTransactionRolledbackException rollback) {
			ValidateManageLogic.printErrorNotice(response.getWriter(), "Duplicate record occur!! ",
					"departmentemployee");
			logger.setContentPoints(request, "Unsuccess --> " + action + rollback.getStackTrace().toString());
		} catch (EJBException invalid) {
			if (invalid.toString().contains("NullPointerException"))
				ValidateManageLogic.printErrorNotice(response.getWriter(), "Empty input!! " + invalid.toString(),
						"departmentemployee");
			else
				ValidateManageLogic.printErrorNotice(response.getWriter(), "Invalid input!!. " + invalid.toString(),
						"departmentemployee");
			logger.setContentPoints(request, "Unsuccess --> " + action + invalid.getStackTrace().toString());
		} finally {
			logger.setExitPoints(request);
		}

	}

}