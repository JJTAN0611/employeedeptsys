<%@page import="java.util.List"%>
<%@page import="model.entity.Department"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String direction = (String) request.getAttribute("direction");
%>
<%@ include file="header.jsp"%>

<div class="container top-first">
	<h1>
		<div class="badge bg-warning text-dark text-wrap large col-12">
			<div class="row">
				<div class="text-start col">
					Department Record
					<div class="badge bg-light text-primary text-wrap">View</div>
				</div>
				<div class="text-end col">
					<button class="btn btn-info" style="font-size: 20px;"
						onclick="document.getElementById('adddepartment').click()">+ Add
						Record</button>
				</div>
			</div>
		</div>
	</h1>

	<br> <br>
	<hr>
	<br> <br>
</div>

<div class="container">
	<div class="row">
		<div class="col"></div>
		<div class="col"></div>
		<form class="form-inline md-form col" action="MainServlet"
			method="get">
			<input type="hidden" name=table value="department" /> <input
				type="hidden" name=action value="view" />
			<div class="input-group">
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
	<div class="card">
		<div class="table-responsive">
			<table class="table table-bordered table-striped table-hover">
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
									+ "&table=department&action=update\" class=\"text-success\"><i class=\"fas fa-marker\"></i>Update</a></td>");
							out.println("<td><a href=\"MainServlet?id=" + t.getId()
									+ "&table=department&action=remove\" class=\"text-danger\"><i class=\"fas fa-trash-alt\"></i>Delete</a></td>");
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


<%@ include file="footer.jsp"%>
