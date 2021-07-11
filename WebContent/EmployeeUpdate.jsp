<%@page import="model.entity.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp" %>  
	<%
		Employee emp = (Employee) request.getAttribute("EMP");
	%>
	<form action="EmployeeController" method="post">
		<table>
			<tr>
				<td>Employee ID</td>
				<td>
					<%
						out.println("<input type=\"text\" name=\"id\" readonly value=" + emp.getId());
					%>
				</td>
			</tr>
			<tr>
				<td>DOB</td>
				<td>
					<%
						out.println("<input type=\"text\" name=\"dob\" value=" + emp.getBirthDate());
					%>
				</td>
			</tr>
			<tr>
				<td>First Name</td>
				<td>
					<%
						out.println("<input type=\"text\" name=\"fname\" value=" + emp.getFirstName());
					%>
				</td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td>
					<%
						out.println("<input type=\"text\" name=\"lname\" value=" + emp.getLastName());
					%>
				</td>
			</tr>
			<tr>
				<td>Gender</td>
				<td>
					<%
						out.println("<input type=\"text\" name=\"gender\" value=" + emp.getGender());
					%>
				</td>
			</tr>
			<tr>
				<td>Hired Date</td>
				<td>
					<%
						out.println("<input type=\"text\" name=\"hdate\" value=" + emp.getHireDate());
					%>
				</td>
			</tr>
		</table>
		<input type="submit" name="UPDATE" value="UPDATE" /> <input
			type="submit" name="DELETE" value="DELETE" />
	</form>
<%@ include file="footer.jsp" %>  