<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%--===================================================================
              Checking for localisaton
===================================================================--%>
<c:if test="${empty sessionScope.localize}">
    <fmt:setLocale value="${cookie['localize'].value}"/>
</c:if>
<c:if test="${not empty sessionScope.localize}">
    <fmt:setLocale value="${sessionScope.localize}"/>
</c:if>
<fmt:setBundle basename="messages"/>

<html>

<c:set var="title" value="Booking" scope="page"/>
<%--===================================================================
              Including HEAD
===================================================================--%>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<%--===================================================================
              Including HEADER
===================================================================--%>
<%@ include file="/WEB-INF/static/header.jsp" %>
<section class="content">
    <div class="block-content">
        <h1><fmt:message key="ticket.header_name"/></h1>
    </div>
    <%--===================================================================
              Error message field
    ===================================================================--%>
    <h:errorValid error="${sessionScope.errorMessage}"/>

    <div class="block-centered-content">
        <form action="booking" method="POST">
            <%--=================================================
                Action : 'findTickets' will trying get account for user
            ==================================================--%>
            <input type="hidden" name="action" value="findTickets">

            <input id="cityStart" list="cities" type="text" name="cityStart"
                   value="${sessionScope.cityStart}"
                   placeholder="<fmt:message key="ticket.from" />" style="width: 250px">

            <img class="switch_img" src="style/img/reload.png"/>

            <input id="cityEnd" list="cities" type="text" name="cityEnd"
                   value="${sessionScope.cityEnd}"
                   placeholder="<fmt:message key="ticket.destination" />" style="width: 250px">

            <input type="date" name="date" required
                   value="${sessionScope.date}" style="width: 200px">

            <input class="normal" type="submit"
                   value="<fmt:message key="ticket.search" />"/>
        </form>
        <%--===================================================================
                      Cities data list
        ===================================================================--%>
        <datalist id="cities">
            <c:forEach var="c" items="${requestScope.listStation}">
                <option value="${c.name}"></option>
            </c:forEach>
        </datalist>
    </div>
    <div class="block-centered-content">
        <p>${sessionScope.infoBookingMessage}</p>
    </div>
    <%--===================================================================
              Route information
    ===================================================================--%>
    <c:if test="${not empty sessionScope.userRoute}">
        <div class="block-centered-content">
            <table>
                <th><fmt:message key="ticket.train"/></th>
                <th><fmt:message key="ticket.from"/></th>
                <th><fmt:message key="ticket.destination"/></th>
                <th><fmt:message key="ticket.date"/></th>
                <th><fmt:message key="ticket.time_start"/></th>
                <th><fmt:message key="ticket.route"/></th>
                <th><fmt:message key="ticket.travel_time"/></th>
                <th><fmt:message key="ticket.coach_type"/></th>
                <tr>
                    <td>${sessionScope.userRoute.train.number}</td>
                    <td>${sessionScope.cityStart}</td>
                    <td>${sessionScope.cityEnd}</td>
                    <td>${sessionScope.userRoute.dateFrom}</td>
                    <td>${sessionScope.userRoute.timeFrom}</td>
                    <td>
                        <c:forEach var="stat" items="${sessionScope.userRoute.stationList}">
                            ${stat.name}
                        </c:forEach>
                    </td>
                    <td>${sessionScope.userRoute.travelTime}</td>

                    <td>
                            ${sessionScope.seatCheckType}
                    </td>
                </tr>
            </table>

        </div>
        <div class="block-centered-content">
            <h2><fmt:message key="ticket.carriage"/></h2>
        </div>
        <%--===================================================================
              Checking carriage login and carriage information
        ===================================================================--%>
        <div class="block-centered-content">
            <c:forEach var="carriage" items="${sessionScope.serviceCarriage}" varStatus="i">
                <form action="booking" method="GET">
                    <input type="hidden" name="action" value="seatInCarriage">
                    <input type="hidden" name="carrNum" value="${carriage.numCarriage}">
                    <input type="hidden" name="serviceCarriage" value="${sessionScope.serviceCarriage}">

                    <c:if test="${sessionScope.checkedCarriage == carriage.numCarriage}">
                        <input class="danger" type="submit" value="${carriage.numCarriage}">
                    </c:if>
                    <c:if test="${sessionScope.checkedCarriage != carriage.numCarriage}">
                        <input class="normal" type="submit" value="${carriage.numCarriage}">
                    </c:if>
                </form>
            </c:forEach>
        </div>
        <%--===================================================================
              Visualisation carriage
        ===================================================================--%>
        <div class="block-centered-content">
            <ul style="display:none;">
                <c:forEach var="seat" items="${sessionScope.serviceSeats}">
                    <li class="sLi">${seat}</li>
                </c:forEach>
            </ul>
        </div>
        <div class="block-centered-content">
            <input id="rtPrice" type="hidden" value="${sessionScope.routePrice}">
            <div id="holder">
                <ul id="place">
                        <%--=================================================
                               There will be a visual reservation block
                               founded by /seat_reservation.js
                         ==================================================--%>
                </ul>
            </div>
        </div>
        <div class="block-centered-content">
            <h3><fmt:message key="ticket.total_price"/></h3>
            <input id="totalPrice" type="text" value="0" readonly>
        </div>
        <div class="block-centered-content">
            <form action="ordering" method="post">
                <input type="hidden" name="action" value="orderTickets">
                    <%--=================================================
                                There will be dynamic <input> method
                                which should contains information
                                about checked seats
                     ==================================================--%>
                <input type="hidden" name="checkedCarriage" value="${sessionScope.checkedCarriage}">
                <input id="chSeat" type="hidden" name="checkedSeats" value="">
                <input class="normal" type="submit" value="<fmt:message key="ticket.buy"/>"/>
            </form>
        </div>
    </c:if>
</section>
<%--===================================================================
              Including FOOTER
===================================================================--%>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
<script src="style/js/seat_reservation.js" type="text/javascript"></script>
</html>
