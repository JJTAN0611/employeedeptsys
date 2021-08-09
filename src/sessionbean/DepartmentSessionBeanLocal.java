package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import model.entity.Department;
import model.javabean.DepartmentJavaBean;

@Local
public interface DepartmentSessionBeanLocal {
	public List<Object[]> getDepartmentReport(String direction) throws EJBException;

	public String getAutoId() throws EJBException;

	public Department findDepartment(String id) throws EJBException;

	public Department getDepartmentByName(String name) throws EJBException;

	public List<Department> readDepartment(String direction) throws EJBException;

	public int getNumberOfRows() throws EJBException;

	public boolean updateDepartment(DepartmentJavaBean dup) throws EJBException;

	public boolean deleteDepartment(DepartmentJavaBean dup) throws EJBException;

	public void addDepartment(DepartmentJavaBean dup) throws EJBException;
}
