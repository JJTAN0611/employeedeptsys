<%@ page errorPage = "error.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="deub" type="model.usebean.DepartmentEmployeeUseBean"
	scope="session" />
<%@ include file="header.jsp"%>

<div class="container top-first">
	<div class="badge bg-primary text-light text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Department Employee Record
				<div class="badge bg-light text-danger text-wrap">Delete</div>
			</div>
		</div>
	</div>
	<br> <br>
	<h1>
		<center>You are deleting following details.</center>
	</h1>
	<hr>
	<center>Click the ID input textbox to see detail.</center>
		<div class="text-danger text-center"><jsp:getProperty name="deub"
			property="overall_error" /> </div>
	<br>
</div>

<div class="container wow bounceInDown" data-wow-duration="1.5s"
	data-wow-delay="0.2s">
	<form method="post" action="MainServlet">
		<input type="hidden" name="target" value="departmentemployee" />
		<div class="form-group row">
			<label class="control-label col-3 text-end">Department ID:</label>
			<div class="col-8">
				<div class="input-group">
					<input id="dept_id" type="text" class="form-control" name="dept_id"
						value='<jsp:getProperty name="deub" property="dept_id"/>' readonly='readonly'>
					<div class="input-group-append">
						<button id="checkdept" class="btn btn-info"
							data-bs-toggle='collapse' data-bs-target='#deptcontent'
							type="button"
							onclick="
								$('#checkdept').attr('class', 'btn btn-info spinner-border');
								 $.get('MainServlet?target=departmentemployee&action=getDepartment&id='+$('#dept_id').val(), function(data, status){
									  var aid = $.parseJSON(data);
									  if(aid.name==''){
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
				<div class="text-danger"><jsp:getProperty name="deub"
						property="dept_id_error" /> ${deub.getExpress()}</div>
				<div id="deptcontent" class="collapse">
					<hr>
					<ul class='list-group'>
						<li id="dept_name"
							class='list-group-item list-group-item-action list-group-item-dark'>Department
							Name:&emsp;</li>
					</ul>
				</div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">Employee ID:</label>
			<div class="col-8">
				<div class="input-group">
					<input id="emp_id" type="number" class="form-control" name="emp_id"
						value='<jsp:getProperty name="deub" property="emp_id"/>' readonly='readonly'>
					<div class="input-group-append">
						<button id="checkemp" class="btn btn-info"
							data-bs-toggle='collapse' data-bs-target='#empcontent'
							type="button"
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
				<div class="text-danger"><jsp:getProperty name="deub"
							property="emp_id_error" /> ${deub.getExpress()}</div>
				<div id="empcontent" class="collapse">
					<hr>
					<ul class='list-group'>
						<li id="empfname"
							class='list-group-item list-group-item-dark  list-group-item-action'>First
							Name: &emsp;</li>
						<li id="emplname"
							class='list-group-item list-group-item-dark  list-group-item-action'>Last
							Name Name: &emsp;</li>
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
			<label class="control-label col-3 text-end">From Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="from_date"
					value='<jsp:getProperty name="deub" property="from_date"/>'>
				<div class="text-danger"><jsp:getProperty name="deub"
						property="from_date_error" /></div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">To Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="to_date"
					value='<jsp:getProperty name="deub" property="to_date"/>'>
				<div class="text-danger"><jsp:getProperty name="deub"
						property="to_date_error" /></div>
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
