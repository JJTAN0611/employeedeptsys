package model.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the department database table.
 * 
 */

@Entity
@NamedQuery(name = "Department.findbyId", query = "SELECT d FROM Department d WHERE d.id = :id")
@NamedQuery(name = "Department.findbyName", query = "SELECT d FROM Department d WHERE LOWER(deptName) LIKE LOWER(:name)")
@Table(name="department", schema="employees")
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="dept_name")
	private String deptName;

	//bi-directional many-to-one association to DepartmentEmployee
	@JsonIgnore
	@OneToMany(mappedBy="department")
	private List<DepartmentEmployee> departmentEmployees;

	public Department() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<DepartmentEmployee> getDepartmentEmployees() {
		return this.departmentEmployees;
	}

	public void setDepartmentEmployees(List<DepartmentEmployee> departmentEmployees) {
		this.departmentEmployees = departmentEmployees;
	}

	public DepartmentEmployee addDepartmentEmployee(DepartmentEmployee departmentEmployee) {
		getDepartmentEmployees().add(departmentEmployee);
		departmentEmployee.setDepartment(this);

		return departmentEmployee;
	}

	public DepartmentEmployee removeDepartmentEmployee(DepartmentEmployee departmentEmployee) {
		getDepartmentEmployees().remove(departmentEmployee);
		departmentEmployee.setDepartment(null);

		return departmentEmployee;
	}

}