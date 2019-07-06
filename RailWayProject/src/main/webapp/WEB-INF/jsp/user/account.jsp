<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="au" uri="/WEB-INF/tld/auth_secure.tld" %>

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
        <label for="tabone">History</label>
        <div class="tab">
            <table border="1">
                <th>Date</th>
                <th>Full name</th>
                <th>Train</th>
                <th>Carriage</th>
                <th>Seat</th>
                <th>From</th>
                <th>Destination</th>
                <th>Document</th>
                <th>Refuse</th>
                <c:forEach items="${sessionScope.userChecks}" var="check" varStatus="loop">
                    <tr>
                        <td>${check.dateEnd}</td>
                        <td>${check.userInitial}</td>
                        <td>${check.numTrain}</td>
                        <td>${check.numCarriage}</td>
                        <td>${check.numSeat}</td>
                        <td>${check.cityStart}</td>
                        <td>${check.cityEnd}</td>
                        <td>
                            <form action="account" method="get">
                                <input class="normal" type="submit" value="Doc">
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
        <label for="tabtwo">Profile</label>
        <div class="tab">
            <div class="login-panel-bg">
                <div class="login-panel-content">
                    <h3>Hello, ${sessionScope.user.login}!</h3>
                    <form action="account" method="post">
                        <input type="hidden" name="action" value="updatePersonal">

                        <div class="edit-tag">Surname :</div>
                        <input type="text" name="Surname" value="${sessionScope.user.surname}">
                        <div class="edit-tag">Name :</div>
                        <input type="text" name="Name" value="${sessionScope.user.name}">
                        <div class="edit-tag">Email :</div>
                        <input type="text" name="Email" value="${sessionScope.user.email}">
                        <input class="normal" type="submit" value="Save Info">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
</html>
