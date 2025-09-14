<%@ page import="org.unibl.etf.ip.erent.dto.ManagerDTO" %>
<%@ page import="org.unibl.etf.ip.erent.dao.PromotionDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.PromotionDTO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>

<%
    ManagerDTO manager = (ManagerDTO) session.getAttribute("manager");
    if (manager == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    List<PromotionDTO> promotions = PromotionDAO.getAll();
%>

<!DOCTYPE html>
<html>
<head>
    <title>eRent</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/promotions.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<div class="admin-layout">
    <aside class="sidebar">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/images/logo.png" class="brand-logo" alt="eRent"/>
            <div class="brand-name">eRent</div>
            <div class="brand-tagline">Easy. Electric. Everywhere.</div>
        </div>

        <nav class="menu">
            <a href="posts.jsp">
                <span class="material-icons">article</span>
                <span>Posts</span>
            </a>
            <a href="promotions.jsp" class="active">
                <span class="material-icons">local_offer</span>
                <span>Promotions</span>
            </a>
        </nav>

        <div class="bottom">
            <a href="home.jsp?action=logout" onclick="return confirm('Are you sure you want to logout?')">
                <span class="material-icons">logout</span>
                <span>Logout</span>
            </a>
        </div>
    </aside>

    <main class="content">
        <header class="topbar">
            <div class="dashboard-title">Manager dashboard - Promotions management</div>
            <div class="spacer"></div>
            <div class="user-section">
                <img src="${pageContext.request.contextPath}/images/admin.png" alt="Manager" class="user-avatar">
            </div>
        </header>

        <div class="page-container">

            <div class="toolbar-card">
                <div class="search-field small">
                    <span class="material-icons search-icon">search</span>
                    <input type="text" id="searchInput" placeholder="Search...">
                </div>

                <div class="actions">
                    <a href="create-promotion.jsp" class="btn-primary">
                        <span class="material-icons">add</span>
                        Add promotion
                    </a>
                </div>
            </div>

            <div class="posts-list" id="promotionsList">
                <% for (PromotionDTO p : promotions) { %>
                <div class="card promo-card">
                    <h3>
                        <span class="material-icons">local_offer</span>
                        <%= p.getTitle() %>
                    </h3>
                    <p><%= p.getDescription() %></p>
                    <div class="date-range">
                        <span class="material-icons">event</span>
                        <%= p.getStartDate().format(formatter) %> - <%= p.getEndDate().format(formatter) %>
                    </div>
                    <div class="created-at">
                        <%= p.getCreatedAt().format(formatter) %>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
    </main>
</div>

<script>
    const searchInput = document.getElementById("searchInput");
    const promotionsList = document.getElementById("promotionsList");
    const promos = promotionsList.getElementsByClassName("promo-card");

    searchInput.addEventListener("input", function() {
        const filter = this.value.toLowerCase();
        for (let i = 0; i < promos.length; i++) {
            let title = promos[i].getElementsByTagName("h3")[0].innerText.toLowerCase();
            let desc = promos[i].getElementsByTagName("p")[0].innerText.toLowerCase();
            if (title.includes(filter) || desc.includes(filter)) {
                promos[i].style.display = "";
            } else {
                promos[i].style.display = "none";
            }
        }
    });
</script>
</body>
</html>
