<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>User management/Browse</title></head>
<body>
<form name="manager" action="<%=request.getContextPath()%>/browse" method="post">
    <table id="userTable" border="1">
        <tr>
            <th></th>
            <th>First name</th>
            <th>Last name</th>
            <th>Date of birth</th>
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
    <input type="submit" name="add" value="Add">
    <input type="submit" name="edit" value="Edit">
    <input type="submit" name="delete" value="Delete" onclick="deleteUser()">
    <input type="submit" name="details" value="Details">
</form>
<c:if test="${requestScope.error != null}">
    <script>alert("${requestScope.error}")</script>
</c:if>
<script>
    function deleteUser() {
        if (document.getElementById("id").checked) {
            if (confirm("Are you sure to delete this user?")) {
                document.manager.key.value = "delete";
                document.manager.submit();
            }
        }
    }
</script>
</body>
</html>