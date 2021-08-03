package model.usebean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import model.entity.Employee;
import utilities.LoggingGeneral;

public class EmployeeUseBean {
	private Long id = null;

	private String first_name = "";
	private String last_name = "";
	private String gender = "";
	private Date birth_date = null;
	private Date hire_date = null;

	private String id_error = "";
	private String first_name_error = "";
	private String last_name_error = "";
	private String gender_error = "";
	private String birth_date_error = "";
	private String hire_date_error = "";
	private String overall_error = "";

	public EmployeeUseBean() {
		id = null;
	}

	public EmployeeUseBean(Long id) {
		this.id = id;
	}

	public EmployeeUseBean(Employee e) {
		this.id = e.getId();
		this.first_name = e.getFirstName();
		this.last_name = e.getLastName();
		this.gender = e.getGender();
		this.birth_date = (Date) e.getBirthDate();
		this.hire_date = (Date) e.getHireDate();
	}

	public boolean validate() {
		boolean allTrue = true;

		if (first_name == null || first_name.equals("")) {
			first_name = "";
			allTrue = false;
			first_name_error = "Please enter a first name.";
		} else if (first_name.length() < 1 || first_name.length() > 14) {
			allTrue = false;
			first_name_error = "Please enter first name within 14 character";
		}

		if (last_name == null || last_name.equals("")) {
			last_name = "";
			allTrue = false;
			last_name_error = "Please enter a last name.";
		} else if (first_name.length() < 1 || last_name.length() > 14) {
			allTrue = false;
			last_name_error = "Please enter last name within 16 character";
		}

		if (gender == null || gender.equals("")) {
			gender = "";
			allTrue = false;
			gender_error = "Please enter a last name.";
		} else if (!(gender.compareTo("M") == 0 || gender.compareTo("F") == 0)) {
			allTrue = false;
			gender_error = "Please enter only 'M' or 'F'";
		}

		if (birth_date == null) {
			allTrue = false;
			birth_date_error = "Please enter a birth date";
		}

		if (hire_date == null) {
			allTrue = false;
			hire_date_error = "Please enter a hire date";
		}

		if (hire_date.compareTo(birth_date) <= 0) {
			allTrue = false;
			hire_date_error = "Hire date must large than birth date";
			birth_date_error = "Hire date must large than birth date";
		}

		return allTrue;
	}

	public String getCheckedM() {
		if(gender.compareTo("M")==0)
			return "checked";
		return "";
	}
	public String getCheckedF() {
		if(gender.compareTo("F")==0)
			return "checked";
		return "";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(String id) {
		this.id = Long.valueOf(id);
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		Date dob;
		try {
			dob = new SimpleDateFormat("yyyy-MM-dd").parse(birth_date);
			this.birth_date = new java.sql.Date(dob.getTime());
		} catch (ParseException e) {
		}
	
	}

	public Date getHire_date() {
		return hire_date;
	}

	public void setHire_date(String hire_date) {
		Date hd;
		try {
			hd = new SimpleDateFormat("yyyy-MM-dd").parse(hire_date);
			this.hire_date = new java.sql.Date(hd.getTime());
		} catch (ParseException e) {
		}

	}

	public String getFirst_name_error() {
		return first_name_error;
	}

	public void setFirst_name_error(String first_name_error) {
		this.first_name_error = first_name_error;
	}

	public String getLast_name_error() {
		return last_name_error;
	}

	public void setLast_name_error(String last_name_error) {
		this.last_name_error = last_name_error;
	}

	public String getGender_error() {
		return gender_error;
	}

	public void setGender_error(String gender_error) {
		this.gender_error = gender_error;
	}

	public String getBirth_date_error() {
		return birth_date_error;
	}

	public void setBirth_date_error(String birth_date_error) {
		this.birth_date_error = birth_date_error;
	}

	public String getHire_date_error() {
		return hire_date_error;
	}

	public void setHire_date_error(String hire_date_error) {
		this.hire_date_error = hire_date_error;
	}

	public String getOverall_error() {
		return overall_error;
	}

	public void setOverall_error(String overall_error) {
		this.overall_error = overall_error;
	}

	public String getId_error() {
		return id_error;
	}

	public void setId_error(String id_error) {
		this.id_error = id_error;
	}

}
