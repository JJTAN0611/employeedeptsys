package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import controller.ValidateManageLogic;
import model.entity.Department;
import model.entity.Employee;
import model.usebean.DepartmentUseBean;

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

	// Write some codes here…
	@PersistenceContext(unitName = "TanJingJie1804560")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public DepartmentSessionBean() {
		// TODO Auto-generated constructor stub
	}

	public List<Department> getAllDepartment() throws EJBException {
		// Write some codes here…
		return em.createNamedQuery("Department.findAll").getResultList();
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
		q.setParameter("id", String.valueOf(id));
		Department d;
		try {
			d = (Department) q.getSingleResult();
			return d;
		} catch (NoResultException e) {
			d = new Department();
			d.setId(id);
			d.setDeptName("null");
			return d;
		}
	}

	public List<Department> getDepartmentByName(String name) throws EJBException {
		// Write some codes here…
		Query q = em.createNamedQuery("Department.findbyName");
		q.setParameter("dept_name", name);
		return q.getResultList();
	}

	public void updateDepartment(String[] s) throws EJBException {
		// Write some codes here…
		Department d = findDepartment(s[0]);
		d.setDeptName(s[1]);
		em.merge(d);
	}

	public void deleteDepartment(String id) throws EJBException {
		// Write some codes here…
		Department d = findDepartment(id);
		em.remove(d);
	}

	public void addDepartment(DepartmentUseBean dup) throws EJBException {
		// Write some codes here…
		Department d = new Department();
		d.setId(dup.getId());
		d.setDeptName(dup.getDept_name());
		em.persist(d);
	}
}
