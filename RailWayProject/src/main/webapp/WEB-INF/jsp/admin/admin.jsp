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

<c:set var="title" value="Admin" scope="page"/>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<%----%>
<au:AuthSecure/>
<%@ include file="/WEB-INF/static/header.jsp" %>
<section class="content">

    <div class="block-content">
        <h1><fmt:message key="admin.admin_panel"/></h1>
    </div>
    <h:errorValid error="${sessionScope.errorMessage}"/>
    <div class="tabs-content">
        <div class="tabs">
            <input type="radio" name="tabs" id="tab_1" value=1
                   <c:if test="${sessionScope.tab == 1 or empty sessionScope.tab}">checked</c:if>
            />
            <label for="tab_1"><fmt:message key="admin.personal"/></label>
            <div class="tab">
                <div class="login-panel-bg">
                    <div class="login-panel-content">
                        <h3><fmt:message key="admin.change_user_role"/></h3>
                        <form action="admin">
                            <input type="hidden" name="action" value="changeUser">
                            <input type="hidden" name="checkVal" value="1">

                            <div class="edit-tag"><fmt:message key="auth.login"/> :</div>
                            <input type="text" name="loginUser">

                            <div class="edit-tag"><fmt:message key="admin.role"/> :</div>
                            <select name="tagRole">
                                <option>USER</option>
                                <option>ADMIN</option>
                            </select>
                            <input class="normal" type="submit" name="updateUserInfo" value="Save">
                            <input class="danger" type="submit" name="updateUserInfo" value="Delete">
                        </form>
                    </div>
                </div>
            </div>

            <input type="radio" name="tabs" id="tab_2" value=2
                   <c:if test="${sessionScope.tab == 2}">checked</c:if>
            />
            <label for="tab_2"><fmt:message key="admin.stations"/></label>
            <div class="tab">
                <%--=================================================
                               Country Settings
                    ==================================================--%>
                <h3><fmt:message key="admin.country_info"/></h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeCountry">
                    <input type="hidden" name="checkVal" value="2">

                    <div class="edit-tag"><fmt:message key="admin.country"/> :</div>
                    <input type="text" name="countryName">
                    <input class="normal" type="submit" name="changeCountryInfo" value="Save">
                    <input class="danger" type="submit" name="changeCountryInfo" value="Delete">
                </form>
                <input class="middle sidebar-country" type="submit" name="showCountry" value="Show All">
                <br>
                <div id="sidebar-country" class="sidebar">
                    <h3><fmt:message key="admin.all_available_country"/></h3>
                    <c:forEach items="${requestScope.allCountryInfo}" var="country">
                        <li>${country.name}</li>
                    </c:forEach>
                </div>
                <%--=================================================
                              City Settings
                    ==================================================--%>
                <h3><fmt:message key="admin.cities_info"/></h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeCity">
                    <input type="hidden" name="checkVal" value="2">

                    <div class="edit-tag"><fmt:message key="admin.city_name"/> :</div>
                    <input type="text" name="cityName">

                    <div class="edit-tag"><fmt:message key="admin.country"/> :</div>
                    <select name="tagCountries">
                        <c:forEach items="${requestScope.allCountryInfo}" var="country">
                            <option value="${country.name}">${country.name}</option>
                        </c:forEach>
                    </select>
                    <input class="normal" type="submit" name="changeCityInfo" value="Save">
                    <input class="danger" type="submit" name="changeCityInfo" value="Delete">
                </form>
                <input class="middle sidebar-city" type="submit" name="showCity" value="Show All">
                <br>
                <div id="sidebar-city" class="sidebar">
                    <h3><fmt:message key="admin.all_available_cities"/></h3>
                    <c:forEach items="${requestScope.allCityInfo}" var="city">
                        <li>${city.name}</li>
                    </c:forEach>
                </div>
                <%--=================================================
                           Station settings
                ==================================================--%>
                <h3><fmt:message key="admin.station_info"/></h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeStation">
                    <input type="hidden" name="checkVal" value="2">

                    <div class="edit-tag"><fmt:message key="admin.station"/> :</div>
                    <input type="text" name="stationName">

                    <div class="edit-tag"><fmt:message key="admin.city_name"/> :</div>
                    <select name="tagCities">
                        <c:forEach items="${requestScope.allCityInfo}" var="city">
                            <option value="${city.name}">${city.name}</option>
                        </c:forEach>
                    </select>
                    <input class="normal" type="submit" name="changeStationInfo" value="Save">
                    <input class="danger" type="submit" name="changeStationInfo" value="Delete">
                </form>
                <input class="middle sidebar-station" type="submit" name="showStation" value="Show All">
                <br>
                <div id="sidebar-station" class="sidebar">
                    <h3><fmt:message key="admin.all_available_station"/></h3>
                    <c:forEach items="${requestScope.allStationInfo}" var="station">
                        <li>${station.name}</li>
                    </c:forEach>
                </div>
            </div>

            <input type="radio" name="tabs" id="tab_3" value=3
                   <c:if test="${sessionScope.tab == 3}">checked</c:if>
            />
            <label for="tab_3"><fmt:message key="admin.train"/></label>
            <div class="tab">
                <%--=================================================
                           Train settings
                ==================================================--%>
                <h3><fmt:message key="admin.train_info"/></h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeTrain">
                    <input type="hidden" name="checkVal" value="3">

                    <div class="edit-tag"><fmt:message key="admin.train_number"/> :</div>
                    <input type="text" name="trainNumber">

                    <input class="normal" type="submit" name="changeTrainInfo" value="Save">
                    <input class="danger" type="submit" name="changeTrainInfo" value="Delete">
                </form>
                <input class="middle sidebar-train" type="submit" name="showTrain" value="Show All">
                <br>
                <div id="sidebar-train" class="sidebar">
                    <h3><fmt:message key="admin.all_available_train"/></h3>
                    <c:forEach items="${requestScope.allTrainInfo}" var="train">
                        <li>${train.number}</li>
                    </c:forEach>
                </div>
                <%--=================================================
                   Carriage-type settings
                 ==================================================--%>
                <h3><fmt:message key="admin.carr_type_info"/></h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeType">
                    <input type="hidden" name="checkVal" value="3">

                    <div class="edit-tag"><fmt:message key="admin.carr_name"/> :</div>
                    <input type="text" name="typeName">

                    <div class="edit-tag"><fmt:message key="admin.carr_price"/> :</div>
                    <input type="text" name="typePrice">

                    <input class="normal" type="submit" name="changeTypeInfo" value="Save">
                    <input class="danger" type="submit" name="changeTypeInfo" value="Delete">
                </form>
                <input class="middle sidebar-type" type="submit" name="showType" value="Show All">
                <br>
                <div id="sidebar-type" class="sidebar">
                    <h3><fmt:message key="admin.all_available_types"/></h3>
                    <table style="margin: auto">
                        <th>Type</th>
                        <th>Price</th>
                        <c:forEach items="${requestScope.allTypeInfo}" var="type">
                            <tr>
                                <td>${type.name}</td>
                                <td>${type.price}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <%--=================================================
                           Carriage settings
                ==================================================--%>
                <h3><fmt:message key="admin.inside_transport_information"/></h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeCarrSeat">
                    <input type="hidden" name="checkVal" value="3">

                    <div class="edit-tag"><fmt:message key="admin.train_number"/> :</div>
                    <select name="tagTrains">
                        <c:forEach items="${requestScope.allTrainInfo}" var="train">
                            <option value="${train.number}">${train.number}</option>
                        </c:forEach>
                    </select>

                    <div class="edit-tag"><fmt:message key="admin.number_of_carr"/> :</div>
                    <input id="rangeCarr" class="slider" type="range" min="1" max="20"
                           value="5" name="carriageCount">
                    <output for="rangeCarr" class="countRangeCarr">5</output>

                    <div class="edit-tag"><fmt:message key="admin.number_of_seat"/> :</div>
                    <input id="rangeSeat" class="slider" type="range" min="1" max="45"
                           value="45" name="seatCount">
                    <output for="rangeCarr" class="countRangeSeat">45</output>


                    <div class="edit-tag"><fmt:message key="admin.carr_type"/> :</div>
                    <select name="tagTypes">
                        <c:forEach items="${requestScope.allTypeInfo}" var="type">
                            <option value="${type.name}">${type.name}</option>
                        </c:forEach>
                    </select>

                    <input class="normal" type="submit" name="changeTrainInfo" value="Save">
                    <input class="danger" type="submit" name="changeTrainInfo" value="Delete">
                </form>
                <input class="middle sidebar-train-stat" type="submit" name="showTrainStatistic" value="Show All">
                <br>
                <div id="sidebar-train-stat" class="sidebar">
                    <h3><fmt:message key="admin.all_available_train_statistic"/></h3>
                    <table style="margin: auto">
                        <th>Train</th>
                        <th><fmt:message key="admin.number_of_carr"/></th>
                        <th><fmt:message key="admin.number_of_seat"/></th>
                        <c:forEach items="${requestScope.allTrainStatInfo}" var="trStat">
                            <tr>
                                <td>${trStat.trainNumber}</td>
                                <td>${trStat.countCarriages}</td>
                                <td>${trStat.countSeats}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>


            <input type="radio" name="tabs" id="tab_4" value=4
                   <c:if test="${sessionScope.tab == 4}">checked</c:if>
            />
            <label for="tab_4"><fmt:message key="admin.routes"/></label>
            <div class="tab">
                <%--=================================================
                             Route Settings
                ==================================================--%>
                <h3><fmt:message key="admin.routes_info"/></h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeRoute">
                    <input type="hidden" name="checkVal" value="4">

                    <div id="myDIV" class="header">
                        <h2><fmt:message key="admin.add_station_to_new_route"/> :</h2>
                        <%--<input type="" id="myInput" placeholder="Title...">--%>
                        <select name="tagTypes" id="myInput">
                            <c:forEach items="${requestScope.allStationInfo}" var="station">
                                <option>${station.name}</option>
                            </c:forEach>
                        </select>
                        <input type="datetime-local" id="time-station" name="time-station" required
                               style="width: 210px">
                        <input type="datetime-local" id="time-station-end" name="time-station-end" required
                               style="width: 210px">

                        <input type="text" id="price-value" name="price-value"
                               placeholder="<fmt:message key="admin.price"/>" required value="">

                        <span onclick="newElement()" class="addBtn">
                            <fmt:message key="admin.btn_add"/>
                        </span>
                        <span onclick="removeAllElement()" class="addBtn">
                            <fmt:message key="admin.btn_remove"/>
                        </span>
                    </div>

                    <ul id="myUL" class=".checker">

                    </ul>

                    <input type="hidden" id="listInfoStation" name="listInfoStation" value="">

                    <div class="edit-tag"><fmt:message key="admin.train_number_for_route"/> :</div>
                    <select name="tagTrains">
                        <c:forEach items="${requestScope.allTrainInfo}" var="train">
                            <option value="${train.number}">${train.number}</option>
                        </c:forEach>
                    </select>

                    <input class="normal" type="submit" name="changeRouteInfo" value="Save">
                </form>
                <hr>
                <h3><fmt:message key="admin.route_on_date"/></h3>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeRoute">
                    <input type="hidden" name="checkVal" value="4">

                    <div class="edit-tag"><fmt:message key="admin.route_ID"/> :</div>
                    <input type="text" name="routeId" value="">

                    <div class="edit-tag"><fmt:message key="admin.date_start"/> :</div>
                    <input type="date" name="date-station"
                           style="width: 150px">

                    <div class="edit-tag"><fmt:message key="admin.date_end"/> :</div>
                    <input type="date" name="date-station-end"
                           style="width: 150px">

                    <button class="input-sbt" type="submit" name="changeRouteInfo" value="SaveRoutesDate">
                        <fmt:message key="admin.btn_save"/>
                    </button>
                    <input class="danger" type="submit" name="changeRouteInfo" value="Delete">
                </form>


                <input class="middle sidebar-route-stat" type="submit" name="showRoute" value="Show All">
                <br>
                <div id="sidebar-route-stat" class="sidebar">
                    <h3><fmt:message key="admin.all_routes"/></h3>
                    <table>
                        <th><fmt:message key="admin.route_ID"/></th>
                        <th><fmt:message key="admin.station_list"/></th>
                        <c:forEach items="${requestScope.allRouteInfo}" var="route">
                            <tr>
                                <td>${route.idRoute}</td>
                                <td>
                                    <ul>
                                        <c:forEach items="${route.stationList}" var="station">
                                            <li>${station.name}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>

        </div>
    </div>
</section>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
<script src="style/js/show_button.js" type="text/javascript"></script>
<script src="style/js/range_slider.js" type="text/javascript"></script>
<script src="style/js/check_stations.js" type="text/javascript"></script>
</html>
