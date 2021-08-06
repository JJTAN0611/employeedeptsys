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
		<div class="col"><a type="button" onclick='javascript:window.open("department_report.jsp", "_blank", "scrollbars=1,resizable=1,height=600,width=550");'
				class="btn btn-dark btn-circle float-end"
				style="border-radius: 30px">Download <i
				class="fas fa-file-download"></i>
		</a></div>
		<div class="col"></div>
		
	</div>
	<br> <br>
	
</div>



<%@ include file="footer.jsp"%>
