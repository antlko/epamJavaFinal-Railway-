<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>


<%--===================================================================
              Error Page
===================================================================--%>
<h:responceStatus errorNumber="401"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>401</title>
</head>
<body>
<h1>401 - Unauthorized</h1>
<br>The requested page needs a username and a password.
</body>
</html>
