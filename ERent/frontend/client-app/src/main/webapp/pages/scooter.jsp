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

    request.setAttribute("pageTitle", "Rent a Scooter");
    request.setAttribute("activePage", "scooter");
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= request.getAttribute("pageTitle") %> - eRent</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<div class="admin-layout">
    <aside class="sidebar">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="eRent" class="brand-logo" />
            <span class="brand-name">eRent</span>
            <span class="brand-tagline">Easy. Electric. Everywhere.</span>
        </div>

        <div class="menu">
            <a href="car.jsp" class="<%= "car".equals(request.getAttribute("activePage")) ? "active" : "" %>">
                <span class="material-icons">electric_car</span>
                <span>Rent a Car</span>
            </a>
            <a href="bike.jsp" class="<%= "bike".equals(request.getAttribute("activePage")) ? "active" : "" %>">
                <span class="material-icons">electric_bike</span>
                <span>Rent a Bike</span>
            </a>
            <a href="scooter.jsp" class="<%= "scooter".equals(request.getAttribute("activePage")) ? "active" : "" %>">
                <span class="material-icons">electric_scooter</span>
                <span>Rent a Scooter</span>
            </a>
            <a href="profile.jsp" class="<%= "profile".equals(request.getAttribute("activePage")) ? "active" : "" %>">
                <span class="material-icons">person</span>
                <span>Profile</span>
            </a>
        </div>

        <div class="bottom">
            <a href="home.jsp?action=logout">
                <span class="material-icons">logout</span>
                <span>Logout</span>
            </a>
        </div>
    </aside>

    <main class="content">
        <header class="topbar">
            <div class="dashboard-title"><%= request.getAttribute("pageTitle") %></div>
            <div class="spacer"></div>
            <div class="user-section">
                <img src="${pageContext.request.contextPath}/images/client.png" alt="Client" class="user-avatar">
            </div>
        </header>

        <div class="page-container">
            <h3>Select your scooter rental options here</h3>
            <p>Form for choosing location, payment, and confirming the scooter rental will go here.</p>
        </div>
    </main>
</div>
</body>
</html>
