<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="header.jsp"%>
<%!String checked;%>
<%
	checked = (String) request.getAttribute("log_refresh");
	if(checked.compareTo("true")==0){
		response.setHeader("Refresh", "3");
	}
%>

<div class="container top-first">
	<h1>
		<div class="badge bg-dark text-info text-wrap large col-12">
			<div class="row">
				<div class="text-start col" style="font-size: 35px;">
					Log
					<div class="badge bg-light text-info text-wrap">view</div>
				</div>
			</div>
		</div>
	</h1>

	<br> <br>

	<div class="row">
		<div class="col"></div>
		<div class="col">
			<h1>The log content.</h1>
		</div>
		<div class="col form-check form-switch h3 align-middle">
			<label class="bg float-end"  style="align-item:center">&nbsp;Auto Refresh </label><input
				class="form-check-input float-end" type="checkbox"
				id="flexSwitchCheckChecked"
				onchange="
					if ($('#flexSwitchCheckChecked').is(':checked')) {
						document.cookie = 'log_refresh=true';
					}else{
						document.cookie = 'log_refresh=false';
					}
					location.reload();
					"
				<%=checked.compareTo("true") == 0 ? "checked" : ""%>></input>

		</div>

	</div>
		<div class="text-end">**This page use cookie to perform better</div>
	<hr>
	<div class="row">
		<div class="col">
			<a type="button" href="MainServlet?table=log&action=delete"
				class="btn btn-danger btn-circle float-start"
				style="border-radius: 30px">Remove <i class="fas fa-trash-alt"></i></a>
		</div>

		<div class="col">
			<a type="button" href="MainServlet?table=log&action=download"
				class="btn btn-dark btn-circle float-end"
				style="border-radius: 30px">Download <i
				class="fas fa-file-download"></i>
			</a>
		</div>
	</div>


	<br> <br>
</div>

<div class="container">
	<div class="row">
		<code class="text-dark">
			<center>---------Start----------</center>
			<%
				String line = "";
				BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Public\\OEDRS.log"));
				int i = 0;
				while ((line = in.readLine()) != null) {
					i++;
					out.println(line);
					out.println("<br>");
				}
				in.close();
				if (i == 0)
					out.println("<center>No any log yet</center>");
				else if (i == 1)
					out.println("<center>---------Total of " + i + " row---------</center>");
				else
					out.println("<center>---------Total of " + i + " rows---------</center>");
			%>
			<center>---------End----------</center>
		</code>
	</div>
</div>

<%@ include file="footer.jsp"%>
