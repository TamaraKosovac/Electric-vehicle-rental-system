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

    if ("POST".equalsIgnoreCase(request.getMethod())
            && "create".equals(request.getParameter("action"))) {
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            LocalDateTime startDate = LocalDateTime.parse(request.getParameter("startDate"));
            LocalDateTime endDate = LocalDateTime.parse(request.getParameter("endDate"));

            PromotionDTO promo = new PromotionDTO(0, title, description, startDate, endDate, LocalDateTime.now());
            PromotionDAO.insert(promo);
            response.sendRedirect("promotions.jsp?status=success");
            return;
        } catch (Exception e) {
            response.sendRedirect("promotions.jsp?status=error");
            return;
        }
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
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
            <a href="#" data-bs-toggle="modal" data-bs-target="#logoutModal">
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
                <img src="${pageContext.request.contextPath}/images/manager.png" alt="Manager" class="user-avatar">
            </div>
        </header>

        <div class="page-container">

            <div class="toolbar-card">
                <div class="search-field small">
                    <span class="material-icons search-icon">search</span>
                    <input type="text" id="searchInput" placeholder="Search...">
                </div>

                <div class="actions">
                    <button type="button" class="btn-primary" data-bs-toggle="modal" data-bs-target="#createPromotionModal">
                        <span class="material-icons">add</span>
                        Add promotion
                    </button>
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

        <div class="modal fade" id="createPromotionModal" tabindex="-1" aria-labelledby="createPromotionModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="createPromotionModalLabel">Add promotion</h5>
                    </div>

                    <form method="post" onsubmit="return validatePromotionForm(this)">
                        <div class="modal-body">
                            <input type="hidden" name="action" value="create"/>

                            <div class="mb-3">
                                <label class="form-label">Title</label>
                                <input type="text" name="title" class="form-control" placeholder="Title"
                                       required minlength="2" maxlength="100"
                                       title="Title must be between 2 and 100 characters">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Start date</label>
                                <div class="date-input-wrapper">
                                    <input type="datetime-local"
                                           class="form-control"
                                           name="startDate"
                                           id="startDate"
                                           required
                                           title="Please select a valid start date and time">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">End date</label>
                                <div class="date-input-wrapper">
                                    <input type="datetime-local"
                                           class="form-control"
                                           name="endDate"
                                           id="endDate"
                                           required
                                           title="Please select a valid end date and time">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Description</label>
                                <textarea name="description" class="form-control" placeholder="Description" rows="3"
                                          required minlength="5" maxlength="500"
                                          title="Description must be between 5 and 500 characters"></textarea>
                            </div>
                        </div>

                        <div class="modal-footer dialog-actions">
                            <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div id="snackbar-success" class="snackbar snackbar-success">Promotion added successfully!</div>
        <div id="snackbar-error" class="snackbar snackbar-error">Failed to add promotion.</div>

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

<script>
    const searchInput = document.getElementById("searchInput");
    const promotionsList = document.getElementById("promotionsList");
    const promos = promotionsList.getElementsByClassName("promo-card");

    searchInput.addEventListener("input", function() {
        const filter = this.value.toLowerCase();
        for (let i = 0; i < promos.length; i++) {
            let title = promos[i].getElementsByTagName("h3")[0].innerText.toLowerCase();
            let desc = promos[i].getElementsByTagName("p")[0].innerText.toLowerCase();
            promos[i].style.display = (title.includes(filter) || desc.includes(filter)) ? "" : "none";
        }
    });

    window.addEventListener("DOMContentLoaded", () => {
        const urlParams = new URLSearchParams(window.location.search);
        const status = urlParams.get("status");

        if (status === "success") {
            showSnackbar("snackbar-success");
        } else if (status === "error") {
            showSnackbar("snackbar-error");
        }
    });

    function showSnackbar(id) {
        const snackbar = document.getElementById(id);
        snackbar.classList.add("show");
        setTimeout(() => snackbar.classList.remove("show"), 3000);
    }

    function validatePromotionForm(form) {
        const title = form.title.value.trim();
        const description = form.description.value.trim();
        const startDate = form.startDate.value;
        const endDate = form.endDate.value;

        if (title.length < 2 || title.length > 100) {
            alert("Title must be between 2 and 100 characters.");
            form.title.focus();
            return false;
        }

        if (description.length < 5 || description.length > 500) {
            alert("Description must be between 5 and 500 characters.");
            form.description.focus();
            return false;
        }

        if (!startDate) {
            alert("Please select a start date.");
            form.startDate.focus();
            return false;
        }

        if (!endDate) {
            alert("Please select an end date.");
            form.endDate.focus();
            return false;
        }

        if (new Date(endDate) <= new Date(startDate)) {
            alert("End date must be after start date.");
            form.endDate.focus();
            return false;
        }

        return true;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>