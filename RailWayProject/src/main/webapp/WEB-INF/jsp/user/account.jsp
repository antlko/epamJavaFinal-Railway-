<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="au" uri="/WEB-INF/tld/auth_secure.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<%@ include file="/WEB-INF/static/header.jsp" %>

<div class="tabs-content">
    <div class="tabs">
        <input type="radio" name="tabs" id="tabone" checked="checked">
        <label for="tabone"><fmt:message key="account.history"/></label>
        <div class="tab">
            <table>
                <th><fmt:message key="ticket.date"/> </th>
                <th><fmt:message key="ticket.full_name"/></th>
                <th><fmt:message key="ticket.train"/></th>
                <th><fmt:message key="ticket.carriage"/></th>
                <th><fmt:message key="ticket.seat"/></th>
                <th><fmt:message key="ticket.from"/></th>
                <th><fmt:message key="ticket.destination"/></th>
                <th><fmt:message key="ticket.doc"/></th>
                <th><fmt:message key="ticket.refuse"/></th>
                <c:forEach items="${sessionScope.userChecks}" var="check" varStatus="loop">
                    <tr>
                        <td id="dateFrom">${check.dateEnd}</td>
                        <td id="init">${check.userInitial}</td>
                        <td id="numTrain">${check.numTrain}</td>
                        <td id="numCarr">${check.numCarriage}</td>
                        <td id="numSeat">${check.numSeat}</td>
                        <td id="cityStart">${check.cityStart}</td>
                        <td id="cityEnd">${check.cityEnd}</td>
                        <td>
                            <form action="account" method="get">
                                <input type="hidden" name="action" value="showCheck">
                                <input type="hidden" name="checkInd" value="${loop.index}">
                                <input id="print-doc" class="normal" type="submit" value="Doc">
                            </form>
                        </td>
                        <td>
                            <form action="account" method="post">
                                <input type="hidden" name="action" value="deleteCheck">
                                <input type="hidden" name="checkInd" value="${loop.index}">
                                <input class="danger" type="submit" value="X" style="width: 40px;">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <input type="radio" name="tabs" id="tabtwo">
        <label for="tabtwo"><fmt:message key="account.profile"/></label>
        <div class="tab">
            <div class="login-panel-bg">
                <div class="login-panel-content">
                    <h3><fmt:message key="label.welcome"/>, ${sessionScope.user.login}!</h3>
                    <form action="account" method="post">
                        <input type="hidden" name="action" value="updatePersonal">

                        <div class="edit-tag"><fmt:message key="account.surname"/> :</div>
                        <input type="text" name="Surname" value="${sessionScope.user.surname}">
                        <div class="edit-tag"><fmt:message key="account.name"/> :</div>
                        <input type="text" name="Name" value="${sessionScope.user.name}">
                        <div class="edit-tag"><fmt:message key="auth.email"/> :</div>
                        <input type="text" name="Email" value="${sessionScope.user.email}">
                        <input class="normal" type="submit" value="<fmt:message key="account.save"/>">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
<script src="style/js/qr.js" type="text/javascript"></script>
</html>
