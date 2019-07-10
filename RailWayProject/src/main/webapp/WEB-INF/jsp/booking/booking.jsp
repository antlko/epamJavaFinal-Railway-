<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<c:set var="title" value="Booking" scope="page"/>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<%@ include file="/WEB-INF/static/header.jsp" %>
<section class="content">
    <div class="block-content">
        <h1>Buy Tickets</h1>
    </div>
    <div class="block-centered-content">
        <form action="booking" method="POST">
            <%--=================================================
                Action : 'findTickets' will trying get account for user
            ==================================================--%>
            <input type="hidden" name="action" value="findTickets">

            <input id="cityStart" list="cities" type="text" name="cityStart"
                   value="${sessionScope.cityStart}" placeholder="From" style="width: 250px">
            <img class = "switch_img" src="style/img/reload.png"/>
            <input id="cityEnd" list="cities" type="text" name="cityEnd"
                   value="${sessionScope.cityEnd}" placeholder="Destination" style="width: 250px">
            <input type="date" name="date" required
                   value="${sessionScope.date}" style="width: 200px">

            <input class="normal" type="submit" value="Submit"/>
        </form>
        <datalist id="cities">
            <c:forEach var="c" items="${requestScope.listStation}">
                <option value="${c.name}"></option>
            </c:forEach>
        </datalist>
    </div>
    <div class="block-centered-content">
        <p>${sessionScope.infoBookingMessage}</p>
    </div>
    <c:if test="${not empty sessionScope.infoRoutes}">
        <div class="block-centered-content">
            <table>
                <th>Train</th>
                <th>From</th>
                <th>Destination</th>
                <th>Date</th>
                <th>Time</th>
                <th>Your route</th>
                <th>Travel time</th>
                <th>Coach type</th>
                <th>Free seats</th>
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
                            Show route
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
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
<script src="style/js/tooltip.js" type="text/javascript"></script>
<script src="style/js/switch_cities.js" type="text/javascript"></script>
</html>
