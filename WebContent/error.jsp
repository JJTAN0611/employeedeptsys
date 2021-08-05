<%@ page errorPage = "404.jsp" %>
<%@ include file="header.jsp"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>

<div class="main-banner wow fadeIn top-first" id="top"
	data-wow-duration="1s" data-wow-delay="0.5s">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="row">
					<div class="col-lg-6 align-self-center">
						<div class="left-content header-text wow fadeInLeft"
							data-wow-duration="1s" data-wow-delay="1s">
							<h2>
								<em>Invalid </em><span>Request</span><em>...</em>
							</h2>
							<b> You are using invalid request. Don't try to do URL sniffing attack!</b>
							<hr>
							<b>Your request <%=request.getAttribute("filtered") %></b><br><br>
							<%
								Map map = request.getParameterMap();
								Set keSet = map.entrySet();
								for (Iterator itr = keSet.iterator(); itr.hasNext();) {
									Map.Entry me = (Map.Entry) itr.next();
									Object ok = me.getKey();
									Object ov = me.getValue();
									String[] value = new String[1];
									if (ov instanceof String[]) {
										value = (String[]) ov;
									} else {
										value[0] = ov.toString();
									}

									for (int k = 0; k < value.length; k++) {
										out.println(ok + " = " + value[k]+"<br>");
									}
								}
							%>
							<br>
							<b>is invalid!</b>
							<br>
							<hr>
							<b> If you think there is an error. Feel free to contact +6011-38100852</b>
							<br><br><br>
							<div class="main-red-button">
								<a href="index.jsp">Back to home page now.</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>
