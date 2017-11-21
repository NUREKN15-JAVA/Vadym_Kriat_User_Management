<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="ua.nure.kn156.kriat.User" scope="session"/>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
<fmt:setBundle basename="message" var="lang"/>
<form action="<%=request.getContextPath()%>/edit" method="post">
    <table>
        <tr>
            <th><label for="fn"><fmt:message key="label.firstname" bundle="${lang}"/>:</label></th>
            <td><input id="fn" type="text" name="firstName" value="${user.firstName}"></td>
        </tr>
        <tr>
            <th><label for="ln"><fmt:message key="label.lastname" bundle="${lang}"/>:</th>
            <td><input id="ln" type="text" name="lastName" value="${user.lastName}"></td>
        </tr>
        <tr>
            <th><label for="date"><fmt:message key="label.dateofbirth" bundle="${lang}"/>:</th>
            <td><input id="date" type="text"
                       value="<fmt:formatDate value="${user.date}" type="date" dateStyle="medium"/>" name="date"></td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${user.id}">
    <input type="submit" name="ok" value="OK">
    <input type="submit" name="cancel" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>alert("${requestScope.error}")</script>
</c:if>
</body>
</html>
