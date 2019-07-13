<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag dynamic-attributes="map" %>
<p>These are the dynamic attributes:</p>
<ol>
    <c:forEach var="item" items="${map}">
        <li>${item.key}</li </c:forEach>
</ol>