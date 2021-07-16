<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="weblistener.UserTrackingListener"%>
<%@page import="javax.servlet.http.Cookie"%>
<%
	String table = (String) request.getAttribute("table");
	if (table == null)
		table = "index";
	String color;
	switch (table) {
	case "department":
		color = "#ffffee";
		break;
	case "departmentemployee":
		color = "#d9edf3";
		break;
	case "employee":
		color = "#d6f8d6";
		break;
	default:
		color = "gainsboro";
		break;
	}
%>

<%
	Cookie[] cookies = request.getCookies();

	if (cookies != null) {
		Cookie cookie;
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (!cookie.getName().equals("oedrsUserId") && i == cookies.length - 1) {
				Cookie nc = new Cookie("oedrsUserId",
						String.valueOf(weblistener.UserTrackingListener.getUserId()));
				nc.setMaxAge(-1);
				response.addCookie(nc);
			}
		}
	} else {
		Cookie nc = new Cookie("oedrsUserId", String.valueOf(weblistener.UserTrackingListener.getUserId()));
		nc.setMaxAge(-1);
		response.addCookie(nc);
	}
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
<script src="https://kit.fontawesome.com/5641842b84.js"
	crossorigin="anonymous"></script>


</head>

<body style="background:<%=color%>;">

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

	<a id="b2t"></a>

	<!-- ***** Header Area Start ***** -->
	<header class="header-area header-sticky wow slideInDown animated"
		data-wow-duration="0.75s" data-wow-delay="0s"
		style="visibility: visible; -webkit-animation-duration: 0.75s; -moz-animation-duration: 0.75s; animation-duration: 0.75s; -webkit-animation-delay: 0s; -moz-animation-delay: 0s; animation-delay: 0s;">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="main-nav">
						<!-- ***** Logo Start ***** -->
						<a href="index.jsp" class="logo">
							<h4>
								O<span>ED</span>RS
							</h4>
						</a>
						<!-- ***** Logo End ***** -->
						<!-- ***** Menu Start ***** -->
						<ul class="nav">
							<li>
								<div class="btn-group">
									<button id="department" type="button"
										onclick="location.href='MainServlet?table=department&action=view'"
										class="btn btn-warning">Department View</button>
									<button type="button"
										class="btn btn-warning dropdown-toggle dropdown-toggle-split"
										aria-labelledby="dropdownMenuReference"
										data-bs-toggle="dropdown" aria-expanded="false">
										<span class="visually-hidden">Toggle Dropdown</span>
									</button>
									<ul class="dropdown-menu">
										<li><a id="adddepartment" class="dropdown-item"
											href="MainServlet?table=department&action=add">Add
												Department</a></li>
										<li></li>
									</ul>
								</div>
							</li>
							<li><div class="btn-group">
									<button id="department_employee" type="button"
										onclick="location.href='MainServlet?table=departmentemployee&action=view'"
										class="btn btn-primary">Department Employee View</button>
									<button type="button"
										class="btn btn-primary dropdown-toggle dropdown-toggle-split"
										aria-labelledby="dropdownMenuReference"
										data-bs-toggle="dropdown" aria-expanded="false">
										<span class="visually-hidden">Toggle Dropdown</span>
									</button>
									<ul class="dropdown-menu">
										<li><a id="adddepartmentemployee" class="dropdown-item"
											href="MainServlet?table=departmentemployee&action=add">Add
												Department Employee</a></li>
										<li></li>
									</ul>
								</div></li>
							<li><div class="btn-group">
									<button id="employee" type="button"
										onclick="location.href='MainServlet?table=employee&action=view'"
										class="btn btn-success">Employee View</button>
									<button type="button"
										class="btn btn-success dropdown-toggle dropdown-toggle-split"
										aria-labelledby="dropdownMenuReference"
										data-bs-toggle="dropdown" aria-expanded="false">
										<span class="visually-hidden">Toggle Dropdown</span>
									</button>
									<ul class="dropdown-menu">
										<li><a id="addemployee" class="dropdown-item"
											href="MainServlet?table=employee&action=add">Add Employee</a></li>
										<li></li>
									</ul>
								</div></li>
							<li><button type="button"
									onclick="location.href='MainServlet?table=log&action=view'"
									class="btn btn-dark text-light  btn-circle"
									style="border-radius: 30px">
									Log <i class="fas fa-calendar-week"></i>
								</button></li>
							<li></li>
						</ul>

						<a class="menu-trigger"> <span>Menu</span>
						</a>

						<!-- ***** Menu End ***** -->
					</nav>

				</div>
			</div>
			<!-- ***** Header Area End ***** -->
			<div class="row">
				<div class="col-6">
					<div class="text-start">
						Number of users viewing: <b><a
							onClick="window.location.reload()"><%=UserTrackingListener.getActiveSessions()%></a></b>
					</div>
				</div>
				<div class="col-6">
					<div class="text-end">
						Hi, welcome <b><a id="userId"
							onClick="window.location.reload()"></a></b>!
					</div>
				</div>
			</div>
		</div>
	</header>