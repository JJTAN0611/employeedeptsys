package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.entity.Department;
import model.entity.Employee;
import model.usebean.EmployeeUseBean;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
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
	@PersistenceContext(unitName = "TanJingJie1804560")
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
			q = em.createNativeQuery("SELECT * FROM employees.employee order by id "+direction, Employee.class);
			start = currentPage * recordsPerPage - recordsPerPage;
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.employee WHERE lower(concat(id,' ',first_name,' ',last_name,' ',gender,' ',hire_date,' ',birth_date)) LIKE lower(?) order by id "+direction,
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
					"SELECT COUNT(id) AS totalrow  from employees.employee WHERE lower(concat(id,' ',first_name,' ',last_name,' ',gender,' ',hire_date,' ',birth_date)) LIKE lower(?)");
			q.setParameter(1, "%" + keyword + "%");
		}
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}

	public Employee findEmployee(Long id) throws EJBException {
		// Write some codes here…
		Query q = em.createNamedQuery("Employee.findbyId");

		try {
			q.setParameter("id", id);
			return (Employee) q.getSingleResult();
		}catch (NoResultException | NumberFormatException e ) {
			return null;
		}
	}

	public boolean updateEmployee(EmployeeUseBean eub) throws EJBException {
		// Write some codes here…
		Employee e = findEmployee(eub.getId());
		if(e==null)
			return false;
		e.setFirstName(eub.getFirst_name());
		e.setLastName(eub.getLast_name());
		e.setGender(eub.getGender());
		e.setBirthDate(eub.getBirth_date());
		e.setHireDate(eub.getHire_date());
		em.merge(e);
		return true;
	}

	public boolean deleteEmployee(EmployeeUseBean eub) throws EJBException {
		// Write some codes here…
		Employee e = findEmployee(eub.getId());
		if(e==null)
			return false;
		em.remove(e);
		return true;
	}

	public void addEmployee(EmployeeUseBean eub) throws EJBException {
		// Write some codes here…
		Employee e = new Employee();
		e.setFirstName(eub.getFirst_name());
		e.setLastName(eub.getLast_name());
		e.setGender(eub.getGender());
		e.setBirthDate(eub.getBirth_date());
		e.setHireDate(eub.getHire_date());
		em.persist(e);
	}

}
