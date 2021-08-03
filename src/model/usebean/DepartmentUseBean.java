package model.usebean;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import utilities.LoggingGeneral;

public class DepartmentUseBean {
	private String id="";
	private String dept_name="";

	private String id_error = "";
	private String dept_name_error = "";
	private String overall_error = "";

	public DepartmentUseBean() {
		this.id = "";
	}

	public DepartmentUseBean(String id) {
		this.id = id;
	}

	public DepartmentUseBean(String id, String dept_name) {
		this.id = id;
		this.dept_name = dept_name;
	}

	public boolean validate() {
		boolean allTrue = true;
		if (id == null || id.equals("")) {
			id = "";
			allTrue = false;
			id_error = "Please enter a ID.";
		} else if (id.length() < 1 || id.length() > 4) {
			allTrue = false;
			id_error = "Please enter 1-4 character ID";
		}

		if (dept_name == null || dept_name.equals("")) {
			dept_name = "";
			allTrue = false;
			dept_name_error = "Please enter a department name.";
		} else if (dept_name.length() < 1 || dept_name.length() > 40) {
			allTrue = false;
			dept_name_error = "Please enter department name within 40 character";
		}
		return allTrue;
	}

	public boolean validateId() {
		boolean allTrue = true;
		System.out.println("aa" + dept_name);
		if (id == null || id.equals("")) {
			id = "";
			allTrue = false;
			id_error = "Please enter a ID.";
		} else if (id.length() < 1 || id.length() > 4) {
			allTrue = false;
			id_error = "Please enter 1-4 character ID";
		}
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

}
