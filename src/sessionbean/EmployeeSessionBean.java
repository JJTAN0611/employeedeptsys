package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.entity.Employee;

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
public class EmployeeSessionBean implements EmployeeSessionBeanLocal {

	// Write some codes here…
	@PersistenceContext(unitName = "DepartmentEmployeeWebApp")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public EmployeeSessionBean() {
		// TODO Auto-generated constructor stub
	}

	public List<Employee> getAllEmployees() throws EJBException {
		// Write some codes here…
		return em.createNamedQuery("Employee.findAll").getResultList();
	}

	public List<Employee> readEmployee(int currentPage, int recordsPerPage, String keyword,String direction) throws EJBException {
		// Write some codes here…
		Query q = null;
		int start = 0;
		direction = " " + direction;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT * FROM employees.employee order by id"+direction, Employee.class);
			start = currentPage * recordsPerPage - recordsPerPage;
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.employee WHERE concat(id,first_name,last_name,gender) LIKE ? order by id"+direction,
					Employee.class);
			start = currentPage * recordsPerPage - recordsPerPage;
			q.setParameter(1, "%" + keyword + "%");
		}
		List<Employee> results = q.setFirstResult(start).setMaxResults(recordsPerPage).getResultList();
		return results;
	}

	public int getNumberOfRows(String keyword) throws EJBException {
		// Write some codes here…
		Query q = null;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM employees.employee");
		} else {
			q = em.createNativeQuery(
					"SELECT COUNT(*) AS totalrow from employees.employee WHERE concat(id,first_name,last_name,gender) LIKE ?");
			q.setParameter(1, "%" + keyword + "%");
		}
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}

	public Employee findEmployee(String id) throws EJBException {
		// Write some codes here…
		Query q = em.createNamedQuery("Employee.findbyId");
		q.setParameter("id", Long.valueOf(id));
		return (Employee) q.getSingleResult();
	}

	public void updateEmployee(String[] s) throws EJBException {
		// Write some codes here…
		Date dob = null;
		Date hd = null;
		Employee e = findEmployee(s[0]);
		try {
			dob = new SimpleDateFormat("yyyy-MM-dd").parse(s[4]);
			hd = new SimpleDateFormat("yyyy-MM-dd").parse(s[5]);
		} catch (Exception ex) {
		}
		java.sql.Date DOB = new java.sql.Date(dob.getTime());
		java.sql.Date HD = new java.sql.Date(hd.getTime());
		e.setFirstName(s[1]);
		e.setLastName(s[2]);
		e.setGender(s[3]);
		e.setBirthDate(DOB);
		e.setHireDate(HD);
		em.merge(e);
	}

	public void deleteEmployee(String id) throws EJBException {
		// Write some codes here…
		Employee e = findEmployee(id);
		em.remove(e);
	}

	public void addEmployee(String[] s) throws EJBException {
		// Write some codes here…
		Date dob = null;
		Date hd = null;
		try {
			dob = new SimpleDateFormat("yyyy-MM-dd").parse(s[4]);
			hd = new SimpleDateFormat("yyyy-MM-dd").parse(s[5]);
		} catch (Exception ex) {
		}
		Employee e = new Employee();
		java.sql.Date DOB = new java.sql.Date(dob.getTime());
		java.sql.Date HD = new java.sql.Date(hd.getTime());
		e.setFirstName(s[1]);
		e.setLastName(s[2]);
		e.setGender(s[3]);
		e.setBirthDate(DOB);
		e.setHireDate(HD);
		em.persist(e);
	}
}
