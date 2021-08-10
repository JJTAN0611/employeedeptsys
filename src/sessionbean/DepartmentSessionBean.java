package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.postgresql.util.PSQLException;

import model.entity.Department;
import model.entity.DepartmentEmployee;
import model.javabean.DepartmentJavaBean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;

/**
 * Session Bean implementation class EmployeeSessionBean
 */
@Stateless
public class DepartmentSessionBean implements DepartmentSessionBeanLocal {

	@PersistenceContext(unitName = "TanJingJie18ACB04560")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public DepartmentSessionBean() {
		// TODO Auto-generated constructor stub
	}

	//
	public List<Object[]> getDepartmentReport(String direction) throws EJBException {
		// For report use. Using object instead of entity class is to minimize the
		// heaviness of computing
		Query q = null;
		q = em.createNativeQuery("SELECT * FROM employees.department order by id " + direction);
		try {
			List<Object[]> results = q.getResultList();
			return results;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public String getAutoId() throws EJBException {
		// Get the list of number which is the id start from dxxx, where xxx is number
		// (The xxx will be return as object in list)
		Query q = null;
		q = em.createNativeQuery(
				"SELECT SUBSTRING(d.id,2,3) FROM employees.department d WHERE d.id~'^d[0-9][0-9][0-9]' ORDER BY 1");

		try {
			List<Object> idNumber = q.getResultList();

			if (idNumber.size() >= 999)
				return "allUsed";

			int largest = Integer.valueOf(idNumber.get(idNumber.size() - 1).toString());
			if (largest == 999) {
				// No have space after the largest, loop and check the remain space
				for (int i = 1; i <= idNumber.size(); i++) {
					int temp = Integer.valueOf(idNumber.get(i - 1).toString());
					if (i != temp)
						return "d" + String.format("%03d", i);
				}
			} else {
				// Still have space after the largest
				return "d" + String.format("%03d", largest + 1);
			}

		} catch (Exception e) {
			return "allUsed";
		}
		return "allUsed";
	}

	public List<Department> readDepartment(String direction) throws EJBException {
		// Get the list of department for pagination
		Query q = null;
		q = em.createNativeQuery("SELECT * FROM employees.department order by id " + direction, Department.class);

		try {
			List<Department> results = q.getResultList();
			return results;
		} catch (NoResultException e) {
			return null;
		}

	}

	public int getNumberOfRows() throws EJBException {
		// Get the number of row for a search key
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM employees.department");

		try {
			BigInteger results = (BigInteger) q.getSingleResult();
			return results.intValue();
		} catch (NoResultException n) {
			return 0;
		}
	}

	public Department findDepartment(String id) throws EJBException {
		// Find a record based on id
		Query q = em.createNamedQuery("Department.findbyId");

		try {
			q.setParameter("id", String.valueOf(id));
			return (Department) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Department getDepartmentByName(String name) throws EJBException {
		// get department by name, for quick search use
		Query q = em.createNamedQuery("Department.findbyName");
		try {
			q.setParameter("name", "%" + name + "%");
			return (Department) q.setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean updateDepartment(DepartmentJavaBean dup) throws EJBException, PSQLException {
		// update record with given javabean
		// do find first, avoid directly use the id, sometimes may not exist and will
		// become "add" automatically, if detect return false
		// surround by try catch when calling this function, checking for unique
		// constraint
		Department d = findDepartment(dup.getId());
		if (d == null)
			return false;

		// Check pk constraint
		Query q2 = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM employees.department d WHERE d.dept_name = '"
				+ dup.getDept_name() + "'");
		if (((BigInteger) q2.getSingleResult()).intValue() > 0) {
			throw new PSQLException("duplicate key value violates unique constraint, dept_name", null);
		}

		d.setDeptName(dup.getDept_name());
		em.merge(d);
		return true;
	}

	public boolean deleteDepartment(DepartmentJavaBean dup) throws EJBException, PSQLException {
		// delete record with given javabean (extract the id)
		// if detect delete null record, return false
		// try and catch surround(not the following) when calling this function,
		// checking for foreign key constraint

		Department d = findDepartment(dup.getId());

		// checking for not exist department
		if (d == null)
			return false;

		// checking for foreign key constraint
		Query q = em.createNativeQuery(
				"SELECT COUNT(*) AS totalrow FROM employees.department_employee de WHERE de.department_id = '"
						+ d.getId() + "'");

		// checking for foreign key constraint
		if (((BigInteger) q.getSingleResult()).intValue() > 0) {
			throw new PSQLException("violates foreign key constraint", null);
		}

		em.remove(d);

		return true;

	}

	public void addDepartment(DepartmentJavaBean dup) throws EJBException, PSQLException {
		// add record with use bean
		// surround by try catch when calling this function, checking for unique
		// constraint

		// Check pk constraint
		Query q1 = em.createNativeQuery(
				"SELECT COUNT(*) AS totalrow FROM employees.department d WHERE d.id = '" + dup.getId() + "'");
		if (((BigInteger) q1.getSingleResult()).intValue() > 0) {
			throw new PSQLException("duplicate key value violates unique constraint, primary", null);
		}

		// Check unique constraint
		Query q2 = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM employees.department d WHERE d.dept_name = '"
				+ dup.getDept_name() + "'");
		if (((BigInteger) q2.getSingleResult()).intValue() > 0) {
			throw new PSQLException("duplicate key value violates unique constraint, dept_name", null);
		}

		Department d = new Department();
		d.setId(dup.getId());
		d.setDeptName(dup.getDept_name());
		em.persist(d);
	}

}
