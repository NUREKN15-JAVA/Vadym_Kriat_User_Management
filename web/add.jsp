<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add user</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/add" method="post">
    <input type="text" name="firstName">
    <input type="text" name="lastName">
    <input type="text" name="date">
    <input type="submit" name="ok" value="OK">
    <input type="submit" name="cancel" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>alert("${requestScope.error}")</script>
</c:if>
</body>
</html>
