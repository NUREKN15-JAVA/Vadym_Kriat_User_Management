<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Details</title>
</head>
<body>
<fmt:setBundle basename="message" var="lang"/>
<table>
    <tr>
        <th><fmt:message key="label.firstname" bundle="${lang}"/>:</th>
        <td>${sessionScope.user.firstName}</td>
    </tr>
    <tr>
        <th><fmt:message key="label.lastname" bundle="${lang}"/>:</th>
        <td>${sessionScope.user.lastName}</td>
    </tr>
    <tr>
        <th><fmt:message key="label.age" bundle="${lang}"/>:</th>
        <td>${sessionScope.user.getAge()}</td>
    </tr>
</table>
<form action="<%=request.getContextPath()%>/details" method="post">
    <input type="submit" name="back" value="<fmt:message key="button.close" bundle="${lang}"/>:">
</form>
</body>
</html>
