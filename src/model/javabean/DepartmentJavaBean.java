package model.javabean;

import model.entity.Department;

public class DepartmentJavaBean {
	private String id = "";
	private String dept_name = "";

	private String id_error = "";
	private String dept_name_error = "";
	private String overall_error = "";
	private String express = "";

	public DepartmentJavaBean() {
	}

	// For auto generate use
	// Only apply for this useBean
	public DepartmentJavaBean(String id) {
		this.id = id;
	}

	public DepartmentJavaBean(Department d) {
		this.id = d.getId();
		this.dept_name = d.getDeptName();
	}

	public boolean validate() {
		boolean allTrue = true;

		allTrue=validateId();

		if (dept_name == null || dept_name.equals("")) {
			dept_name = "";
			allTrue = false;
			dept_name_error = "Please enter a department name. Department name cannot be empty.";
		} else if (dept_name.trim().length() == 0) {
			allTrue = false;
			dept_name_error = "Department name cannot contain only space.";
		} else if (dept_name.length() < 1 || dept_name.length() > 40) {
			allTrue = false;
			dept_name_error = "Please enter department name within 40 character";
		} else if (dept_name.contains("\"")) {
			allTrue = false;
			dept_name_error = "Department name cannot contain double quote";
		}

		if (!allTrue)
			overall_error = "Please fix the error below.";
		return allTrue;
	}

	public boolean validateId() {
		boolean allTrue = true;
		if (id == null || id.equals("")) {
			id = "";
			allTrue = false;
			id_error = "Please enter an  ID. ID cannot be null.";
		} else if (id.contains(" ")) {
			allTrue = false;
			id_error = "ID cannot contain space!";
		} else if (id.length() < 1 || id.length() > 4) {
			allTrue = false;
			id_error = "Please enter an ID with 1-4 character.";
		}
		if (!allTrue)
			overall_error = "Please fix the error below.";
		return allTrue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getId_error() {
		return id_error;
	}

	public void setId_error(String id_error) {
		this.id_error = id_error;
	}

	public String getDept_name_error() {
		return dept_name_error;
	}

	public void setDept_name_error(String dept_name_error) {
		this.dept_name_error = dept_name_error;
	}

	public String getOverall_error() {
		return overall_error;
	}

	public void setOverall_error(String overall_error) {
		this.overall_error = overall_error;
	}

	public String getExpress() {
		if (!express.equals(""))
			return "<a href='MainServlet?target=" + express
					+ "&action=view' target='_blank'> Click me to go.</a>";
		else
			return "";
	}

	public void setExpress(String express) {
		this.express = express;
	}

}
