<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ taglib prefix="au" uri="/WEB-INF/tld/auth_secure.tld" %>

<%--===================================================================
              Checking for localisaton
===================================================================--%>
<c:if test="${empty sessionScope.localize}">
    <fmt:setLocale value="${cookie['localize'].value}"/>
</c:if>
<c:if test="${not empty sessionScope.localize}">
    <fmt:setLocale value="${sessionScope.localize}"/>
</c:if>
<fmt:setBundle basename="messages"/>

<html>

<c:set var="title" value="Booking" scope="page"/>
<%--===================================================================
              Including HEAD
===================================================================--%>
<%@ include file="/WEB-INF/static/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<%--===================================================================
              Cheking user logged status
===================================================================--%>
<au:AuthSecure/>
<%--===================================================================
              Including HEADER
===================================================================--%>
<%@ include file="/WEB-INF/static/header.jsp" %>
<section class="content">
    <%--===================================================================
                  Error message field
    ===================================================================--%>
    <h:errorValid error="${sessionScope.errorMessage}"/>

    <div class="block-content">
        <h1>Ordering page...</h1>
    </div>
    <div class="block-content">
        <h1>Bank operations...</h1>
    </div>
    <c:redirect url="/account"/>
</section>
<%--===================================================================
              Including FOOTER
===================================================================--%>
<%@ include file="/WEB-INF/static/footer.jsp" %>
</body>
</html>
