<%@page import="java.util.List"%>
<%@page import="model.entity.Department"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	Department dept = (Department) request.getAttribute("dept");
	if (dept == null) {
		dept = new Department();
		dept.setId("");
		dept.setDeptName("");
	}
%>
<%@ include file="header.jsp"%>

<div class="container top-first">
	<div class="badge bg-warning text-dark text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Department Record
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

<div class="container wow bounceInDown" data-wow-duration="1.5s"
	data-wow-delay="0.2s">
	<form method="post" action="MainServlet">
		<input type="hidden" name="target" value="department" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department ID:</label>
			<div class="col-8">
				<input id="id_placeholder" type="text" class="form-control"
					placeholder="Enter name" name="id" value="<%=dept.getId()%>"
					readonly='readonly'>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department name:</label>
			<div class="col-8">
				<input id="name_placeholder" type="text" class="form-control"
					placeholder="Enter name" name="dept_name"
					value="<%=dept.getDeptName()%>">
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
<!--
	<div class="input-group">
					<input id="id_placeholder" type="text" class="form-control"
						placeholder="Enter name" name="id" value="<%=dept.getId()%>"
						readonly='readonly'>
					<button class="btn btn-primary" type="button" id="getDept">Search</button>
				</div>
<!%=(dept.getId().compareTo("")!=0?"readonly='readonly'":"") %>
<script>
	$(document).ready(function() {
		$("#getDept").click(function() {
			$.ajax({
				url : 'MainServlet',
				type : 'GET',
				dataType : 'json',
				data : jQuery.param({ target:'department',action:'getDepartment',id: 'd001'}),
				success : function(data) {
					if (data === null) {
						alert("Invalid department id");
					} else {
						alert($.parseJSON(data));
						$("#id_placeholder").val(data.id);
						$("#name_placeholder").val(data.name);
					}
				},
				error : function() {
					alert('Invalid id no or an error occurred');
				}
			});
			return false;
		});
	});
</script>
  -->