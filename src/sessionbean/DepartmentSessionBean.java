package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.entity.Department;
import model.entity.DepartmentEmployee;
import model.usebean.DepartmentUseBean;
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
		} catch (Exception e) {
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
		} catch (Exception e) {
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

	public boolean updateDepartment(DepartmentUseBean dup) throws EJBException {
		// update record with given usebean
		Department d = findDepartment(dup.getId());
		if (d == null)
			return false;
		d.setDeptName(dup.getDept_name());
		em.merge(d);
		return true;
	}

	public boolean deleteDepartment(DepartmentUseBean dup) throws EJBException {
		// delete record with given usebean (extract the id)
		Department d = findDepartment(dup.getId());
		if (d == null)
			return false;
		em.remove(d);
		return true;
	}

	public void addDepartment(DepartmentUseBean dup) throws EJBException {
		// add record with use bean
		Department d = new Department();
		d.setId(dup.getId());
		d.setDeptName(dup.getDept_name());
		em.persist(d);
	}

}
