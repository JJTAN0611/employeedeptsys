package sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import model.entity.Department;
import model.entity.DepartmentEmployee;
import model.entity.DepartmentEmployeePK;
import model.entity.Employee;
import model.usebean.DepartmentEmployeeUseBean;

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
	@PersistenceContext(unitName = "TanJingJie1804560")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public DepartmentEmployeeSessionBean() {
		// TODO Auto-generated constructor stub
	}

	public List<Object[]> getDepartmentEmployeeReport(String keyword,String direction) throws EJBException {
		// Write some codes here…
		Query q = null;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT * FROM employees.department_employee order by department_id " + direction);
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id "
							+ "AND lower(concat(de.department_id,' ',d.dept_name,' ', de.employee_id,' ', e.first_name,' ',e.last_name,' ',de.from_date,' ',de.to_date)) "
							+ "LIKE lower(?) order by de.department_id " + direction);
			q.setParameter(1, "%" + keyword + "%");
		}
		
		return q.getResultList();
	}

	public List<DepartmentEmployee> readDepartmentEmployee(int currentPage, int recordsPerPage, String keyword,
			String direction) throws EJBException {
		// Write some codes here…
		Query q = null;
		int start = 0;
		if (keyword.isEmpty()) {
			q = em.createNativeQuery("SELECT * FROM employees.department_employee order by department_id " + direction,
					DepartmentEmployee.class);

			start = currentPage * recordsPerPage - recordsPerPage;
		} else {
			q = em.createNativeQuery(
					"SELECT * from employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id "
							+ "AND lower(concat(de.department_id,' ',d.dept_name,' ', de.employee_id,' ', e.first_name,' ',e.last_name,' ',de.from_date,' ',de.to_date)) "
							+ "LIKE lower(?) order by de.department_id " + direction,
					DepartmentEmployee.class);
			q.setParameter(1, "%" + keyword + "%");
			start = currentPage * recordsPerPage - recordsPerPage;

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
					"SELECT COUNT(*) AS totalrow from employees.department_employee de, employees.department d, employees.employee e "
							+ "WHERE de.department_id=d.id AND de.employee_id=e.id "
							+ "AND lower(concat(de.department_id,' ',d.dept_name,' ', de.employee_id,' ', e.first_name,' ',e.last_name,' ',de.from_date,' ',de.to_date)) "
							+ "LIKE lower(?)");
			q.setParameter(1, "%" + keyword + "%");
		}
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}

	public DepartmentEmployee findDepartmentEmployee(String dept_id, Long emp_id) throws EJBException {
		// Write some codes here…
		Query q = em.createNamedQuery("DepartmentEmployee.findbyId");
		try {
			q.setParameter("id", new DepartmentEmployeePK(dept_id, emp_id));
			return (DepartmentEmployee) q.getSingleResult();
		} catch (NoResultException | NumberFormatException e) {
			return null;
		}

	}

	public boolean updateDepartmentEmployee(DepartmentEmployeeUseBean deub) throws EJBException {
		// Write some codes here…
		DepartmentEmployee de = findDepartmentEmployee(deub.getDept_id(), deub.getEmp_id());
		if (de == null)
			return false;
		de.setFromDate(deub.getFrom_date());
		de.setToDate(deub.getTo_date());
		em.merge(de);
		return true;
	}

	public boolean deleteDepartmentEmployee(DepartmentEmployeeUseBean deub) throws EJBException {
		// Write some codes here…
		DepartmentEmployee de = findDepartmentEmployee(deub.getDept_id(), deub.getEmp_id());
		if (de == null)
			return false;
		em.remove(de);
		return true;
	}

	public void addDepartmentEmployee(DepartmentEmployeeUseBean deub) throws EJBException {
		// Write some codes here…

		DepartmentEmployeePK depk = new DepartmentEmployeePK(deub.getDept_id(), deub.getEmp_id());
		DepartmentEmployee de = new DepartmentEmployee();
		de.setId(depk);
		de.setFromDate(deub.getFrom_date());
		de.setToDate(deub.getTo_date());
		em.persist(de);
	}
}
