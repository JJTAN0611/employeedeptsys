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
		<div class="card text-white bg-dark shadow-lg">
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

					<div class="card text-dark bg-light">
						<div class="card-header"><h5>Filter by keyword</h5> (You may leave
							empty), (Case is not sensitive)</div>
						<div class="card-body">
							<div class="row">
								<div class="col-2 float-end"><b>Keyword:</b></div>
								<div class="col-10">
									<input class="form-control border-info border-5" type="text" aria-label="Search"
										name="keyword" value="<%=keyword%>"
										placeholder="Enter keyword. (Id, name, etc)" /><br> <i>**You
										may use <b>%</b> for searching mask. For example, <b>Georgi
											Facello%M%1956</b>.
									</i>
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
										<div class="col"><b>Record per Page:</b></div>
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
										<div class="col"><b>Direction:</b></div>
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
							out.println("<a class=\"page-link \" href=\""
									+ "MainServlet?target=employee&action=view&recordsPerPage=" + recordsPerPage + "&currentPage="
									+ nOfPages + "&keyword=" + keyword + "&direction=" + direction + "\">Last</a>");
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
<!-- 
							<div class="row">
								<div class="col-3 float-end">ID</div>
								<div class="col-9">
									<input class="form-control" type="text" aria-label="Search"
										name="keyword" value="" placeholder="Enter id" />
								</div>
							</div>
							<div class="row">
								<div class="col-3 float-end">Name</div>
								<div class="col-9">
									<input class="form-control" type="text" aria-label="Search"
										name="name" value="" placeholder="Enter name" />
								</div>
							</div>
							<div class="row">
								<div class="col-3 float-end">Gender</div>
								<div class="col-9">
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="checkbox" value="Male"
											id="male" checked> <label
											class="form-check-label" for="male">
											Male</label>
									</div>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="checkbox" value="Female"
											id="female" checked> <label
											class="form-check-label" for="female">
											Female</label>
									</div>
								</div>
							</div>
							<div class="row align-items-center">
								<div class="col-3 float-end">DOB</div>
								<div class="col-9">
									<input class="form-control" type="date" aria-label="Search"
										name="dob" value="" />
								</div>
							</div>
							<div class="row">
								<div class="col-3 float-end">Hired date</div>
								<div class="col-9">
									<input class="form-control" type="date" aria-label="Search"
										name="dob" value="" />
								</div>
							</div>
 -->