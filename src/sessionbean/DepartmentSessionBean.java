package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.entity.Department;
import model.entity.Employee;
import model.usebean.DepartmentUseBean;
import utilities.ControllerManagement;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Local;

/**
 * Session Bean implementation class EmployeeSessionBean
 */
@Stateless
public class DepartmentSessionBean implements DepartmentSessionBeanLocal {

	@PersistenceContext(unitName = "TanJingJie1804560")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public DepartmentSessionBean() {
		// TODO Auto-generated constructor stub
	}

	//
	public List<Object[]> getDepartmentReport(String direction) throws EJBException {
		Query q = null;
		q = em.createNativeQuery("SELECT * FROM employees.department order by id " + direction);

		return q.getResultList();
	}
	
	@Override
	public List<Object> getSortedDepartmentStartWithD() throws EJBException {
		Query q = null;
		q = em.createNativeQuery("SELECT SUBSTRING(d.id,2,3) FROM employees.department d WHERE d.id~'^d[0-9][0-9][0-9]' ORDER BY 1");
		return q.getResultList();
	}

	public List<Department> readDepartment(String direction) throws EJBException {
		// Write some codes here…
		Query q = null;
		q = em.createNativeQuery("SELECT * FROM employees.department order by id " + direction, Department.class);
		List<Department> results = q.getResultList();
		return results;
	}

	public int getNumberOfRows() throws EJBException {
		// Write some codes here…
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM employees.department");
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}

	public Department findDepartment(String id) throws EJBException {
		// Write some codes here…
		Query q = em.createNamedQuery("Department.findbyId");

		try {
			q.setParameter("id", String.valueOf(id));
			return (Department) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Department> getDepartmentByName(String name) throws EJBException {
		// Write some codes here…
		Query q = em.createNamedQuery("Department.findbyName");
		q.setParameter("dept_name", name);
		return q.getResultList();
	}

	public boolean updateDepartment(DepartmentUseBean dup) throws EJBException {
		// Write some codes here…
		Department d = findDepartment(dup.getId());
		if (d == null)
			return false;
		d.setDeptName(dup.getDept_name());
		em.merge(d);
		return true;
	}

	public boolean deleteDepartment(DepartmentUseBean dup) throws EJBException {
		// Write some codes here…
		Department d = findDepartment(dup.getId());
		if (d == null)
			return false;
		em.remove(d);
		return true;
	}

	public void addDepartment(DepartmentUseBean dup) throws EJBException {
		// Write some codes here…
		Department d = new Department();
		d.setId(dup.getId());
		d.setDeptName(dup.getDept_name());
		em.persist(d);
	}


}
