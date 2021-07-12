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
		<input type="hidden" name="table" value="departmentemployee" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department ID:</label>
			<div class="col-8">
				<input type="text" class="form-control"
					name="dept_id" value="<%=deptemp.getDepartment().getId()%>" readonly="readonly">
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee ID:</label>
			<div class="col-8">
				<input type="text" class="form-control"
					name="emp_id" value="<%=deptemp.getEmployee().getId()%>" readonly="readonly">
			</div>
		</div>
		<br>
		
		<div class="form-group row">
			<label class="control-label col-3 text-end">Birth Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="from_date"
					value=<%=deptemp.getFromDate()%> >
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Hire Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="to_date"
					value=<%=deptemp.getToDate()%>>
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
