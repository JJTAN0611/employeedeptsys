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

<div class="container wow bounceIn" data-wow-duration="1s"
	data-wow-delay="0.2s">
	<form method="post" action="MainServlet">
		<input type="hidden" name="target" value="departmentemployee" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department ID:</label>
			<div class="col-8">
				<div class="input-group">
					<input id="dept_id" type="text" class="form-control" name="dept_id">
					<div class="input-group-append">
					<button id="checkdept" class="btn btn-info" data-bs-toggle='collapse' data-bs-target='#deptcontent' type="button"
							onclick="
								$('#checkdept').attr('class', 'btn btn-info spinner-border');
								 $.get('MainServlet?target=departmentemployee&action=getDepartment&id='+$('#dept_id').val(), function(data, status){
									  var aid = $.parseJSON(data);
									  if(aid.name=='null'){
										  $('#deptcontent').hide();
										  $('#deptname').html('null');
										  alert('Department ID: ['+$('#dept_id').val()+'] not exist.');
									  }else{
										$('#dept_id').val(aid.id);
									  	$('#dept_name').html('Department Name:&emsp;'+aid.name);
									  	$('#deptcontent').show();
									  }
										$('#checkdept').attr('class', 'btn btn-info');
									});
						">Check</button>
					</div>
				</div>

				<div id="deptcontent" class="collapse">
					<hr>
					<ul class='list-group'>
						<li id="dept_name" class='list-group-item list-group-item-action list-group-item-dark'>Department Name:&emsp;</li>
					</ul>
				</div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee ID:</label>
			<div class="col-8">
				<div class="input-group">
					<input id="emp_id" type="text" class="form-control" name="emp_id">
					<div class="input-group-append">
						<button id="checkemp" class="btn btn-info" data-bs-toggle='collapse' data-bs-target='#empcontent' type="button"
							onclick="
								$('#checkemp').attr('class', 'btn btn-info spinner-border');
						  $.get('MainServlet?target=departmentemployee&action=getEmployee&id='+$('#emp_id').val(), function(data, status){
							  var aid = $.parseJSON(data);
							  if(aid.first_name=='null'){
								  $('#empcontent').hide();
								  alert('Employee ID: ['+$('#emp_id').val()+'] not exist.');
								  $('#empfname').html('null');
								  $('#emplname').html('null');
								  $('#empgender').html('null');
								  $('#empbdate').html('null');
								  $('#emphdate').html('null');
							  }else{
								  $('#emp_id').val(aid.id);
							  	  $('#empfname').html('First name:&emsp;'+aid.first_name);
								  $('#emplname').html('Last name:&emsp;'+aid.last_name);
								  $('#empgender').html('Gender:&emsp;'+(aid.gender=='M'?'Male':'Female'));
								  $('#empbdate').html('Birth date:&emsp;'+aid.birth_date);
								  $('#emphdate').html('Hire date:&emsp;'+aid.hire_date);
							  	$('#empcontent').show();
							  }
							  $('#checkemp').attr('class', 'btn btn-info');
							  });
						">Check</button>
					</div>
				</div>
				<div id="empcontent" class="collapse">
					<hr>
					<ul class='list-group'>
						<li id="empfname"
							class='list-group-item list-group-item-dark  list-group-item-action'>First
							Name: &emsp;</li>
						<li  id="emplname"
							class='list-group-item list-group-item-dark  list-group-item-action'>Last Name
							Name: &emsp;</li>
						<li id="empgender"
							class='list-group-item list-group-item-dark  list-group-item-action'>Gender:
							&emsp;</li>
						<li id="empbdate"
							class='list-group-item list-group-item-dark  list-group-item-action'>Birth
							date: &emsp;</li>
						<li id="emphdate"
							class='list-group-item list-group-item-dark  list-group-item-action'>Hire
							date: &emsp;</li>
					</ul>
				</div>
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
				<input type="submit" class="btn btn-success" name="action"
					value="add">
			</div>
		</div>
	</form>
</div>

<%@ include file="footer.jsp"%>
