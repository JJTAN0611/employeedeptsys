<%@page import="java.util.List"%>
<%@page import="model.entity.Department"%>
<%@ page errorPage="error.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String direction = (String) request.getSession().getAttribute("ddirection");
	String verificationToken = (String) request.getSession().getAttribute("dverificationToken");
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
			<div class="text-end col-3 wow bounceIn" data-wow-duration="2s" data-wow-delay="0.2s">
				<button class="btn btn-success rounded-pill border border-light border-5"
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
							out.println("<option value=\"ASC\">Ascending by department id</option>");
							out.println("<option value=\"DESC\" selected>Descending by department id</option>");
						} else {
							out.println("<option value=\"ASC\" selected>Ascending by department id</option>");
							out.println("<option value=\"DESC\">Descending by department id</option>");
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
		<div class="row">
			<a type="button"
				onclick='alert("Report generating. Please hold on."); javascript:window.open("<%=response.encodeURL("MainServlet?target=department&action=report&verificationToken="
					+ ((String) request.getSession().getAttribute("dverificationToken")))%>", "_blank", "scrollbars=1,resizable=1,height=700,width=600"); '
				class="btn btn-info btn-circle float-end shadow-lg"
				style="border-radius: 30px"><i class="fas fa-file"></i>  Report for this page. Click to download.
			</a>
		</div>
		<br>
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
				List<Department> departments = (List<Department>) request.getAttribute("departmentList");
				if (departments.size() != 0) {
					for (Department t : departments) {
			%>
			<tr>
				<td><%=t.getId()%></td>
				<td><%=t.getDeptName()%></td>
				<td><a
					href='MainServlet?target=department&action=update&id=<%=t.getId()%>'
					class='text-primary'><i class='fas fa-marker'></i>Update</a></td>
				<td><a
					href='MainServlet?target=department&action=delete&id=<%=t.getId()%>'
					class='text-danger'><i class='fas fa-trash-alt'></i>Delete</a></td>
			</tr>
			<%
				}
				} else {
			%>
			<tr>
				<td colspan=4 class='text-center'>No records</td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</div>



<%@ include file="footer.jsp"%>
