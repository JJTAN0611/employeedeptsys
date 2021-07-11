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

<div class="container top-first">
	<div class="badge bg-success text-light text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 30px;">
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
	<div class="row">
		<form class="form-inline md-form mr-auto" action="MainServlet"
			method="get">
			<input type="hidden" name=table value="employee" /> <input
				type="hidden" name=action value="view" />
			<div class="input-group col-sm">
				<input class="form-control" type="text" aria-label="Search"
					name="keyword" value="<%=keyword%>" />
				<div class="input-group-append">
					<button class="btn btn-primary btn-info" type="submit">Search</button>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col">
					<div class="input-group col-sm">
						<input class="form-control" id="records" name="recordsPerPage"
							type="number" id="quantity" name="quantity" min="1" max="100"
							value="<%=recordsPerPage%>">

						<div class="input-group-append">
							<button class="btn btn-primary btn-info" type="submit">Records
								per page</button>
						</div>
					</div>
				</div>
				<div class="col"></div>
				<div class="col">
					<div class="input-group col-sm">
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
				</div>
			</div>
		</form>
	</div>
	<br>

	<div class="table-responsive">
		<table
			class="table table-bordered table-striped table-hover table-light"
			style="background: white">
			<tr class="table-dark">
				<th scope="col">ID</th>
				<th scope="col">DOB</th>
				<th scope="col">First Name</th>
				<th scope="col">Last Name</th>
				<th scope="col">Gender</th>
				<th scope="col">Hired Date</th>
				<th colspan=2 scope="col"><center>Method</center></th>
			</tr>
			<tr class="table-info">

			</tr>
			<%
				List<Employee> staffs = (List<Employee>) request.getAttribute("staffs");
				if (staffs.size() != 0) {
					for (Employee t : staffs) {
						out.println("<tr>");
						out.println("<td>" + t.getId() + "</td>");
						out.println("<td>" + t.getBirthDate() + "</td>");
						out.println("<td>" + t.getFirstName() + "</td>");
						out.println("<td>" + t.getLastName() + "</td>");
						out.println("<td>" + t.getGender() + "</td>");
						out.println("<td>" + t.getHireDate() + "</td>");
						out.println("<td><a href=\"MainServlet?table=employee&action=update&id=" + t.getId()
								+ "\" class=\"text-success\" ><i class=\"fas fa-marker\"></i>Update</a></td>");
						out.println("<td><a href=\"MainServlet?table=employee&action=delete&id=" + t.getId()
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
						out.println("<a class=\"page-link\" href=\"" + "MainServlet?table=employee&action=view&recordsPerPage="
								+ recordsPerPage + "&currentPage=1" + "&keyword=" + keyword + "&direction=" + direction
								+ "\">First</a>");
						out.println("</li><li class=\"page-item\">");
						out.println("<a class=\"page-link\" href=\"" + "MainServlet?table=employee&action=view&recordsPerPage="
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
						out.println("<a class=\"page-link\" href=\"" + "MainServlet?table=employee&action=view&recordsPerPage="
								+ recordsPerPage + "&currentPage=" + (currentPage + 1) + "&keyword=" + keyword + "&direction="
								+ direction + "\">Next</a>");
						out.println("</li>");
						out.println("<li class=\"page-item\">");
						out.println("<a class=\"page-link \" href=\"" + "MainServlet?table=employee&action=view&recordsPerPage="
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