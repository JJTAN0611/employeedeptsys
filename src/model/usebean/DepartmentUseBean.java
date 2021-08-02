package model.usebean;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import utilities.LoggingGeneral;

public class DepartmentUseBean {
	private String id;
	private String dept_name;
	private Hashtable<String, String> errors;

	public boolean validate(HttpServletRequest request) {
		LoggingGeneral logger = (LoggingGeneral) request.getServletContext().getAttribute("log");
		logger.setContentPoints(request, "Hi"+dept_name+"|"+id);
		boolean allTrue = true;
		System.out.println("aa"+dept_name);
		if (id==null||id.equals("")) {
			id="";
			allTrue = false;
			errors.put("id", "Please enter a ID.");
		} else if (id.length() < 1 || id.length() > 4) {
			allTrue = false;
			errors.put("id", "Please enter 1-4 character ID");
		}

		if (dept_name==null||dept_name.equals("")) {
			dept_name="";
			allTrue = false;
			errors.put("dept_name", "Please enter a department name.");
		} else if (dept_name.length() < 1 || dept_name.length() > 40) {
			allTrue = false;
			errors.put("dept_name", "Please enter department name within 40 character");
		}
		return allTrue;
	}

	public String getErrorMsg(String s) {
		String errorMsg = (String) errors.get(s.trim());
		return (errorMsg == null) ? "" : errorMsg;
	}

	public DepartmentUseBean() {
		this.id = "";
		this.dept_name = "";
		errors = new Hashtable<String, String>();
	}
	
	public DepartmentUseBean(String id) {
		this.id = id;
		this.dept_name = "";
		errors = new Hashtable<String, String>();
	}

	public DepartmentUseBean(String id, String dept_name) {
		this.id = id;
		this.dept_name = dept_name;
		errors = new Hashtable<String, String>();
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

	public void setErrors(String key, String msg) {
		errors.put(key, msg);
	}

}
