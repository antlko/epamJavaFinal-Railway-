<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty sessionScope.localize}">
    <fmt:setLocale value="${cookie['localize'].value}"/>
</c:if>
<c:if test="${not empty sessionScope.localize}">
    <fmt:setLocale value="${sessionScope.localize}"/>
</c:if>
<fmt:setBundle basename="messages"/>

<h:responceStatus errorNumber="404"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
</head>
<body>
<h1>404 - Not Found</h1>
<br>The server can not find the requested page.
</body>
</html>
