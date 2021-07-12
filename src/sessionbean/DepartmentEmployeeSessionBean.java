package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.entity.Department;
import model.entity.DepartmentEmployee;
import model.entity.DepartmentEmployeePK;
import model.entity.Employee;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;

/**
 * Session Bean implementation class EmployeeSessionBean
 */
@Stateless
public class DepartmentEmployeeSessionBean implements DepartmentEmployeeSessionBeanLocal {
	@EJB
	private DepartmentSessionBeanLocal a;
	@EJB
	private EmployeeSessionBeanLocal b;
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
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT * FROM employees.department_employee order by department_id "+direction, DepartmentEmployee.class);
			start = currentPage * recordsPerPage - recordsPerPage;
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.department_employee  WHERE concat(department_id,employee_id,from_date,to_date) LIKE ? order by department_id "+direction,
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
					"SELECT COUNT(*) AS totalrow from employees.department_employee WHERE concat(department_id,employee_id,from_date,to_date) LIKE ?");
			q.setParameter(1, "%" + keyword + "%");
		}
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}

	public DepartmentEmployee findDepartmentEmployee(String[] id) throws EJBException {
		// Write some codes here…
		Query q = em.createNamedQuery("DepartmentEmployee.findbyId");
		q.setParameter("id", new DepartmentEmployeePK(String.valueOf(id[0]),Long.valueOf(id[1])));
		
		return (DepartmentEmployee) q.getSingleResult();
	}

	public void updateDepartmentEmployee(String[] s) throws EJBException {
		// Write some codes here…
		Date from_date = null;
		Date to_date = null;
		try {
			from_date = new SimpleDateFormat("yyyy-MM-dd").parse(s[2]);
			to_date = new SimpleDateFormat("yyyy-MM-dd").parse(s[3]);
		} catch (Exception ex) {
		}
		
		java.sql.Date fd = new java.sql.Date(from_date.getTime());
		java.sql.Date td = new java.sql.Date(to_date.getTime());
	

		DepartmentEmployeePK depk = new DepartmentEmployeePK(s[0],Long.valueOf(s[1]));
		DepartmentEmployee de = new DepartmentEmployee();
		de.setId(depk);
		de.setFromDate(fd);
		de.setToDate(td);
		em.merge(de);
	}

	public void deleteDepartmentEmployee(String[] id) throws EJBException {
		// Write some codes here…
		DepartmentEmployee de = findDepartmentEmployee(id);
		em.remove(de);
	}

	public void addDepartmentEmployee(String[] s) throws EJBException {
		// Write some codes here…
		Date from_date = null;
		Date to_date = null;
		try {
			from_date = new SimpleDateFormat("yyyy-MM-dd").parse(s[2]);
			to_date = new SimpleDateFormat("yyyy-MM-dd").parse(s[3]);
		} catch (Exception ex) {
		}
		java.sql.Date fd = new java.sql.Date(from_date.getTime());
		java.sql.Date td = new java.sql.Date(to_date.getTime());
	
		DepartmentEmployeePK depk = new DepartmentEmployeePK(s[0],Long.valueOf(s[1]));
		DepartmentEmployee de = new DepartmentEmployee();
		de.setId(depk);
		de.setFromDate(fd);
		de.setToDate(td);
		em.persist(de);
	}
}
