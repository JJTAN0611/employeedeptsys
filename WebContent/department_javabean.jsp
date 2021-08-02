<%@page import="model.usebean.DepartmentUseBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body>
	<h1>JAVA BEAN approach</h1>
	<jsp:useBean id="dub" class="model.usebean.DepartmentUseBean" scope="session">
		<jsp:setProperty name="dub" property="*" />
	</jsp:useBean>

	<%
		DepartmentUseBean dubtemp = (DepartmentUseBean) session.getAttribute("dub");
		if (dubtemp.validate(request)) {
	%>
	<jsp:forward page="MainServlet" />
	<%
		} else {
	%>
	<jsp:forward page="department_add.jsp" />
	<%
		}
	%>
</body>
</html>