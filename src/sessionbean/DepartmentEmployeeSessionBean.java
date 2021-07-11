package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.entity.DepartmentEmployee;
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
public class DepartmentEmployeeSessionBean implements DepartmentEmployeeSessionBeanLocal {

	// Write some codes here…
	@PersistenceContext(unitName = "DepartmentEmployeeWebApp")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public DepartmentEmployeeSessionBean() {
		// TODO Auto-generated constructor stub
	}

	public List<DepartmentEmployee> getAllDepartmentEmployees() throws EJBException {
		// Write some codes here…
		return em.createNamedQuery("DepartmentEmployee.findAll").getResultList();
	}

	public List<DepartmentEmployee> readDepartmentEmployee(int currentPage, int recordsPerPage, String keyword,String direction) throws EJBException {
		// Write some codes here…
		Query q = null;
		int start = 0;
		direction = " " + direction;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT * FROM employees.department_employee", DepartmentEmployee.class);
			start = currentPage * recordsPerPage - recordsPerPage;
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.department_employee",
					DepartmentEmployee.class);
			start = currentPage * recordsPerPage - recordsPerPage;
			q.setParameter(1, "%" + keyword + "%");
		}
		List<DepartmentEmployee> results = q.setFirstResult(start).setMaxResults(recordsPerPage).getResultList();
		return results;
	}

	public int getNumberOfRows(String keyword) throws EJBException {
		// Write some codes here…
		Query q = null;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM employees.department_employee");
		} else {
			q = em.createNativeQuery(
					"SELECT COUNT(*) AS totalrow from employees.department_employee");
			q.setParameter(1, "%" + keyword + "%");
		}
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}

	public DepartmentEmployee findDepartmentEmployee(String[] id) throws EJBException {
		// Write some codes here…
		Query q = em.createNamedQuery("Employee.findbyId");
		q.setParameter("id", id);
		return (DepartmentEmployee) q.getSingleResult();
	}

	public void updateDepartmentEmployee(String[] s) throws EJBException {
		// Write some codes here…
		Date dob = null;
		Date hd = null;
		DepartmentEmployee e = findDepartmentEmployee(s);
		try {
			dob = new SimpleDateFormat("yyyy-MM-dd").parse(s[4]);
			hd = new SimpleDateFormat("yyyy-MM-dd").parse(s[5]);
		} catch (Exception ex) {
		}
		java.sql.Date DOB = new java.sql.Date(dob.getTime());
		java.sql.Date HD = new java.sql.Date(hd.getTime());
		em.merge(e);
	}

	public void deleteDepartmentEmployee(String[] id) throws EJBException {
		// Write some codes here…
		DepartmentEmployee e = findDepartmentEmployee(id);
		em.remove(e);
	}

	public void addDepartmentEmployee(String[] s) throws EJBException {
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
		e.setId(Long.parseLong(s[0]));
		e.setFirstName(s[1]);
		e.setLastName(s[2]);
		e.setGender(s[3]);
		e.setBirthDate(DOB);
		e.setHireDate(HD);
		em.persist(e);
	}
}
