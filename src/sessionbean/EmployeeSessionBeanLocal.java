package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import org.postgresql.util.PSQLException;

import model.entity.Employee;
import model.javabean.EmployeeJavaBean;

@Local
public interface EmployeeSessionBeanLocal {
	public List<Object[]> getEmployeeReport(String keyword, String direction) throws EJBException;

	public Employee findEmployee(Long id) throws EJBException;

	public Employee getEmployeeByName(String name) throws EJBException;

	public List<Employee> readEmployee(int currentPage, int recordsPerPage, String keyword, String direction)
			throws EJBException;

	public int getNumberOfRows(String keyword) throws EJBException;

	public boolean updateEmployee(EmployeeJavaBean eub) throws EJBException;

	public boolean deleteEmployee(EmployeeJavaBean eub) throws EJBException, PSQLException;

	public Long addEmployee(EmployeeJavaBean eub) throws EJBException;
}
