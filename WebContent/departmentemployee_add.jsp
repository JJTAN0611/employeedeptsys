<%@page import="java.util.List"%>
<%@page import="model.entity.DepartmentEmployee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ include file="header.jsp"%>

<div class="container top-first">
	<div class="badge bg-primary text-light text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Department Employee Record
				<div class="badge bg-light text-success text-wrap">Add</div>
			</div>
		</div>
	</div>
	<br> <br>
	<h1>
		<center>Please fill in the following details.</center>
	</h1>
	<hr>
	<br> <br>
</div>

<div class="container wow bounceIn" data-wow-duration="1s" data-wow-delay="0.2s">
	<form method="post" action="MainServlet">
		<input type="hidden" name="table" value="departmentemployee" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department ID:</label>
			<div class="col-8">
				<input type="text" class="form-control" data-bs-toggle='collapse'
					data-bs-target='#dept_content'
					name="dept_id"
					readonly="readonly">

				<div id="dept_content" class="collapse">
					<hr>
					<ul class='list-group'>
						<li class='list-group-item list-group-item-action'>Department
							Name: &emsp;</li>
					</ul>
				</div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee ID:</label>
			<div class="col-8">
				<input type="text" class="form-control"
					name="emp_id">
			</div>
		</div>
		<br>
		
		<div class="form-group row">
			<label class="control-label col-3 text-end">Birth Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="from_date">
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Hire Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="to_date">
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end"></label>
			<div class="col-8">
				<input type="submit" class="btn btn-info" name="action"
					value="add">
			</div>
		</div>
	</form>
</div>

<%@ include file="footer.jsp"%>
