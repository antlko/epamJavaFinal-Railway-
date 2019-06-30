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
    <c:if test="${not empty sessionScope.infoRoutes}">
        <div class="block-centered-content">
            <table border="1">
                <th>Train</th>
                <th>From</th>
                <th>Destination</th>
                <th>Date</th>
                <th>Your route</th>
                <th>Travel time</th>
                <th></th>
                <c:forEach var="route" items="${sessionScope.infoRoutes}">
                    <tr>
                        <td>${route.train.number}</td>
                        <td>${sessionScope.cityStart}</td>
                        <td>${sessionScope.cityEnd}</td>
                        <td>${sessionScope.date}</td>
                        <td>
                            <c:forEach var="stat" items="${route.stationList}">
                                ${stat.name}
                            </c:forEach>
                        </td>
                        <td>${route.travelTime}</td>
                        <td><a href="#">Buy</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</section>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
</html>
