<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>User management</title></head>
<body>
<fmt:setBundle basename="message" var="lang"/>
<h1><fmt:message key="title" bundle="${lang}"/></h1>
<form action="<%=request.getContextPath()%>/browse" method="get">
    <input type="submit" name="next" value="<fmt:message key="button.next" bundle="${lang}"/>">
</form>
</body>
</html>