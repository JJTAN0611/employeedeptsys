package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import model.entity.Employee;
import model.usebean.EmployeeUseBean;

@Local
public interface EmployeeSessionBeanLocal {
	public List<Object[]> getEmployeeReport(String keyword,String direction) throws EJBException;

	public Employee findEmployee(Long id) throws EJBException;

	public List<Employee> readEmployee(int currentPage, int recordsPerPage, String keyword,String direction) throws EJBException;

	public int getNumberOfRows(String keyword) throws EJBException;

	public boolean updateEmployee(EmployeeUseBean eub) throws EJBException;

	public boolean deleteEmployee(EmployeeUseBean eub) throws EJBException;

	public void addEmployee(EmployeeUseBean eub) throws EJBException;
}
