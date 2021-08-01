<%@page import="java.util.List"%>
<%@page import="model.entity.Department"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String direction = (String) request.getAttribute("direction");
%>
<%@ include file="header.jsp"%>

<div class="container top-first wow fadeIn" data-wow-duration="2s"
	data-wow-delay="0.4s">
	<div class="badge bg-warning text-dark text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Department Record
				<div class="badge bg-light text-info text-wrap">View</div>
			</div>
			<div class="text-end col">
				<button class="btn btn-light btn-outline-success text-dark"
					style="font-size: 20px; font-weight: bold;"
					onclick="document.getElementById('adddepartment').click()">+
					Add Record</button>
			</div>
		</div>
	</div>
	<br> <br>
	<hr>
	<br> <br>
</div>

<div class="container">
	<div class="row wow fadeInLeft" data-wow-duration="1s"
		data-wow-delay="0.5s">
		<div class="col"></div>
		<div class="col"></div>
		<form class="form-inline md-form col" action="MainServlet"
			method="get">
			<input type="hidden" name=target value="department" /> <input
				type="hidden" name=action value="view" />
			<div class="input-group shadow-lg">
				<select class="form-control custom-select" id="direction"
					name="direction">
					<%
						if (direction.compareTo("DESC") == 0) {
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
	<br> <br>
	<div class="wow fadeInRight" data-wow-duration="1s"
		data-wow-delay="0.5s">
		<table
			class="table-responsive table table-bordered table-striped table-hover table-light shadow-lg">
			<tr class="table-dark">
				<th scope="col">ID</th>
				<th scope="col">NAME</th>
				<th colspan=2 scope="col"><center>Method</center></th>
			</tr>
			<tr class="table-info">
			</tr>
			<%
				List<Department> departments = (List<Department>) request.getAttribute("departments");
				if (departments.size() != 0) {
					for (Department t : departments) {
						out.println("<tr>");
						out.println("<td>" + t.getId() + "</td>");
						out.println("<td>" + t.getDeptName() + "</td>");
						out.println("<td><a href=\"MainServlet?id=" + t.getId()
								+ "&target=department&action=update\" class=\"text-primary\"><i class=\"fas fa-marker\"></i>Update</a></td>");
						out.println("<td><a href=\"MainServlet?id=" + t.getId()
								+ "&target=department&action=delete\" class=\"text-danger\"><i class=\"fas fa-trash-alt\"></i>Delete</a></td>");
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


<%@ include file="footer.jsp"%>
