<%@ page errorPage="error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="model.entity.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	//per request
	int nOfPages = (int) request.getAttribute("nOfPages");
	//get from session
	int currentPage = (int) request.getSession().getAttribute("ecurrentPage");
	int recordsPerPage = (int) request.getSession().getAttribute("erecordsPerPage");
	String keyword = (String) request.getSession().getAttribute("ekeyword");
	String direction = (String) request.getSession().getAttribute("edirection");

	String verificationToken = (String) request.getSession().getAttribute("everificationToken");
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
						<div class="card-body">
							<div class="row">
								<div class="col-2 float-end">
									<h5>Keyword:</h5>
								</div>
								<div class="col-10">
									<input class="form-control border-info border-5" type="text"
										aria-label="Search" name="keyword" value="<%=keyword%>"
										placeholder="Enter keyword. (Id, name, etc)" /> </i>
								</div>
							</div>
						</div>
					</div>
					<hr>
					<div class="card  text-dark bg-light">
						<div class="card-body">
							<div class="row">
								<div
									class="col-6 item-aligns-center border-end border-dark border-5">
									<div class="row">
										<div class="col">
											<h5>Record per Page:</h5>
										</div>
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
										<div class="col">
											<h5>Direction</h5>
											(by Employee Id):
										</div>
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
					<div class="row">
						<div class="col-10">
							<div class="text-light">
								**You may use <b>department name</b> or <b>employee name</b>
								instead of use <b>id</b> to search. <br> **You may use <b>%</b>
								for searching mask. For example, <b>Georgi Facello%M%1956</b>. <br>
								**Please enable cookie for record your session preference.
							</div>
						</div>
						<div class="col-2">
							<button class="btn btn-primary btn-info float-end" type="submit">Go</button>
						</div>
					</div>
				</form>
			</div>

		</div>
	</div>
	<br> <br>
	<div class=" wow fadeInRight" data-wow-duration="1s"
		data-wow-delay="0.5s">
		<div class="row">
			<a type="button"
				title="URL encoding used here. This feature compulsory require session"
				onclick='alert("Report generating. Please hold on."); javascript:window.open("<%=response.encodeURL("MainServlet?target=employee&action=report&verificationToken="
					+ ((String) request.getSession().getAttribute("everificationToken")))%>", "_blank", "scrollbars=1,resizable=1,height=700,width=600");'
				class="btn btn-info btn-circle float-end shadow-lg"
				style="border-radius: 30px">Report <i class="fas fa-file"></i>
			</a>
		</div>
		<br>
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
				List<Employee> employee = (List<Employee>) request.getAttribute("employeeList");
				if (employee.size() != 0) {
					for (Employee t : employee) {
			%>

			<tr>
				<td><%=t.getId()%></td>
				<td><%=t.getFirstName()%></td>
				<td><%=t.getLastName()%></td>
				<td><%=(t.getGender().compareTo("M") == 0 ? "Male" : "Female")%> <i
					class='fas fa-<%=(t.getGender().compareTo("M") == 0 ? "mars" : "venus")%>'></i></td>
				<td><%=t.getBirthDate()%></td>
				<td><%=t.getHireDate()%></td>
				<td><a
					href='MainServlet?target=employee&action=update&id=<%=t.getId()%>'
					class='text-primary'><i class='fas fa-marker'></i>Update</a></td>
				<td><a
					href='MainServlet?target=employee&action=delete&id=<%=t.getId()%>'
					class='text-danger'><i class='fas fa-trash-alt'></i>Delete</a></td>
			</tr>
			<%
				}
				} else {
			%>
			<tr>
				<td colspan=8 class='text-center'>No records</td>
			</tr>
			<%
				}
			%>
		</table>
		<div class=row>
			<nav class="col-6" aria-label="Navigation for staffs">
				<ul class="pagination">
					<%
						if (currentPage != 1 && nOfPages != 0) {
					%>
					<li class="page-item"><a class="page-link"
						href="MainServlet?target=employee&action=view&recordsPerPage=<%=recordsPerPage%>&currentPage=1&keyword=<%=keyword%>&direction=<%=direction%>">First</a>
					</li>
					<li class="page-item"><a class="page-link"
						href="MainServlet?target=employee&action=view&recordsPerPage=<%=recordsPerPage%>&currentPage=<%=(currentPage - 1)%>&keyword=<%=keyword%>&direction=<%=direction%>">Previous</a>
					</li>
					<%
						} else {
					%>
					<li class="page-item disabled"><a class="page-link disabled">First</a>
					</li>
					<li class="page-item disabled"><a class="page-link disabled"
						disabled>Previous</a></li>
					<%
						}
						if (currentPage < nOfPages) {
					%>

					<li class="page-item"><a class="page-link"
						href="MainServlet?target=employee&action=view&recordsPerPage=<%=recordsPerPage%>&currentPage=<%=(currentPage + 1)%>&keyword=<%=keyword%>&direction=<%=direction%>">Next</a>
					</li>

					<li class="page-item"><a class="page-link"
						href="MainServlet?target=employee&action=view&recordsPerPage=<%=recordsPerPage%>&currentPage=<%=nOfPages%>&keyword=<%=keyword%>&direction=<%=direction%>">Last</a>
					</li>
					<%
						} else {
					%>

					<li class="page-item disabled"><a class="page-link disabled">Next</a>

					</li>
					<li class="page-item disabled"><a class="page-link disabled"
						disabled>Last</a></li>
					<%
						}
					%>


				</ul>
			</nav>
			<div class='pageref col-6 text-end'><%=currentPage%>
				of
				<%=nOfPages%></div>
		</div>
	</div>


</div>
<%@ include file="footer.jsp"%>
