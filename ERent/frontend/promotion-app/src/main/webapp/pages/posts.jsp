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

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        PostDTO post = new PostDTO(0, title, content, LocalDateTime.now());
        PostDAO.insert(post);
        response.sendRedirect("posts.jsp");
        return;
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
            <a href="home.jsp?action=logout" onclick="return confirm('Are you sure you want to logout?')">
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

                    <form method="post">
                        <div class="modal-body form-container">
                            <input type="hidden" name="action" value="create"/>

                            <div class="form-row">
                                <div>
                                    <label class="field-label">Title</label>
                                    <input type="text" name="title" class="form-control full-width" placeholder="Title" required minlength="2">
                                </div>
                            </div>

                            <div class="form-row">
                                <div>
                                    <label class="field-label">Content</label>
                                    <textarea name="content" class="form-control full-width" rows="4" placeholder="Content" required minlength="5"></textarea>
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


    </main>
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
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
