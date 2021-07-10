<%@page import="java.util.List"%>
<%@page import="model.entity.Department"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%	
	 Department dept = (Department) request.getAttribute("dept");
%>		
<%@ include file="header.html" %>

	<div class="container">
		<div class="row">
			<div class='"badge bg-warning text-dark text-wrap large col-12 col-md"'>
				<h1>UPDATE Department Record</h1>
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
				<p><%=dept.getId()%></p>
				<p><%=dept.getDeptName()%></p>
			</form>
		</div>
	</div>


<%@ include file="footer.html" %>  