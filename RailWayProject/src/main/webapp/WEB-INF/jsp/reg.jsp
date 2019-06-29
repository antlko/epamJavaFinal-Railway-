<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<c:set var="title" value="Registation page" scope="page"/>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<%@ include file="/WEB-INF/static/header.jsp" %>

<div class="login-panel-bg">
    <div class="login-panel-content">
        <img src="style/img/new_logo.svg" height="80" width="80" alt="">
        <form action="login" method="POST">
            <%--=================================================
                Action : 'createUser' will create new user
            ==================================================--%>
            <input type="hidden" name="action" value="createUser">

            <input type="text" name="login" placeholder="Login">
            <input type="password" name="password" placeholder="Password">
            <input type="email" name="email" placeholder="Email">
            <input type="submit" value="Submit"/>
        </form>
        <a href="login?action=login">Have account? Login!</a>
    </div>
</div>

<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
</html>
