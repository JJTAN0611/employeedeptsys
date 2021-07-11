<%@page import="java.util.List"%>
<%@page import="model.entity.Department"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	Department dept = (Department) request.getAttribute("dept");
%>
<%@ include file="header.jsp"%>

<div class="container top-first">
	<h1>
		<div class="badge bg-warning text-dark text-wrap large col-12">
			<div class="row">
				<div class="text-start col">
					Department Record
					<div class="badge bg-light text-primary text-wrap">Update</div>
				</div>
			</div>
		</div>
	</h1>

	<br> <br>
	<hr>
	<br> <br>
</div>

<div class="container">
	<form method="post" action="MainServlet" >
		<input type="hidden" name="table" value="department" /> 
		<input type="hidden" name="action" value="update" /> 
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department ID:</label>
			<div class="col-8">
				<input type="text" class="form-control" placeholder="Enter name"
					name="id" value="<%=dept.getId()%>" readonly="readonly">
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department name:</label>
			<div class="col-8">
				<input type="text" class="form-control" placeholder="Enter name"
					name="dept_name" value="<%=dept.getDeptName()%>">
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end"></label>
			<div class="col-8">
				<input type="submit" class="btn btn-primary" name="action" value="update">
			</div>
		</div>
	</form>
</div>

<%@ include file="footer.jsp"%>
