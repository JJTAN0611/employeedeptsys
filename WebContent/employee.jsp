<%@page import="java.util.List"%>
<%@page import="model.entity.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%	int currentPage=(int) request.getAttribute("currentPage"); 
	int recordsPerPage=(int)request.getAttribute("recordsPerPage");
	int nOfPages=(int) request.getAttribute("nOfPages"); 
	String keyword=(String) request.getAttribute("keyword");
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

<body>

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
	<header class="header-area header-sticky wow slideInDown"
		data-wow-duration="0.75s" data-wow-delay="0s">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="main-nav">
						<!-- ***** Logo Start ***** -->
						<a href="index.html" class="logo">
							<h4>
								Department<span>Employee</span>
							</h4>
						</a>
						<!-- ***** Logo End ***** -->
						<!-- ***** Menu Start ***** -->
						<ul class="nav">
							<li><a href="#functionality">Department</a></li>
							<li><a href="#functionality">Department_Employee</a></li>
							<li><a href="#functionality">Employee</a></li>
							<li class="scroll-to-section">
								<div class="main-red-button">
									<a href="#contact">Contact me</a>
								</div>
							</li>
						</ul>
						<a class='menu-trigger'> <span>Menu</span>
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
			<div class="badge bg-warning text-wrap large col-12 col-md">
				<h1>Department Page</h1>
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
			<form class="form-inline md-form mr-auto" action="PaginationServlet"
				method="get">
				<div class="input-group col-sm">
					<input class="form-control" type="text" aria-label="Search"
						name="keyword" value="<%=keyword%>" />
					<div class="input-group-append">
						<button class="btn btn-primary btn-info" type="submit">Search</button>
					</div>
				</div>
				<input type="hidden" name="currentPage" value="<%=currentPage%>" />
				<input type="hidden" name="recordsPerPage"
					value="<%=recordsPerPage%>" /> <input type="hidden"
					name="direction" value="<%=direction%>" />
			</form>
		</div>
		<br>
		<div class="row">
			<div class="col">
				<form class="form-inline md-form mr-auto" action="PaginationServlet">
					<div class="input-group col-sm">
						<select class="form-control" id="records" name="recordsPerPage">
							<option value="20">20</option>
							<option value="50" selected>50</option>
							<option value="70" selected>70</option>
							<option value="100">100</option>
						</select>
						<div class="input-group-append">
							<button class="btn btn-primary btn-info" type="submit">Records
								per page</button>
						</div>
					</div>
					<input type="hidden" name="currentPage" value="<%=currentPage%>" />
					<input type="hidden" name="recordsPerPage"
						value="<%=recordsPerPage%>" /> <input type="hidden"
						name="direction" value="<%=direction%>" /> <input type="hidden"
						class="form-control" type="text" aria-label="Search"
						name="keyword" value="<%=keyword%>" />
				</form>
			</div>
			<div class="col"></div>
			<div class="col">
				<form class="form-inline md-form mr-auto" action="PaginationServlet"
					method="get">
					<div class="input-group col-sm">
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
						<input type="hidden" name="currentPage" value="<%=currentPage%>" />
						<input type="hidden" name="recordsPerPage"
							value="<%=recordsPerPage%>" /> <input type="hidden"
							name="keyword" value="<%=keyword%>" />
				</form>
			</div>
		</div>
		<br> <br>
		<div
			class="card>
			<div class="table-responsive">
				<table class="table table-bordered table-striped table-hover">
					<tr class="table-dark">
						<th  scope="col">ID</th>
						<th  scope="col">DOB</th>
						<th  scope="col">First Name</th>
						<th  scope="col">Last Name</th>
						<th scope="col">Gender</th>
						<th scope="col">Hired Date</th>
						<th  colspan=2 scope="col" ><center>Method</center></th>
						</tr>
					<tr class="table-info">
						<td  scope="col"><input type="text" class="input-group" size="8"/></th>
						<td  scope="col"><input type="text" class="input-group" size="8"/></th>
						<td  scope="col"><input type="text" class="input-group" size="8"/></th>
						<td  scope="col"><input type="text" class="input-group" size="8"/></th>
						<td scope="col"><input type="text"	class="input-group" size="2"/></th>
						<td scope="col"><input type="text" class="input-group" size="8"/></th>
						<td  colspan=2 scope="col" ><center><button type="button" class="btn btn-primary btn-rounded">
					<strong>+</strong> Add Record
				</button></center></td>
						</tr>
					<% List<Employee> staffs = (List<Employee>) request.getAttribute("staffs");
							if (staffs.size() != 0) {
								for (Employee t : staffs) {
									out.println("<tr>");
									out.println("<td>" + t.getId() + "</td>");
									out.println("<td>" + t.getBirthDate() + "</td>");
									out.println("<td>" + t.getFirstName() + "</td>");
									out.println("<td>" + t.getLastName() + "</td>");
									out.println("<td>" + t.getGender() + "</td>");
									out.println("<td>" + t.getHireDate() + "</td>");
									out.println("<td><a href=\"EmployeeController?id=" + t.getId() + "\" class=\"text-success\" ><i class=\"fas fa-marker\"></i>Update</a></td>");
									out.println("<td><a href=\"EmployeeController?id=" + t.getId() + "\" class=\"text-danger\" ><i class=\"fas fa-trash-alt\"></i>Delete</a></td>");
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
			<nav aria-label="Navigation for staffs">
				<ul class="pagination">
					<% if (currentPage !=1 && nOfPages !=0) { %>
						<% out.println("<li class=\"page-item\">");
							out.println("<a class=\"page-link bg-secondary text-light\" href=\"" + "PaginationServlet?recordsPerPage=" +
								recordsPerPage + "&currentPage=1" + "&keyword=" + keyword + "&direction=" + direction
								+ "\">First</a>");
							out.println("</li>");
							%>
							<li class="page-item">
								<% out.println("<a class=\"page-link bg-success text-light\" href=\"" + "PaginationServlet?recordsPerPage=" +
									recordsPerPage + "&currentPage=" + (currentPage - 1) + "&keyword=" + keyword
									+ "&direction=" + direction + "\">Previous</a>");
									%>
							</li>
							<% } %>
								<%%>
									<%if (currentPage < nOfPages) {
				out.println("<li class=\"page-item\">");
				out.println("<a class=\"page-link bg-success text-light\" href=\"" + "PaginationServlet?recordsPerPage=" + recordsPerPage
						+ "&currentPage=" + (currentPage + 1) + "&keyword=" + keyword + "&direction=" + direction
						+ "\">Next</a>");
				out.println("</li>");
				out.println("<li class=\"page-item\">");
				out.println("<a class=\"page-link bg-secondary text-light\" href=\"" + "PaginationServlet?recordsPerPage=" + recordsPerPage
						+ "&currentPage=" + nOfPages + "&keyword=" + keyword + "&direction=" + direction
						+ "\">Last</a>");
				out.println("</li>");
			}%>
				</ul>
			</nav>
			<%if (nOfPages != 0) {
				out.println("<p class=\"pageref\">");
				out.println(currentPage + " of " + nOfPages);
				out.println("</p>");
			}%>
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