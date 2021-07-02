package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import model.entity.Employee;

@Local
public interface EmployeeSessionBeanLocal {
	public List<Employee> getAllEmployees() throws EJBException;

	public Employee findEmployee(String id) throws EJBException;

	public List<Employee> readEmployee(int currentPage, int recordsPerPage, String keyword,String direction) throws EJBException;

	public int getNumberOfRows(String keyword) throws EJBException;

	public void updateEmployee(String[] s) throws EJBException;

	public void deleteEmployee(String id) throws EJBException;

	public void addEmployee(String[] s) throws EJBException;
}
