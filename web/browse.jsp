<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="message" class="ua.nure.kn156.kriat.util.Message" scope="session"/>
<html>
<head><title>User management/Browse</title></head>
<body>
<fmt:setBundle basename="message" var="lang"/>
<form name="manager" action="<%=request.getContextPath()%>/browse" method="post">
    <table id="userTable" border="1">
        <tr>
            <th></th>
            <th><fmt:message key="label.firstname" bundle="${lang}"/></th>
            <th><fmt:message key="label.lastname" bundle="${lang}"/></th>
            <th><fmt:message key="label.dateofbirth" bundle="${lang}"/></th>
        </tr>
        <c:forEach var="user" items="${sessionScope.users}">
            <tr>
                <td><input type="radio" name="id" id="id" value="${user.id}"></td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.date}</td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" name="add" value="<fmt:message key="button.add" bundle="${lang}"/>">
    <input type="submit" name="edit" value="<fmt:message key="button.edit" bundle="${lang}"/>">
    <input type="submit" name="delete" value="<fmt:message key="button.delete" bundle="${lang}"/>"
           onclick="return deleteUser()">
    <input type="submit" name="details" value="<fmt:message key="button.details" bundle="${lang}"/>">
</form>
<c:if test="${requestScope.error != null}">
    <script>alert("${requestScope.error}")</script>
</c:if>
<script>
    function deleteUser() {
        return confirm("<fmt:message key="options.warning.delete" bundle="${lang}"/>");
    }
</script>
</body>
</html>