<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Details</title>
</head>
<body>
<table>
    <tr>
        <th>First name:</th>
        <td>${sessionScope.user.firstName}</td>
    </tr>
    <tr>
        <th>Last name:</th>
        <td>${sessionScope.user.lastName}</td>
    </tr>
    <tr>
        <th>Age:</th>
        <td>${sessionScope.user.getAge()}</td>
    </tr>
</table>
<form action="<%=request.getContextPath()%>/details" method="post">
    <input type="submit" name="back" value="Back">
</form>
</body>
</html>
