<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="ua.nure.kn156.kriat.User" scope="session"/>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/edit" method="post">
    <input type="hidden" name="id" value="${user.id}">
    <input type="text" name="firstName" value="${user.firstName}">
    <input type="text" name="lastName" value="${user.lastName}">
    <input type="text" value="<fmt:formatDate value="${user.date}" type="date" dateStyle="medium"/>" name="date">
    <input type="submit" name="ok" value="OK">
    <input type="submit" name="cancel" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>alert("${requestScope.error}")</script>
</c:if>
</body>
</html>
