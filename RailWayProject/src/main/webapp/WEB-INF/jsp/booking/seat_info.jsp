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

            <input list="cities" type="text" name="cityStart"
                   value="${sessionScope.cityStart}" placeholder="From" style="width: 250px">
            < - >
            <input list="cities" type="text" name="cityEnd"
                   value="${sessionScope.cityEnd}" placeholder="Destination" style="width: 250px">
            <input type="date" name="date" required
                   value="${sessionScope.date}" style="width: 200px">

            <input type="submit" value="Submit"/>
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
    <c:if test="${not empty sessionScope.userRoute}">
        <div class="block-centered-content">
            <table border="1">
                <th>Train</th>
                <th>From</th>
                <th>Destination</th>
                <th>Date</th>
                <th>Time</th>
                <th>Your route</th>
                <th>Travel time</th>
                <th>Coach type</th>
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
            <h2>Carriages</h2>
        </div>
        <div class="block-centered-content">
            <c:forEach var="carriage" items="${sessionScope.serviceCarriage}">
                <form action="booking" method="GET">
                    <input type="hidden" name="action" value="seatInCarriage">
                    <input type="hidden" name="carrNum" value="${carriage.numCarriage}">
                    <input type="hidden" name="serviceCarriage" value="${sessionScope.serviceCarriage}">
                    <input type="submit" value="${carriage.numCarriage}">
                </form>
            </c:forEach>
        </div>
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
            <h3>Total price :</h3>
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
                <input id="chSeat" type="hidden" name="checkedSeats" value="">
                <input type="submit" value="Buy"/>
            </form>
        </div>
    </c:if>
</section>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
<script src="style/js/seat_reservation.js" type="text/javascript"></script>
</html>
