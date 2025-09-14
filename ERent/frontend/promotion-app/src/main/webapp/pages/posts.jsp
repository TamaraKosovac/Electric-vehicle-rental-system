<%@ page import="org.unibl.etf.ip.erent.dto.ManagerDTO" %>
<%@ page import="org.unibl.etf.ip.erent.dao.PostDAO" %>
<%@ page import="org.unibl.etf.ip.erent.dto.PostDTO" %>
<%@ page import="java.time.LocalDateTime" %>
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

    String keyword = request.getParameter("q");
    List<PostDTO> posts;
    if (keyword != null && !keyword.trim().isEmpty()) {
        posts = PostDAO.search(keyword);
    } else {
        posts = PostDAO.getAll();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>eRent</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/posts.css">
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
                <img src="${pageContext.request.contextPath}/images/admin.png" alt="Admin" class="user-avatar">
            </div>
        </header>

        <div class="page-container">
            <h2>Create New Post</h2>
            <form method="post">
                <input type="hidden" name="action" value="create"/>
                <label>Title:</label><br>
                <input type="text" name="title" required><br><br>
                <label>Content:</label><br>
                <textarea name="content" rows="4" cols="40" required></textarea><br><br>
                <button type="submit">Save</button>
            </form>

            <hr>
            <h3>Search Posts</h3>
            <form method="get">
                <input type="text" name="q" placeholder="Enter keyword" value="<%= keyword != null ? keyword : "" %>">
                <button type="submit">Search</button>
            </form>

            <hr>
            <h3>All Posts</h3>
            <ul>
                <% for (PostDTO p : posts) { %>
                <li>
                    <b><%= p.getTitle() %></b><br>
                    <%= p.getContent() %><br>
                    <small><%= p.getCreatedAt() %></small>
                </li>
                <% } %>
            </ul>
        </div>
    </main>
</div>
</body>
</html>
