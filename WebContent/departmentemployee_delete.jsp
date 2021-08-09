<%@ page errorPage="error.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="deub" type="model.javabean.DepartmentEmployeeJavaBean"
	scope="request" />
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
	<center>Click the view button to see detail.</center>
	<div class="text-danger text-center"><jsp:getProperty name="deub"
			property="overall_error" />
	</div>
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
						value='<jsp:getProperty name="deub" property="dept_id"/>'
						readonly='readonly'>
					<div class="input-group-append">
						<button id="checkdept" class="btn btn-info"
							data-bs-toggle='collapse' data-bs-target='#deptcontent'
							type="button"
							onclick="
								$('#checkdept').attr('class', 'btn btn-info spinner-border');
							 $.get('MainServlet?target=department&action=getByIdAjax&id='+$('#dept_id').val(), function(data, status){
			
								  if(data[0]==null){
									  $('#deptcontent').hide();
									  $('#deptname').html('null');
									  alert('Department ID: ['+$('#dept_id').val()+'] not exist.');
								  }else{
									$('#dept_id').val(data[0].id);
								  	$('#dept_name').html('Department Name:&emsp;'+data[0].deptName);
								  	$('#deptcontent').show();
								  }
									$('#checkdept').attr('class', 'btn btn-info');
								});
						">View</button>
					</div>

				</div>
				<div class="text-danger"><jsp:getProperty name="deub"
						property="dept_id_error" />
					${deub.getExpress()}
				</div>
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
						value='<jsp:getProperty name="deub" property="emp_id"/>'
						readonly='readonly'>
					<div class="input-group-append">
						<button id="checkemp" class="btn btn-info"
							data-bs-toggle='collapse' data-bs-target='#empcontent'
							type="button"
							onclick="
								$('#checkemp').attr('class', 'btn btn-info spinner-border');
							  $.get('MainServlet?target=employee&action=getByIdAjax&id='+$('#emp_id').val(), function(data, status){
								  if(data[0]==null){
									  $('#empcontent').hide();
									  alert('Employee ID: ['+$('#emp_id').val()+'] not exist.');
									  $('#empfname').html('null');
									  $('#emplname').html('null');
									  $('#empgender').html('null');
									  $('#empbdate').html('null');
									  $('#emphdate').html('null');
								  }else{
									  $('#emp_id').val(data[0].id);
								  	  $('#empfname').html('First name:&emsp;'+data[0].firstName);
									  $('#emplname').html('Last name:&emsp;'+data[0].lastName);
									  $('#empgender').html('Gender:&emsp;'+(data[0].gender=='M'?'Male':'Female'));
									  $('#empbdate').html('Birth date:&emsp;'+data[0].birthDate);
									  $('#emphdate').html('Hire date:&emsp;'+data[0].hireDate);
								  	$('#empcontent').show();
								  }
								  $('#checkemp').attr('class', 'btn btn-info');
								  });
						">View</button>
					</div>

				</div>
				<div class="text-danger"><jsp:getProperty name="deub"
						property="emp_id_error" />
					${deub.getExpress()}
				</div>
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
					value='<jsp:getProperty name="deub" property="from_date"/>' readonly='readonly'>
				<div class="text-danger"><jsp:getProperty name="deub"
						property="from_date_error" /></div>
			</div>
		</div>
		<br>
		<div class="form-group row">
			<label class="control-label col-3 text-end">To Date:</label>
			<div class="col-8">
				<input type="date" class="form-control" name="to_date"
					value='<jsp:getProperty name="deub" property="to_date"/>' readonly='readonly'>
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
