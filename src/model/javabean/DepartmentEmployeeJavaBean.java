package model.javabean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.entity.DepartmentEmployee;

public class DepartmentEmployeeJavaBean {

	private String dept_id = "";
	private Long emp_id = null;
	private Date from_date = null;
	private Date to_date = null;

	private String dept_id_error = "";
	private String emp_id_error = "";
	private String from_date_error = "";
	private String to_date_error = "";
	private String overall_error = "";
	private String navigateExpress = "";

	public DepartmentEmployeeJavaBean() {
		this.dept_id = "";
	}

	public DepartmentEmployeeJavaBean(DepartmentEmployee deptemp) {
		this.dept_id = deptemp.getId().getDepartmentId();
		this.emp_id = deptemp.getId().getEmployeeId();
		this.from_date = deptemp.getFromDate();
		this.to_date = deptemp.getToDate();
	}

	public boolean validate() {
		boolean allTrue = true;

		allTrue = validateId();

		if (from_date == null) {
			allTrue = false;
			from_date_error = "Please enter a valid from date.";
		}

		if (to_date == null) {
			allTrue = false;
			to_date_error = "Please enter a valid to date.";
		}

		if (from_date != null && to_date != null) {
			if (from_date.compareTo(to_date) > 0) {
				allTrue = false;
				from_date_error = "To date must large than or same with from date.";
				to_date_error = "To date must large than or same with from date.";
			}
		}
		return allTrue;
	}

	public boolean validateId() {
		boolean allTrue = true;

		if (dept_id == null || dept_id.equals("")) {
			dept_id = "";
			allTrue = false;
			dept_id_error = "Please enter a department ID. Department ID cannot be null.";
		} else if (dept_id.contains(" ")) {
			allTrue = false;
			dept_id_error = "Department ID cannot contain space!";
		} else if (dept_id.length() < 1 || dept_id.length() > 4) {
			allTrue = false;
			dept_id_error = "Please enter a valid department ID with 1-4 character.";
		}

		if (emp_id == null) {
			allTrue = false;
			emp_id_error = "Please enter a valid employee ID (digit).";
		}

		if (!allTrue)
			overall_error = "Please fix the error below.";

		return allTrue;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public Long getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		try {
			this.emp_id = Long.valueOf(emp_id);
		} catch (NumberFormatException e) {
		}
	}

	public Date getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		Date fd;
		try {
			fd = new SimpleDateFormat("yyyy-MM-dd").parse(from_date);
			this.from_date = new java.sql.Date(fd.getTime());
		} catch (ParseException e) {
		} catch (IllegalArgumentException i) {
		} catch (NullPointerException n) {
		}

	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		Date td;
		try {
			td = new SimpleDateFormat("yyyy-MM-dd").parse(to_date);
			this.to_date = new java.sql.Date(td.getTime());
		} catch (ParseException e) {
		} catch (IllegalArgumentException i) {
		} catch (NullPointerException n) {
		}

	}

	public String getDept_id_error() {
		return dept_id_error;
	}

	public void setDept_id_error(String dept_id_error) {
		this.dept_id_error = dept_id_error;
	}

	public String getEmp_id_error() {
		return emp_id_error;
	}

	public void setEmp_id_error(String emp_id_error) {
		this.emp_id_error = emp_id_error;
	}

	public String getFrom_date_error() {
		return from_date_error;
	}

	public void setFrom_date_error(String from_date_error) {
		this.from_date_error = from_date_error;
	}

	public String getTo_date_error() {
		return to_date_error;
	}

	public void setTo_date_error(String to_date_error) {
		this.to_date_error = to_date_error;
	}

	public String getOverall_error() {
		return overall_error;
	}

	public void setOverall_error(String overall_error) {
		this.overall_error = overall_error;
	}

	public String getNavigateExpress() {
		if (!navigateExpress.equals("")) {
			if (navigateExpress.equals("departmentemployee")) {
				return "<a href='MainServlet?target=departmentemployee&action=view' target='_blank'> Click me to go department-employee view</a>";
			} else {
				return "<a href='MainServlet?target=" + navigateExpress + "&action=add' target='_blank'> Click me to add "
						+ navigateExpress + "</a>";
			}
		} else
			return "";
	}

	public void setNavigateExpress(String navigateExpress) {
		this.navigateExpress = navigateExpress;
	}

}
