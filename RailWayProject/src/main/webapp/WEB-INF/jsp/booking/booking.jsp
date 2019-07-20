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
                  Error Message field
    ===================================================================--%>
    <h:errorValid error="${sessionScope.errorMessage}"/>

    <div class="block-centered-content">
        <form action="booking" method="POST">
            <%--=================================================
                Action : 'findTickets' will trying get account for user
            ==================================================--%>
            <input type="hidden" name="action" value="findTickets">

            <input id="cityStart" list="cities" type="text" name="cityStart" required
                   value="${sessionScope.cityStart}"
                   placeholder="<fmt:message key="ticket.from" />" style="width: 250px">

            <img class="switch_img" src="style/img/reload.png"/>

            <input id="cityEnd" list="cities" type="text" name="cityEnd" required
                   value="${sessionScope.cityEnd}"
                   placeholder="<fmt:message key="ticket.destination" />" style="width: 250px">

            <input type="date" name="date" required
                   value="${sessionScope.date}" style="width: 200px">

            <input class="normal" type="submit"
                   value="<fmt:message key="ticket.search" />"/>
        </form>
        <%--===================================================================
              Data List for cities
        ===================================================================--%>
        <datalist id="cities">
            <c:forEach var="c" items="${requestScope.listStation}">
                <option value="${c.name}"></option>
            </c:forEach>
        </datalist>
    </div>

    <%--===================================================================
              Table which contains information about route,
              carriage, type, free seats etc.
    ===================================================================--%>
    <div class="block-centered-content">
        <p>${sessionScope.infoBookingMessage}</p>
    </div>
    <c:if test="${not empty sessionScope.infoRoutes}">
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
                <th><fmt:message key="ticket.free"/></th>
                <c:forEach var="route" items="${sessionScope.infoRoutes}" varStatus="loop">
                    <tr>
                        <td>${route.train.number}</td>
                        <td>${sessionScope.cityStart}</td>
                        <td>${sessionScope.cityEnd}</td>
                        <td>${route.dateFrom}</td>
                        <td>${route.timeFrom}</td>
                        <td data-tooltip="
                            <ol>
                            <c:forEach var="stat" items="${route.stationList}">
                                <li>${stat.name}</li>
                            </c:forEach>
                             </ol>">
                            <fmt:message key="ticket.show_route"/>
                        </td>
                        <td>${route.travelTime}</td>

                        <td>
                            <c:forEach var="seat" items="${route.seatList}">
                                <p>${seat.seatType}</p>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="seat" items="${route.seatList}">
                                <p>
                                        <%--===================================================================
                                                      Open this type of carriage/s for ordering seats
                                        ===================================================================--%>
                                <form action="booking" method="GET">

                                    <input type="hidden" name="action" value="toOrdering">
                                    <input type="hidden" name="checkedRouteForUser" value="${loop.index}"/>
                                    <input type="hidden" name="seatCheckType" value="${seat.seatType}"/>
                                    <input type="hidden" name="dateFrom" value="${route.dateFrom}">
                                    <input type="hidden" name="timeFrom" value="${route.timeFrom}">
                                    <input class="checkButton" type="submit" value="${seat.free}">
                                </form>
                                </p>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</section>
<%--===================================================================
              Including FOOTER
===================================================================--%>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
<script src="style/js/tooltip.js" type="text/javascript"></script>
<script src="style/js/switch_cities.js" type="text/javascript"></script>
</html>
