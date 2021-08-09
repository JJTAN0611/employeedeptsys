package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import model.entity.DepartmentEmployee;
import model.javabean.DepartmentEmployeeJavaBean;

@Local
public interface DepartmentEmployeeSessionBeanLocal {
	public List<Object[]> getDepartmentEmployeeReport(String keyword, String direction) throws EJBException;

	public Integer[] getDepartmentEmployeeSummary(String keyword) throws EJBException;

	public DepartmentEmployee findDepartmentEmployee(String dept_id, Long emp_id) throws EJBException;

	public List<DepartmentEmployee> readDepartmentEmployee(int currentPage, int recordsPerPage, String keyword,
			String direction) throws EJBException;

	public int getNumberOfRows(String keyword) throws EJBException;

	public boolean updateDepartmentEmployee(DepartmentEmployeeJavaBean deub) throws EJBException;

	public boolean deleteDepartmentEmployee(DepartmentEmployeeJavaBean deub) throws EJBException;

	public void addDepartmentEmployee(DepartmentEmployeeJavaBean deub) throws EJBException;
}
