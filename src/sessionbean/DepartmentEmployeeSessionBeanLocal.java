package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import model.entity.DepartmentEmployee;
import model.entity.Employee;

@Local
public interface DepartmentEmployeeSessionBeanLocal {
	public List<DepartmentEmployee> getAllDepartmentEmployees() throws EJBException;

	public DepartmentEmployee findDepartmentEmployee(String[] id) throws EJBException;

	public List<DepartmentEmployee> readDepartmentEmployee(int currentPage, int recordsPerPage, String keyword,String direction) throws EJBException;

	public int getNumberOfRows(String keyword) throws EJBException;

	public void updateDepartmentEmployee(String[] s) throws EJBException;

	public void deleteDepartmentEmployee(String[] id) throws EJBException;

	public void addDepartmentEmployee(String[] s) throws EJBException;
}
