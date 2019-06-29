<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<c:set var="title" value="Account" scope="page"/>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<%@ include file="/WEB-INF/static/header.jsp" %>

<h2>Buy tickets</h2>
${requestScope.infoBookingMessage}</br>
<form action="booking" method="POST">
    <%--=================================================
        Action : 'findTickets' will trying get account for user
    ==================================================--%>
    <input type="hidden" name="action" value="findTickets">

    <input type="text" name="cityFrom" placeholder="From" style="width: 250px">
    < - >
    <input type="text" name="cityDestination" placeholder="Destination" style="width: 250px">
    <input type="date" required style="width: 200px">

    <input type="submit" value="Submit"/>
</form>

<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
</html>
