<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Add user</title>
</head>
<body>
<fmt:setBundle basename="message" var="lang"/>
<form action="<%=request.getContextPath()%>/add" method="post">
    <table>
        <tr>
            <th><label for="fn"><fmt:message key="label.firstname" bundle="${lang}"/>:</label></th>
            <td><input id="fn" type="text" name="firstName"></td>
        </tr>
        <tr>
            <th><label for="ln"><fmt:message key="label.lastname" bundle="${lang}"/>:</th>
            <td><input id="ln" type="text" name="lastName"></td>
        </tr>
        <tr>
            <th><label for="date"><fmt:message key="label.dateofbirth" bundle="${lang}"/>:</th>
            <td><input id="date" type="text" name="date"></td>
        </tr>
    </table>
    <input type="submit" name="ok" value="<fmt:message key="button.ok" bundle="${lang}"/>">
    <input type="submit" name="cancel" value="<fmt:message key="button.cancel" bundle="${lang}"/>">
</form>
<c:if test="${requestScope.error != null}">
    <script>alert("${requestScope.error}")</script>
</c:if>
</body>
</html>
