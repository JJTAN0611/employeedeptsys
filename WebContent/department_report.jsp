<%@page import="java.util.List"%>
<%@page import="model.entity.Department"%>
<%@ page errorPage="error.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="header.jsp"%>

<div class="container top-first wow fadeIn" data-wow-duration="2s"
	data-wow-delay="0.4s">
	<div class="badge bg-warning text-dark text-wrap large col-12">
		<div class="row">
			<div class="text-start col" style="font-size: 35px;">
				Department Record
				<div class="badge bg-light text-info text-wrap">Report</div>
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
		<div class="text-center">If no any download box prompted,</div>
		<div class="text-center">Please click the below download button.</div>
		<a id="download" type="button"
			href='MainServlet?target=department&action=download'
			class="btn btn-dark btn-circle float-end" style="border-radius: 30px">Download
			<i class="fas fa-file-download"></i>
		</a>

	</div>
	<br> <br>

</div>



<%@ include file="footer.jsp"%>
<script>
	window.onload = function() {
		document.getElementById('download').click();
	}
</script>