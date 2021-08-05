<%@ page errorPage="error.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp"%>
<style>
.progress-bar {
	-webkit-transition: none !important;
	transition: none !important;
}
</style>
<div class="container top-first">
	<h2 class="text-center">
		Your <span class="text-info"><%=request.getAttribute("action")%></span>
		action to <span class="text-info"><%=request.getAttribute("target")%></span>
		is successful.
	</h2>
	<br>
	<hr>
	<br>
	<h3 class="text-center">
		Redirecting to
		<%=request.getAttribute("target")%>
		view page... <span id="countdown">5</span>
	</h3>
	<br>
	<div class="progress">
		<div
			class="progress-bar progress-bar-striped bg-info progress-bar-animated"
			role="progressbar" style="width: 0%" aria-valuenow="0"
			aria-valuemin="0" aria-valuemax="100"></div>
	</div>
	<br>
	<div class="text-center">
		<a class="btn btn-dark"
			href="MainServlet?target=<%=request.getAttribute("target")%>&action=view">Skip</a>
	</div>
	<br> <br>
	<h2 class="text-center">Have a nice day!</h2>
	<br> <br> <br>
</div>
<%@ include file="footer.jsp"%>
<SCRIPT type="text/javascript">
	$(document).ready(function() {
		var counter = 5;
		var interval = setInterval(function() {
		    counter--;
		    // Display 'counter' wherever you want to display it.
		    if (counter < 0) {
		    	window.location.href = "MainServlet?target=<%=request.getAttribute("target")%>&action=view";
									} else {
										$('#countdown').html(counter);
									}
								}, 1000);

					});
	$(document).ready(function() {
		$(".progress-bar").animate({
			width : "100%"
		}, 5000);

	});
</SCRIPT>