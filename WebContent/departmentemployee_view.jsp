<%@page import="java.util.List"%>
<%@page import="model.entity.DepartmentEmployee"%>
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
	<div class="badge bg-primary text-light text-wrap large col-12">
		<div class="row">
			<div class="text-start col-9" style="font-size: 35px;">
				Department Employee Record
				<div class="badge bg-light text-info text-wrap">View</div>
			</div>
			<div class="text-end col-3">
				<button class="btn btn-light btn-outline-success text-dark"
					style="font-size: 20px; font-weight: bold;"
					onclick="document.getElementById('adddepartmentemployee').click()">+
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
		<div class="card text-white bg-dark shadow-lg">
			<h4 class="card-header text-center">
				Page Sorting and Filter Control
				<button class="btn btn-light float-end btn-circle rounded-pill"
					data-bs-toggle="collapse" data-bs-target="#pane"
					aria-expanded="true">
					<i class="fa fa-chevron-down pull-right" aria-hidden="false"></i> <i
						class="fa fa-chevron-right pull-right" aria-hidden="true"></i>

				</button>
			</h4>
			<div id="pane" class="card-body collapse show ">
				<form class="form-inline md-form mr-auto" action="MainServlet"
					method="get">
					<input type="hidden" name=target value="departmentemployee" /> <input
						type="hidden" name=action value="view" />


					<div class="card text-dark bg-light">
						<div class="card-header"><h5>Filter by keyword</h5> (You may leave empty), (Case is not sensitive)
						</div>
						<div class="card-body">
							<div class="row">
								<div class="col-2 float-end">Keyword:</div>
								<div class="col-10">
									<input class="form-control" type="text" aria-label="Search"
										name="keyword" value="<%=keyword%>"
										placeholder="Enter keyword. (Id, name, etc)" /><br>
										<i>**You may use <b>department name</b> or <b>employee name</b> instead of use <b>id</b> to search.</i><br>
										<i>**You may use <b>%</b> for searching mask. For example, <b>Marketing%Ekawit Diderrich</b>.</i>
								</div>
							</div>
						</div>
					</div>
					<hr>
					<div class="card  text-dark bg-light">
						<div class="card-header"><h5>Page Sorting</h5></div>
						<div class="card-body">
							<div class="row">
								<div
									class="col-6 item-aligns-center border-end border-dark border-5">
									<div class="row">
										<div class="col">Record per Page:</div>
										<div class="col">
											<input class="form-control-range" type="range"
												name="recordsPerPage" style="color: red" min="1" max="100"
												value="<%=recordsPerPage%>"
												oninput="$('#value').text($(this).val())"> <span
												id="value"><%=recordsPerPage%></span>
										</div>
									</div>

								</div>
								<div class="col-6 item-aligns-center">
									<div class="row">
										<div class="col">Direction:</div>
										<div class="col">
											<div class="form-check">
												<input class="form-check-input" type="radio" value="ASC"
													name="direction" id="asc"
													<%=direction.compareTo("ASC") == 0 ? "checked" : ""%>>
												<label class="form-check-label" for="asc"> Ascending</label>
											</div>
											<div class="form-check">
												<input class="form-check-input" type="radio"
													name="direction" id="desc"
													<%=direction.compareTo("DESC") == 0 ? "checked" : ""%>
													value="DESC"> <label class="form-check-label"
													for="desc">Descending </label>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<br>
					<button class="btn btn-primary btn-info float-end" type="submit">Go</button>

				</form>
			</div>

		</div>

	</div>
	<br>

	<div class="wow fadeInRight" data-wow-duration="1s"
		data-wow-delay="0.5s">
		<table
			class="table-responsive table table-bordered table-striped table-hover table-light shadow-lg"
			style="background: white">
			<tr class="table-dark">
				<th scope="col">PK ID</th>
				<th scope="col">Department ID</th>
				<th scope="col">Employee ID</th>
				<th scope="col">From Date</th>
				<th scope="col">To Date</th>
				<th colspan=2 scope="col"><center>Method</center></th>
			</tr>
			<tr class="table-info">

			</tr>
			<%
				List<DepartmentEmployee> des = (List<DepartmentEmployee>) request.getAttribute("departmentemployee");
				if (des.size() != 0) {
					for (DepartmentEmployee de : des) {
						out.println("<tr>");
						//id
						out.println("<td>" + de.getId().toString().substring(34) + "</td>");

						//department
						out.println(
								"<td>" + de.getDepartment().getId() + " | " + de.getDepartment().getDeptName() + "</td>");

						//employee
						out.println("<td>" + de.getEmployee().getId() + " | " + de.getEmployee().getFirstName() + " "
								+ de.getEmployee().getLastName());
						out.println(
								"<button class='btn btn-info btn-sm btntgl float-end' data-bs-toggle='collapse' data-bs-target='.a"
										+ de.getId().toString().substring(34)
										+ "' aria-expanded='false'><i class='fa fa-chevron-right pull-right'></i><i class='fa fa-chevron-down pull-right'></i></button>");
						out.println("<div class='collapse a" + de.getId().toString().substring(34) + "'>");

						//Fill the relevent (show more)
						out.println(
								"<hr><ul class='list-group'><li class='list-group-item list-group-item-action'>Gender: &emsp;"
										+ (de.getEmployee().getGender() == "M" ? "Male" : "Female") + "</li>");
						out.println("<li class='list-group-item list-group-item-action'>Birth date: &emsp;"
								+ de.getEmployee().getBirthDate() + "</li>");
						out.println("<li class='list-group-item list-group-item-action'>Hire date: &emsp;"
								+ de.getEmployee().getHireDate() + "</li>");
						out.println("</ul></div></td>");

						out.println("<td>" + de.getFromDate() + "</td>");
						out.println("<td>" + de.getToDate() + "</td>");

						out.println("<td><a href=\"MainServlet?target=departmentemployee&action=update&emp_id="
								+ de.getEmployee().getId() + "&dept_id=" + de.getDepartment().getId()
								+ "\" class=\"text-primary\" ><i class=\"fas fa-marker\"></i>Update</a></td>");
						out.println("<td><a href=\"MainServlet?target=departmentemployee&action=delete&emp_id="
								+ de.getEmployee().getId() + "&dept_id=" + de.getDepartment().getId()
								+ "\" class=\"text-danger\" ><i class=\"fas fa-trash-alt\"></i>Delete</a></td>");
						out.println("</tr>");
					}
				} else {
					out.println("<tr>");
					String status = "No records";
					for (int i = 0; i < 7; i++) {
						out.println("<td>" + status + "</td>");
					}
					out.println("</tr>");
				}
			%>
		</table>
		<div class=row>
		<nav class="col-6" aria-label="Navigation for staffs">
			<ul class="pagination">
				<%
					if (currentPage != 1 && nOfPages != 0) {
						out.println("<li class=\"page-item\">");
						out.println("<a class=\"page-link\" href=\""
								+ "MainServlet?target=departmentemployee&action=view&recordsPerPage=" + recordsPerPage
								+ "&currentPage=1" + "&keyword=" + keyword + "&direction=" + direction + "\">First</a>");
						out.println("</li><li class=\"page-item\">");
						out.println("<a class=\"page-link\" href=\""
								+ "MainServlet?target=departmentemployee&action=view&recordsPerPage=" + recordsPerPage
								+ "&currentPage=" + (currentPage - 1) + "&keyword=" + keyword + "&direction=" + direction
								+ "\">Previous</a>	</li>");
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
						out.println("<a class=\"page-link\" href=\""
								+ "MainServlet?target=departmentemployee&action=view&recordsPerPage=" + recordsPerPage
								+ "&currentPage=" + (currentPage + 1) + "&keyword=" + keyword + "&direction=" + direction
								+ "\">Next</a>");
						out.println("</li>");
						out.println("<li class=\"page-item\">");
						out.println("<a class=\"page-link \" href=\""
								+ "MainServlet?target=departmentemployee&action=view&recordsPerPage=" + recordsPerPage
								+ "&currentPage=" + nOfPages + "&keyword=" + keyword + "&direction=" + direction
								+ "\">Last</a>");
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
	

</div>




<%@ include file="footer.jsp"%>