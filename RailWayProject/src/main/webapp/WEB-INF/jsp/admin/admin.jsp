<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="au" uri="/WEB-INF/tld/auth_secure.tld" %>

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
        <h1>Admin panel</h1>
    </div>
    <div class="tabs-content">
        <div class="tabs">
            <input type="radio" name="tabs" id="tab_1" value=1
                   <c:if test="${sessionScope.tab == 1 or empty sessionScope.tab}">checked</c:if>
            />
            <label for="tab_1">Personal</label>
            <div class="tab">
                <div class="login-panel-bg">
                    <div class="login-panel-content">
                        <h3>Change user role:</h3>
                        <form action="admin">
                            <input type="hidden" name="action" value="changeUser">
                            <input type="hidden" name="checkVal" value="1">

                            <div class="edit-tag">User login :</div>
                            <input type="text" name="loginUser">

                            <div class="edit-tag">Role :</div>
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
            <label for="tab_2">Stations</label>
            <div class="tab">
                <%--=================================================
                               Country Settings
                    ==================================================--%>
                <h3>Country info</h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeCountry">
                    <input type="hidden" name="checkVal" value="2">

                    <div class="edit-tag">Country :</div>
                    <input type="text" name="countryName">
                    <input class="normal" type="submit" name="changeCountryInfo" value="Save">
                    <input class="danger" type="submit" name="changeCountryInfo" value="Delete">
                </form>
                <input class="middle sidebar-country" type="submit" name="showCountry" value="Show All">
                <br>
                <div id="sidebar-country" class="sidebar">
                    <h3>All available country</h3>
                    <c:forEach items="${requestScope.allCountryInfo}" var="country">
                        <li>${country.name}</li>
                    </c:forEach>
                </div>
                <%--=================================================
                              City Settings
                    ==================================================--%>
                <h3>Cities info</h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeCity">
                    <input type="hidden" name="checkVal" value="2">

                    <div class="edit-tag">City :</div>
                    <input type="text" name="cityName">

                    <div class="edit-tag">Country :</div>
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
                    <h3>All available cities</h3>
                    <c:forEach items="${requestScope.allCityInfo}" var="city">
                        <li>${city.name}</li>
                    </c:forEach>
                </div>
                <%--=================================================
                           Station settings
                ==================================================--%>
                <h3>Stations info</h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeStation">
                    <input type="hidden" name="checkVal" value="2">

                    <div class="edit-tag">Station :</div>
                    <input type="text" name="stationName">

                    <div class="edit-tag">City :</div>
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
                    <h3>All available station</h3>
                    <c:forEach items="${requestScope.allStationInfo}" var="station">
                        <li>${station.name}</li>
                    </c:forEach>
                </div>
            </div>

            <input type="radio" name="tabs" id="tab_3" value=3
                   <c:if test="${sessionScope.tab == 3}">checked</c:if>
            />
            <label for="tab_3">Trains</label>
            <div class="tab">
                <%--=================================================
                           Train settings
                ==================================================--%>
                <h3>Train info</h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeTrain">
                    <input type="hidden" name="checkVal" value="3">

                    <div class="edit-tag">Train number :</div>
                    <input type="text" name="trainNumber">

                    <input class="normal" type="submit" name="changeTrainInfo" value="Save">
                    <input class="danger" type="submit" name="changeTrainInfo" value="Delete">
                </form>
                <input class="middle sidebar-train" type="submit" name="showTrain" value="Show All">
                <br>
                <div id="sidebar-train" class="sidebar">
                    <h3>All available train</h3>
                    <c:forEach items="${requestScope.allTrainInfo}" var="train">
                        <li>${train.number}</li>
                    </c:forEach>
                </div>
                <%--=================================================
                   Carriage-type settings
                 ==================================================--%>
                <h3>Carriage-type info</h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeType">
                    <input type="hidden" name="checkVal" value="3">

                    <div class="edit-tag">Type name :</div>
                    <input type="text" name="typeName">

                    <div class="edit-tag">Type price :</div>
                    <input type="text" name="typePrice">

                    <input class="normal" type="submit" name="changeTypeInfo" value="Save">
                    <input class="danger" type="submit" name="changeTypeInfo" value="Delete">
                </form>
                <input class="middle sidebar-type" type="submit" name="showType" value="Show All">
                <br>
                <div id="sidebar-type" class="sidebar">
                    <h3>All available train</h3>
                    <c:forEach items="${requestScope.allTypeInfo}" var="type">
                        <li>${type.name}</li>
                    </c:forEach>
                </div>
                <%--=================================================
                           Carriage settings
                ==================================================--%>
                <h3>Inside transport information</h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeTrain">
                    <input type="hidden" name="checkVal" value="3">

                    <div class="edit-tag">Train number :</div>
                    <select name="tagTypes">
                        <c:forEach items="${requestScope.allTrainInfo}" var="train">
                            <option value="${train.number}">${train.number}</option>
                        </c:forEach>
                    </select>

                    <div class="edit-tag">Number of carriage :</div>
                    <input id="rangeCarr" class="slider" type="range" min="1" max="20" name="carriageCount">
                    <output for="rangeCarr" class="countRangeCarr">10</output>

                    <div class="edit-tag">Number of seats :</div>
                    <input id="rangeSeat" class="slider" type="range" min="1" max="45" name="seatCount">
                    <output for="rangeCarr" class="countRangeSeat">45</output>


                    <div class="edit-tag">Carriage type :</div>
                    <select name="tagTypes">
                        <c:forEach items="${requestScope.allTypeInfo}" var="city">
                            <option value="${type.name}">${type.name}</option>
                        </c:forEach>
                    </select>

                    <input class="normal" type="submit" name="changeTrainInfo" value="Save">
                    <input class="danger" type="submit" name="changeTrainInfo" value="Delete">
                </form>
                <input class="middle sidebar-station" type="submit" name="showStation" value="Show All">
                <br>
                <div id="sidebar-train" class="sidebar">
                    <h3>All available train</h3>
                    <c:forEach items="${requestScope.allStationInfo}" var="station">
                        <li>${station.name}</li>
                    </c:forEach>
                </div>
            </div>

            <input type="radio" name="tabs" id="tab_4" value=4
                   <c:if test="${sessionScope.tab == 4}">checked</c:if>
            />
            <label for="tab_4">Routes</label>
            <div class="tab">

            </div>
        </div>
    </div>
</section>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
<script src="style/js/show_button.js" type="text/javascript"></script>
<script src="style/js/range_slider.js" type="text/javascript"></script>
</html>
