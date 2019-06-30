<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<c:set var="title" value="Main page" scope="page"/>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<%@ include file="/WEB-INF/static/header.jsp" %>

<main>
    <section class="presentation">
        <div class="introduction">
            <div class="intro-text">
                <h1>Facilities and services</h1>
                <p>
                    Book a group study room. Print, copy, scan and pay.
                    Technology and wifi access.
                    Disability services and adaptive technology
                </p>
            </div>
            <div class="cta">
                <form action="login" method="get">
                    <input type="hidden" name="action" value="register">
                    <button class="cta-reg login-js">
                        Create Account
                    </button>
                </form>
                <form action="login" method="get">
                    <input type="hidden" name="action" value="login">
                    <button class="cta-login login-js">
                        Login
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