<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cpath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>oauth2</h1>
<hr>

<script>
	const cpath = 'http://localhost:8080/convenienceStore'
	opener.location.href = '${cpath}${location}'
	window.close()
</script>

</body>
</html>