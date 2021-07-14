<%@page import="java.util.List"%>
<%@page import="model.entity.DepartmentEmployee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	DepartmentEmployee deptemp = (DepartmentEmployee) request.getAttribute("deptemp");
%>
<%@ include file="header.jsp"%>

<div class="container top-first">
	<div class="badge bg-primary text-light text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Department Employee Record
				<div class="badge bg-light text-danger text-wrap">Remove</div>
			</div>
		</div>
	</div>
	<br> <br>
	<h1>
		<center>You are removing following details.</center>
	</h1>
	<hr>
	<center>Click the ID input textbox to see detail.</center>
	<br>
</div>

<div class="container wow bounceInDown" data-wow-duration="1.5s"
	data-wow-delay="0.2s">
	<form method="post" action="MainServlet">
		<input type="hidden" name="table" value="departmentemployee" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department ID:</label>

			<div class="col-8">
				<input type="text" class="form-control" data-bs-toggle='collapse'
					data-bs-target='.id<%=deptemp.getId().toString().substring(34)%>'
					name="dept_id" value="<%=deptemp.getDepartment().getId()%>"
					readonly="readonly">

				<div class="collapse id<%=deptemp.getId().toString().substring(34)%>">
					<hr>
					<ul class='list-group'>
						<li class='list-group-item list-group-item-dark list-group-item-action'>Department
							Name: &emsp;<%=deptemp.getDepartment().getDeptName()%></li>
					</ul>
				</div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee ID:</label>
			<div class="col-8">
				<input type="text" class="form-control" data-bs-toggle='collapse'
					data-bs-target='.id<%=deptemp.getId().toString().substring(34)%>'
					name="emp_id" value="<%=deptemp.getEmployee().getId()%>"
					readonly="readonly">
				<div class="collapse id<%=deptemp.getId().toString().substring(34)%>">
				<hr>
					<ul class='list-group'>
						<li class='list-group-item list-group-item-dark list-group-item-action'>First
							Name: &emsp;<%=deptemp.getEmployee().getFirstName()%></li>
						<li class='list-group-item list-group-item-dark list-group-item-action'>First
							Name: &emsp;<%=deptemp.getEmployee().getLastName()%></li>
						<li class='list-group-item list-group-item-dark list-group-item-action'>Gender:
							&emsp;<%=(deptemp.getEmployee().getGender() == "M" ? "Male" : "Female")%></li>
						<li class='list-group-item list-group-item-dark list-group-item-action'>Birth
							date: &emsp;<%=deptemp.getEmployee().getBirthDate()%></li>
						<li class='list-group-item list-group-item-dark list-group-item-action'>Hire date:
							&emsp;<%=deptemp.getEmployee().getHireDate()%></li>
					</ul>
				</div>
			</div>
		</div>
		<br>

		<div class="form-group row">
			<label class="control-label col-3 text-end">Birth Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="from_date"
					value=<%=deptemp.getFromDate()%> readonly="readonly">
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Hire Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="to_date"
					value=<%=deptemp.getToDate()%> readonly="readonly">
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end"></label>
			<div class="col-8">
				<input type="submit" class="btn btn-danger" name="action"
					value="delete">
			</div>
		</div>
	</form>
</div>

<%@ include file="footer.jsp"%>
