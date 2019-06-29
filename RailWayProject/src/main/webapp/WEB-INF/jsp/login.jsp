<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<c:set var="title" value="Login page" scope="page"/>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<%@ include file="/WEB-INF/static/header.jsp" %>

<div class="login-panel-bg">
    <div class="login-panel-content">
        <%--<dib class="close">+</dib>--%>
        <img src="style/img/new_logo.svg" height="80" width="80" alt="">
        <form action="login" method="POST">
            <input type="hidden" name="action" value="account">
            ${requestScope.errorMessage}
            <input type="text" name="login" placeholder="Login">
            <input type="password" name="password" placeholder="Password">
            <input type="submit" value="Submit"/>
        </form>
        <a href="login?action=register">Haven't account? Create!</a>
    </div>
</div>

<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
</html>
