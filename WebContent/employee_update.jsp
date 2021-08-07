<%@ page errorPage = "error.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="eub" type="model.usebean.EmployeeUseBean"
	scope="request" />
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
	<div class="text-danger text-center"><jsp:getProperty name="eub"
			property="overall_error" /></div>

	<br> <br>
</div>

<div class="container wow bounceInDown" data-wow-duration="1.5s"
	data-wow-delay="0.2s">
	<form method="post" action="MainServlet">
		<input type="hidden" name="target" value="employee" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee ID:</label>
			<div class="col-8">
				<input type="number" class="form-control" placeholder="Enter id"
					name="id"  value='<jsp:getProperty name="eub" property="id"/>' readonly="readonly">
				<div class="text-danger"><jsp:getProperty name="eub"
						property="id_error" /></div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee name:</label>
			<div class="col-4">
				<input type="text" class="form-control" placeholder="First name"
					name="first_name"
					value='<jsp:getProperty name="eub" property="first_name"/>'>
				<div class="text-danger"><jsp:getProperty name="eub"
						property="first_name_error" /></div>
			</div>
			<div class="col-4">
				<input type="text" class="form-control" placeholder="Last name"
					name="last_name"
					value='<jsp:getProperty name="eub" property="last_name"/>'>
				<div class="text-danger"><jsp:getProperty name="eub"
						property="last_name_error" /></div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Gender:</label>
			<div class="col-8">
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender"
						id="rmale" value="M" ${eub.getChecked("M")}> <label
						class="form-check-label" for="rmale">Male</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender"
						id="rfemale" value="F" ${eub.getChecked("F")}> <label
						class="form-check-label" for="rfemale">Female</label>
				</div>
				<div class="text-danger"><jsp:getProperty name="eub"
						property="gender_error" /></div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Birth Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="birth_date"
					value='<jsp:getProperty name="eub" property="birth_date"/>'>
				<div class="text-danger"><jsp:getProperty name="eub"
						property="birth_date_error" /></div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Hire Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="hire_date"
					value='<jsp:getProperty name="eub" property="hire_date"/>'>
				<div class="text-danger"><jsp:getProperty name="eub"
						property="hire_date_error" /></div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end"></label>
			<div class="col-8">
				<input type="submit" class="btn btn-success" name="action"
					value="update">
			</div>
		</div>
	</form>
</div>

<%@ include file="footer.jsp"%>
