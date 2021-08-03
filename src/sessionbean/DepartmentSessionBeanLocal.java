package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import model.entity.Department;
import model.entity.Employee;
import model.usebean.DepartmentUseBean;

@Local
public interface DepartmentSessionBeanLocal {
	public List<Department> getAllDepartment() throws EJBException;

	public Department findDepartment(String id) throws EJBException;

	public List<Department> readDepartment(String direction) throws EJBException;

	public int getNumberOfRows() throws EJBException;

	public boolean updateDepartment(DepartmentUseBean dup) throws EJBException;

	public boolean deleteDepartment(DepartmentUseBean dup) throws EJBException;

	public void addDepartment(DepartmentUseBean dup) throws EJBException;
}
