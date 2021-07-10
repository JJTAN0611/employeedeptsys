<%@page import="java.util.List"%>
<%@page import="model.entity.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%	int currentPage=(int) request.getAttribute("currentPage"); 
	int recordsPerPage=(int)request.getAttribute("recordsPerPage");
	int nOfPages=(int) request.getAttribute("nOfPages"); 
	String keyword=(String) request.getAttribute("keyword");
	 String direction=(String)request.getAttribute("direction"); 
	 String table=(String)request.getAttribute("table"); 
	 String colour[]=new String[2];
	 switch(table){
	 case "Department":colour[0]="warning";break;
	 case "Department_Employee":colour[0]="primary";colour[1]="white";break;
	 case "Employee":colour[0]="success";colour[1]="white";break;
	 default: colour[0]="dark"; colour[1]="white";
	 }
%>		
<%@ include file="header.html" %>  
	<br>
	<br>
	<br>
	<br>
	<br>
	<div class="container">
		<div class="row">
			<div class='"badge bg-<%=colour[0]%> text-<%=colour[1] %> text-wrap large col-12 col-md"'>
				<h1><%=table%></h1>
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
			<form class="form-inline md-form mr-auto" action="EmployeePaginationServlet"
				method="get">
				<div class="input-group col-sm">
					<input class="form-control" type="text" aria-label="Search"
						name="keyword" value="<%=keyword%>" />
					<div class="input-group-append">
						<button class="btn btn-primary btn-info" type="submit">Search</button>
					</div>
				</div>
				<input type="hidden" name="currentPage" value="<%=currentPage%>" />
				<input type="hidden" name="table" value="<%=table%>" />
				<input type="hidden" name="recordsPerPage"
					value="<%=recordsPerPage%>" /> <input type="hidden"
					name="direction" value="<%=direction%>" />
			</form>
		</div>
		<br>
		<div class="row">
			<div class="col">
				<form class="form-inline md-form mr-auto" action="EmployeePaginationServlet">
					<div class="input-group col-sm">
						<select class="form-control" id="records" name="recordsPerPage">
							<option value="20">20</option>
							<option value="50" selected>50</option>
							<option value="70" selected>70</option>
							<option value="100">100</option>
						</select>
						<div class="input-group-append">
							<button class="btn btn-primary btn-info" type="submit">Records
								per page</button>
						</div>
					</div>
					<input type="hidden" name="table" value="<%=table%>" />
					<input type="hidden" name="currentPage" value="<%=currentPage%>" />
					<input type="hidden" name="recordsPerPage"
						value="<%=recordsPerPage%>" /> <input type="hidden"
						name="direction" value="<%=direction%>" /> <input type="hidden"
						class="form-control" type="text" aria-label="Search"
						name="keyword" value="<%=keyword%>" />
				</form>
			</div>
			<div class="col"></div>
			<div class="col">
				<form class="form-inline md-form mr-auto" action="EmployeePaginationServlet"
					method="get">
					<div class="input-group col-sm">
						<select class="form-control custom-select" id="direction"
							name="direction">
							<% if (direction.compareTo("DESC")==0) {
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
						<fieldset>
						<input type="hidden" name="table" value="<%=table%>" />
						<input type="hidden" name="currentPage" value="<%=currentPage%>" />
						<input type="hidden" name="recordsPerPage"
							value="<%=recordsPerPage%>" /> <input type="hidden"
							name="keyword" value="<%=keyword%>" />
						</fieldset>
				</form>
			</div>
		</div>
		<br> <br>
		<div class="card">
			<div class="table-responsive">
				<table class="table table-bordered table-striped table-hover">
					<tr class="table-dark">
						<th  scope="col">ID</th>
						<th  scope="col">DOB</th>
						<th  scope="col">First Name</th>
						<th  scope="col">Last Name</th>
						<th scope="col">Gender</th>
						<th scope="col">Hired Date</th>
						<th  colspan=2 scope="col" ><center>Method</center></th>
						</tr>
					<tr class="table-info">
					<form class="form-container" action="EmployeeController" method="post">
					<fieldset>
						<td  scope="col"><input class="form-control" type="text" class="input-group" size="8"  /></td>
						<td  scope="col"><input class="form-control" type="text" class="input-group" size="8" name="dob"/></td>
						<td  scope="col"><input class="form-control" type="text" class="input-group" size="8" name="fname"/></td>
						<td  scope="col"><input class="form-control" type="text" class="input-group" size="8" name="lname"/></td>
						<td scope="col"><input class="form-control" type="text"	class="input-group" size="2" name="gender"/></td>
						<td scope="col"><input class="form-control" type="text" class="input-group" size="8" name="hdate"/></td>
						<td  colspan=2 scope="col" ><center><button type="submit" class="btn btn-primary btn-rounded">
					<strong>+</strong> Add Record
				</button></center></td>
					</fieldset>
				</form>
						</tr>
					<% List<Employee> staffs = (List<Employee>) request.getAttribute("staffs");
							if (staffs.size() != 0) {
								for (Employee t : staffs) {
									out.println("<tr>");
									out.println("<td>" + t.getId() + "</td>");
									out.println("<td>" + t.getBirthDate() + "</td>");
									out.println("<td>" + t.getFirstName() + "</td>");
									out.println("<td>" + t.getLastName() + "</td>");
									out.println("<td>" + t.getGender() + "</td>");
									out.println("<td>" + t.getHireDate() + "</td>");
									out.println("<td><a href=\"EmployeeController?id=" + t.getId() + "\" class=\"text-success\" ><i class=\"fas fa-marker\"></i>Update</a></td>");
									out.println("<td><a href=\"EmployeeController?id=" + t.getId() + "\" class=\"text-danger\" ><i class=\"fas fa-trash-alt\"></i>Delete</a></td>");
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
			<nav aria-label="Navigation for staffs">
				<ul class="pagination">
					<% if (currentPage !=1 && nOfPages !=0) { %>
						<% out.println("<li class=\"page-item\">");
							out.println("<a class=\"page-link bg-secondary text-light\" href=\"" + "EmployeePaginationServlet?recordsPerPage=" +
								recordsPerPage + "&currentPage=1" + "&keyword=" + keyword + "&direction=" + direction +  "&table="+table
								+ "\">First</a>");
							out.println("</li>");
							%>
							<li class="page-item">
								<% out.println("<a class=\"page-link bg-success text-light\" href=\"" + "EmployeePaginationServlet?recordsPerPage=" +
									recordsPerPage + "&currentPage=" + (currentPage - 1) + "&keyword=" + keyword+ "&direction=" + direction + "&table="+table
									+ "&direction=" + direction + "\">Previous</a>");
									%>
							</li>
							<% } %>
								<%%>
									<%if (currentPage < nOfPages) {
				out.println("<li class=\"page-item\">");
				out.println("<a class=\"page-link bg-success text-light\" href=\"" + "EmployeePaginationServlet?recordsPerPage=" + recordsPerPage
						+ "&currentPage=" + (currentPage + 1) + "&keyword=" + keyword + "&direction=" + direction +  "&table="+table
						+ "\">Next</a>");
				out.println("</li>");
				out.println("<li class=\"page-item\">");
				out.println("<a class=\"page-link bg-secondary text-light\" href=\"" + "EmployeePaginationServlet?recordsPerPage=" + recordsPerPage
						+ "&currentPage=" + nOfPages + "&keyword=" + keyword + "&direction=" + direction + "&table=" +table
						+ "\">Last</a>");
				out.println("</li>");
			}%>
				</ul>
			</nav>
			<%if (nOfPages != 0) {
				out.println("<p class=\"pageref\">");
				out.println(currentPage + " of " + nOfPages);
				out.println("</p>");
			}%>
		</div>
	</div>



<%@ include file="footer.html" %>  