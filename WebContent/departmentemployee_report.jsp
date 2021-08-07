<%@ page errorPage="error.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ include file="header.jsp"%>

<div class="container top-first wow fadeIn" data-wow-duration="2s"
	data-wow-delay="0.4s">
	<div class="badge bg-primary text-dark text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Department Employee Record
				<div class="badge bg-light text-info text-wrap">Report</div>
			</div>
		</div>
	</div>
	<br> <br>
	<hr>
	<br> <br>
</div>
<%
	if (((String) request.getSession().getAttribute("dereportVerify")).equals("true")) {
%>
<div class="container  wow fadeInLeft" data-wow-duration="1s"
	data-wow-delay="0.5s">
	<div class="row">
		<div class="text-center">If no any download box prompted,</div>
		<div class="text-center">Please click the below download button.</div>
		<a id="download" type="button"
			href='<%=response.encodeURL("MainServlet?target=departmentemployee&action=download&verificationToken="
						+ ((String) request.getSession().getAttribute("deverificationToken")))%>'
			class="btn btn-dark btn-circle float-end" style="border-radius: 30px">Download
			<i class="fas fa-file-download"></i>
		</a>
	</div>
	<hr>
	<div class="row">
		<div class="text-center">
			<h2>
				<u>The summary</u>
			</h2>
		</div>
		<div class="text-center">
			Keyword Filter:
			<%=request.getSession().getAttribute("dekeyword").equals("")
						? "No filter"
						: request.getSession().getAttribute("dekeyword")%><br>
			Order Direction:
			<%=request.getSession().getAttribute("dedirection")%><br> Total
			Records:
			<%=request.getSession().getAttribute("departmentEmployeeReportSize")%><br>
		</div>
	</div>
	<br>
	<hr>
	<div class="text-center">
		**URL encoding is used for compulsory session tracking.<br> The
		session ID:
		<%=request.getSession().getId()%><br> The unique ID:
		<%=((String) request.getParameter("verificationToken"))%>
	</div>
	<br> <br>

</div>
<%
	} else {
%>
<div class="text-center">
	<h2>
		Please close this window and try again.<br> Remember! Do one
		things at one time.
	</h2>
	The unique ID:
	<%=((String) request.getParameter("verificationToken"))%>
	is invalid.
</div>
<%
	}
%>


<%@ include file="footer.jsp"%>
<script>
	window.onload = function() {
		document.getElementById('download').click();
	}
</script>