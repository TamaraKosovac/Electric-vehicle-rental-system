<%@ page import="org.unibl.etf.ip.erent.dto.ClientDTO" %>
<%
    if ("logout".equals(request.getParameter("action"))) {
        session.invalidate();
        response.sendRedirect("login.jsp");
        return;
    }

    ClientDTO client = (ClientDTO) session.getAttribute("client");
    if (client == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String activePage = request.getParameter("activePage");
    if (activePage == null) {
        activePage = "car";
    }

    String pageTitle;
    switch (activePage) {
        case "bike": pageTitle = "Rent a bike"; break;
        case "scooter": pageTitle = "Rent a scooter"; break;
        case "profile": pageTitle = "Edit profile"; break;
        default: pageTitle = "Rent a car";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>eRent</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="admin-layout">
    <aside class="sidebar">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="eRent" class="brand-logo" />
            <span class="brand-name">eRent</span>
            <span class="brand-tagline">Easy. Electric. Everywhere.</span>
        </div>

        <nav class="menu">
            <a href="home.jsp?activePage=car" class="<%= "car".equals(activePage) ? "active" : "" %>">
                <span class="material-icons">electric_car</span>
                <span>Rent a car</span>
            </a>
            <a href="home.jsp?activePage=bike" class="<%= "bike".equals(activePage) ? "active" : "" %>">
                <span class="material-icons">electric_bike</span>
                <span>Rent a bike</span>
            </a>
            <a href="home.jsp?activePage=scooter" class="<%= "scooter".equals(activePage) ? "active" : "" %>">
                <span class="material-icons">electric_scooter</span>
                <span>Rent a scooter</span>
            </a>
            <a href="home.jsp?activePage=profile" class="<%= "profile".equals(activePage) ? "active" : "" %>">
                <span class="material-icons">person</span>
                <span>Profile</span>
            </a>
        </nav>

        <div class="bottom">
            <a href="#" data-bs-toggle="modal" data-bs-target="#logoutModal">
                <span class="material-icons">logout</span>
                <span>Logout</span>
            </a>
        </div>
    </aside>

    <main class="content">
        <header class="topbar">
            <div class="dashboard-title">Client dashboard - <%= pageTitle %></div>
            <div class="spacer"></div>
            <div class="user-section">
                <img src="${pageContext.request.contextPath}/clientphotocontroller?clientId=<%= client.getId() %>"
                     alt="Client"
                     class="user-avatar"
                     onerror="this.src='${pageContext.request.contextPath}/images/client.png'">
            </div>
        </header>

        <div class="page-container">
            <% if ("car".equals(activePage)) { %>
            <jsp:include page="car.jsp" />
            <% } else if ("bike".equals(activePage)) { %>
            <jsp:include page="bike.jsp" />
            <% } else if ("scooter".equals(activePage)) { %>
            <jsp:include page="scooter.jsp" />
            <% } else if ("profile".equals(activePage)) { %>
            <jsp:include page="profile.jsp" />
            <% } %>
        </div>
    </main>
</div>

<div class="modal fade" id="logoutModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content dlg">

            <div class="dlg-header">
                <span class="material-icons">logout</span>
                <h2>Logout</h2>
            </div>

            <div class="dlg-content">
                <p>Are you sure you want to log out?</p>
            </div>

            <div class="dlg-actions">
                <button type="button" class="btn cancel-btn" data-bs-dismiss="modal">No</button>
                <a href="home.jsp?action=logout" class="btn confirm-btn">Yes</a>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>