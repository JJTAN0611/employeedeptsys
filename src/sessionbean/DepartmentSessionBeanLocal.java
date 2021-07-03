package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import model.entity.Department;
import model.entity.Employee;

@Local
public interface DepartmentSessionBeanLocal {
	public List<Department> getAllDepartment() throws EJBException;

	public Department findDepartment(String id) throws EJBException;

	public List<Department> readDepartment(int currentPage, int recordsPerPage, String keyword,String direction) throws EJBException;

	public int getNumberOfRows(String keyword) throws EJBException;

	public void updateDepartment(String[] s) throws EJBException;

	public void deleteDepartment(String id) throws EJBException;

	public void addDepartment(String[] s) throws EJBException;
}
