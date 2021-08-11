package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.postgresql.util.PSQLException;

import model.entity.Employee;
import model.javabean.EmployeeJavaBean;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJBException;

/**
 * Session Bean implementation class EmployeeSessionBean
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanLocal {

	@PersistenceContext(unitName = "TanJingJie18ACB04560")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public EmployeeSessionBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Object[]> getEmployeeReport(String keyword, String direction) throws EJBException {
		Query q = null;
		// For report use. Using object instead of entity class is to minimize the
		// heaviness of computing
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT * FROM employees.employee order by id " + direction);
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.employee WHERE lower(concat(id,' ',first_name,' ',last_name,' ',gender,' ',hire_date,' ',birth_date)) LIKE lower(?) order by id "
							+ direction);
			q.setParameter(1, "%" + keyword + "%");
		}

		try {
			List<Object[]> results = q.getResultList();
			return results;
		} catch (NoResultException n) {
			return null;
		}
	}

	@Override
	public List<Employee> readEmployee(int currentPage, int recordsPerPage, String keyword, String direction)
			throws EJBException {
		// Get the list of employee for pagination
		Query q = null;
		int start = 0;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT * FROM employees.employee order by id " + direction, Employee.class);
			start = currentPage * recordsPerPage - recordsPerPage;
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.employee WHERE lower(concat(id,' ',first_name,' ',last_name,' ',gender,' ',hire_date,' ',birth_date)) LIKE lower(?) order by id "
							+ direction,
					Employee.class);
			start = currentPage * recordsPerPage - recordsPerPage;
			q.setParameter(1, "%" + keyword + "%");
		}

		try {
			List<Employee> results = q.setFirstResult(start).setMaxResults(recordsPerPage).getResultList();
			return results;
		} catch (NoResultException n) {
			return null;
		}

	}

	@Override
	public int getNumberOfRows(String keyword) throws EJBException {
		// Get the number of row for a search key
		Query q = null;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM employees.employee");
		} else {
			q = em.createNativeQuery(
					"SELECT COUNT(id) AS totalrow  from employees.employee WHERE lower(concat(id,' ',first_name,' ',last_name,' ',gender,' ',hire_date,' ',birth_date)) LIKE lower(?)");
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
	public Employee findEmployee(Long id) throws EJBException {
		// Find a record based on ids
		Query q = em.createNamedQuery("Employee.findbyId");

		try {
			q.setParameter("id", id);
			return (Employee) q.getSingleResult();
		} catch (NoResultException | NumberFormatException e) {
			return null;
		}
	}

	@Override
	public Employee getEmployeeByName(String name) throws EJBException {
		// Find a record based on name for quick search use
		// only first result will be shown
		Query q = em.createNamedQuery("Employee.findbyName");

		try {
			q.setParameter("name", "%" + name + "%");
			return (Employee) q.setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (NoResultException n) {
			return null;
		}
	}

	@Override
	public Long addEmployee(EmployeeJavaBean eub) throws EJBException {
		// add record with java bean
		// return the added employee primary key(auto)
		// No pk constraint

		Employee e = new Employee();
		e.setFirstName(eub.getFirst_name());
		e.setLastName(eub.getLast_name());
		e.setGender(eub.getGender());
		e.setBirthDate(eub.getBirth_date());
		e.setHireDate(eub.getHire_date());
		em.persist(e);
		return e.getId();
	}

	@Override
	public boolean updateEmployee(EmployeeJavaBean eub) throws EJBException {
		// update record with given javabean
		// do find first, avoid directly use the id, sometimes may not exist and will
		// become "add" automatically, if detect return false
		Employee e = findEmployee(eub.getId());
		if (e == null)
			return false;
		e.setFirstName(eub.getFirst_name());
		e.setLastName(eub.getLast_name());
		e.setGender(eub.getGender());
		e.setBirthDate(eub.getBirth_date());
		e.setHireDate(eub.getHire_date());
		em.merge(e);
		return true;
	}

	@Override
	public boolean deleteEmployee(EmployeeJavaBean eub) throws EJBException, PSQLException {
		// delete record with given javabean (extract the id)
		// if attempt to delete empty record return false
		// try and catch surround(not the following) when calling this function,
		// checking for foreign key constraint

		Employee e = findEmployee(eub.getId());

		// checking for not exist department
		if (e == null)
			return false;

		// checking for foreign key constraint
		// department_employee table check, is the most important
		Query q = em.createNativeQuery(
				"SELECT COUNT(*) AS totalrow FROM employees.department_employee de WHERE de.employee_id = ?");
		q.setParameter(1, e.getId() );
		if (((BigInteger) q.getSingleResult()).intValue() > 0) {
			throw new PSQLException("violates foreign key constraint, \"department_employee\"", null);
		}
		
		//Other table (Not in this system)
		//salary table
		Query q1 = em.createNativeQuery(
				"SELECT COUNT(*) AS totalrow FROM employees.salary s WHERE s.employee_id = ?");
		q1.setParameter(1, e.getId() );
		if (((BigInteger) q1.getSingleResult()).intValue() > 0) {
			throw new PSQLException("violates foreign key constraint, \"salary\"", null);
		}
		// title table
		Query q2 = em.createNativeQuery(
				"SELECT COUNT(*) AS totalrow FROM employees.title t WHERE t.employee_id = ?");
		q2.setParameter(1, e.getId() );
		if (((BigInteger) q2.getSingleResult()).intValue() > 0) {
			throw new PSQLException("violates foreign key constraint, \"title\"", null);
		}
		//manager
		Query q3 = em.createNativeQuery(
				"SELECT COUNT(*) AS totalrow FROM employees.department_manager dm WHERE dm.employee_id = ?");
		q3.setParameter(1, e.getId() );
		if (((BigInteger) q3.getSingleResult()).intValue() > 0) {
			throw new PSQLException("violates foreign key constraint, \"department_manager\"", null);
		}
		
		em.remove(e);
		return true;

	}

}
