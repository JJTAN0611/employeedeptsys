<%@ page errorPage = "error.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="dub" type="model.javabean.DepartmentJavaBean"
	scope="request" />

<%@ include file="header.jsp"%>

<div class="container top-first">
	<div class="badge bg-warning text-dark text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Department Record
				<div class="badge bg-light text-danger text-wrap">Delete</div>
			</div>
		</div>
	</div>


	<br> <br>
	<h1>
		<center>You are deleting following details.</center>
	</h1>
	<hr>
	<div class="text-danger text-center"><jsp:getProperty name="dub"
			property="overall_error" /></div>
	<br> <br>
</div>

<div class="container wow bounceInDown" data-wow-duration="1.5s"
	data-wow-delay="0.2s">
	<form method="post" action="MainServlet">
		<input type="hidden" name="target" value="department" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department ID:</label>
			<div class="col-8">
				<input type="text" class="form-control" placeholder="Enter name"
					name="id" value='<jsp:getProperty name="dub" property="id"/>'
					readonly="readonly">
				<div class="text-danger"><jsp:getProperty name="dub"
						property="id_error" />  ${dub.getExpress()}  </div>
			</div>

		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department name:</label>
			<div class="col-8">
				<input type="text" class="form-control" placeholder="Enter name"
					name="dept_name"
					value='<jsp:getProperty name="dub" property="dept_name"/>'
					readonly="readonly">
				<div class="text-danger"><jsp:getProperty name="dub"
						property="dept_name_error" /></div>
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
