<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%--
	<%! code java %> : khai báo biến
	<% code java %> : Thẻ xử lý logic code
	<%= code java %> : Xuất giá trị của biến ra HTML
 --%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Hello Servlet
	<%! int count = 1; %>
		
		<%
		count++;
		if(count % 2 == 0) { %>
			<h1 style="color: red;"><%= count %><h1>
		<%} else { %>
			<h1 style="color: blue;"><%= count %><h1>
		<%} %>
		

</body>
</html>