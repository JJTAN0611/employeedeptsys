package model.javabean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.entity.Employee;

public class EmployeeJavaBean {
	
	private Long id = null;
	private String first_name = "";
	private String last_name = "";
	private String gender = "";
	private Date birth_date = null;
	private Date hire_date = null;

	//this error is used for display
	private String id_error = "";
	private String first_name_error = "";
	private String last_name_error = "";
	private String gender_error = "";
	private String birth_date_error = "";
	private String hire_date_error = "";
	private String overall_error = "";
	
	//when there is a link to resolve the error for user
	private String navigateExpress = "";

	public EmployeeJavaBean() {
	}

	public EmployeeJavaBean(Employee e) {
		this.id = e.getId();
		this.first_name = e.getFirstName();
		this.last_name = e.getLastName();
		this.gender = e.getGender();
		this.birth_date = (Date) e.getBirthDate();
		this.hire_date = (Date) e.getHireDate();
	}

	// for add or update use
	public boolean validate() {
		boolean allTrue = true;
		
		//cannot do id validation here, employee add is auto id

		if (first_name == null || first_name.equals("")) {
			first_name = "";
			allTrue = false;
			first_name_error = "Please enter a first name. First name cannot be null.";
		} else if (first_name.trim().length() == 0) {
			allTrue = false;
			first_name_error = "First name cannot contain only space.";
		} else if (first_name.length() < 1 || first_name.length() > 14) {
			allTrue = false;
			first_name_error = "Please enter a first name within 14 character.";
		}else if (first_name.contains("\"")) {
			allTrue = false;
			first_name_error = "Last name cannot contain double quote";
		}

		if (last_name == null || last_name.equals("")) {
			last_name = "";
			allTrue = false;
			last_name_error = "Please enter a last name. Last name cannot be null.";
		} else if (last_name.trim().length() == 0) {
			allTrue = false;
			last_name_error = "First name cannot contain only space.";
		} else if (last_name.length() < 1 || last_name.length() > 14) {
			allTrue = false;
			last_name_error = "Please enter last name within 16 character.";
		}else if (last_name.contains("\"")) {
			allTrue = false;
			last_name_error = "Last name cannot contain double quote";
		}

		if (gender == null || gender.equals("")) {
			gender = "";
			allTrue = false;
			gender_error = "Please select a gender. Gender cannot be null.";
		} else if (!(gender.compareTo("M") == 0 || gender.compareTo("F") == 0)) {
			allTrue = false;
			gender_error = "Please select only 'M' or 'F'. Dont change the source html!";
		}

		if (birth_date == null) {
			allTrue = false;
			birth_date_error = "Please enter a valid birth date.";
		}

		if (hire_date == null) {
			allTrue = false;
			hire_date_error = "Please enter a valid hire date.";
		}

		if (hire_date != null && birth_date != null) {
			if (hire_date.compareTo(birth_date) <= 0) {
				allTrue = false;
				hire_date_error = "Hire date must large than birth date.";
				birth_date_error = "Hire date must large than birth date.";
			}
		}

		if (!allTrue)
			overall_error = "Please fix the error below.";

		return allTrue;
	}

	// for update and delete use
	public boolean validateId() {
		boolean allTrue = true;
		if (id == null) {
			allTrue = false;
			id_error = "Please enter a ID.";
		}
		if (!allTrue)
			overall_error = "Please fix the error below.";
		return allTrue;
	}

	public String getChecked(String g) {
		if (gender.compareTo("M") == 0 && g.compareTo("M") == 0)
			return "checked";
		else if (gender.compareTo("F") == 0 && g.compareTo("F") == 0)
			return "checked";
		return "";
	}

	public Long getId() {
		return id;
	}

	public void setId(String id) {
		try {
			this.id = Long.valueOf(id);
		} catch (NumberFormatException e) {
		}

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
		} catch (IllegalArgumentException i) {
		} catch (NullPointerException n) {
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
		} catch (IllegalArgumentException i) {
		} catch (NullPointerException n) {
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

	public String getNavigateExpress() {
		if (!navigateExpress.equals(""))
			return "<a href='MainServlet?target=" + navigateExpress + "&action=view' target='_blank'> Click me to go.</a>";
		else
			return "";
	}

	public void setNavigateExpress(String navigateExpress) {
		this.navigateExpress = navigateExpress;
	}

}
