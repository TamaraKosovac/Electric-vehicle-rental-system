<%@ page import="org.unibl.etf.ip.erent.dto.ManagerDTO" %>
<%@ page import="org.unibl.etf.ip.erent.dao.PostDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.PostDTO" %>
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
            String content = request.getParameter("content");

            PostDTO post = new PostDTO(0, title, content, LocalDateTime.now());
            PostDAO.insert(post);
            response.sendRedirect("posts.jsp?status=success");
            return;
        } catch (Exception e) {
            response.sendRedirect("posts.jsp?status=error");
            return;
        }
    }

    List<PostDTO> posts = PostDAO.getAll();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
%>

<!DOCTYPE html>
<html>
<head>
    <title>eRent</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/posts.css">
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
            <a href="posts.jsp" class="active">
                <span class="material-icons">article</span>
                <span>Posts</span>
            </a>
            <a href="promotions.jsp">
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
            <div class="dashboard-title">Manager dashboard - Posts management</div>
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
                    <button type="button" class="btn-primary" data-bs-toggle="modal" data-bs-target="#createPostModal">
                        <span class="material-icons">add</span>
                        Add post
                    </button>
                </div>
            </div>

            <div class="posts-list" id="postsList">
                <% for (PostDTO p : posts) { %>
                <div class="card post-card">
                    <h3>
                        <span class="material-icons">article</span>
                        <%= p.getTitle() %>
                    </h3>
                    <p><%= p.getContent() %></p>
                    <small>
                        <%= p.getCreatedAt().format(formatter) %>
                    </small>
                </div>
                <% } %>
            </div>

        </div>
        <div class="modal fade" id="createPostModal" tabindex="-1" aria-labelledby="createPostModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="createPostModalLabel">Add post</h5>
                    </div>

                    <form method="post" onsubmit="return validatePostForm(this)">
                        <div class="modal-body form-container">
                            <input type="hidden" name="action" value="create"/>

                            <div class="form-row">
                                <div>
                                    <label class="field-label">Title</label>
                                    <input type="text" name="title" class="form-control full-width"
                                           placeholder="Title" required minlength="2" maxlength="100"
                                           title="Title must be between 2 and 100 characters">
                                </div>
                            </div>

                            <div class="form-row">
                                <div>
                                    <label class="field-label">Content</label>
                                    <textarea name="content" class="form-control full-width" rows="4"
                                              placeholder="Content" required minlength="5" maxlength="1000"
                                              title="Content must be between 5 and 1000 characters"></textarea>
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer dialog-actions">
                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div id="snackbar-success" class="snackbar snackbar-success">Post added successfully!</div>
        <div id="snackbar-error" class="snackbar snackbar-error">Failed to add post.</div>

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
    const postsList = document.getElementById("postsList");
    const posts = postsList.getElementsByClassName("post-card");

    searchInput.addEventListener("input", function() {
        const filter = this.value.toLowerCase();
        for (let i = 0; i < posts.length; i++) {
            let title = posts[i].getElementsByTagName("h3")[0].innerText.toLowerCase();
            let content = posts[i].getElementsByTagName("p")[0].innerText.toLowerCase();
            if (title.includes(filter) || content.includes(filter)) {
                posts[i].style.display = "";
            } else {
                posts[i].style.display = "none";
            }
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

    function validatePostForm(form) {
        const title = form.title.value.trim();
        const content = form.content.value.trim();

        if (title.length < 2 || title.length > 100) {
            alert("Title must be between 2 and 100 characters.");
            form.title.focus();
            return false;
        }

        if (content.length < 5 || content.length > 1000) {
            alert("Content must be between 5 and 1000 characters.");
            form.content.focus();
            return false;
        }

        return true;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>