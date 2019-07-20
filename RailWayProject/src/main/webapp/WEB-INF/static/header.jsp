<%--===================================================================
                         HEADER
===================================================================--%>
<header>
    <div class="logo-container">
        <img src="style/img/new_logo.svg" height="50" width="50"/>
        <h4 class="logo">Train-Too</h4>
    </div>
    <%--===================================================================
                Site naviagation block
    ===================================================================--%>
    <nav>
        <ul class="nav-links">
            <li><a class="nav-link" href="index.jsp"><fmt:message key="header.home"/></a></li>
            <li><a class="nav-link" href="booking"><fmt:message key="header.ticket"/></a></li>
            <li><a class="nav-link" href="contact"><fmt:message key="header.contact"/></a></li>
        </ul>
    </nav>

    <%--===================================================================
                Change language block
    ===================================================================--%>
    <div class="cart login-js">
        <div class="lang">
            <a href="?pageLocale=ru">RU</a>
            <a href="?pageLocale=en">EN</a>
        </div>
        <c:if test="${not empty sessionScope.user}">
            <a href="account">
                    ${sessionScope.user.login}
            </a>
            <a href="account?action=logout">
                <img src="style/img/exit.svg" height="30" width="30"/>
            </a>
        </c:if>
        <c:if test="${empty sessionScope.user}">
            <a href="account">
                <img src="style/img/login.svg" height="30" width="30"/>
            </a>
        </c:if>
    </div>

</header>