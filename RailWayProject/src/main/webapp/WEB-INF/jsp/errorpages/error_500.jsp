<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty sessionScope.localize}">
    <fmt:setLocale value="${cookie['localize'].value}"/>
</c:if>
<c:if test="${not empty sessionScope.localize}">
    <fmt:setLocale value="${sessionScope.localize}"/>
</c:if>
<fmt:setBundle basename="messages"/>

<h:responceStatus errorNumber="500"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
</head>
<body>
<h1>500 - Internal Server Error</h1>
<br>The request was not completed. The server met an unexpected condition.
</body>
</html>
