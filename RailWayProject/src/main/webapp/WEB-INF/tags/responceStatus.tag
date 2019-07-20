<%--===================================================================
     Set status of error
===================================================================--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="errorNumber" required="true" %>

<% response.setStatus(Integer.parseInt(errorNumber)); %>