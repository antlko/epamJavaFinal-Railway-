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

<c:set var="title" value="Registation page" scope="page"/>
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

<div class="login-panel-bg">
    <div class="login-panel-content">
        <img src="style/img/new_logo.svg" height="80" width="80" alt="">
        <%--===================================================================
                    Error message field
        ===================================================================--%>
        <h:errorValid error="${sessionScope.errorMessage}"/>

        <form action="login" method="POST">
            <%--=================================================
                Action : 'createUser' will create new user
            ==================================================--%>
            <input type="hidden" name="action" value="createUser">
            <input type="hidden" name="errorPage" value="/login?action=register">

            <input type="text" name="login" placeholder="<fmt:message key="auth.login"/>" required>
            <input type="password" name="password" placeholder="<fmt:message key="auth.password"/>" required>
            <input type="email" name="email" placeholder="<fmt:message key="auth.email"/>" required>
            <input class="normal" type="submit" value="<fmt:message key="label.sign_in"/>"/>
        </form>
        <a href="login?action=login"><fmt:message key="auth.reg_message"/></a>
    </div>
</div>
<%--===================================================================
                  Including FOOTER
===================================================================--%>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
</html>
