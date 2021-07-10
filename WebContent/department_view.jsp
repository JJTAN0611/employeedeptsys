<%@page import="java.util.List"%>
<%@page import="model.entity.Department"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%	
	 String direction=(String)request.getAttribute("direction"); 
%>		
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
	rel="stylesheet">

<title>Department Employee System</title>

<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Additional CSS Files -->
<link rel="stylesheet" href="assets/css/fontawesome.css">
<link rel="stylesheet" href="assets/css/framestyle.css">
<link rel="stylesheet" href="assets/css/animated.css">
<link rel="stylesheet" href="assets/css/owl.css">
<script src="https://kit.fontawesome.com/5641842b84.js" crossorigin="anonymous"></script>
</head>

<body style="background:#fffff0">

	<!-- ***** Preloader Start ***** -->
	<div id="js-preloader" class="js-preloader">
		<div class="preloader-inner">
			<span class="dot"></span>
			<div class="dots">
				<span></span> <span></span> <span></span>
			</div>
		</div>
	</div>
	<!-- ***** Preloader End ***** -->

   <!-- ***** Header Area Start ***** -->
  <header class="header-area header-sticky wow slideInDown animated" data-wow-duration="0.75s" data-wow-delay="0s" style="visibility: visible;-webkit-animation-duration: 0.75s; -moz-animation-duration: 0.75s; animation-duration: 0.75s;-webkit-animation-delay: 0s; -moz-animation-delay: 0s; animation-delay: 0s;">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <nav class="main-nav">
            <!-- ***** Logo Start ***** -->
            <a href="index.html" class="logo">
              <h4>O<span>ED</span>RS</h4>
            </a>
            <!-- ***** Logo End ***** -->
            <!-- ***** Menu Start ***** -->
            <ul class="nav">
			  <li><a href="DepartmentPaginationServlet?currentPage=1&recordsPerPage=100&direction=ASC&keyword=&table=Department" class="btn btn-warning">Department</a></li>
              <li><a href="DepartmentPaginationServlet?currentPage=1&recordsPerPage=100&direction=ASC&keyword=&table=Department_Employee" class="btn btn-primary" >Department Employee</a></li>
              <li><a href="EmployeePaginationServlet?currentPage=1&recordsPerPage=100&direction=ASC&keyword=&table=Employee" class="btn btn-success">Employee</a></li>
            </ul>  
                  
            <a class="menu-trigger">
                <span>Menu</span>
            </a>
            
            <!-- ***** Menu End ***** -->
          </nav>
        </div>
      </div>
    </div>
  </header>
   <!-- ***** Header Area Start ***** -->
  <header class="header-area header-sticky wow slideInDown animated" data-wow-duration="0.75s" data-wow-delay="0s" style="visibility: visible;-webkit-animation-duration: 0.75s; -moz-animation-duration: 0.75s; animation-duration: 0.75s;-webkit-animation-delay: 0s; -moz-animation-delay: 0s; animation-delay: 0s;">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <nav class="main-nav">
            <!-- ***** Logo Start ***** -->
            <a href="index.html" class="logo">
              <h4>O<span>ED</span>RS</h4>
            </a>
            <!-- ***** Logo End ***** -->
            <!-- ***** Menu Start ***** -->
			<ul class="nav">
			  <li><a href="MainServlet?table=department&action=view" class="btn btn-warning">Department</a></li>
			  <li><a href="MainServlet?table=deptemp&action=view" class="btn btn-primary">Department-Employee</a></li>
			  <li><a href="MainServlet?table=employee&action=view" class="btn btn-success">Employee</a></li>
            </ul>  
                  
            <a class="menu-trigger">
                <span>Menu</span>
            </a>
            
            <!-- ***** Menu End ***** -->
          </nav>
        </div>
      </div>
    </div>
  </header>
  <!-- ***** Header Area End ***** -->  

	<br>
	<br>
	<br>
	<br>
	<br>
	<div class="container">
		<div class="row">
			<div class='"badge bg-warning text-dark text-wrap large col-12 col-md"'>
				<h1>Department Record</h1>
			</div>
			<br>
			<br>
			<hr>
			<br>
			<br>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col"></div><div class="col"></div>
			<form class="form-inline md-form col" action="MainServlet"
					method="get">
					<input type="hidden" name=table value="department"/>
					<div class="input-group">
						<select class="form-control custom-select" id="direction"
							name="direction">
							<% if (direction.compareTo("DESC")==0) {
						out.println("<option value=\"ASC\">ascending</option>");
						out.println("<option value=\"DESC\" selected>descending</option>");
						} else {
						out.println("<option value=\"ASC\" selected>ascending</option>");
						out.println("<option value=\"DESC\">descending</option>");
						}
					%>
						</select>
						<div class="input-group-append">
							<button class="btn btn-primary" type="submit">Sorting</button>
						</div>
						</div>
				</form>
		</div>
		<br>
		<br>
		<div class="card">
			<div class="table-responsive">
				<table class="table table-bordered table-striped table-hover">
					<tr class="table-dark">
						<th  scope="col">ID</th>
						<th  scope="col">NAME</th>
						<th  colspan=2 scope="col" ><center>Method</center></th>
						</tr>
					<tr class="table-info">
						</tr>
					<% List<Department> departments = (List<Department>) request.getAttribute("departments");
							if (departments.size() != 0) {
								for (Department t : departments) {
									out.println("<tr>");
									out.println("<td>" + t.getId() + "</td>");
									out.println("<td>" + t.getDeptName() + "</td>");
									out.println("<td><a href=\"DepartmentController?id=" + t.getId() + "\" class=\"text-success\" ><i class=\"fas fa-marker\"></i>Update</a></td>");
									out.println("<td><a href=\"DepartmentController?id=" + t.getId() + "\" class=\"text-danger\" ><i class=\"fas fa-trash-alt\"></i>Delete</a></td>");
									out.println("</tr>");
								}
							} else {
							out.println("<tr>");
								String status = "No records";
								for (int i = 0; i < 8; i++) { 
									out.println("<td>" + status + "</td>");
								}
									out.println("</tr>");
							}
					%>
				</table>
			</div>
			</div>
		</div>
	</div>



	<!-- ***** Footer Area Start ***** -->
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-lg-12 wow fadeIn" data-wow-duration="1s" data-wow-delay="0.25s">
					<p>
						© Copyright 2021 <b>DEPARTMENTEMPLOYEE</b> All Rights Reserved.
					</p>
				</div>
			</div>
		</div>
	</footer>
	<!-- Scripts -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="assets/js/owl-carousel.js"></script>
	<script src="assets/js/animation.js"></script>
	<script src="assets/js/imagesloaded.js"></script>
	<script src="assets/js/templatemo-custom.js"></script>
	<!-- Footer end -->
</body>

</html>