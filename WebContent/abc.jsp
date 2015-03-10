<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2 align="center" style="color: white" align="center">Crime
		Reports</h2>
	<div id="displayTable" align="center" style="width: 1000;">
		<table id=retrieveSQLData cellpadding="1" cellspacing="1" border="1"
			style="color: white;">
			<thead>
				<tr align="center">
					<th>Address</th>
					<th>Agency</th>
					<th>Date</th>
					<th>Time</th>
				</tr>

			</thead>

			<tbody id="rows" align="center">
				<c:forEach items="${list}" var="val">
					<tr align="center">
						<td><c:out value="${val.address}"></c:out></td>
						<td><c:out value="${val.agency}"></c:out></td>
						<td><c:out value="${val.dateValue}"></c:out></td>
						<td><c:out value="${val.timeValue}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>