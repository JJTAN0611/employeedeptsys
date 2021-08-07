package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import model.entity.DepartmentEmployee;
import model.entity.Employee;
import model.usebean.DepartmentEmployeeUseBean;

@Local
public interface DepartmentEmployeeSessionBeanLocal {
	public List<Object[]> getDepartmentEmployeeReport(String keyword,String direction) throws EJBException;

	public DepartmentEmployee findDepartmentEmployee(String dept_id, Long emp_id) throws EJBException;

	public List<DepartmentEmployee> readDepartmentEmployee(int currentPage, int recordsPerPage, String keyword,String direction) throws EJBException;

	public int getNumberOfRows(String keyword) throws EJBException;

	public boolean updateDepartmentEmployee(DepartmentEmployeeUseBean deub) throws EJBException;

	public boolean deleteDepartmentEmployee(DepartmentEmployeeUseBean deub) throws EJBException;

	public void addDepartmentEmployee(DepartmentEmployeeUseBean deub) throws EJBException;
}
