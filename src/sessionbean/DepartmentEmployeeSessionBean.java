package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.postgresql.util.PSQLException;

import model.entity.DepartmentEmployee;
import model.entity.DepartmentEmployeePK;
import model.javabean.DepartmentEmployeeJavaBean;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJBException;

/**
 * Session Bean implementation class EmployeeSessionBean
 */
@Stateless
public class DepartmentEmployeeSessionBean implements DepartmentEmployeeSessionBeanLocal {

	@PersistenceContext(unitName = "TanJingJie18ACB04560")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public DepartmentEmployeeSessionBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Object[]> getDepartmentEmployeeReport(String keyword, String direction) throws EJBException {
		// For report use. Using object instead of entity class is to minimize the
		// heaviness of computing
		Query q = null;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery(
					"SELECT de.department_id, d.dept_name, de.employee_id, e.first_name, e.last_name, de.from_date, de.to_date "
							+ "FROM employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id " + "ORDER BY department_id "
							+ direction + ", de.employee_id " + direction);
		} else {
			q = em.createNativeQuery(
					"SELECT de.department_id, d.dept_name, de.employee_id, e.first_name, e.last_name, de.from_date, de.to_date "
							+ "FROM employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id "
							+ "AND lower(concat(de.department_id,' ',d.dept_name,' ', de.employee_id,' ', e.first_name,' ',e.last_name,' ',de.from_date,' ',de.to_date)) "
							+ "LIKE lower(?) ORDER BY de.department_id " + direction + ", de.employee_id " + direction);

			q.setParameter(1, "%" + keyword + "%");
		}
		try {
			List<Object[]> results = q.getResultList();
			return results;
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public Integer[] getDepartmentEmployeeInvolvedSummary(String keyword) throws EJBException {
		// For report use. Using object instead of entity class is to minimize the
		// heaviness of computing
		Integer[] result = new Integer[2];
		Query q1 = null;
		Query q2 = null;
		if (keyword.isEmpty()) {
			q1 = em.createNativeQuery("SELECT COUNT(DISTINCT de.department_id) FROM employees.department_employee de ");
			q2 = em.createNativeQuery("SELECT COUNT(DISTINCT de.employee_id) FROM employees.department_employee de ");
		} else {
			q1 = em.createNativeQuery(
					"SELECT COUNT(DISTINCT de.department_id) FROM employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id "
							+ "AND lower(concat(de.department_id,' ',d.dept_name,' ', de.employee_id,' ', e.first_name,' ',e.last_name,' ',de.from_date,' ',de.to_date)) "
							+ "LIKE lower(?)");
			q1.setParameter(1, "%" + keyword + "%");
			q2 = em.createNativeQuery(
					"SELECT COUNT(DISTINCT de.employee_id) FROM employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id "
							+ "AND lower(concat(de.department_id,' ',d.dept_name,' ', de.employee_id,' ', e.first_name,' ',e.last_name,' ',de.from_date,' ',de.to_date)) "
							+ "LIKE lower(?)");
			q2.setParameter(1, "%" + keyword + "%");
		}
		try {
			result[0] = ((BigInteger) q1.getSingleResult()).intValue();
		} catch (NoResultException n) {
			result[0] = 0;
		}
		try {
			result[1] = ((BigInteger) q2.getSingleResult()).intValue();
		} catch (NoResultException n) {
			result[1] = 0;
		}

		return result;
	}

	@Override
	public List<DepartmentEmployee> readDepartmentEmployee(int currentPage, int recordsPerPage, String keyword,
			String direction) throws EJBException {
		// Get the list of departmentemployee for pagination
		Query q = null;
		int start = 0;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT * FROM employees.department_employee de order by department_id "
					+ direction + " , de.employee_id " + direction, DepartmentEmployee.class);

			start = currentPage * recordsPerPage - recordsPerPage;
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id "
							+ "AND lower(concat(de.department_id,' ',d.dept_name,' ', de.employee_id,' ', e.first_name,' ',e.last_name,' ',de.from_date,' ',de.to_date)) "
							+ "LIKE lower(?) order by de.department_id " + direction + " , de.employee_id " + direction,
					DepartmentEmployee.class);
			q.setParameter(1, "%" + keyword + "%");
			start = currentPage * recordsPerPage - recordsPerPage;

		}
		try {
			List<DepartmentEmployee> results = q.setFirstResult(start).setMaxResults(recordsPerPage).getResultList();
			return results;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public int getNumberOfRows(String keyword) throws EJBException {
		// Get the number of row for a search key
		Query q = null;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM employees.department_employee");
		} else {
			q = em.createNativeQuery(
					"SELECT COUNT(*) AS totalrow from employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id "
							+ "AND lower(concat(de.department_id,' ',d.dept_name,' ', de.employee_id,' ', e.first_name,' ',e.last_name,' ',de.from_date,' ',de.to_date)) "
							+ "LIKE lower(?)");
			q.setParameter(1, "%" + keyword + "%");
		}

		try {
			BigInteger results = (BigInteger) q.getSingleResult();
			return results.intValue();
		} catch (NoResultException n) {
			return 0;
		}

	}

	@Override
	public DepartmentEmployee findDepartmentEmployee(String dept_id, Long emp_id) throws EJBException {
		// Find a record based on ids
		Query q = em.createNamedQuery("DepartmentEmployee.findbyId");
		try {
			q.setParameter("id", new DepartmentEmployeePK(dept_id, emp_id));
			return (DepartmentEmployee) q.getSingleResult();
		} catch (NoResultException | NumberFormatException e) {
			return null;
		}

	}

	@Override
	public void addDepartmentEmployee(DepartmentEmployeeJavaBean deub) throws EJBException, PSQLException {
		// add record with javabean
		// a try and catch is surround by calling this action (check foreign key
		// constraint and duplicate)

		// Check existence in other table(FK)
		// Check department
		Query q1 = em.createNativeQuery(
				"SELECT COUNT(*) AS totalrow FROM employees.department d WHERE d.id = ?");
		q1.setParameter(1,  deub.getDept_id() );
		if (((BigInteger) q1.getSingleResult()).intValue() == 0) {
			throw new PSQLException("violates foreign key constraint, \"department\"", null);
		}
		//Check employee
		Query q2 = em.createNativeQuery(
				"SELECT COUNT(*) AS totalrow FROM employees.employee e WHERE e.id = ?");
		q2.setParameter(1, deub.getEmp_id() );
		if (((BigInteger) q2.getSingleResult()).intValue() == 0) {
			throw new PSQLException("violates foreign key constraint, \"employee\"", null);
		}

		// Check duplicate
		if (findDepartmentEmployee(deub.getDept_id(), deub.getEmp_id()) != null)
			throw new PSQLException("duplicate key value violates unique constraint", null);

		DepartmentEmployeePK depk = new DepartmentEmployeePK(deub.getDept_id(), deub.getEmp_id());
		DepartmentEmployee de = new DepartmentEmployee();
		de.setId(depk);
		de.setFromDate(deub.getFrom_date());
		de.setToDate(deub.getTo_date());
		em.persist(de);
	}
	
	@Override
	public boolean updateDepartmentEmployee(DepartmentEmployeeJavaBean deub) throws EJBException {
		// update record with given javabean
		// do find first, avoid directly use the id, sometimes may not exist and will
		// become "add" automatically, if detect return false
		DepartmentEmployee de = findDepartmentEmployee(deub.getDept_id(), deub.getEmp_id());
		if (de == null)
			return false;
		de.setFromDate(deub.getFrom_date());
		de.setToDate(deub.getTo_date());
		em.merge(de);
		return true;
	}

	@Override
	public boolean deleteDepartmentEmployee(DepartmentEmployeeJavaBean deub) throws EJBException {
		// delete record with given javabean (extract the id)

		DepartmentEmployee de = findDepartmentEmployee(deub.getDept_id(), deub.getEmp_id());
		if (de == null)
			return false;

		em.remove(de);
		return true;

	}

}
