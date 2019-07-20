<%--===================================================================
        Content page of error
===================================================================--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="error" required="true" %>

<c:if test="${not empty error}">
    <div class="block-centered-content-error">
            ${error}
        <c:remove var="errorMessage" scope="session"/>
    </div>
</c:if>