<header>
    <div class="logo-container">
        <img src="style/img/new_logo.svg" height="50" width="50"/>
        <h4 class="logo">Train-Too</h4>
    </div>
    <nav>
        <ul class="nav-links">
            <li><a class="nav-link" href="index.jsp">Home</a></li>
            <li><a class="nav-link" href="booking">Tickets</a></li>
            <li><a class="nav-link" href="contact">Contact</a></li>
        </ul>
    </nav>

    <div class="cart login-js">
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