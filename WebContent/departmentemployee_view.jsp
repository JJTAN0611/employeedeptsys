<%@page import="java.util.List"%>
<%@page import="model.entity.DepartmentEmployee"%>
<%@ page errorPage="error.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	int nOfPages = (int) request.getAttribute("nOfPages");

	int currentPage = (int) request.getSession().getAttribute("decurrentPage");
	int recordsPerPage = (int) request.getSession().getAttribute("derecordsPerPage");
	String keyword = (String) request.getSession().getAttribute("dekeyword");
	String direction = (String) request.getSession().getAttribute("dedirection");
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
			<div class="text-end col-3 wow bounceIn" data-wow-duration="2s" data-wow-delay="0.2s">
				<button class="btn btn-success rounded-pill border border-light border-5"
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
						<div class="card-body">
							<div class="row">
								<div class="col-2 float-end">
									<h5>Keyword:</h5>
								</div>
								<div class="col-10">
									<input class="form-control border-info border-5" type="text"
										aria-label="Search" name="keyword" value="<%=keyword%>"
										placeholder="Enter keyword. (Id, name, etc) [Case not sensitive]" />

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
												name="recordsPerPage" min="1" max="100"
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
											(by Department Id):
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
								for searching mask. For example, <b>Marketing%Ekawit
									Diderrich</b>. <br> **Will be remember by session to give you
								a wonderful experience.
							</div>
						</div>
						<div class="col-2">
							<button class="btn btn-primary btn-info btn-lg font-weight-bold float-end" type="submit">Go</button>
						</div>
					</div>

				</form>
			</div>

		</div>

	</div>
	<br> <br>

	<div class="wow fadeInRight" data-wow-duration="1s"
		data-wow-delay="0.5s">
		<div class="row">
			<a type="button"
				onclick='alert("Report generating. Please hold on."); javascript:window.open("<%=response.encodeURL("MainServlet?target=departmentemployee&action=report&verificationToken="
					+ ((String) request.getSession().getAttribute("deverificationToken")))%>", "_blank", "scrollbars=1,resizable=1,height=700,width=600"); '
				class="btn btn-info btn-circle float-end shadow-lg"
				style="border-radius: 30px"><i class="fas fa-file"></i> Report for this current search. Click to download.
			</a>
		</div>
		<br>
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
				List<DepartmentEmployee> des = (List<DepartmentEmployee>) request.getAttribute("departmentEmployeeList");
				if (des.size() != 0) {
					for (DepartmentEmployee de : des) {
			%>

			<tr>
				<td><%=de.getId().toString().substring(34)%></td>
				<td><%=de.getDepartment().getId()%> | <%=de.getDepartment().getDeptName()%></td>

				<td><%=de.getEmployee().getId()%> | <%=de.getEmployee().getFirstName()%>
					<%=de.getEmployee().getLastName()%>
					<button class='btn btn-info btn-sm btntgl float-end'
						data-bs-toggle='collapse'
						data-bs-target='.a<%=de.getId().toString().substring(34)%>'
						aria-expanded='false'>
						<i class='fa fa-chevron-right pull-right'></i><i
							class='fa fa-chevron-down pull-right'></i>
					</button>

					<div class='collapse a<%=de.getId().toString().substring(34)%>'>

						<hr>
						<ul class='list-group'>
							<li class='list-group-item list-group-item-action'>Gender:
								&emsp; <%=(de.getEmployee().getGender() == "M" ? "Male" : "Female")%></li>
							<li class='list-group-item list-group-item-action'>Birth
								date: &emsp;<%=de.getEmployee().getBirthDate()%></li>

							<li class='list-group-item list-group-item-action'>Hire
								date: &emsp;<%=de.getEmployee().getHireDate()%></li>
						</ul>
					</div></td>
				<td><%=de.getFromDate()%></td>
				<td><%=de.getToDate()%></td>
				<td><a
					href='MainServlet?target=departmentemployee&action=update&emp_id=<%=de.getEmployee().getId()%>&dept_id=<%=de.getDepartment().getId()%>'
					class="text-primary"><i class="fas fa-marker"></i>Update</a></td>

				<td><a
					href='MainServlet?target=departmentemployee&action=delete&emp_id=<%=de.getEmployee().getId()%>&dept_id=<%=de.getDepartment().getId()%>'
					class="text-danger"><i class="fas fa-trash-alt"></i>Delete</a></td>

			</tr>
			<%
				}
				} else {
			%>
			<tr>
				<td colspan=7 class='text-center'>No records</td>
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
						href="MainServlet?target=departmentemployee&action=view&recordsPerPage=<%=recordsPerPage%>&currentPage=1&keyword=<%=keyword%>&direction=<%=direction%>">First</a>
					</li>
					<li class="page-item"><a class="page-link"
						href="MainServlet?target=departmentemployee&action=view&recordsPerPage=<%=recordsPerPage%>&currentPage=<%=(currentPage - 1)%>&keyword=<%=keyword%>&direction=<%=direction%>">Previous</a>
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
						href="MainServlet?target=departmentemployee&action=view&recordsPerPage=<%=recordsPerPage%>&currentPage=<%=(currentPage + 1)%>&keyword=<%=keyword%>&direction=<%=direction%>">Next</a>
					</li>

					<li class="page-item"><a class="page-link"
						href="MainServlet?target=departmentemployee&action=view&recordsPerPage=<%=recordsPerPage%>&currentPage=<%=nOfPages%>&keyword=<%=keyword%>&direction=<%=direction%>">Last</a>
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
			<div class='pageref col-6 text-end'><%=(nOfPages!=0?currentPage:0)%>
				of
				<%=nOfPages%></div>

		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>