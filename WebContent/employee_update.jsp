<%@page import="java.util.List"%>
<%@page import="model.entity.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	Employee emp = (Employee) request.getAttribute("emp");
%>
<%@ include file="header.jsp"%>

<div class="container top-first">
	<div class="badge bg-success text-light text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Employee Record
				<div class="badge bg-light text-primary text-wrap">Update</div>
			</div>
		</div>
	</div>
	<br> <br>
	<h1>
		<center>Please update the following details.</center>
	</h1>
	<hr>
	<br> <br>
</div>

<div class="container wow bounceInDown" data-wow-duration="1.5s" data-wow-delay="0.2s">
	<form method="post" action="MainServlet">
		<input type="hidden" name="table" value="employee" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee ID:</label>
			<div class="col-8">
				<input type="text" class="form-control" placeholder="Enter name"
					name="id" value="<%=emp.getId()%>" readonly="readonly">
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee name:</label>
			<div class="col-4">
				<input type="text" class="form-control" placeholder="First name"
					name="first_name" value="<%=emp.getFirstName()%>">
			</div>
			<div class="col-4">
				<input type="text" class="form-control" placeholder="Last name"
					name="last_name" value="<%=emp.getLastName()%>" >
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Gender:</label>
			<div class="col-8">
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio"
						name="gender" id="rmale" value="M"
						<%=emp.getGender().compareTo("M") == 0 ? "checked" : ""%> >
					<label class="form-check-label" for="rmale">Male</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio"
						name="gender" id="rfemale" value="F"
						<%=emp.getGender().compareTo("F") == 0 ? "checked" : ""%> >
					<label class="form-check-label" for="rfemale">Female</label>
				</div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Birth Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="birth_date"
					value=<%=emp.getBirthDate()%> >
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Hire Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="hire_date"
					value=<%=emp.getHireDate()%> >
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end"></label>
			<div class="col-8">
				<input type="submit" class="btn btn-primary" name="action"
					value="update">
			</div>
		</div>
	</form>
</div>

<%@ include file="footer.jsp"%>
