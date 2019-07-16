<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="au" uri="/WEB-INF/tld/auth_secure.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:if test="${empty sessionScope.localize}">
    <fmt:setLocale value="${cookie['localize'].value}"/>
</c:if>
<c:if test="${not empty sessionScope.localize}">
    <fmt:setLocale value="${sessionScope.localize}"/>
</c:if>
<fmt:setBundle basename="messages"/>


<html>

<c:set var="title" value="Account" scope="page"/>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<au:AuthSecure/>

<h:errorValid error="${sessionScope.errorMessage}"/>
<div class="tabs-content">
    <table>
        <th>QR</th>
        <th>Date</th>
        <th>Full name</th>
        <th>Train</th>
        <th>Carriage</th>
        <th>Seat</th>
        <th>From</th>
        <th>Destination</th>
            <tr>
                <td><img id="qr" src=""></td>
                <td id="dateFrom">${requestScope.checkInfo.dateEnd}</td>
                <td id="init">${requestScope.checkInfo.userInitial}</td>
                <td id="numTrain">${requestScope.checkInfo.numTrain}</td>
                <td id="numCarr">${requestScope.checkInfo.numCarriage}</td>
                <td id="numSeat">${requestScope.checkInfo.numSeat}</td>
                <td id="cityStart">${requestScope.checkInfo.cityStart}</td>
                <td id="cityEnd">${requestScope.checkInfo.cityEnd}</td>
            </tr>
    </table>
</div>
</body>
<script src="style/js/qr.js" type="text/javascript"></script>
</html>
