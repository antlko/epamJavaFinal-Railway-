<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<c:set var="title" value="Main page" scope="page"/>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<au:AuthSecure/>
<%@ include file="/WEB-INF/static/header.jsp" %>

<main>
    <section class="presentation">
        <div class="introduction">
            <div class="intro-text">
                <h1><fmt:message key="label.message_preview.header"/></h1>
                <p>
                    <fmt:message key="label.message_preview"/>
                </p>
            </div>
            <div class="cta">
                <form action="login" method="get">
                    <input type="hidden" name="action" value="register">
                    <button class="cta-reg login-js">
                        <fmt:message key="label.reg"/>
                    </button>
                </form>
                <form action="login" method="get">
                    <input type="hidden" name="action" value="login">
                    <button class="cta-login login-js">
                        <fmt:message key="label.sign_in"/>
                    </button>
                </form>
            </div>
        </div>
        <div class="cover">
            <img src="style/img/tr_main.jpg" width="650"/>
        </div>
    </section>
</main>

<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
</html>