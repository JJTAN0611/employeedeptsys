<%@page import="java.util.List"%>
<%@page import="model.entity.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	int currentPage = (int) request.getAttribute("currentPage");
	int recordsPerPage = (int) request.getAttribute("recordsPerPage");
	int nOfPages = (int) request.getAttribute("nOfPages");
	String keyword = (String) request.getAttribute("keyword");
	String direction = (String) request.getAttribute("direction");
%>
<%@ include file="header.jsp"%>
<style>
button[aria-expanded=true] .fa-chevron-right {
	display: none;
}

button[aria-expanded=false] .fa-chevron-down {
	display: none;
}
</style>
<div class="container top-first wow fadeIn" data-wow-duration="2s"
	data-wow-delay="0.4s">
	<div class="badge bg-success text-light text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Employee Record
				<div class="badge bg-light text-info text-wrap">View</div>
			</div>
			<div class="text-end col">
				<button class="btn btn-light btn-outline-success text-dark"
					style="font-size: 20px; font-weight: bold;"
					onclick="document.getElementById('addemployee').click()">+
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
		<div class="card text-white bg-dark">
			<h4 class="card-header text-center">
				Page Sorting and Filter Control
				<button class="btn btn-light float-end btn-circle  rounded-pill"
					data-bs-toggle="collapse" data-bs-target="#pane"
					aria-expanded="true">
					<i class="fa fa-chevron-down pull-right" aria-hidden="false"></i> <i
						class="fa fa-chevron-right pull-right" aria-hidden="true"></i>

				</button>
			</h4>
			<div id="pane" class="card-body collapse show ">
				<form class="form-inline md-form mr-auto" action="MainServlet"
					method="get">
					<input type="hidden" name=target value="employee" /> <input
						type="hidden" name=action value="view" />
					<!-- Left -->

					<div class="row">
						<!-- Left -->
						<div class="col-3 border-end border-light">
							<div class="card  text-dark bg-light">
								<div class="card-header">Record per page</div>
								<div class="card-body">
									<input class="form-control-range" type="range"
										name="recordsPerPage" style="color: red" min="1" max="100"
										value="<%=recordsPerPage%>"
										oninput="$('#value').text($(this).val())"> <span
										id="value"><%=recordsPerPage%></span>
								</div>
							</div>
							<br>
							<div class="card text-dark bg-light">
								<div class="card-header">Sorting</div>
								<div class="card-body">
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
								</div>

							</div>
						</div>
						<!-- Right -->
						<div class="col-9">
							<div class="input-group col-sm">
								<input class="form-control" type="text" aria-label="Search"
									name="keyword" value="<%=keyword%>" />
								<div class="input-group-append">
									<button class="btn btn-primary btn-info" type="submit">Search</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>

		</div>





	</div>
	<br>

	<div class=" wow fadeInRight " data-wow-duration="1s"
		data-wow-delay="0.5s">
		<table
			class="table-responsive table table-bordered table-striped table-hover table-light shadow-lg"
			style="background: white">
			<tr class="table-dark shadow-lg">
				<th scope="col">ID</th>
				<th scope="col">First Name</th>
				<th scope="col">Last Name</th>
				<th scope="col">Gender</th>
				<th scope="col">DOB</th>
				<th scope="col">Hired Date</th>
				<th colspan=2 scope="col"><center>Method</center></th>
			</tr>
			<tr class="table-info">

			</tr>
			<%
				List<Employee> staffs = (List<Employee>) request.getAttribute("employees");
				if (staffs.size() != 0) {
					for (Employee t : staffs) {
						out.println("<tr>");
						out.println("<td>" + t.getId() + "</td>");
						out.println("<td>" + t.getFirstName() + "</td>");
						out.println("<td>" + t.getLastName() + "</td>");
						out.println("<td>" + t.getGender() + "</td>");
						out.println("<td>" + t.getBirthDate() + "</td>");
						out.println("<td>" + t.getHireDate() + "</td>");
						out.println("<td><a href=\"MainServlet?target=employee&action=update&id=" + t.getId()
								+ "\" class=\"text-primary\" ><i class=\"fas fa-marker\"></i>Update</a></td>");
						out.println("<td><a href=\"MainServlet?target=employee&action=delete&id=" + t.getId()
								+ "\" class=\"text-danger\" ><i class=\"fas fa-trash-alt\"></i>Delete</a></td>");
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
	<div class=row>
		<nav class="col-6" aria-label="Navigation for staffs">
			<ul class="pagination">
				<%
					if (currentPage != 1 && nOfPages != 0) {
						out.println("<li class=\"page-item\">");
						out.println("<a class=\"page-link\" href=\"" + "MainServlet?target=employee&action=view&recordsPerPage="
								+ recordsPerPage + "&currentPage=1" + "&keyword=" + keyword + "&direction=" + direction
								+ "\">First</a>");
						out.println("</li><li class=\"page-item\">");
						out.println("<a class=\"page-link\" href=\"" + "MainServlet?target=employee&action=view&recordsPerPage="
								+ recordsPerPage + "&currentPage=" + (currentPage - 1) + "&keyword=" + keyword + "&direction="
								+ direction + "&direction=" + direction + "\">Previous</a>	</li>");
					} else {
						out.println("<li class=\"page-item disabled\">");
						out.println("<a class=\"page-link disabled\">First</a>");
						out.println("</li>");
						out.println("<li class=\"page-item disabled\">");
						out.println("<a class=\"page-link disabled\" disabled>Previous</a>");
						out.println("</li>");
					}
					if (currentPage < nOfPages) {
						out.println("<li class=\"page-item\">");
						out.println("<a class=\"page-link\" href=\"" + "MainServlet?target=employee&action=view&recordsPerPage="
								+ recordsPerPage + "&currentPage=" + (currentPage + 1) + "&keyword=" + keyword + "&direction="
								+ direction + "\">Next</a>");
						out.println("</li>");
						out.println("<li class=\"page-item\">");
						out.println("<a class=\"page-link \" href=\"" + "MainServlet?target=employee&action=view&recordsPerPage="
								+ recordsPerPage + "&currentPage=" + nOfPages + "&keyword=" + keyword + "&direction="
								+ direction + "\">Last</a>");
						out.println("</li>");
					} else {
						out.println("<li class=\"page-item disabled\">");
						out.println("<a class=\"page-link disabled\">Next</a>");
						out.println("</li>");
						out.println("<li class=\"page-item disabled\">");
						out.println("<a class=\"page-link disabled\" disabled>Last</a>");
						out.println("</li>");
					}
				%>


			</ul>
		</nav>
		<%
			if (nOfPages != 0) {
				out.println("<div class=\"pageref col-6 text-end\">");
				out.println(currentPage + " of " + nOfPages);
				out.println("</div>");
			}
		%>
	</div>

</div>






<%@ include file="footer.jsp"%>