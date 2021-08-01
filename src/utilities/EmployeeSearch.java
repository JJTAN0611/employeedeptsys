package utilities;

import java.util.Date;

public class EmployeeSearch {
	String id;
	String name;
	String gender;
	Date dob_from;
	Date dob_to;
	Date hire_date_from;
	Date hire_date_to;
	
	public EmployeeSearch(String id, String name, String gender, Date dob_from, Date dob_to, Date hire_date_from,
			Date hire_date_to) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.dob_from = dob_from;
		this.dob_to = dob_to;
		this.hire_date_from = hire_date_from;
		this.hire_date_to = hire_date_to;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob_from() {
		return dob_from;
	}
	public void setDob_from(Date dob_from) {
		this.dob_from = dob_from;
	}
	public Date getDob_to() {
		return dob_to;
	}
	public void setDob_to(Date dob_to) {
		this.dob_to = dob_to;
	}
	public Date getHire_date_from() {
		return hire_date_from;
	}
	public void setHire_date_from(Date hire_date_from) {
		this.hire_date_from = hire_date_from;
	}
	public Date getHire_date_to() {
		return hire_date_to;
	}
	public void setHire_date_to(Date hire_date_to) {
		this.hire_date_to = hire_date_to;
	}
}
