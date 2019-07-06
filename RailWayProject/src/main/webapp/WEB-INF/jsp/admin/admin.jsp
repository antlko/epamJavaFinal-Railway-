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
            <input type="radio" name="tabs" id="tab_1" checked="checked">
            <label for="tab_1">Personal</label>
            <div class="tab">
                <div class="login-panel-bg">
                    <div class="login-panel-content">
                        <h3>Change user role:</h3>
                        <form action="admin">
                            <input type="hidden" name="action" value="changeUser">

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

            <input type="radio" name="tabs" id="tab_2">
            <label for="tab_2">Stations</label>
            <div class="tab">
                <%--=================================================
                               County Settings
                    ==================================================--%>
                <h3>Country info</h3>
                <hr>
                <form action="admin" method="post">
                    <input type="hidden" name="action" value="changeCountry">

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
            </div>

            <input type="radio" name="tabs" id="tab_3">
            <label for="tab_3">Routes</label>
            <div class="tab">

            </div>

            <input type="radio" name="tabs" id="tab_4">
            <label for="tab_4">Trains</label>
            <div class="tab">

            </div>
        </div>
    </div>
</section>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
<script src="style/js/show_button.js" type="text/javascript"></script>
</html>
